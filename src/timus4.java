import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class timus4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        int m = sc.nextInt();

        double g = 10.0;

        // dp[x][y] будет хранить минимальное время для достижения точки (x, y)
        double[][] dp = new double[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Double.POSITIVE_INFINITY);
        }
        dp[0][0] = 0.0;

        // Предвычисляем скорость для каждого уровня высоты y
        double[] v = new double[m + 1];
        for (int y = 0; y <= m; y++) {
            v[y] = Math.sqrt(2.0 * g * y);
        }

        // Обходим граф состояний по слоям сверху вниз (по координате y)
        for (int y1 = 0; y1 <= m; y1++) {
            for (int x1 = 0; x1 <= n; x1++) {
                if (dp[x1][y1] == Double.POSITIVE_INFINITY) {
                    continue; // Эта точка недостижима
                }

                // Перебираем все возможные конечные точки доски (x2, y2)
                // y2 > y1 (нижний конец строго ниже верхнего)
                for (int y2 = y1 + 1; y2 <= m; y2++) {
                    // x2 >= x1 (нижний конец не правее верхнего, т.е. левее или на том же уровне)
                    for (int x2 = x1; x2 <= n; x2++) {
                        double dx = x2 - x1;
                        double dy = y2 - y1;
                        double L = Math.sqrt(dx * dx + dy * dy);

                        // Время скатывания по текущей доске
                        double t = (2.0 * L) / (v[y1] + v[y2]);

                        // Обновляем минимальное время для точки (x2, y2)
                        if (dp[x1][y1] + t < dp[x2][y2]) {
                            dp[x2][y2] = dp[x1][y1] + t;
                        }
                    }
                }
            }
        }

        // Выводим результат с фиксированной точкой (Locale.US гарантирует точку вместо запятой)
        System.out.printf(Locale.US, "%.4f\n", dp[n][m]);
    }
}
