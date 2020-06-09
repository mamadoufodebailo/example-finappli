package fd.app.service;

import fd.app.domain.Product;
import fd.app.domain.ProductItem;

import java.util.List;

public interface Panier {
    ProductItem addItem(Product product, int quantity);
    List<ProductItem> getItems();
    int getItemSize();
    void deleteItem(String code);
    double getSum();
}
