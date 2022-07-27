package tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class Basics {

    public static void main(String[] args) {

//        Validate if Add Place API is working as expected

//        given - all the inputs
//        when - submit the API
//        Then - Validate the response

        RestAssured.baseURI= "https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\r\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Test POST API 222\",\n" +
                        "  \"phone_number\": \"(+91) 989 5545 22\",\n" +
                        "  \"address\": \"30, side layout, cohen 659\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}\r\n")
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200);
    }
}
