package tests;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UpdatePlace {

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

        // Update Place

        String newAddress = "70 winter - Updated4";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));


         // Get Place

        String getPlaceRes = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath jss = ReUsableMethods.rawToJson(getPlaceRes);
        String actualAddress = jss.getString("address");
        System.out.println("Get Place Response : "+ actualAddress);

        Assert.assertEquals(newAddress,actualAddress);
//        Assert.assertEquals(newAddress,"sdf");
    }
}