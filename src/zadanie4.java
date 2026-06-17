public class zadanie4 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            final int threadNumber = i; // Сохраняем номер для потока

            Thread thread = new Thread(() -> {
                System.out.println("Поток номер: " + threadNumber);
            });

            thread.start();
        }
    }
}