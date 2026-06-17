import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class zadanie5 {

    public static int findMaxMultiThreaded(int[] array) throws InterruptedException, ExecutionException {
        // Получаем количество ядер
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        List<Future<Integer>> futures = new ArrayList<>();

        // Вычисляем размер части массива для каждого потока
        int chunkSize = (int) Math.ceil((double) array.length / cores);

        for (int i = 0; i < cores; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, array.length);

            if (start >= array.length) break;

            // Отправляем задачу в пул потоков
            futures.add(executor.submit(() -> {
                int localMax = array[start];
                for (int j = start + 1; j < end; j++) {
                    if (array[j] > localMax) {
                        localMax = array[j];
                    }
                }
                return localMax; // Возвращаем максимум текущей части
            }));
        }

        // Собираем результаты и находим глобальный максимум
        int globalMax = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            globalMax = Math.max(globalMax, future.get());
        }

        executor.shutdown(); // Обязательно закрываем пул
        return globalMax;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {3, 5, 1, 24, 7, 88, 12, 45, 9, 102, 4};
        System.out.println("Максимальный элемент: " + findMaxMultiThreaded(arr));
    }
}