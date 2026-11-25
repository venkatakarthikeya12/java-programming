import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class LineAndGrowthBenchmark {
    public static long unbufferedLines(Path file) throws IOException {
        long lines = 0;
        try (FileInputStream in = new FileInputStream(file.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                if (value == '\n') lines++;
            }
        }
        return lines;
    }

    public static long bufferedLines(Path file) throws IOException {
        long lines = 0;
        try (BufferedInputStream in = new BufferedInputStream(
                new FileInputStream(file.toFile()), 65536)) {
            int value;
            while ((value = in.read()) != -1) {
                if (value == '\n') lines++;
            }
        }
        return lines;
    }

    public static void main(String[] args) throws IOException {
        Path base = Files.createTempDirectory("io-benchmark");
        for (int mb : new int[]{1, 10, 50}) {
            Path file = base.resolve(mb + "mb.txt");
            createFile(file, mb * 1024 * 1024);
            long u1 = System.nanoTime();
            unbufferedLines(file);
            long u = (System.nanoTime() - u1) / 1_000_000;
            long b1 = System.nanoTime();
            bufferedLines(file);
            long b = (System.nanoTime() - b1) / 1_000_000;
            System.out.printf("%d MB unbuffered=%d ms buffered=%d ms%n", mb, u, b);
        }

        for (int n : new int[]{1000, 10000, 100000}) {
            Path passFile = base.resolve("passes-" + n + ".csv");
            try (BufferedWriter w = Files.newBufferedWriter(passFile, StandardCharsets.UTF_8)) {
                for (int i = 1; i <= n; i++) {
                    w.write("P" + i + ",192311" + i + ",Student " + i + ","
                            + (i % 12 + 1) + "," + (4500.0 + i) + "\n");
                }
            }
            PassStore store = new PassStore(passFile);
            long t1 = System.nanoTime();
            store.find("P" + n);
            long t = (System.nanoTime() - t1) / 1_000_000;
            System.out.printf("%d passes find=%d ms%n", n, t);
        }
    }

    private static void createFile(Path file, int bytes) throws IOException {
        byte[] block = "Attendance student record\n".getBytes(StandardCharsets.UTF_8);
        try (OutputStream out = Files.newOutputStream(file)) {
            for (int written = 0; written < bytes;) {
                int size = Math.min(block.length, bytes - written);
                out.write(block, 0, size);
                written += size;
            }
        }
    }
}
