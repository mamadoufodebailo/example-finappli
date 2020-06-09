package fd.app.business;

import fd.app.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
public class ProductServiceTests {
    @MockBean
    private ProductService productService;

    @BeforeEach
    void init() {
        Mockito.when(productService.getAll())
                .thenReturn(Arrays.asList(
                        new Product("PC-100", "HP 960", "", 430, 7),
                        new Product("PC-200", "Acer A9000", "", 320, 4),
                        new Product("PC-300", "Samsung S7000", "", 150, 5)));
    }

    @Test
    void test_getAll() {
        List<Product> products = productService.getAll();

        assertEquals(3, products.size());
    }

}
