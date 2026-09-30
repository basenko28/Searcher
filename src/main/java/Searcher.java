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
}
