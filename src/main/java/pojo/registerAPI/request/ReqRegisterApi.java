package pojo.registerAPI.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRegisterApi {
    public String email;
    public String password;
    public String name;
}
