package lab11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class zadanie3 {

    public static List<String> filterCapitalized(List<String> strings) {
        return strings.stream()
                // Проверяем, что строка не null, не пустая и начинается с заглавной буквы
                .filter(s -> s != null && !s.isEmpty() && Character.isUpperCase(s.charAt(0)))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("яблоко", "Апельсин", "банан", "Груша", "");
        System.out.println(filterCapitalized(words)); // Вывод: [Апельсин, Груша]
    }
}