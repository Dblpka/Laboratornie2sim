import java.util.Scanner;

public class timus5 {
    // Таблица мемоизации: [bx][by][px][py][turn]
    // Значения: 1 = WHITE, 0 = DRAW, -1 = BLACK, -2 = еще не вычислено
    private static int[][][][][] memo = new int[8][8][8][8][2];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNext()) return;
        String bishopStr = sc.next();
        if (!sc.hasNext()) return;
        String pawnStr = sc.next();

        // Переводим шахматную нотацию в индексы 0..7
        int bx = bishopStr.charAt(0) - 'a';
        int by = bishopStr.charAt(1) - '1';
        int px = pawnStr.charAt(0) - 'a';
        int py = pawnStr.charAt(1) - '1';

        // Инициализируем массив мемоизации значениями -2
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        memo[i][j][k][l][0] = -2;
                        memo[i][j][k][l][1] = -2;
                    }
                }
            }
        }

        // Запускаем минимакс (начинают белые -> turn = 0)
        int result = solve(bx, by, px, py, 0);

        if (result == 1) {
            System.out.println("WHITE");
        } else if (result == 0) {
            System.out.println("DRAW");
        } else {
            System.out.println("BLACK");
        }
    }

    private static int solve(int bx, int by, int px, int py, int turn) {
        if (memo[bx][by][px][py][turn] != -2) {
            return memo[bx][by][px][py][turn];
        }

        if (turn == 0) { // Ход Белых
            // Если слон бьет пешку/ферзя на текущем шагу
            if (Math.abs(bx - px) == Math.abs(by - py)) {
                return memo[bx][by][px][py][turn] = 1; // WHITE
            }
            // Если черные дошли до края и превратились, а слон их не бьет
            if (py == 0) {
                return memo[bx][by][px][py][turn] = -1; // BLACK
            }

            int maxScore = -1; // Белые стремятся к максимуму (1 > 0 > -1)
            int[] dx = {-1, -1, 1, 1};
            int[] dy = {-1, 1, -1, 1};

            // Генерируем все возможные ходы для слона
            for (int d = 0; d < 4; d++) {
                for (int step = 1; step < 8; step++) {
                    int bnx = bx + dx[d] * step;
                    int bny = by + dy[d] * step;

                    if (bnx < 0 || bnx >= 8 || bny < 0 || bny >= 8) break;

                    int score = solve(bnx, bny, px, py, 1);
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
            return memo[bx][by][px][py][turn] = maxScore;

        } else { // Ход Черных
            // Пешка ходит сверху вниз (уменьшая координату 'y')
            boolean canMoveForward = (py > 0 && !(bx == px && by == py - 1));
            boolean canCaptureLeft = (px > 0 && py > 0 && bx == px - 1 && by == py - 1);
            boolean canCaptureRight = (px < 7 && py > 0 && bx == px + 1 && by == py - 1);

            int minScore = 2; // Черные стремятся к минимуму (-1 < 0 < 1)

            if (canMoveForward) {
                int score = solve(bx, by, px, py - 1, 0);
                if (score < minScore) minScore = score;
            }
            if (canCaptureLeft || canCaptureRight) {
                int score = -1; // Если черные съедают слона, они побеждают
                if (score < minScore) minScore = score;
            }

            if (minScore == 2) {
                // Если у черных нет вообще никаких ходов, это ничья (пешка заблокирована)
                return memo[bx][by][px][py][turn] = 0; // DRAW
            }

            return memo[bx][by][px][py][turn] = minScore;
        }
    }
}