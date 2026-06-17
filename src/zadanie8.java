import java.util.Scanner;

public class zadanie8 {

    // Внутренний класс структуры элемента списка (Узел)
    private static class Node {
        public int value;
        public Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node head = null; // Голова нашего списка
    private final Scanner scanner = new Scanner(System.in);

    // =========================================================================
    // а) МЕТОДЫ С ИСПОЛЬЗОВАНИЕМ ЦИКЛОВ
    // =========================================================================

    // 1. Ввод с головы (итерационный)
    public void createHead() {
        head = null; // Очищаем список перед вводом
        System.out.println("Ввод с головы. Вводите числа (для окончания введите -1):");
        while (true) {
            int val = scanner.nextInt();
            if (val == -1) break;
            head = new Node(val, head);
        }
    }

    // 2. Ввод с хвоста (итерационный)
    public void createTail() {
        head = null;
        System.out.println("Ввод с хвоста. Вводите числа (для окончания введите -1):");
        Node tail = null;
        while (true) {
            int val = scanner.nextInt();
            if (val == -1) break;
            Node newNode = new Node(val, null);
            if (head == null) {
                head = newNode;
                tail = head;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }
    }

    // 3. Вывод списка в строку (итерационный)
    @Override
    public String toString() {
        if (head == null) return "Список пуст";
        StringBuilder sb = new StringBuilder();
        Node ref = head;
        while (ref != null) {
            sb.append(ref.value).append(" -> ");
            ref = ref.next;
        }
        sb.append("null");
        return sb.toString();
    }

    // 4. Добавление элемента в начало списка
    public void addFirst(int value) {
        head = new Node(value, head);
    }

    // 5. Добавление элемента в конец списка
    public void addLast(int value) {
        Node newNode = new Node(value, null);
        if (head == null) {
            head = newNode;
            return;
        }
        Node ref = head;
        while (ref.next != null) {
            ref = ref.next;
        }
        ref.next = newNode;
    }

    // 6. Вставка элемента по индексу (нумерация с 0)
    public void insert(int value, int index) {
        if (index < 0) {
            System.out.println("Ошибка: Индекс не может быть отрицательным.");
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node ref = head;
        int count = 0;
        // Ищем элемент, стоящий ДО позиции вставки
        while (ref != null && count < index - 1) {
            ref = ref.next;
            count++;
        }
        if (ref == null) {
            System.out.println("Ошибка: Индекс выходит за границы списка.");
        } else {
            ref.next = new Node(value, ref.next);
        }
    }

    // 7. Удаление элемента с головы списка
    public void removeFirst() {
        if (head == null) {
            System.out.println("Ошибка: Список пуст, удалять нечего.");
            return;
        }
        head = head.next;
    }

    // 8. Удаление последнего элемента списка
    public void removeLast() {
        if (head == null) {
            System.out.println("Ошибка: Список пуст.");
            return;
        }
        if (head.next == null) {
            head = null;
            return;
        }
        Node ref = head;
        // Бежим до предпоследнего элемента
        while (ref.next.next != null) {
            ref = ref.next;
        }
        ref.next = null;
    }

    // 9. Удаление элемента по индексу
    public void remove(int index) {
        if (head == null || index < 0) {
            System.out.println("Ошибка удаления.");
            return;
        }
        if (index == 0) {
            removeFirst();
            return;
        }
        Node ref = head;
        int count = 0;
        // Ищем элемент, стоящий ДО удаляемого
        while (ref != null && count < index - 1) {
            ref = ref.next;
            count++;
        }
        if (ref == null || ref.next == null) {
            System.out.println("Ошибка: Индекс выходит за границы списка.");
        } else {
            ref.next = ref.next.next;
        }
    }

    // =========================================================================
    // б) МЕТОДЫ С ИСПОЛЬЗОВАНИЕМ РЕКУРСИИ
    // =========================================================================

    // 1. Рекурсивный ввод с головы (публичная точка входа)
    public void createHeadRec() {
        head = null;
        System.out.println("Рекурсивный ввод с головы. Вводите числа (-1 для конца):");
        head = inputHeadRecStep();
    }

    // Вспомогательный рекурсивный шаг для ввода с головы
    private Node inputHeadRecStep() {
        int val = scanner.nextInt();
        if (val == -1) {
            return null;
        }
        // Уходим в рекурсию за следующим узлом
        Node nextNode = inputHeadRecStep();
        // Текущее число становится головой для всех последующих
        return new Node(val, nextNode);
    }

    // 2. Рекурсивный ввод с хвоста (публичная точка входа)
    public void createTailRec() {
        head = null;
        System.out.println("Рекурсивный ввод с хвоста. Вводите числа (-1 для конца):");
        head = inputTailRecStep();
    }

    // Вспомогательный рекурсивный шаг для ввода с хвоста
    private Node inputTailRecStep() {
        int val = scanner.nextInt();
        if (val == -1) {
            return null;
        }
        Node currentNode = new Node(val, null);
        // Ссылка на хвост привязывается на выходе из рекурсии
        currentNode.next = inputTailRecStep();
        return currentNode;
    }

    // 3. Рекурсивный вывод списка в строку (публичная точка входа)
    public String toStringRec() {
        if (head == null) return "Список пуст";
        return toStringRecStep(head);
    }

    // Вспомогательный рекурсивный шаг для сборки строки
    private String toStringRecStep(Node current) {
        if (current == null) {
            return "null";
        }
        return current.value + " -> " + toStringRecStep(current.next);
    }

    // =========================================================================
    // ТОЧКА ВХОДА ДЛЯ ДЕМОНСТРАЦИИ РАБОТЫ
    // =========================================================================
    public static void main(String[] args) {
        zadanie8 list = new zadanie8();

        // Тестируем циклы
        list.createTail();
        System.out.println("После ввода с хвоста: " + list);

        System.out.println("\n--- Тестируем операции вставки/удаления ---");
        list.addFirst(99);
        list.addLast(88);
        System.out.println("Добавили 99 в начало и 88 в конец: " + list);

        list.insert(55, 2);
        System.out.println("Вставили 55 на позицию с индексом 2: " + list);

        list.removeFirst();
        list.removeLast();
        System.out.println("Удалили первый и последний: " + list);

        list.remove(1);
        System.out.println("Удалили элемент с индексом 1: " + list);

        // Тестируем рекурсию
        System.out.println("\n--- Тестируем рекурсивные методы ---");
        list.createHeadRec();
        System.out.print("Вывод через toStringRec(): ");
        System.out.println(list.toStringRec());
    }
}