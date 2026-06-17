package lab11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class zadanie4 {

    public static List<Integer> squareNumbers(List<Integer> numbers) {
        return numbers.stream()
                // Преобразуем каждое число в его квадрат
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(squareNumbers(nums)); // Вывод: [1, 4, 9, 16, 25]
    }
}