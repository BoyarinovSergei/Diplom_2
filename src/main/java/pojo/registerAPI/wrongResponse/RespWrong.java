package pojo.registerAPI.wrongResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespWrong {
    public boolean success;
    public String message;
}