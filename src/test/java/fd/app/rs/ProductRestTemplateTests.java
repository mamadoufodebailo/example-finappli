package fd.app.rs;

import fd.app.ExampleFinappliApplication;
import fd.app.domain.Product;;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = ExampleFinappliApplication.class)
@ActiveProfiles("integration")
@TestPropertySource("classpath:application-integration.yml")
public class ProductRestTemplateTests {
    private static final String host = "http://localhost:";
    private static final int port = 8080;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test_getAll() {
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(host + port + "/products", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Product>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
    }

    @Test
    void test_getOne() {
        String code = "PC-134";

        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(host + port + "/products/"+code, Product.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertEquals(code, Objects.requireNonNull(responseEntity.getBody()).getCode());
    }

}
