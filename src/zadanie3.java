
import java.util.Scanner;

public class zadanie3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите размер массива: ");
        if (scanner.hasNextInt()) {
            int size = scanner.nextInt();

            if (size <= 0) {
                System.out.println("Размер массива должен быть больше 0.");
                return;
            }

            // Создаем массив указанного размера
            int[] array = new int[size];

            // Запускаем рекурсивный ввод (начиная с индекса 0)
            System.out.println("Введите элементы массива:");
            inputArray(array, 0, scanner);

            // Запускаем рекурсивный вывод (начиная с индекса 0)
            System.out.print("Ваш массив: ");
            printArray(array, 0);
            System.out.println(); // Перенос строки в конце программы

        } else {
            System.out.println("Ошибка: размер должен быть целым числом.");
        }

        scanner.close();
    }

    /**
     * Рекурсивный метод для ввода элементов массива.
     * @param arr - ссылка на массив
     * @param index - текущий индекс для заполнения
     * @param scanner - объект для чтения с клавиатуры
     */
    public static void inputArray(int[] arr, int index, Scanner scanner) {
        // Базовый случай: если дошли до конца массива, выходим из рекурсии
        if (index == arr.length) {
            return;
        }

        System.out.print("Элемент [" + index + "]: ");
        arr[index] = scanner.nextInt();

        // Рекурсивный вызов для следующего индекса (шаг рекурсии)
        inputArray(arr, index + 1, scanner);
    }

    /**
     * Рекурсивный метод для вывода элементов массива.
     * @param arr - ссылка на массив
     * @param index - текущий индекс для вывода
     */
    public static void printArray(int[] arr, int index) {
        // Базовый случай: если вывели все элементы, прекращаем работу
        if (index == arr.length) {
            return;
        }

        // Печатаем текущий элемент
        System.out.print(arr[index] + " ");

        // Рекурсивный вызов для печати следующего элемента
        printArray(arr, index + 1);
    }
}
