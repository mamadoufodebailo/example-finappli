package fd.app.service;

import fd.app.domain.Product;
import fd.app.domain.ProductItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PanierImpl implements Panier {
    private Map<String, ProductItem> items = new HashMap<>();

    @Override
    public ProductItem addItem(Product product, int quantity) {
        ProductItem productItem = items.get(product.getCode());

        if (productItem == null) {
            productItem = new ProductItem();
            productItem.setProduct(product);
            productItem.setQuantity_selling(quantity);
            productItem.setPrice_selling(product.getPrice());

            items.put(product.getCode(), productItem);
        }
        else {
            productItem.setQuantity_selling(productItem.getQuantity_selling() + quantity);
        }

        return productItem;
    }

    @Override
    public List<ProductItem> getItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public int getItemSize() {
        return items.size();
    }

    @Override
    public void deleteItem(String code) {
        items.remove(code);
    }

    @Override
    public double getSum() {
        double total = 0;

        for(ProductItem productItem : items.values()){
            total += productItem.getPrice_selling() * productItem.getQuantity_selling();
        }

        return total;
    }
}
