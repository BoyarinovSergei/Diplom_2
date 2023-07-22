package pojo.login.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.register.correctResponse.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespLogin {
    private Boolean success;
    private String accessToken;
    private String refreshToken;
    private User user;
}
