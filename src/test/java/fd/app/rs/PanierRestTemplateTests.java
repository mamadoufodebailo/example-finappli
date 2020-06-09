package fd.app.rs;

import fd.app.ExampleFinappliApplication;
import fd.app.beans.ProductBean;
import fd.app.domain.Product;
import fd.app.domain.ProductItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = ExampleFinappliApplication.class)
@ActiveProfiles("integration")
@TestPropertySource("classpath:application-integration.yml")
public class PanierRestTemplateTests {
    private static final String host = "http://localhost:";
    private static final int port = 8080;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getItems() {
        ResponseEntity<List<ProductItem>> responseEntity = restTemplate.exchange(host + port + "/items", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<ProductItem>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
    }

    @Test
    void addItem() {
        ProductBean productBean = new ProductBean("PC-100", 3);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductBean> entity = new HttpEntity<>(productBean, httpHeaders);

        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(host + port + "/items", entity, Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
    }

    @Test
    void deleteItem() {
        String code = "PM-170";

        ResponseEntity<Object> responseEntity = restTemplate.exchange(host + port + "/items/"+code, HttpMethod.DELETE,
                null, Object.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
