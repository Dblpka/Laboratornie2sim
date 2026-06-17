import java.util.Scanner;

public class timus8
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextInt()) {
            int g = scanner.nextInt();
            int l = scanner.nextInt();

            // Сколько не прострелил Гарри -> это все банки Ларри, кроме одной общей
            int garryMissed = l - 1;
            // Сколько не прострелил Ларри -> это все банки Гарри, кроме одной общей
            int larryMissed = g - 1;

            System.out.println(garryMissed + " " + larryMissed);
        }

        scanner.close();
    }
}