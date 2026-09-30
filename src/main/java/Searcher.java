import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Searcher {
    static class Product {
        final int code;
        final String name;

        Product(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    public static Product[] linearSearch(Product[] catalog, int[] orders) {
        Product[] result = new Product[orders.length];
        for (int i = 0; i < orders.length; i++) {
            Product found = null;
            for (Product p : catalog) {
                if (p.code == orders[i]) {
                    found = p;
                    break;
                }
            }
            result[i] = found;
        }
        return result;
    }

    public static Product[] sortBinarySearch(Product[] catalog, int[] orders) {
        Product[] sorted = catalog.clone();
        Arrays.sort(sorted, Comparator.comparingInt(p -> p.code));

        Product[] result = new Product[orders.length];
        for (int i = 0; i < orders.length; i++) {
            int idx = binarySearch(sorted, orders[i]);
            result[i] = idx >= 0 ? sorted[idx] : null;
        }
        return result;
    }

    static int binarySearch(Product[] arr, int code) {
        int lo = 0, hi = arr.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid].code == code) return mid;
            if (arr[mid].code < code) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
}
