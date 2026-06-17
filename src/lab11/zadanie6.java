package lab11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class zadanie6 {

    public static List<Integer> filterDivisible(List<Integer> numbers, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Делитель не может быть равен нулю");
        }
        return numbers.stream()
                .filter(n -> n % divisor == 0)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 10, 15, 20, 25, 30);
        int divisor = 10;

        System.out.println("Числа, которые делятся на " + divisor + ": " + filterDivisible(nums, divisor));
        // Вывод: [10, 20, 30]
    }
}