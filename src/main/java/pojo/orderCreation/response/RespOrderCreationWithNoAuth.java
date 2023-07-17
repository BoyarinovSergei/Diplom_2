package pojo.orderCreation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespOrderCreationWithNoAuth {
    public boolean success;
    public String name;
    public Order order;
}
