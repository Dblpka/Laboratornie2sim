import java.util.InputMismatchException;
import java.util.Scanner;

public class zadanie3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите количество элементов: ");
            int n = scanner.nextInt();
            byte[] arr = new byte[n];

            int sum = 0; // Используем int для безопасного накопления суммы

            for (int i = 0; i < n; i++) {
                System.out.print("Введите значение byte (от -128 до 127) для элемента " + i + ": ");
                // Если ввести > 127 или строку, вылетит InputMismatchException
                arr[i] = scanner.nextByte();
                sum += arr[i];
            }

            // Ручная проверка выхода суммы за границы типа byte
            if (sum > Byte.MAX_VALUE || sum < Byte.MIN_VALUE) {
                throw new ArithmeticException("Вычисленная сумма (" + sum + ") выходит за границы диапазона типа byte!");
            }

            System.out.println("Сумма элементов: " + (byte) sum);

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: Введена строка вместо числа или введено значение за границами типа byte.");
        } catch (ArithmeticException e) {
            System.out.println("Ошибка вычисления: " + e.getMessage());
        } finally {
            System.out.println("Блок finally: Завершение работы программы (Задание 3).");
            scanner.close();
        }
    }
}