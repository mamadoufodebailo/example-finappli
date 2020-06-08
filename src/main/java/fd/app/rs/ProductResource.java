package fd.app.rs;

import fd.app.business.ProductService;
import fd.app.domain.Product;
import fd.app.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductResource {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();

        if (products.isEmpty())
            throw new NotFoundException("Aucun product trouvé");

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/products/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getOne(@PathVariable String code) {
        Optional<Product> productOptional = productService.getOne(code);

        if (!productOptional.isPresent())
            throw new NotFoundException("Product with code => "+ code +" not found !");

        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

}