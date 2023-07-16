package headers;

import java.util.HashMap;

public abstract class SetOfHeaders {
    public static HashMap<String, String> DEFAULT_HEADERS = new HashMap() {{
        put("Accept-Encoding", "gzip, deflate, br");
        put("Connection", "keep-alive");
        put("Content-Type", "application/json");
        put("Accept", "*/*");
    }};
}
