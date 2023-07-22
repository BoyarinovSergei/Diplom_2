package pojo.orderCreation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespOrderCreationWithNoAuth {
    private Boolean success;
    private String name;
    private Order order;
}
