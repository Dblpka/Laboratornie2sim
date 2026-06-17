import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class zadanie1 {
    public static void main(String[] args) {
        // Создаем задачу, которую будут выполнять потоки
        Runnable timeTask = () -> {
            long startTime = System.currentTimeMillis();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Выполнять цикл, пока с момента старта не пройдет 10 секунд (10000 мс)
            while (System.currentTimeMillis() - startTime < 10000) {
                String threadName = Thread.currentThread().getName();
                String currentTime = LocalTime.now().format(formatter);

                System.out.println("Поток: " + threadName + " | Время: " + currentTime);

                try {
                    // Пауза в 1 секунду между выводами
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Поток " + threadName + " был прерван.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        // Создаем два потока и передаем им задачу
        Thread thread1 = new Thread(timeTask, "Первый");
        Thread thread2 = new Thread(timeTask, "Второй");

        // Запускаем потоки
        System.out.println("Запуск потоков на 10 секунд...");
        thread1.start();
        thread2.start();
    }
}