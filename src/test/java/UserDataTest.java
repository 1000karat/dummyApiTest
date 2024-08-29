import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("User Data cases")
@Feature("User Controller")
@Owner("1000karat")
@Tag("prop")
public class UserDataTest {
    String appId = System.getProperty("appid");

    @Test
    @DisplayName("Get List")
    @Tag("getList")
    public void getUserListTest() {
        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("app-id", appId)
                .get("https://dummyapi.io/data/v1/user")
                .andReturn();

        response.prettyPrint();
        assertEquals(200, response.getStatusCode(), "Unexpected status code");
    }
}
