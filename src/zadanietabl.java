import java.util.*;

public class zadanietabl {
    private static final int SIZE = 1_000_000; // Количество элементов из методички

    public static void main(String[] args) {
        // Инициализация коллекций
        List<Integer> arrayList = new ArrayList<>();
        Map<Integer, Integer> hashMap = new HashMap<>();
        List<Integer> linkedList = new LinkedList<>();

        // Первичное наполнение коллекций для корректных замеров середины и конца
        for (int i = 0; i < SIZE; i++) {
            arrayList.add(i);
            hashMap.put(i, i);
            linkedList.add(i);
        }

        System.out.println("=== РЕЗУЛЬТАТЫ ЗАМЕРОВ ВРЕМЕНИ (в миллисекундах) ===");

        // =====================================================================
        // ТАБЛИЦА 1. ОПЕРАЦИИ ДОБАВЛЕНИЯ
        // =====================================================================
        System.out.println("\n--- Таблица 1. Добавление ---");

        // В начало
        System.out.println("ArrayList (в начало): " + getAddFirstTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("HashMap (вставка):    " + getMapPutTime(new HashMap<>(hashMap)) + " мс");
        System.out.println("LinkedList (в начало): " + getAddFirstTime(new LinkedList<>(linkedList)) + " мс");

        // В середину
        System.out.println("ArrayList (в середину): " + getAddMiddleTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("LinkedList (в середину): " + getAddMiddleTime(new LinkedList<>(linkedList)) + " мс");

        // В конец
        System.out.println("ArrayList (в конец): " + getAddLastTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("LinkedList (в конец): " + getAddLastTime(new LinkedList<>(linkedList)) + " мс");

        // =====================================================================
        // ТАБЛИЦА 2. ОПЕРАЦИИ УДАЛЕНИЯ
        // =====================================================================
        System.out.println("\n--- Таблица 2. Удаление ---");

        // Из начала
        System.out.println("ArrayList (из начала): " + getRemoveFirstTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("HashMap (удаление):    " + getMapRemoveTime(new HashMap<>(hashMap)) + " мс");
        System.out.println("LinkedList (из начала): " + getRemoveFirstTime(new LinkedList<>(linkedList)) + " мс");

        // Из середины
        System.out.println("ArrayList (из середины): " + getRemoveMiddleTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("LinkedList (из середины): " + getRemoveMiddleTime(new LinkedList<>(linkedList)) + " мс");

        // Из конца
        System.out.println("ArrayList (из конца): " + getRemoveLastTime(new ArrayList<>(arrayList)) + " мс");
        System.out.println("LinkedList (из конца): " + getRemoveLastTime(new LinkedList<>(linkedList)) + " мс");

        // =====================================================================
        // ТАБЛИЦА 3. ПОЛУЧЕНИЕ ЭЛЕМЕНТА
        // =====================================================================
        System.out.println("\n--- Таблица 3. Получение ---");
        System.out.println("ArrayList (по индексу): " + getReadTime(arrayList) + " мс");
        System.out.println("HashMap (по ключу):     " + getMapGetTime(hashMap) + " мс");
        System.out.println("LinkedList (по индексу): " + getReadTime(linkedList) + " мс");
    }

    // Вспомогательные методы для замера времени добавления
    private static long getAddFirstTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.add(0, -999);
        return System.currentTimeMillis() - start;
    }

    private static long getAddMiddleTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.add(SIZE / 2, -999);
        return System.currentTimeMillis() - start;
    }

    private static long getAddLastTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.add(-999);
        return System.currentTimeMillis() - start;
    }

    // Вспомогательные методы для замера времени удаления
    private static long getRemoveFirstTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.remove(0);
        return System.currentTimeMillis() - start;
    }

    private static long getRemoveMiddleTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.remove(SIZE / 2);
        return System.currentTimeMillis() - start;
    }

    private static long getRemoveLastTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        list.remove(list.size() - 1);
        return System.currentTimeMillis() - start;
    }

    // Вспомогательные методы для получения элементов
    private static long getReadTime(List<Integer> list) {
        long start = System.currentTimeMillis();
        int value = list.get(SIZE / 2); // Берем элемент из середины
        return System.currentTimeMillis() - start;
    }

    // Методы замеров для HashMap
    private static long getMapPutTime(Map<Integer, Integer> map) {
        long start = System.currentTimeMillis();
        map.put(-999, -999);
        return System.currentTimeMillis() - start;
    }

    private static long getMapRemoveTime(Map<Integer, Integer> map) {
        long start = System.currentTimeMillis();
        map.remove(SIZE / 2);
        return System.currentTimeMillis() - start;
    }

    private static long getMapGetTime(Map<Integer, Integer> map) {
        long start = System.currentTimeMillis();
        int value = map.get(SIZE / 2);
        return System.currentTimeMillis() - start;
    }
}