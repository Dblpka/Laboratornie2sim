package lab11;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class zadanie2 {

    public static int[] findIntersectionStream(int[] arr1, int[] arr2) {
        // Преобразуем второй массив в Set для быстрого поиска O(1)
        Set<Integer> set2 = Arrays.stream(arr2)
                .boxed() // преобразуем int в Integer
                .collect(Collectors.toSet());

        // Фильтруем первый массив, оставляя только уникальные элементы, которые есть в set2
        return Arrays.stream(arr1)
                .distinct() // оставляем только уникальные числа
                .filter(set2::contains) // проверяем наличие во втором массиве
                .toArray(); // собираем обратно в int[]
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 2, 4, 5};
        int[] array2 = {2, 5, 5, 8, 9};

        int[] commonElements = findIntersectionStream(array1, array2);

        System.out.println(Arrays.toString(commonElements)); // Вывод: [2, 5]
    }
}