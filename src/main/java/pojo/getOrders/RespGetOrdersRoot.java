package pojo.getOrders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespGetOrdersRoot {
    private Boolean success;
    private List<Order> orders;
    private int total;
    private int totalToday;
}

