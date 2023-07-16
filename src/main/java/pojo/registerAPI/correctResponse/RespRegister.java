package pojo.registerAPI.correctResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RespRegister {
    public boolean success;
    public User user;
    public String accessToken;
    public String refreshToken;
}
