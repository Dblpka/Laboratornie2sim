class Node1 {
    public int value;
    public Node1 next;
    Node1(int value, Node1 next) {
        this.value = value;
        this.next = next;
    }
}

public class zadanie5_1 {
    public static void main(String[] args) {
        Node1 head = null; // Изначально список пуст

        // Формируем список из элементов: 3, 2, 1, 0
        for (int i = 0; i <= 3; i++) {
            head = new Node1(i, head);
        }

        // Вывод списка на экран (результат будет: 3 2 1 0)
        System.out.print("Список с головы:");
        Node1 ref = head;
        while (ref != null) {
            System.out.print(" " + ref.value);
            ref = ref.next;
        }
    }
}