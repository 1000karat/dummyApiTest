import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lib.GenerateData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
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

        assertEquals(200, response.getStatusCode(), "Unexpected status code");
    }

    @Test
    @DisplayName("Get list with empty appid")
    @Tags({@Tag("auth"), @Tag("getList")})
    public void getListWithEmptyAppIdTest() {
        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("app-id", "")
                .get("https://dummyapi.io/data/v1/user")
                .andReturn();

        assertEquals(403, response.getStatusCode(), "Unexpected status code");
        assertEquals("APP_ID_MISSING", response.jsonPath().get("error"), "Unexpected error message");
    }

    @Test
    @DisplayName("Get list with fake appId")
    @Tags({@Tag("withoutAppId"), @Tag("auth"), @Tag("getList")})
    public void getListWithFakeAppIdTest() {
        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("app-id", GenerateData.generateRandomAppId(24))
                .get("https://dummyapi.io/data/v1/user")
                .andReturn();

        assertEquals(403, response.getStatusCode(), "Unexpected status code");
        assertEquals("APP_ID_NOT_EXIST", response.jsonPath().get("error"), "Unexpected error message");
    }
}
