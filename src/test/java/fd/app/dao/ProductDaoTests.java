package fd.app.dao;

import fd.app.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductDaoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void test_dao() {
        entityManager.merge(new Product("PA-100", "Chargeur Dell", "Chargeur d'une machine Dell",
                540, 3));

        Optional<Product> productOptional = productRepository.findById("PA-100");

        assertTrue(productOptional.isPresent());
        assertEquals("PA-100", productOptional.get().getCode());
    }
}
