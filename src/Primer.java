public class Primer {

    public static void main(String[] args) {
        System.out.println("=== Запуск всех примеров ===");

        example1();
        example2();
        example3();
        example4();

        // Пример 5 не перехватывает ошибку внутри себя, поэтому ловим её здесь, чтобы продолжить работу
        try { example5(); } catch (RuntimeException e) { System.out.println("  [main] Перехвачено исключение из Примера 5"); }

        example6();

        // Пример 7 выбрасывает ArithmeticException, который не ловится внутренним catch
        try { example7(); } catch (ArithmeticException e) { System.out.println("  [main] Перехвачено исключение из Примера 7"); }

        // Пример 8 выбрасывает RuntimeException из метода
        try { example8(); } catch (RuntimeException e) { System.out.println("  [main] Перехвачено исключение из Примера 8"); }

        example9();
        example10();
        example11();

        // Пример 12 выбрасывает IllegalArgumentException из-за неверных аргументов
        try { example12(); } catch (IllegalArgumentException e) { System.out.println("  [main] Перехвачено исключение из Примера 12: " + e.getMessage()); }

        // Передаем пустой массив аргументов для симуляции работы
        String[] emptyArgs = new String[0];
        example13(emptyArgs);
        example14(emptyArgs);
    }

    // --- Пример 1 ---
    public static void example1() {
        System.out.println("\nПример 1:");
        try {
            System.out.println("0");
            throw new RuntimeException("Непроверяемая ошибка");
        } catch (RuntimeException e) { // исключение перехвачено
            System.out.println("1  " + e);  // исключение обработано
        }
        System.out.println("2");
    }

    // --- Пример 2 ---
    public static void example2() {
        System.out.println("\nПример 2:");
        try {
            System.out.println("0");
            throw new RuntimeException("Непроверяемая ошибка");
            // ИСПРАВЛЕНО: строка ниже закомментирована, так как это Unreachable statement (недостижимый код)
            // System.out.println("1");
        } catch (Exception e) {
            System.out.println("2 " + e);
        }
        System.out.println("3");
    }

    // --- Пример 3 ---
    public static void example3() {
        System.out.println("\nПример 3:");
        try {
            System.out.println("0");
            throw new RuntimeException("ошибка");
        } catch (NullPointerException e) {
            System.out.println("1");
        } catch (RuntimeException e) {
            System.out.println("2");
        } catch (Exception e) {
            System.out.println("3");
        }
        System.out.println("4");
    }

    // --- Пример 4 ---
    public static void example4() {
        System.out.println("\nПример 4:");
        try {
            System.out.println("0");
            throw new RuntimeException("ошибка");
        } catch (NullPointerException e) {
            System.out.println("1");
        } catch (Exception e) {
            System.out.println("2");
        } catch (Error e) {
            System.out.println("3");
        }
        System.out.println("4");
    }

    // --- Пример 5 ---
    public static void example5() {
        System.out.println("\nПример 5:");
        try {
            System.out.println("0");
            throw new RuntimeException("ошибка");
        } catch (NullPointerException e) {
            System.out.println("1");
        }
        System.out.println("2"); // До этой строки код не дойдет из-за непойманного исключения
    }

    // --- Пример 6 ---
    public static void example6() {
        System.out.println("\nПример 6:");
        try {
            System.out.println("0");
            throw new NullPointerException("ошибка");
        } catch (ArithmeticException e) {
            System.out.println("1");
        } catch (RuntimeException e) {
            // ИСПРАВЛЕНО: Блок RuntimeException поднят выше блока Exception.
            // Предок не должен перехватывать исключения раньше потомков.
            System.out.println("3");
        } catch (Exception e) {
            System.out.println("2");
        }
        System.out.println("4");
    }

    // --- Пример 7 ---
    public static void example7() {
        System.out.println("\nПример 7:");
        try {
            System.out.println("0");
            throw new NullPointerException("ошибка");
        } catch (NullPointerException e) {
            System.out.println("1");
            throw new ArithmeticException();
        } catch (ArithmeticException e) {
            System.out.println("2");
        }
        System.out.println("3");
    }

    // --- Пример 8 ---
    public static int m8() {
        try {
            System.out.println("0");
            throw new RuntimeException();
        } finally {
            System.out.println("1");
        }
    }
    public static void example8() {
        System.out.println("\nПример 8:");
        System.out.println(m8());
    }

    // --- Пример 9 ---
    public static int m9() {
        try {
            System.out.println("0");
            return 55; // выход из метода
        } finally {
            System.out.println("1");
        }
    }
    public static void example9() {
        System.out.println("\nПример 9:");
        System.out.println(m9());
    }

    // --- Пример 10 ---
    public static int m10() {
        try {
            System.out.println("0");
            return 15;
        } finally {
            System.out.println("1");
            return 20;
        }
    }
    public static void example10() {
        System.out.println("\nПример 10:");
        System.out.println(m10());
    }

    // --- Пример 11 ---
    public static void example11() {
        System.out.println("\nПример 11:");
        try {
            System.out.println("0");
            throw new NullPointerException("ошибка");
        } catch (NullPointerException e) {
            System.out.println("1");
        } finally {
            System.out.println("2");
        }
        System.out.println("3");
    }

    // --- Пример 12 ---
    public static void m12(String str, double chislo) {
        if (str == null) {
            throw new IllegalArgumentException("Строка введена неверно");
        }
        if (chislo > 0.001) {
            throw new IllegalArgumentException("Неверное число");
        }
    }
    public static void example12() {
        System.out.println("\nПример 12:");
        m12(null, 0.000001);
    }

    // --- Пример 13 ---
    public static void example13(String[] args) {
        System.out.println("\nПример 13:");
        try {
            int l = args.length;
            System.out.println("размер массива= " + l);
            int h = 10 / l; // При пустом массиве здесь будет деление на 0
            args[l + 1] = "10";
        } catch (ArithmeticException e) {
            System.out.println("Деление на ноль");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Индекс не существует");
        }
    }

    // --- Пример 14 ---
    public static void m14(int x) throws ArithmeticException {
        int h = 10 / x;
    }
    public static void example14(String[] args) {
        System.out.println("\nПример 14:");
        try {
            int l = args.length;
            System.out.println("размер массива= " + l);
            m14(l); // Метод выбросит ArithmeticException, если длина массива = 0
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: Деление на ноль");
        }
    }
}