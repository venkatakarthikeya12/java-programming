import java.util.*;

public class Q7 {

    static void originalSolution(int n) {

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++)
            list.add(i);

        long start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.add(0, i);

        long end = System.nanoTime();

        System.out.println("ArrayList Front Insertion: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++)
            list.remove(0);

        end = System.nanoTime();

        System.out.println("ArrayList Front Removal: " +
                (end - start) / 1000000.0 + " ms");
    }

    static void redesignedSolution(int n) {

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++)
            deque.addLast(i);

        long start = System.nanoTime();

        for (int i = 0; i < n; i++)
            deque.addFirst(i);

        long end = System.nanoTime();

        System.out.println("ArrayDeque Front Insertion: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++)
            deque.removeFirst();

        end = System.nanoTime();

        System.out.println("ArrayDeque Front Removal: " +
                (end - start) / 1000000.0 + " ms");
    }

    public static void main(String[] args) {

        int[] sizes = {1000, 5000, 10000};

        for (int n : sizes) {

            System.out.println("\nPlaylist Size: " + n);

            System.out.println("\nOriginal Solution:");

            originalSolution(n);

            System.out.println("\nRedesigned Solution:");

            redesignedSolution(n);
        }
    }
}