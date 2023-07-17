package pojo.orderCreation.responseWithAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public List<Ingredient> ingredients;
    public String _id;
    public Owner owner;
    public String status;
    public String name;
    public String createdAt;
    public String updatedAt;
    public int number;
    public int price;
}
