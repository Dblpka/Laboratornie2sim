import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class zadanie6 {

    public static long sumMultiThreaded(int[] array) throws InterruptedException, ExecutionException {
        // Получаем количество ядер
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        List<Future<Long>> futures = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) array.length / cores);

        for (int i = 0; i < cores; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, array.length);

            if (start >= array.length) break;

            futures.add(executor.submit(() -> {
                long localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += array[j];
                }
                return localSum; // Возвращаем сумму текущей части
            }));
        }

        // Собираем результаты и находим общую сумму
        long globalSum = 0;
        for (Future<Long> future : futures) {
            globalSum += future.get();
        }

        executor.shutdown();
        return globalSum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Сумма = 55
        System.out.println("Сумма элементов: " + sumMultiThreaded(arr));
    }
}