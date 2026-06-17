import java.util.Scanner;

public class zadanie2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите целое число: ");
        // Проверяем, что пользователь ввел именно целое число
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();

            System.out.print("Двоичное представление: ");
            if (number == 0) {
                System.out.print(0);
            } else {
                convertToBinary(number);
            }
            System.out.println(); // Перенос строки в конце вывода
        } else {
            System.out.println("Ошибка: введено не целое число.");
        }

        scanner.close();
    }

    /**
     * Рекурсивный метод для перевода числа в двоичную систему.
     * Он делит число на 2, уходит в рекурсию, а затем на выходе
     * из нее (обратный ход) печатает остатки от деления.
     */
    public static void convertToBinary(int n) {
        // Базовый случай для завершения рекурсии
        if (n == 0) {
            return;
        }

        // Рекурсивный вызов: передаем результат деления нацело на 2
        convertToBinary(n / 2);

        // Печатаем остаток от деления. 
        // Так как вывод идет ПОСЛЕ рекурсивного вызова, цифры напечатаются в правильном порядке
        System.out.print(n % 2);
    }
}