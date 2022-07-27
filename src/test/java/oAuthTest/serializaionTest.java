package oAuthTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class serializaionTest {

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

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        Response response = given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(aP)
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response();

        String res = response.asString();
        System.out.println(res);
    }
}