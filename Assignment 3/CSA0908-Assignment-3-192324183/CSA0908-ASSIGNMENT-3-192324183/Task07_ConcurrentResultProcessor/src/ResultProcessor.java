
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ResultProcessor {

    static final String INPUT_DIR =
            "Task07_ConcurrentResultProcessor/input";

    static class Result {
        String fileName;
        boolean success;
        String message;

        Result(String fileName, boolean success, String message) {
            this.fileName = fileName;
            this.success = success;
            this.message = message;
        }
    }

    // Generate 10,000 test files, including missing and corrupt files
    static void generateFiles() throws IOException {

        Path directory = Paths.get(INPUT_DIR);
        Files.createDirectories(directory);

        for (int i = 1; i <= 10000; i++) {

            Path file = directory.resolve("student" + i + ".txt");

            // Every 100th file is deliberately missing
            if (i % 100 == 0) {
                Files.deleteIfExists(file);
                continue;
            }

            // Every 97th file contains invalid data
            if (i % 97 == 0) {
                Files.write(file, Arrays.asList("INVALID_SCORE"));
            } else {
                int score = 50 + (i % 51);
                Files.write(file, Arrays.asList(String.valueOf(score)));
            }
        }
    }

    // Each file is processed independently
    static Callable<Result> createTask(Path file) {

        return () -> {

            try {
                String data = Files.readAllLines(file).get(0);
                int score = Integer.parseInt(data);

                if (score < 0 || score > 100) {
                    throw new IllegalArgumentException("Invalid score");
                }

                return new Result(
                        file.getFileName().toString(),
                        true,
                        "Score: " + score
                );

            } catch (IOException | NumberFormatException |
                     IllegalArgumentException e) {

                return new Result(
                        file.getFileName().toString(),
                        false,
                        e.getMessage()
                );
            }
        };
    }

    static void processFiles(int threadCount) throws Exception {

        ExecutorService executor =
                Executors.newFixedThreadPool(threadCount);

        ConcurrentLinkedQueue<Result> results =
                new ConcurrentLinkedQueue<>();

        List<Future<Result>> futures = new ArrayList<>();

        Path directory = Paths.get(INPUT_DIR);

        long startTime = System.nanoTime();

        try {
            for (int i = 1; i <= 10000; i++) {

                Path file = directory.resolve("student" + i + ".txt");

                futures.add(executor.submit(createTask(file)));
            }

            for (Future<Result> future : futures) {
                results.add(future.get());
            }

        } finally {
            executor.shutdown();
        }

        executor.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.nanoTime();

        int successful = 0;
        int failed = 0;

        for (Result result : results) {
            if (result.success) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println("\nThread Pool Size: " + threadCount);
        System.out.println("Total Files: " + results.size());
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
        System.out.printf("Execution Time: %.2f ms%n",
                (endTime - startTime) / 1_000_000.0);
    }

    public static void main(String[] args) throws Exception {

        System.out.println("SEMESTER RESULT PROCESSING SYSTEM");

        generateFiles();

        int[] poolSizes = {1, 2, 4, 8};

        for (int size : poolSizes) {
            processFiles(size);
        }

        System.out.println("\nAll processing experiments completed.");
    }
}
