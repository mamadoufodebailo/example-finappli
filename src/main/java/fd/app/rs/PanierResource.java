package fd.app.rs;

import fd.app.beans.ProductBean;
import fd.app.business.ProductService;
import fd.app.domain.Product;
import fd.app.domain.ProductItem;
import fd.app.exception.NotFoundException;
import fd.app.service.Panier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class PanierResource {
    @Autowired
    private Panier panier;
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ProductItem>> getItems() {
        Collection<ProductItem> items = panier.getItems();

        if (items.isEmpty())
            throw new NotFoundException("Le panier est vide");

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(path = "/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductItem> addItem(@RequestBody ProductBean productBean) {
        Optional<Product> productOptional = productService.getOne(productBean.getCode());

        if (!productOptional.isPresent())
            throw new NotFoundException("Product not found !");

        return new ResponseEntity<>(panier.addItem(productOptional.get(), productBean.getQuantity()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/items/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable String code) {
        panier.deleteItem(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
