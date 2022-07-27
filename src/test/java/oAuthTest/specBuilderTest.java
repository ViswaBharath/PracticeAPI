package oAuthTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class specBuilderTest {

    public static void main(String[] args) {

        AddPlace aP = new AddPlace();
        aP.setAccuracy(50);
        aP.setAddress("70 winter - Updated");
        aP.setLanguage("French-IN");
        aP.setPhone_number("(+91) 989 5545 22");
        aP.setWebsite("https://rahulshettyacademy.com");
        aP.setName("Test Name");

        //   setTypes is List
        List<String> typeList = new ArrayList<String>();
        typeList.add("shoe park");
        typeList.add("shop");

        aP.setTypes(typeList);

        Location l =new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);

        aP.setLocation(l);

       /* RestAssured.baseURI = "https://rahulshettyacademy.com";

        Response response = given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(aP)
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response();*/


        // Request Spec Builder :
        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();

        RequestSpecification requestSpec = given().spec(reqSpec).body(aP);

        // Response Spec Builder :
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        Response response = requestSpec.when().post("maps/api/place/add/json")
                .then().spec(resSpec).extract().response();

        String res = response.asString();
        System.out.println(res);
    }
}