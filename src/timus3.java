import java.util.ArrayList;
import java.util.Scanner;

public class timus3 {
    private static ArrayList<Integer>[] adj;
    private static double[] x;
    private static double[] y;
    private static int counter = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        // Инициализация списка смежности
        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Чтение ребер дерева
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        x = new double[n + 1];
        y = new double[n + 1];

        if (n > 0) {
            // Запускаем DFS из вершины 1 (принимаем её за корень)
            dfs(1, 0, 0);

            // Поиск минимальных и максимальных координат для последующего центрирования
            double minX = x[1], maxX = x[1];
            double minY = y[1], maxY = y[1];
            for (int i = 1; i <= n; i++) {
                if (x[i] < minX) minX = x[i];
                if (x[i] > maxX) maxX = x[i];
                if (y[i] < minY) minY = y[i];
                if (y[i] > maxY) maxY = y[i];
            }

            // Находим центр тяжести сетки
            double midX = (minX + maxX) / 2.0;
            double midY = (minY + maxY) / 2.0;

            // Выводим отцентрированные координаты для каждого компьютера
            for (int i = 1; i <= n; i++) {
                System.out.println((x[i] - midX) + " " + (y[i] - midY));
            }
        }
    }

    private static void dfs(int u, int p, int depth) {
        // Умножаем на 2, чтобы расстояние по вертикали было больше 1
        y[u] = depth * 2;

        // Собираем всех детей текущей вершины (исключая родителя)
        ArrayList<Integer> children = new ArrayList<>();
        for (int v : adj[u]) {
            if (v != p) {
                children.add(v);
            }
        }

        int pieces = children.size();
        int mid = pieces / 2;

        // Обходим первую половину поддеревьев
        for (int i = 0; i < mid; i++) {
            dfs(children.get(i), u, depth + 1);
        }

        // Присваиваем координату X текущей вершине
        x[u] = counter;
        counter += 2; // Шаг 2 гарантирует расстояние больше 1

        // Обходим вторую половину поддеревьев
        for (int i = mid; i < pieces; i++) {
            dfs(children.get(i), u, depth + 1);
        }
    }
}