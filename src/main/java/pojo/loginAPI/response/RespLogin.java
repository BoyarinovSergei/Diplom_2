package pojo.loginAPI.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.registerAPI.response.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespLogin {
    public boolean success;
    public String accessToken;
    public String refreshToken;
    public User user;
}
