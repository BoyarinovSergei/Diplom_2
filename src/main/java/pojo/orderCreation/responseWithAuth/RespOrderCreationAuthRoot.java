package pojo.orderCreation.responseWithAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespOrderCreationAuthRoot {
    private Boolean success;
    private String name;
    private Order order;
}

