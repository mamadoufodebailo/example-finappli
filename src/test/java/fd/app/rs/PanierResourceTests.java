package fd.app.rs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fd.app.beans.ProductBean;
import fd.app.business.ProductService;
import fd.app.dao.ProductRepository;
import fd.app.domain.Product;
import fd.app.domain.ProductItem;
import fd.app.service.Panier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(PanierResource.class)
public class PanierResourceTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private Panier panier;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    @Test
    void test_get_item_panier() throws Exception {
        Collection<ProductItem> items = Arrays
                .asList(new ProductItem(null, 3, 908,
                                new Product("PM-170", "Itel", "", 200, 1)),
                        new ProductItem(null, 5, 105, new Product()),
                        new ProductItem(null, 2, 210, new Product()));

        Mockito.when(panier.getItems()).thenReturn(items);

        mvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.[0].quantity_selling", is(3)));
    }

    @Test
    void test_add_item_panier() throws Exception {
        ProductBean productBean = new ProductBean("PA-100", 3);

        Product product = new Product(productBean.getCode(), "Chargeur Dell", "", 370, 4);

        Mockito.when(panier.addItem(product, productBean.getQuantity()))
                .thenReturn(new ProductItem(null, productBean.getQuantity(), product.getPrice(), product));

        mvc.perform(post("/items").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(objectMapper.writeValueAsBytes(productBean)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.code", is(productBean.getCode())));
    }

    @Test
    void test_delete_item_panier() throws Exception {
        String code = "PM-170";
        Panier panierServiceSpy = Mockito.spy(panier);

        Mockito.doNothing().when(panierServiceSpy).deleteItem(code);
        mvc.perform(delete("/items/"+ code).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(panier).deleteItem(code);
    }
}
