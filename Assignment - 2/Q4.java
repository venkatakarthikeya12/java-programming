import java.util.*;

public class Q4 {

    public static void main(String[] args) {

        HashMap<String, Integer> hashMap = new HashMap<>();

        TreeMap<String, Integer> treeMap = new TreeMap<>();

        LinkedHashMap<String, Integer> linkedMap =
                new LinkedHashMap<>();

        String[] codes = {
            "M103", "M101", "M105", "M102", "M104"
        };

        int[] stocks = {50, 100, 75, 40, 90};

        for (int i = 0; i < codes.length; i++) {

            hashMap.put(codes[i], stocks[i]);

            treeMap.put(codes[i], stocks[i]);

            linkedMap.put(codes[i], stocks[i]);
        }

        System.out.println("HashMap:");

        System.out.println(hashMap);

        System.out.println("\nTreeMap (Code Order):");

        System.out.println(treeMap);

        System.out.println("\nLinkedHashMap (Insertion Order):");

        System.out.println(linkedMap);

        System.out.println("\nStock Lookup:");

        System.out.println("M103 Stock: " + hashMap.get("M103"));

        System.out.println("\n100000 Lookups:");

        long start = System.nanoTime();

        for (int i = 0; i < 100000; i++)
            hashMap.get("M103");

        long end = System.nanoTime();

        System.out.println("HashMap: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < 100000; i++)
            treeMap.get("M103");

        end = System.nanoTime();

        System.out.println("TreeMap: " +
                (end - start) / 1000000.0 + " ms");

        start = System.nanoTime();

        for (int i = 0; i < 100000; i++)
            linkedMap.get("M103");

        end = System.nanoTime();

        System.out.println("LinkedHashMap: " +
                (end - start) / 1000000.0 + " ms");

        System.out.println("\nCode Order Report:");

        for (Map.Entry<String, Integer> e : treeMap.entrySet())
            System.out.println(e.getKey() + " = " + e.getValue());

        System.out.println("\nInsertion Order Report:");

        for (Map.Entry<String, Integer> e : linkedMap.entrySet())
            System.out.println(e.getKey() + " = " + e.getValue());
    }
}