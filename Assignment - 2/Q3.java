import java.util.*;

class Student {

    private final String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Student))
            return false;

        Student s = (Student) o;

        return regNo.equals(s.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

public class Q3 {

    public static void main(String[] args) {

        Set<Student> present = new HashSet<>();

        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + present.size());

        System.out.println("\nStudent Details:");

        for (Student s : present)
            System.out.println(s);

        System.out.println("\nTreeSet Test:");

        Set<Student> tree = new TreeSet<>(
            Comparator.comparing(s -> s.toString().split(" ")[0])
        );

        tree.add(new Student("192311001", "Arun"));
        tree.add(new Student("192311002", "Divya"));
        tree.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + tree.size());

        System.out.println("\nHashMap Test:");

        Map<String, Student> map = new HashMap<>();

        Student s1 = new Student("192311001", "Arun");
        Student s2 = new Student("192311002", "Divya");

        map.put("192311001", s1);
        map.put("192311002", s2);
        map.put("192311001", new Student("192311001", "Arun"));

        System.out.println("Unique students: " + map.size());
    }
}