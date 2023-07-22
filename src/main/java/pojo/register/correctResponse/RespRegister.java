package pojo.register.correctResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespRegister {
    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
}
