package tests;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ExtractResponseAsString {

    public static void main(String[] args) {

//        Validate if Add Place API is working as expected

//        given - all the inputs
//        when - submit the API
//        Then - Validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        // For Parsing Json
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println("Place ID : "+ placeId);
    }
}
