import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class zadanie2 {
    private static final String FILE_PATH = "src/lr11/variant9/movies.json";

    public static void main(String[] args) {
        checkAndCreateJson();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== JSON ПАРСЕР: ФИЛЬМЫ ===");
            System.out.println("1. Показать все фильмы");
            System.out.println("2. Добавить новый фильм");
            System.out.println("3. Найти фильм по режиссеру");
            System.out.println("4. Удалить фильм по названию");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showAllMovies();
                case 2 -> {
                    System.out.print("Введите название фильма: ");
                    String title = scanner.nextLine();
                    System.out.print("Введите режиссёра: ");
                    String director = scanner.nextLine();
                    System.out.print("Введите год: ");
                    long year = scanner.nextLong();
                    addMovie(title, director, year);
                }
                case 3 -> {
                    System.out.print("Введите имя режиссёра: ");
                    String director = scanner.nextLine();
                    searchByDirector(director);
                }
                case 4 -> {
                    System.out.print("Введите название фильма для удаления: ");
                    String title = scanner.nextLine();
                    deleteMovie(title);
                }
                case 5 -> {
                    return;
                }
            }
        }
    }

    private static void checkAndCreateJson() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            JSONObject root = new JSONObject();
            root.put("movies", new JSONArray());
            saveJson(root);
        }
    }

    private static JSONObject getRootObject() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    private static void saveJson(JSONObject root) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showAllMovies() {
        JSONObject root = getRootObject();
        JSONArray movies = (JSONArray) root.get("movies");
        if (movies == null || movies.isEmpty()) {
            System.out.println("Список фильмов в JSON пуст.");
            return;
        }
        movies.forEach(obj -> {
            JSONObject movie = (JSONObject) obj;
            System.out.printf("Фильм: \"%s\" | Режиссер: %s | Год: %s\n",
                    movie.get("title"), movie.get("author"), movie.get("year"));
        });
    }

    private static void addMovie(String title, String director, long year) {
        JSONObject root = getRootObject();
        JSONArray movies = (JSONArray) root.get("movies");
        if (movies == null) movies = new JSONArray();

        JSONObject newMovie = new JSONObject();
        newMovie.put("title", title);
        newMovie.put("author", director);
        newMovie.put("year", year);

        movies.add(newMovie);
        root.put("movies", movies);
        saveJson(root);
        System.out.println("Фильм успешно записан в JSON файл!");
    }

    private static void searchByDirector(String director) {
        JSONObject root = getRootObject();
        JSONArray jsonArray = (JSONArray) root.get("movies");

        // Приводим jsonArray к List<Object>, чтобы включить Generics
        ((java.util.List<Object>) jsonArray).stream()
                .filter(book -> book instanceof JSONObject)
                .map(book -> (JSONObject) book)
                // Теперь компилятор понимает, что дальше по потоку идут JSONObject
                .filter(book -> director.equalsIgnoreCase((String) book.get("ИМЯ_КЛЮЧА_РЕЖИССЕРА")))
                .forEach(book -> {
                    System.out.println("\nТекущий элемент: movie");
                    System.out.println("Название фильма: " + book.get("название"));
                    System.out.println("Автор/Режиссер: " + book.get("режиссер"));
                    System.out.println("Год издания: " + book.get("год"));
                });
    }

    private static void deleteMovie(String title) {
        JSONObject root = getRootObject();
        JSONArray jsonArray = (JSONArray) root.get("movies");
        Iterator iterator = jsonArray.iterator();
        boolean removed = false;

        // Удаление с помощью Iterator.remove() по требованию лабораторной работы
        while (iterator.hasNext()) {
            JSONObject movie = (JSONObject) iterator.next();
            if (title.equalsIgnoreCase((String) movie.get("title"))) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            root.put("movies", jsonArray);
            saveJson(root);
            System.out.println("Фильм успешно удален из JSON.");
        } else {
            System.out.println("Фильм с таким названием не найден.");
        }
    }
}