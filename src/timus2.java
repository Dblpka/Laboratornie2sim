import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class timus2 {

    // Класс для хранения состояния удачи игрока
    static class Player {
        String name;
        long glCount = 0;
        long hfCount = 0;

        Player(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = reader.readLine();
        if (firstLine == null) return;

        StringTokenizer st = new StringTokenizer(firstLine);
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        double A = Double.parseDouble(st.nextToken());
        double B = Double.parseDouble(st.nextToken());

        // Предварительно вычисляем логарифмы для быстрого и точного сравнения
        double logA = Math.log(A);
        double logB = Math.log(B);

        // Все существующие игроки
        Map<String, Player> allPlayers = new HashMap<>();
        // Игроки, которые сейчас онлайн
        Set<String> onlinePlayers = new HashSet<>();

        // Инициализируем администратора admin
        Player admin = new Player("admin");
        allPlayers.put("admin", admin);
        onlinePlayers.add("admin");

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < N; i++) {
            String line = reader.readLine();

            // Парсинг строки вида: [hh:mm:ss] AUTHOR_NICK: /command ...
            int closeBracket = line.indexOf(']');
            int firstColonAfterBracket = line.indexOf(':', closeBracket);

            String author = line.substring(closeBracket + 2, firstColonAfterBracket);
            String rest = line.substring(firstColonAfterBracket + 2);

            if (rest.startsWith("/j ") || rest.startsWith("/join ")) {
                if (onlinePlayers.contains(author)) {
                    output.append("Failed: ").append(author).append(" is already on the server\n");
                } else {
                    onlinePlayers.add(author);
                    if (!allPlayers.containsKey(author)) {
                        allPlayers.put(author, new Player(author));
                    }
                    output.append("Successful\n");
                }
            }
            else if (rest.startsWith("/q ") || rest.startsWith("/quit")) {
                // Предполагается по условию, что /quit идет от авторизованного пользователя
                onlinePlayers.remove(author);
                output.append("Successful\n");
            }
            else if (rest.startsWith("/o ") || rest.startsWith("/online")) {
                output.append("There are ").append(onlinePlayers.size()).append(" players on the server\n");
            }
            else if (rest.startsWith("/s ") || rest.startsWith("/say ")) {
                int spaceIdx = rest.indexOf(' ');
                String msg = rest.substring(spaceIdx + 1);

                int glInMsg = countSubstring(msg, "gl");
                int hfInMsg = countSubstring(msg, "hf");

                // Все игроки онлайн, кроме автора, получают этот текст
                for (String pName : onlinePlayers) {
                    if (!pName.equals(author)) {
                        Player p = allPlayers.get(pName);
                        p.glCount += glInMsg;
                        p.hfCount += hfInMsg;
                    }
                }
                output.append(author).append(": ").append(msg).append("\n");
            }
            else if (rest.startsWith("/w") || rest.startsWith("/write")) {
                // Формат: /w<RECIPIENT> msg или /write<RECIPIENT> msg
                int openAngle = rest.indexOf('<');
                int closeAngle = rest.indexOf('>');
                String recipient = rest.substring(openAngle + 1, closeAngle);
                String msg = rest.substring(closeAngle + 2);

                if (onlinePlayers.contains(recipient)) {
                    int glInMsg = countSubstring(msg, "gl");
                    int hfInMsg = countSubstring(msg, "hf");

                    if (!recipient.equals(author)) {
                        Player p = allPlayers.get(recipient);
                        p.glCount += glInMsg;
                        p.hfCount += hfInMsg;
                    }
                }
                output.append(author).append(" (to ").append(recipient).append("): ").append(msg).append("\n");
            }
            else if (rest.startsWith("/c") || rest.startsWith("/compare")) {
                // Команда сравнения
                int openAngle = rest.indexOf('<');

                if (!author.equals("admin")) {
                    output.append("Failed: you have no such rights\n");
                    continue;
                }

                // Проверяем, это сравнение двух игроков или админа с игроком
                // Формат 1: /c<PLAYER1>-<PLAYER2>
                // Формат 2: /c<PLAYER>
                int dashIdx = rest.indexOf("-<", openAngle);

                if (dashIdx != -1) {
                    // Сравнение PLAYER1 и PLAYER2
                    int closeAngle1 = rest.indexOf('>', openAngle);
                    String p1Name = rest.substring(openAngle + 1, closeAngle1);

                    int openAngle2 = dashIdx + 2;
                    int closeAngle2 = rest.indexOf('>', openAngle2);
                    String p2Name = rest.substring(openAngle2, closeAngle2);

                    if (!onlinePlayers.contains(p1Name)) {
                        output.append("Failed: ").append(p1Name).append(" is not on the server\n");
                    } else if (!onlinePlayers.contains(p2Name)) {
                        output.append("Failed: ").append(p2Name).append(" is not on the server\n");
                    } else {
                        Player p1 = allPlayers.get(p1Name);
                        Player p2 = allPlayers.get(p2Name);

                        double luck1 = p1.glCount * logA - p1.hfCount * logB;
                        double luck2 = p2.glCount * logA - p2.hfCount * logB;

                        // Так как разница существенная (в 1.0001 раза), используем epsilon для точности double
                        if (Math.abs(luck1 - luck2) < 1e-7) {
                            output.append("Their luck is equal\n");
                        } else if (luck1 > luck2) {
                            output.append(p2Name).append(" is a loser\n");
                        } else {
                            output.append(p2Name).append(" is a lucky guy\n");
                        }
                    }
                } else {
                    // Сравнение admin и PLAYER
                    int closeAngle = rest.indexOf('>', openAngle);
                    String pName = rest.substring(openAngle + 1, closeAngle);

                    if (!onlinePlayers.contains(pName)) {
                        output.append("Failed: ").append(pName).append(" is not on the server\n");
                    } else {
                        Player p = allPlayers.get(pName);

                        double luckAdmin = admin.glCount * logA - admin.hfCount * logB;
                        double luckP = p.glCount * logA - p.hfCount * logB;

                        if (Math.abs(luckAdmin - luckP) < 1e-7) {
                            output.append(pName).append(" is good\n");
                        } else if (luckAdmin > luckP) {
                            output.append(pName).append(" is a loser\n");
                        } else {
                            output.append(pName).append(" is a lucky guy\n");
                        }
                    }
                }
            }
        }
        System.out.print(output);
    }

    // Метод подсчета непересекающихся вхождений подстроки
    private static int countSubstring(String str, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}