package lab11;

import java.util.Arrays;

public class zadanie1 {

    public static int[] filterEvenNumbers(int[] numbers) {
        // Преобразуем массив в стрим, фильтруем только четные числа и собираем обратно в массив
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .toArray();
    }

    public static void main(String[] args) {
        int[] originalArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] evenArray = filterEvenNumbers(originalArray);

        System.out.println(Arrays.toString(evenArray)); // Вывод: [2, 4, 6, 8, 10]
    }
}
