package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class StaticJsonPayload {

    @Test
    public void addBook() throws IOException {

        //Content of the file to String -> content of file can convert into Byte -> Byte data to String

        RestAssured.baseURI = "http://216.10.245.166";

        String resp = given().log().all().header("Content-Type", "application/json")

                // Static Json Concept (new String(Files.readAllBytes(Paths.get("./src/jsonFiles/addplace.json"))))
                .body(new String(Files.readAllBytes(Paths.get("./src/jsonFiles/addplace.json"))))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(resp);
        String id = js.get("ID");
        System.out.println("Id is : " + id);

    }

}