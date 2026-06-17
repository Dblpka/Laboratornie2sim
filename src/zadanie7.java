import java.util.ArrayList;
import java.util.List;

public class zadanie7 {
    public static void main(String[] args) {
        int n = 10000; // Количество людей в кругу

        long startTime = System.nanoTime();
        int survivor = getSurvivor(n);
        long endTime = System.nanoTime();

        System.out.println("ArrayList: Остался человек номер " + survivor);
        System.out.println("Время выполнения: " + (endTime - startTime) / 1_000_000.0 + " мс");
    }

    public static int getSurvivor(int n) {
        List<Integer> list = new ArrayList<>();
        // Заполняем круг людьми от 1 до N
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        int index = 0;
        // Удаляем каждого второго, пока не останется один
        while (list.size() > 1) {
            // Формула для подсчета индекса следующего удаляемого человека по кругу
            index = (index + 1) % list.size();
            list.remove(index);
        }

        return list.get(0);
    }
}