/*
* Класс для хранения хостов
* */

package urlsAndAPIs;

import java.util.HashMap;

public abstract class URLs {
    public static final HashMap<String, String> URLs = new HashMap<String, String>(){{
        put("main", "https://stellarburgers.nomoreparties.site/api");
    }};
}
