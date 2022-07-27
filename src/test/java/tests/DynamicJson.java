package tests;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test
    public void addBook() {

        RestAssured.baseURI = "http://216.10.245.166";

        String resp = given().log().all().header("Content-Type", "application/json")
                .body(payload.AddBook("sdf", "3654"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(resp);
        String id = js.get("ID");
        System.out.println("Id is : " + id);

    }
}