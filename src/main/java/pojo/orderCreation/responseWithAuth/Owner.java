package pojo.orderCreation.responseWithAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    public String name;
    public String email;
    public String createdAt;
    public String updatedAt;
}