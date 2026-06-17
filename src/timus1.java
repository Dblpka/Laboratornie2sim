import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class timus1 {
    static class Board {
        int id;
        long x1, y1, x2, y2;

        public Board(int id, long x1, long y1, long x2, long y2) {
            this.id = id;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    static class Event implements Comparable<Event> {
        long x;
        int type; // 0 - левая граница (вход), 1 - правая граница (выход)
        Board board;

        public Event(long x, int type, Board board) {
            this.x = x;
            this.type = type;
            this.board = board;
        }

        @Override
        public int compareTo(Event o) {
            if (this.x != o.x) {
                return Long.compare(this.x, o.x);
            }
            return Integer.compare(this.type, o.type);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        int n = Integer.parseInt(tokenizer.nextToken());
        long m = Long.parseLong(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        long startX = Long.parseLong(tokenizer.nextToken());
        long startY = Long.parseLong(tokenizer.nextToken());

        Board[] boards = new Board[n];
        int startBoardId = -1;

        for (int i = 0; i < n; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            long x1 = Long.parseLong(tokenizer.nextToken());
            long y1 = Long.parseLong(tokenizer.nextToken());
            long x2 = Long.parseLong(tokenizer.nextToken());
            long y2 = Long.parseLong(tokenizer.nextToken());

            boards[i] = new Board(i, x1, y1, x2, y2);

            // Проверяем, находится ли конь на этой доске
            if (startX >= x1 && startX < x2 && startY >= y1 && startY < y2) {
                startBoardId = i;
            }
        }

        // Список смежности для графа досок
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // 1. Поиск ребер по вертикальным зазорам (зазор по X равен 1, перекрытие по Y)
        findEdges(boards, adj, true);

        // 2. Поиск ребер по горизонтальным зазорам (зазор по Y равен 1, перекрытие по X)
        findEdges(boards, adj, false);

        // Обход графа (BFS) для поиска количества достижимых досок
        int reachableBoardsCount = 0;
        if (startBoardId != -1) {
            boolean[] visited = new boolean[n];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(startBoardId);
            visited[startBoardId] = true;
            reachableBoardsCount++;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        visited[v] = true;
                        reachableBoardsCount++;
                        queue.add(v);
                    }
                }
            }
        }

        System.out.println(reachableBoardsCount);
    }

    private static void findEdges(Board[] boards, List<List<Integer>> adj, boolean sweepX) {
        List<Event> events = new ArrayList<>();
        for (Board b : boards) {
            if (sweepX) {
                events.add(new Event(b.x1, 0, b));
                events.add(new Event(b.x2, 1, b));
            } else {
                events.add(new Event(b.y1, 0, b));
                events.add(new Event(b.y2, 1, b));
            }
        }

        Collections.sort(events);

        // Храним активные доски в TreeSet, отсортированном по перпендикулярной координате
        // Используем кастомный компаратор во избежание дубликатов
        TreeSet<Board> activeBoards = new TreeSet<>((b1, b2) -> {
            long coord1 = sweepX ? b1.y1 : b1.x1;
            long coord2 = sweepX ? b2.y1 : b2.x1;
            if (coord1 != coord2) return Long.compare(coord1, coord2);
            return Integer.compare(b1.id, b2.id);
        });

        for (Event e : events) {
            Board curr = e.board;
            if (e.type == 0) { // Вход в доску
                // Ищем уже активные доски, у которых правая граница заканчивается ровно на e.x - 1
                // Для этого смотрим соседей в TreeSet по перпендикулярной координате
                long currMinY = sweepX ? curr.y1 : curr.x1;
                long currMaxY = sweepX ? curr.y2 : curr.x2;

                // Создаем временные объекты для поиска границ в TreeSet
                Board lowerBound = sweepX ? new Board(-1, 0, currMinY - 2, 0, 0) : new Board(-1, currMinY - 2, 0, 0, 0);

                // Перебираем потенциальных соседей в структуре данных
                NavigableSet<Board> candidates = activeBoards.tailSet(lowerBound, true);
                for (Board active : candidates) {
                    long actMinY = sweepX ? active.y1 : active.x1;
                    if (actMinY > currMaxY + 1) {
                        break; // Дальше смотреть бессмысленно, они слишком высоко/далеко
                    }

                    long actMaxY = sweepX ? active.y2 : active.x2;
                    long actEndCoord = sweepX ? active.x2 : active.y2;

                    // Проверяем зазор по текущей оси сканирования (должен быть ровно 1)
                    if (actEndCoord == e.x - 1) {
                        // Проверяем пересечение/смещение по перпендикулярной оси
                        // Интервалы [currMinY, currMaxY] и [actMinY, actMaxY] с учетом смещения на 1
                        if (currMinY <= actMaxY + 1 && actMinY <= currMaxY + 1) {
                            adj.get(curr.id).add(active.id);
                            adj.get(active.id).add(curr.id);
                        }
                    }
                }
                activeBoards.add(curr);
            } else { // Выход из доски
                activeBoards.remove(curr);
            }
        }
    }
}