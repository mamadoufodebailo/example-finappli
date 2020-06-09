package fd.app.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fd.app.beans.ProductBean;
import fd.app.business.AccountService;
import fd.app.business.ProductService;
import fd.app.dao.ProductRepository;
import fd.app.dao.RoleRepository;
import fd.app.domain.Product;
import fd.app.domain.ProductItem;
import fd.app.rs.PanierResource;
import fd.app.service.Panier;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(PanierResource.class)
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
public class PanierResourceTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private Panier panier;
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
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_avoir_item_panier() throws Exception {
        List<ProductItem> items = Arrays
                .asList(new ProductItem(null, 3, 908,
                                new Product("PC-134", "HP Laptop H900", "Machine HP",
                                        1800.0, 10)),
                        new ProductItem(null, 5, 105, new Product()),
                        new ProductItem(null, 2, 210, new Product()));

        Mockito.when(panier.getItems()).thenReturn(items);

        mvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.[0].quantity_selling", is(3)));
    }

    @Test
    void test_mettre_item_panier() throws Exception {
        Product product = new Product("PA-100", "Chargeur Dell", "Chargeur d'une machine Dell",
                540, 3);

        ProductBean productBean = new ProductBean("PA-100", 3);

        Mockito.when(panier.addItem(product, productBean.getQuantity()))
                .thenReturn(new ProductItem(null, productBean.getQuantity(), product.getPrice(), product));

        mvc.perform(post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(objectMapper.writeValueAsBytes(productBean)))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_supprimer_item_panier() throws Exception {
        String code = "PC-134";
        Panier panierServiceSpy = Mockito.spy(panier);

        Mockito.doNothing().when(panierServiceSpy).deleteItem(code);
        mvc.perform(delete("/items/"+ code).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(panier).deleteItem(code);
    }
}
