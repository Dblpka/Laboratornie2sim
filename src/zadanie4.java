import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.Scanner;

public class zadanie4 {
    private static final String FILE_PATH = "src/lr11/variant9/movies.xlsx";

    public static void main(String[] args) {
        createTemplateExcel(); // Создаем корректный файл, если его нет
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== EXCEL ПАРСЕР: ФИЛЬМЫ ===");
            System.out.println("1. Прочитать таблицу фильмов");
            System.out.println("2. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                parseExcelFile();
            } else if (choice.equals("2")) {
                break;
            }
        }
    }

    private static void parseExcelFile() {
        // Улучшенная обработка ошибок согласно методичке
        try (FileInputStream fileInputStream = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet("Фильмы");
            if (sheet == null) {
                // Обработка ошибки отсутствия запрашиваемого листа
                System.err.println("Критическая ошибка: В документе отсутствует необходимый лист \"Фильмы\"!");
                System.out.println("Рекомендация: Проверьте наименование вкладок в вашем Excel файле.");
                return;
            }

            System.out.println("\nЧтение данных из Excel таблицы успешно:");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропуск шапки таблицы

                Cell titleCell = row.getCell(0);
                Cell directorCell = row.getCell(1);
                Cell yearCell = row.getCell(2);

                if (titleCell == null || directorCell == null || yearCell == null) continue;

                // Валидация типов данных ячеек
                String title = titleCell.getStringCellValue();
                String director = directorCell.getStringCellValue();

                double yearDouble;
                if (yearCell.getCellType() == CellType.NUMERIC) {
                    yearDouble = yearCell.getNumericCellValue();
                } else {
                    System.err.println("Предупреждение: обнаружен неверный формат года в строке " + (row.getRowNum() + 1));
                    continue;
                }

                System.out.printf("Строка %d -> Название: %s | Режиссер: %s | Год: %.0f\n",
                        row.getRowNum() + 1, title, director, yearDouble);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл " + FILE_PATH + " не найден в указанной директории.");
            System.out.println("Рекомендация: Перезапустите функцию после проверки пути к файлу.");
        } catch (IOException e) {
            // Обработка поврежденного формата структуры файла
            System.err.println("Критическая ошибка: Файл поврежден или имеет неверный формат (Требуется .xlsx).");
            System.out.println("Рекомендация: Исправьте структуру документа и попробуйте повторный запуск.");
        }
    }

    private static void createTemplateExcel() {
        File file = new File(FILE_PATH);
        if (file.exists()) return;

        file.getParentFile().mkdirs();
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {

            Sheet sheet = workbook.createSheet("Фильмы");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Название");
            header.createCell(1).setCellValue("Режиссер");
            header.createCell(2).setCellValue("Год");

            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Начало");
            row1.createCell(1).setCellValue("Кристофер Нолан");
            row1.createCell(2).setCellValue(2010);

            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("Криминальное чтиво");
            row2.createCell(1).setCellValue("Квентин Тарантино");
            row2.createCell(2).setCellValue(1994);

            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}