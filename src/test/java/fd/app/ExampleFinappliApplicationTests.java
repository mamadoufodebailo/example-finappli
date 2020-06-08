package fd.app;

import fd.app.domain.*;
import fd.app.service.Panier;
import fd.app.service.PanierImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExampleFinappliApplicationTests {
    private Product product;
    private ProductItem productItem;
    private Panier panier;
    private AppUser user;
    private AppRole role;

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

        role = new AppRole(null, "ADMIN");
        user = new AppUser(null, "fode", "passer123", Arrays.asList(role));
    }

    @Test
    void test_entity() {
        assertNotNull(product);
        assertNotNull(productItem);
        assertNotNull(role);
        assertNotNull(user);
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
