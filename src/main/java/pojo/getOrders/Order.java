package pojo.getOrders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public String _id;
    public List<String> ingredients;
    public String status;
    public String name;
    public String createdAt;
    public String updatedAt;
    public int number;
}

