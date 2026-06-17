import java.util.InputMismatchException;
import java.util.Scanner;

public class zadanie1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите количество элементов массива: ");
            int n = scanner.nextInt();
            int[] arr = new int[n];

            int sum = 0;
            int count = 0;

            for (int i = 0; i < n; i++) {
                System.out.print("Введите элемент " + i + ": ");
                arr[i] = scanner.nextInt();
                if (arr[i] > 0) {
                    sum += arr[i];
                    count++;
                }
            }

            // Если положительных нет, count = 0, произойдет ArithmeticException (деление на 0)
            int average = sum / count;
            System.out.println("Среднее значение положительных элементов: " + average);

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: Введена строка вместо числа или несоответствие типа данных int.");
        } catch (ArithmeticException e) {
            System.out.println("Ошибка вычисления: Положительные элементы отсутствуют (невозможно вычислить среднее, деление на ноль).");
        } finally {
            System.out.println("Блок finally: Завершение работы программы (Задание 1).");
            scanner.close();
        }
    }
}