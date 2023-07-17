package pojo.orderCreation.responseWithAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespOrderCreationAuthRoot {
    public boolean success;
    public String name;
    public Order order;
}

