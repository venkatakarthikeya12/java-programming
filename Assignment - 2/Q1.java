public class Q1 {

    static String usingString(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s = s + "Ticket" + i + " ";
        }
        return s;
    }

    static String usingStringBuilder(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("Ticket").append(i).append(" ");
        }
        return sb.toString();
    }

    static String usingStringBuffer(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append("Ticket").append(i).append(" ");
        }
        return sb.toString();
    }

    static void test(String name, int n, int type) {
        long start = System.nanoTime();

        String result = "";

        if (type == 1)
            result = usingString(n);
        else if (type == 2)
            result = usingStringBuilder(n);
        else
            result = usingStringBuffer(n);

        long end = System.nanoTime();

        System.out.println(name + " : " +
                (end - start) / 1000000.0 + " ms");

        System.out.println("Characters: " + result.length());
    }

    public static void main(String[] args) {

        int[] workloads = {10, 1000, 10000};

        for (int n : workloads) {
            System.out.println("\nTickets: " + n);

            test("String", n, 1);
            test("StringBuilder", n, 2);
            test("StringBuffer", n, 3);
        }
    }
}