import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class timus6 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        if (line == null) return;

        int n = Integer.parseInt(line.trim());
        int[][] matrix = new int[n][n];
        int currentNumber = 1;

        // Разность индексов (row - col) меняется от -(n - 1) до (n - 1)
        for (int k = -(n - 1); k <= (n - 1); k++) {
            for (int i = 0; i < n; i++) {
                int j = i - k; // так как i - j = k, то j = i - k

                // Проверяем, что индекс столбца находится в границах матрицы
                if (j >= 0 && j < n) {
                    matrix[i][j] = currentNumber++;
                }
            }
        }

        // Вывод результирующей матрицы
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matrix[i][j]);
                if (j < n - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}