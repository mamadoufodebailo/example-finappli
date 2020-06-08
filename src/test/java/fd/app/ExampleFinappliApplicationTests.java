package fd.app;

import fd.app.domain.*;
import fd.app.service.Panier;
import fd.app.service.PanierImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleFinappliApplicationTests {
    private Product product;
    private ProductItem productItem;
    private Panier panier;

    @BeforeEach
    void init() {
        panier = new PanierImpl();
        Stream.of(new Product("TV-100", "LG Split", "Télévision Samsung", 800, 3),
                new Product("PC-200", "Toshiba-S900", "Machine Toshiba", 700, 5),
                new Product("PM-300", "Nokia lumia", "Portable Nokia", 650, 3))
                .forEach(product -> {
            panier.addItem(product, product.getQuantity_remaining());
        });

        product = new Product("PC-100", "HP-900", "Machine HP DualCore",
                900, 7);
        productItem = new ProductItem(null, 2, 900, product);
    }

    @Test
    void test_entity() {
        assertNotNull(product);
        assertNotNull(productItem);
    }

    @Test
    void test_ajout() {
        panier.addItem(new Product("PM-200", "Samsung duos", "Portable Samsung",
                550, 3), 1);
        assertEquals(4, panier.getItemSize());
    }

    @Test
    void test_supprimer() {
        panier.deleteItem("PM-200");
        assertEquals(3, panier.getItemSize());
    }

    @Test
    void test_sum() {
        assertEquals(7850, panier.getSum());
    }

}
