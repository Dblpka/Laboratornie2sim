package lab11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class zadanie9 {

    public static List<String> filterOnlyLetters(List<String> strings) {
        return strings.stream()
                // Убеждаемся, что строка не пустая и все ее символы являются буквами
                .filter(s -> s != null && !s.isEmpty() && s.chars().allMatch(Character::isLetter))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Java 17", "Hello!", "Мир", "123");

        System.out.println("Только буквы: " + filterOnlyLetters(words));
        // Вывод: [Java, Мир]
    }
}