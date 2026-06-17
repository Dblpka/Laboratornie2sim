
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class zadanie1 {
    private static final String FILE_PATH = "src/lr11/variant9/movies.xml";

    public static void main(String[] args) {
        try {
            checkAndCreateXml();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== XML ПАРСЕР: ФИЛЬМЫ ===");
                System.out.println("1. Показать все фильмы");
                System.out.println("2. Добавить новый фильм");
                System.out.println("3. Поиск фильмов по режиссеру или году");
                System.out.println("4. Удалить фильм по названию");
                System.out.println("5. Выход");
                System.out.print("Выберите действие: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1 -> showAllMovies();
                    case 2 -> {
                        System.out.print("Введите название фильма: ");
                        String title = scanner.nextLine();
                        System.out.print("Введите режиссера (автора): ");
                        String director = scanner.nextLine();
                        System.out.print("Введите год выпуска: ");
                        String year = scanner.nextLine();
                        addMovie(title, director, year);
                    }
                    case 3 -> {
                        System.out.print("Введите имя режиссера или год для фильтрации: ");
                        String filter = scanner.nextLine();
                        searchMovies(filter);
                    }
                    case 4 -> {
                        System.out.print("Введите название фильма для удаления: ");
                        String titleToDelete = scanner.nextLine();
                        deleteMovie(titleToDelete);
                    }
                    case 5 -> {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkAndCreateXml() throws Exception {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("movies");
            doc.appendChild(rootElement);
            saveXml(doc);
        }
    }

    private static Document getDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(FILE_PATH));
    }

    private static void saveXml(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILE_PATH));
        transformer.transform(source, result);
    }

    private static void showAllMovies() throws Exception {
        Document doc = getDocument();
        NodeList nodeList = doc.getElementsByTagName("movie");
        if (nodeList.getLength() == 0) {
            System.out.println("Список фильмов пуст.");
            return;
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            System.out.printf("Фильм: \"%s\" | Режиссер: %s | Год: %s\n",
                    element.getElementsByTagName("title").item(0).getTextContent(),
                    element.getElementsByTagName("author").item(0).getTextContent(),
                    element.getElementsByTagName("year").item(0).getTextContent());
        }
    }

    private static void addMovie(String title, String director, String year) throws Exception {
        Document doc = getDocument();
        Element root = doc.getDocumentElement();

        Element movie = doc.createElement("movie");

        Element t = doc.createElement("title");
        t.setTextContent(title);
        movie.appendChild(t);

        Element d = doc.createElement("author");
        d.setTextContent(director);
        movie.appendChild(d);

        Element y = doc.createElement("year");
        y.setTextContent(year);
        movie.appendChild(y);

        root.appendChild(movie);
        saveXml(doc);
        System.out.println("Фильм успешно добавлен в XML!");
    }

    private static void searchMovies(String filterValue) throws Exception {
        Document doc = getDocument();
        NodeList nodeList = doc.getElementsByTagName("movie");

        // Преобразование NodeList в Stream API согласно подсказке методички
        List<Element> elements = java.util.stream.IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                .map(node -> (Element) node)
                .filter(element -> {
                    String author = element.getElementsByTagName("author").item(0).getTextContent();
                    String year = element.getElementsByTagName("year").item(0).getTextContent();
                    return author.equalsIgnoreCase(filterValue) || year.equals(filterValue);
                })
                .collect(Collectors.toList());

        if (elements.isEmpty()) {
            System.out.println("Фильмы по данному критерию не найдены.");
        } else {
            elements.forEach(element -> System.out.printf("[Найдено] Фильм: \"%s\" | Режиссер: %s | Год: %s\n",
                    element.getElementsByTagName("title").item(0).getTextContent(),
                    element.getElementsByTagName("author").item(0).getTextContent(),
                    element.getElementsByTagName("year").item(0).getTextContent()));
        }
    }

    private static void deleteMovie(String title) throws Exception {
        Document doc = getDocument();
        NodeList nodeList = doc.getElementsByTagName("movie");
        boolean deleted = false;

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getElementsByTagName("title").item(0).getTextContent().equalsIgnoreCase(title)) {
                Node parentNode = element.getParentNode();
                parentNode.removeChild(element); // Удаление узла по методичке
                deleted = true;
                break;
            }
        }

        if (deleted) {
            saveXml(doc);
            System.out.println("Фильм успешно удален из XML.");
        } else {
            System.out.println("Фильм с таким названием не найден.");
        }
    }
}