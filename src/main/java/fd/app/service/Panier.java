package fd.app.service;

import fd.app.domain.Product;
import fd.app.domain.ProductItem;

import java.util.Collection;

public interface Panier {
    ProductItem addItem(Product product, int quantity);
    Collection<ProductItem> getItems();
    int getItemSize();
    void deleteItem(String code);
    double getSum();
}
