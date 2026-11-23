import java.util.*;

public class Q2 {

    static void testArrayList(int n) {
        ArrayList<Integer> list = new ArrayList<>();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.add(i);

        long end = System.nanoTime();

        System.out.println("ArrayList Add End: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        list.add(0, 100);
        list.add(n / 2, 200);

        end = System.nanoTime();

        System.out.println("ArrayList Insert: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.get(i);

        end = System.nanoTime();

        System.out.println("ArrayList Access: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int x : list) {
            int temp = x;
        }

        end = System.nanoTime();

        System.out.println("ArrayList Iteration: " +
                (end - start) / 1000000.0 + " ms");
    }

    static void testLinkedList(int n) {
        LinkedList<Integer> list = new LinkedList<>();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.add(i);

        long end = System.nanoTime();

        System.out.println("LinkedList Add End: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        list.add(0, 100);
        list.add(n / 2, 200);

        end = System.nanoTime();

        System.out.println("LinkedList Insert: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.get(i);

        end = System.nanoTime();

        System.out.println("LinkedList Access: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int x : list) {
            int temp = x;
        }

        end = System.nanoTime();

        System.out.println("LinkedList Iteration: " +
                (end - start) / 1000000.0 + " ms");
    }

    static void testArrayDeque(int n) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++)
            deque.addLast(i);

        long end = System.nanoTime();

        System.out.println("ArrayDeque Add End: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++)
            deque.addFirst(i);

        end = System.nanoTime();

        System.out.println("ArrayDeque Add Front: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        while (!deque.isEmpty())
            deque.removeFirst();

        end = System.nanoTime();

        System.out.println("ArrayDeque Remove Front: " +
                (end - start) / 1000000.0 + " ms");
    }

    public static void main(String[] args) {

        int[] sizes = {1000, 5000, 10000};

        for (int n : sizes) {
            System.out.println("\nSize: " + n);

            testArrayList(n);
            testLinkedList(n);
            testArrayDeque(n);
        }
    }
}