package oAuthTest;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.filter.session.SessionFilter;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class oAuthTest {

    public static void main(String[] args) throws InterruptedException {

        String[] expectedCourseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qgYQZL6zIVYM7shND6hD0HqQYo9ngliI5gZYSPM45-uw4A9IPqiGvnEaeh4sVywDw&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];
        System.out.println("Code is : " + code);

        String response =
                given()
                        .urlEncodingEnabled(false)
                        .queryParams("code", code)
                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParams("grant_type", "authorization_code")
                        .queryParams("state", "verifyfjdss")
                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                        // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                        .when().log().all()
                        .post("https://www.googleapis.com/oauth2/v4/token").asString();

     System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        System.out.println("Access Token is : "+ accessToken);

//        String r2 =    given().contentType("application/json").
//                queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//                .when()
//                .get("https://rahulshettyacademy.com/getCourse.php")
//                .asString();
//        System.out.println(r2);


        // POJO : Deserialization
        GetCourse getCourse =    given().contentType("application/json").
                queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .as(GetCourse.class);

        System.out.println("GetCourse Linkedin : "+ getCourse.getLinkedIn());
        System.out.println("GetCourse Instructor Name : "+ getCourse.getInstructor());
        System.out.println("GetCourse Course Title : "+ getCourse.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = getCourse.getCourses().getApi();
        for (int i=0;i<apiCourses.size();i++)
        {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI WebServices testing"))
            {
                System.out.println("Price for the "+apiCourses.get(i)+" is : "+ apiCourses.get(i).getPrice());
            }
        }

        // Get Course Names of WebAutomation

        ArrayList<String> actualCourseTitles = new ArrayList<String>();

        List<WebAutomation> w = getCourse.getCourses().getWebAutomation();
        for (int j=0;j<w.size();j++)
        {
            actualCourseTitles.add(w.get(j).getCourseTitle());
        }
        List<String> courseTitles = Arrays.asList(expectedCourseTitles);
        Assert.assertTrue(actualCourseTitles.equals(courseTitles));
    }

}