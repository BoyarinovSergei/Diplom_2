package pojo.registerAPI.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RespRegisterApi {
    public boolean success;
    public User user;
    public String accessToken;
    public String refreshToken;
}
