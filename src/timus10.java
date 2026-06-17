import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class timus10 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st = null;

    // Метод для быстрого и надежного посимвольного/потокового чтения токенов
    private static String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null) return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public static void main(String[] args) throws IOException {
        String kStr = nextToken();
        if (kStr == null) return;
        int k = Integer.parseInt(kStr);

        // Чтение предпочтений мальчиков
        int[] B = new int[k + 1];
        for (int i = 1; i <= k; i++) {
            B[i] = Integer.parseInt(nextToken());
        }

        // Чтение предпочтений девочек
        int[] G = new int[k + 1];
        for (int i = 1; i <= k; i++) {
            G[i] = Integer.parseInt(nextToken());
        }

        // invG[b] хранит номер девочки, которой нравится мальчик b
        int[] invG = new int[k + 1];
        for (int g = 1; g <= k; g++) {
            if (G[g] != 0) {
                invG[G[g]] = g;
            }
        }

        // invB[g] хранит номер мальчика, которому нравится девочка g
        int[] invB = new int[k + 1];
        for (int b = 1; b <= k; b++) {
            if (B[b] != 0) {
                invB[B[b]] = b;
            }
        }

        // Подсчет количества доступных опций для каждого влюбленного мальчика
        int[] choices = new int[k + 1];
        int[] Q = new int[k + 5];
        int head = 0, tail = 0;

        for (int b = 1; b <= k; b++) {
            if (B[b] != 0) {
                if (invG[b] == 0) {
                    choices[b] = 1; // Только та, которую любит он
                    Q[tail++] = b;
                } else {
                    choices[b] = 2; // И та, которую любит он, и та, что любит его
                }
            }
        }

        int[] matchB = new int[k + 1];
        int[] matchG = new int[k + 1];

        int startIdx = 1;
        while (true) {
            // Разбираем очередь однозначно определенных пар
            while (head < tail) {
                int b = Q[head++];
                if (matchB[b] != 0) continue;

                int g = 0;
                if (B[b] != 0 && matchG[B[b]] == 0) {
                    g = B[b];
                } else if (invG[b] != 0 && matchG[invG[b]] == 0) {
                    g = invG[b];
                }

                if (g != 0) {
                    matchB[b] = g;
                    matchG[g] = b;

                    // Девочка g занята, обновляем состояние зависимых мальчиков
                    int b_liked = invB[g];
                    if (b_liked != 0 && matchB[b_liked] == 0) {
                        choices[b_liked] = 1;
                        Q[tail++] = b_liked;
                    }

                    int b_likes = G[g];
                    if (b_likes != 0 && matchB[b_likes] == 0) {
                        choices[b_likes]--;
                        if (choices[b_likes] == 1) {
                            Q[tail++] = b_likes;
                        }
                    }
                }
            }

            // Если очередь пуста, ищем любого оставшегося влюбленного и нераспределенного мальчика
            int nextB = -1;
            for (int i = startIdx; i <= k; i++) {
                if (B[i] != 0 && matchB[i] == 0) {
                    nextB = i;
                    startIdx = i + 1;
                    break;
                }
            }

            if (nextB == -1) {
                break; // Все мальчики с обязательствами успешно распределены
            }

            // Инициируем разрыв цикла, принудительно давая ему 1 вариант (девочку, которую он любит)
            choices[nextB] = 1;
            Q[tail++] = nextB;
        }

        // Сбор оставшихся свободных детей (у которых не было взаимных обязательств)
        int[] freeBoys = new int[k + 1];
        int[] freeGirls = new int[k + 1];
        int fbCount = 0;
        int fgCount = 0;

        for (int i = 1; i <= k; i++) {
            if (matchB[i] == 0) {
                freeBoys[fbCount++] = i;
            }
            if (matchG[i] == 0) {
                freeGirls[fgCount++] = i;
            }
        }

        // Соединяем оставшихся свободными мальчиков и девочек без ограничений
        for (int i = 0; i < fbCount; i++) {
            matchB[freeBoys[i]] = freeGirls[i];
        }

        // Быстрый вывод результата на экран
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= k; i++) {
            sb.append(matchB[i]);
            if (i < k) {
                sb.append(" ");
            }
        }
        System.out.println(sb.toString());
    }
}