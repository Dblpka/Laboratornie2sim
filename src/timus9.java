import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class timus9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            // Потребляем оставшийся перевод строки после считывания числа N
            scanner.nextLine();

            Set<String> visitedStores = new HashSet<>();
            int bayanCount = 0;

            for (int i = 0; i < n; i++) {
                String storeName = scanner.nextLine();

                // Метод add() возвращает false, если элемент уже есть в Set
                if (!visitedStores.add(storeName)) {
                    bayanCount++;
                }
            }

            System.out.println(bayanCount);
        }

        scanner.close();
    }
}