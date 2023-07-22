package pojo.ingredients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespIngredientsRoot {
    private Boolean success;
    private List<Datum> data;
}

