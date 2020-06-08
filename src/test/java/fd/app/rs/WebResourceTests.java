package fd.app.rs;

import fd.app.business.ProductService;
import fd.app.domain.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest
public class WebResourceTests {
    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mvc;

    @Test
    void test_getAll() throws Exception {
        List<Product> products = Arrays.asList(new Product("PC-100", "HP 960",
                        "", 430, 7),
                new Product("PC-200", "Acer A9000", "", 320, 4),
                new Product("PC-300", "Samsung S7000", "", 150, 5));

        Mockito.when(productService.getAll()).thenReturn(products);

        mvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.[0].code", is("PC-100")));
    }

    @Test
    void test_getOne() throws Exception {
        Product product = new Product("TV-100", "LG Split", "", 650, 2);

        Mockito.when(productService.getOne("TV-100")).thenReturn(Optional.of(product));

        mvc.perform(get("/products/"+product.getCode()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code", is("TV-100")));
    }
}
