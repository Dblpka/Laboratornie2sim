import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class zadanie3 {
    private static final String SAVE_PATH = "src/lr11/variant9/movie_news.txt";
    private static final String URL = "https://stopgame.ru/movies/news"; // Пример стабильного ресурса с киноновостями

    public static void main(String[] args) {
        Document doc = null;
        int maxAttempts = 3;
        int attempt = 0;

        System.out.println("Установка соединения с сайтом новостей кино...");

        // Реализация логики автоматического переподключения при ошибках
        while (attempt < maxAttempts) {
            try {
                attempt++;
                doc = Jsoup.connect(URL)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .timeout(5000)
                        .get();
                break; // Если подключение успешно, выходим из цикла попыток
            } catch (IOException e) {
                System.err.printf("Ошибка получения HTML-кода (Попытка %d из %d): %s\n", attempt, maxAttempts, e.getMessage());
                if (attempt < maxAttempts) {
                    System.out.println("Ожидание и попытка переподключения к сайту...");
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        }

        if (doc == null) {
            System.err.println("Не удалось установить связь с сервером после серии попыток. Работа программы завершена.");
            return;
        }

        // Парсинг блоков новостей
        try (FileWriter writer = new FileWriter(SAVE_PATH, false)) {
            Elements newsParent = doc.select("div[data-id]"); // селектор блоков новостей

            System.out.println("\n=== ПОЛУЧЕННЫЕ КИНОНОВОСТИ ===");
            writer.write("=== АКТУАЛЬНЫЕ КИНОНОВОСТИ ===\n\n");

            int count = 0;
            for (Element newsElement : newsParent) {
                Element titleElement = newsElement.select("a").first();
                if (titleElement != null && !titleElement.text().isEmpty()) {
                    count++;
                    String newsText = String.format("%d. %s\n", count, titleElement.text());

                    System.out.print(newsText);
                    writer.write(newsText); // Запись результатов парсинга в файл по заданию
                }
                if (count >= 10) break; // Ограничимся 10 свежими заголовками
            }

            System.out.println("\nДанные успешно сохранены в файл: " + SAVE_PATH);

        } catch (IOException e) {
            System.err.println("Ошибка записи собранных данных в файл: " + e.getMessage());
        }
    }
}