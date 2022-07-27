package tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ExampleToCheckTheResScopeValue {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        given().log().all().queryParam("key", "quilck123").header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Test POST API 132\",\n" +
                        "  \"phone_number\": \"(+91) 989 5545 22\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}\n")
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.41 (Ubuntu)");
    }

}
