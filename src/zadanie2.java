import java.util.InputMismatchException;
import java.util.Scanner;

public class zadanie2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Пример матрицы 3x3
        int[][] matrix = {
                {10, 20, 30},
                {40, 50, 60},
                {70, 80, 90}
        };

        try {
            System.out.print("Введите номер столбца для вывода (от 0 до 2): ");
            int colIndex = scanner.nextInt();

            System.out.println("Элементы столбца " + colIndex + ":");
            for (int i = 0; i < matrix.length; i++) {
                // Если colIndex неверный, здесь вылетит ArrayIndexOutOfBoundsException
                System.out.println(matrix[i][colIndex]);
            }

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: Ожидалось целое число, а введена строка или иной символ.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Ошибка индексации: Столбца с таким номером не существует в матрице.");
        } finally {
            System.out.println("Блок finally: Завершение работы программы (Задание 2).");
            scanner.close();
        }
    }
}