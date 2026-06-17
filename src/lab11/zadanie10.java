package lab11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class zadanie10 {

    public static List<Integer> filterLessThan(List<Integer> numbers, int threshold) {
        return numbers.stream()
                .filter(n -> n < threshold)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 8, 15, 22, 40, 5);
        int threshold = 10;

        System.out.println("Числа меньше " + threshold + ": " + filterLessThan(nums, threshold));
        // Вывод: [1, 8, 5]
    }
}