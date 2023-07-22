package pojo.userEdit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.register.correctResponse.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUser {
    private Boolean success;
    private User user;
}
