package lab11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class zadanie8 {

    public static List<Integer> filterGreaterThan(List<Integer> numbers, int threshold) {
        return numbers.stream()
                .filter(n -> n > threshold)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 8, 15, 22, 40, 5);
        int threshold = 15;

        System.out.println("Числа больше " + threshold + ": " + filterGreaterThan(nums, threshold));
        // Вывод: [22, 40]
    }
}