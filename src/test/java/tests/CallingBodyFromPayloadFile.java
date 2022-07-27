package tests;

import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class CallingBodyFromPayloadFile {

    public static void main(String[] args) {

//        Validate if Add Place API is working as expected

//        given - all the inputs
//        when - submit the API
//        Then - Validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200);
    }
}
