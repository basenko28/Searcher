import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private Searcher.Product[] makeCatalog() {
        return new Searcher.Product[]{
                new Searcher.Product(10, "A"),
                new Searcher.Product(20, "B"),
                new Searcher.Product(30, "C"),
        };
    }

    @Test
    void linearFindsProduct() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {20};
        int code = Searcher.linearSearch(catalog, orders)[0].code;
        assertEquals(20, code);
    }

    @Test
    void sortBinaryFindsProduct() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {20};
        int code = Searcher.sortBinarySearch(catalog, orders)[0].code;
        assertEquals(20, code);
    }

    @Test
    void hashMapFindsProduct() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {20};
        int code = Searcher.hashMapSearch(catalog, orders)[0].code;
        assertEquals(20, code);
    }

    @Test
    void linearReturnsNullForMissing() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {999};
        assertNull(Searcher.linearSearch(catalog, orders)[0]);
    }

    @Test
    void sortBinaryReturnsNullForMissing() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {999};
        assertNull(Searcher.sortBinarySearch(catalog, orders)[0]);
    }

    @Test
    void hashMapReturnsNullForMissing() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {999};
        assertNull(Searcher.hashMapSearch(catalog, orders)[0]);
    }

    @Test
    void emptyOrdersReturnEmptyResult() {
        Searcher.Product[] catalog = makeCatalog();
        int[] orders = {};
        assertEquals(0, Searcher.linearSearch(catalog, orders).length);
    }
}