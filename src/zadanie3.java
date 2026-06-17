public class zadanie3 {
    public static void main(String[] args) {
        // Поток для четных чисел
        Thread evenThread = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println("Четное: " + i);
            }
        });

        // Поток для нечетных чисел
        Thread oddThread = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                System.out.println("Нечетное: " + i);
            }
        });

        // Запускаем потоки
        oddThread.start();
        evenThread.start();
    }
}