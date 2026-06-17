

import java.util.HashMap;
import java.util.Map;

public class zadanie6 {
    public static void main(String[] args) {
        // 1. Создаем и заполняем HashMap 10 объектами <Integer, String>
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Ноль");
        map.put(1, "Один");
        map.put(2, "Два");
        map.put(3, "Привет");
        map.put(5, "Пять");
        map.put(6, "ДлиннаяСтрока1");
        map.put(7, "Семь");
        map.put(8, "Яблоко");
        map.put(9, "ДлиннаяСтрока2");
        map.put(10, "Банан");

        System.out.println("Исходная HashMap: " + map);
        System.out.println("----------------------------------------------");

        // Переменные для логики программы
        StringBuilder zeroKeyStrings = new StringBuilder();
        long keysProduct = 1;
        boolean hasLengthGreaterThan5 = false;

        System.out.println("Строки, у которых ключ > 5:");

        // Проходим по всем элементам HashMap
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();

            // Условие 1: Найти строки, у которых ключ > 5
            if (key > 5) {
                System.out.println("Ключ: " + key + ", Строка: " + value);
            }

            // Условие 2: Если ключ = 0, собираем строки (на случай, если бы их было несколько)
            if (key == 0) {
                if (zeroKeyStrings.length() > 0) {
                    zeroKeyStrings.append(", ");
                }
                zeroKeyStrings.append(value);
            }

            // Условие 3: Если длина строки > 5, перемножаем ключи
            if (value.length() > 5) {
                keysProduct *= key;
                hasLengthGreaterThan5 = true;
            }
        }

        System.out.println("----------------------------------------------");

        // 2. Вывод строк, где ключ = 0, через запятую
        System.out.print("Строки, где ключ = 0: ");
        if (zeroKeyStrings.length() > 0) {
            System.out.println(zeroKeyStrings.toString());
        } else {
            System.out.println("Элементы с ключом 0 не найдены.");
        }

        // 3. Вывод произведения ключей
        System.out.print("Произведение всех ключей, где длина строки > 5: ");
        if (hasLengthGreaterThan5) {
            System.out.println(keysProduct);
        } else {
            System.out.println("Строк с длиной > 5 не обнаружено (произведение равно 0).");
        }
    }
}