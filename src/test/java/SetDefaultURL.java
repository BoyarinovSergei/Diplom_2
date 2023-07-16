import io.restassured.RestAssured;
import org.junit.BeforeClass;

import static urlsAndAPIs.URLs.MAIN_HOST;

public abstract class SetDefaultURL {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = MAIN_HOST;
    }
}

