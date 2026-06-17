class Node2 {
    public int value;
    public Node2 next;
    Node2(int value, Node2 next) {
        this.value = value;
        this.next = next;
    }
}

public class zadanie5_2 {
    public static void main(String[] args) {
        Node2 head = new Node2(0, null); // Создаем первый узел (голову)
        Node2 tail = head;               // Хвост пока совпадает с головой

        // Добавляем остальные элементы: 1, 2, 3 в хвост
        for (int i = 1; i <= 3; i++) {
            tail.next = new Node2(i, null); // Создаем новый узел после текущего хвоста
            tail = tail.next;               // Сдвигаем указатель хвоста на новый узел
        }

        // Вывод списка на экран (результат будет: 0 1 2 3)
        System.out.print("Список с хвоста:");
        Node2 ref = head;
        while (ref != null) {
            System.out.print(" " + ref.value);
            ref = ref.next;
        }
    }
}