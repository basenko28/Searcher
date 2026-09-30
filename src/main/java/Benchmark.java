import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Benchmark {

    static Searcher.Product[] generateCatalog(int n, long seed) {
        Random rnd = new Random(seed);
        Set<Integer> used = new HashSet<>();
        Searcher.Product[] catalog = new Searcher.Product[n];
        for (int i = 0; i < n; i++) {
            int code;
            do {
                code = rnd.nextInt(Integer.MAX_VALUE);
            } while (!used.add(code));
            catalog[i] = new Searcher.Product(code, "Product_" + code);
        }
        return catalog;
    }

    static int[] generateOrders(int m, Searcher.Product[] catalog, long seed) {
        Random rnd = new Random(seed);
        int[] orders = new int[m];
        for (int i = 0; i < m; i++) {
            if (rnd.nextDouble() < 0.9) {
                orders[i] = catalog[rnd.nextInt(catalog.length)].code;
            } else {
                orders[i] = rnd.nextInt(Integer.MAX_VALUE);
            }
        }
        return orders;
    }


    static double timeMsAvg(Runnable r, int reps) {
        for (int i = 0; i < 3; i++) {
            r.run();
        }
        long start = System.nanoTime();
        for (int i = 0; i < reps; i++) {
            r.run();
        }
        long end = System.nanoTime();

        return (end - start) / 1_000_000.0 / reps;
    }


    public static void runNM() throws Exception {
        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000};

        try (PrintWriter out = new PrintWriter("results.csv")) {
            out.println("n,m,bytes,linear_ms,sortbinary_ms,hashmap_ms");

            for (int size : sizes) {
                int n = size, m = size;

                Searcher.Product[] catalog = generateCatalog(n, 42);
                int[] orders = generateOrders(m, catalog, 43);
                long bytes = (long) n * 4 + (long) m * 4;

                int reps = n <= 10_000 ? 20 : 3;

                double t1 = timeMsAvg(() -> Searcher.linearSearch(catalog, orders), reps);
                double t2 = timeMsAvg(() -> Searcher.sortBinarySearch(catalog, orders), reps);
                double t3 = timeMsAvg(() -> Searcher.hashMapSearch(catalog, orders), reps);

                out.printf("%d,%d,%d,%.4f,%.4f,%.4f%n", n, m, bytes, t1, t2, t3);
                System.out.printf("n=%d bytes=%d linear=%.3f sortbin=%.3f hash=%.3f%n",
                        n, bytes, t1, t2, t3);
            }
        }
        System.out.println("CSV сохранён: results.csv");
    }


    public static void runNConst() throws Exception {
        int n = 100_000;
        int[] mValues = {1, 10, 100, 1000, 10000, 100000, 1000000};

        Searcher.Product[] catalog = generateCatalog(n, 42);

        try (PrintWriter out = new PrintWriter("results_m.csv")) {
            out.println("n,m,bytes,linear_ms,sortbinary_ms,hashmap_ms");

            for (int m : mValues) {
                int[] orders = generateOrders(m, catalog, 43);
                long bytes = (long) n * 4 + (long) m * 4;
                int reps = m <= 10_000 ? 20 : 3;

                double t1 = timeMsAvg(() -> Searcher.linearSearch(catalog, orders), reps);
                double t2 = timeMsAvg(() -> Searcher.sortBinarySearch(catalog, orders), reps);
                double t3 = timeMsAvg(() -> Searcher.hashMapSearch(catalog, orders), reps);

                out.printf("%d,%d,%d,%.4f,%.4f,%.4f%n", n, m, bytes, t1, t2, t3);
                System.out.printf("m=%d linear=%.3f sortbin=%.3f hash=%.3f%n",
                        m, t1, t2, t3);
            }
        }
        System.out.println("CSV сохранён: results_m.csv");
    }
}