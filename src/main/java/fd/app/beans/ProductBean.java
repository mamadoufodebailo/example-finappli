package fd.app.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBean {
    private String code;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
