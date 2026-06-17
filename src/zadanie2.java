public class zadanie2 {
    public static void main(String[] args) {
        // Создаем поток с задачей вывода чисел
        Thread numberThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println(i);
                try {
                    // Задержка в 1 секунду (1000 миллисекунд)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Поток был прерван.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        // Запускаем поток
        System.out.println("Начало вывода чисел:");
        numberThread.start();
    }
}