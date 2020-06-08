package fd.app.business;

import fd.app.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product add(Product product);
    List<Product> getAll();
    Optional<Product> getOne(String code);
}
