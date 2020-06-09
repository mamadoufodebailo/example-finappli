package fd.app.resources;

import fd.app.business.AccountService;
import fd.app.business.ProductService;
import fd.app.dao.ProductRepository;
import fd.app.dao.RoleRepository;
import fd.app.dao.UserRepository;
import fd.app.domain.Product;
import fd.app.rs.ProductResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductResource.class)
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
public class ProductResourceTests {
    @MockBean
    private ProductService productService;
    @Qualifier("userDetailsServiceImpl")
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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
