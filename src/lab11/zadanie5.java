package lab11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class zadanie5 {

    public static List<String> filterBySubstring(List<String> strings, String substring) {
        return strings.stream()
                // Оставляем только те строки, которые содержат заданную подстроку
                .filter(s -> s != null && s.contains(substring))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> phrases = Arrays.asList("hello world", "world peace", "hi there", "brave new world");
        String target = "world";

        System.out.println(filterBySubstring(phrases, target));
        // Вывод: [hello world, world peace, brave new world]
    }
}