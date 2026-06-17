package lab11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class zadanie7 {

    public static List<String> filterByMinLength(List<String> strings, int minLength) {
        return strings.stream()
                // Проверяем на null, чтобы избежать NullPointerException, затем проверяем длину
                .filter(s -> s != null && s.length() > minLength)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Код", "Программирование", "IT", "Мир");
        int minLength = 3;

        System.out.println("Строки длиннее " + minLength + " символов: " + filterByMinLength(words, minLength));
        // Вывод: [Java, Программирование]
    }
}