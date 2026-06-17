import java.util.Scanner;

public class timus7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Проверяем наличие входных данных
        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            int sum = 0;

            if (n > 0) {
                // Если N положительное, считаем сумму от 1 до N
                for (int i = 1; i <= n; i++) {
                    sum += i;
                }
            } else {
                // Если N <= 0, считаем сумму от N до 1
                for (int i = n; i <= 1; i++) {
                    sum += i;
                }
            }

            // Выводим результат
            System.out.println(sum);
        }

        scanner.close();
    }
}