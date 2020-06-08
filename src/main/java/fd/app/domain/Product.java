package fd.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    private String code;
    @NotNull
    @NotEmpty(message = "Name is required")
    private String name;
    private String description;
    @NotNull
    @NotEmpty(message = "Price is required")
    private double price;
    @NotNull
    @NotEmpty(message = "Quantity is required")
    private int quantity_remaining;
}
