package JiraTest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import java.io.File;
import static io.restassured.RestAssured.*;

public class JiraAPITest {

    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080/";

        //  1.Login Scenario
        SessionFilter session = new SessionFilter();

        String sessionId = given().log().all().header("Content-Type", "application/json")
                .body("{ \"username\": \"viswabharath\", \"password\": \"Jir@test12345\" }").log().all()
                .filter(session)  // This will store the session value for further subsequent requests
                .when().post("rest/auth/1/session")
                .then().extract().response().asString();
        System.out.println("SessionId : "+sessionId);

        String expectedMessage = "Hi How are you?";

        //  2.Add Comment for specific issue
        String addCommentResponse =
                given().pathParam("key", "10003")
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body("{\n" +
                                        "    \"body\": \"" + expectedMessage + "\",\n" +
                                "    \"visibility\": {\n" +
                                        "        \"type\": \"role\",\n" +
                                        "        \"value\": \"Administrators\"\n" +
                                        "    }\n" +
                                        "}")
                        .filter(session)
                        .when().post("rest/api/2/issue/{key}/comment")
                        .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.getString("id");


        // 3.Add Attachment
        given().log().all().header("X-Atlassian-Token", "no-check")
                .header("Content-Type", "multipart/form-data")   // key point2
                .filter(session)
                .pathParams("key", "10003")
                .multiPart("file", new File("jira.txt"))   // key point1
                .when()
                .post("rest/api/2/issue/{key}/attachments")
                .then().assertThat().statusCode(200);


        // 4. Get The Issue Details
        String issueDetails = given().log().all().filter(session)
                .pathParam("key", "10003")
                .queryParam("fields", "comment").log().all()
                .filter(session)
                .when()
                .get("rest/api/2/issue/{key}")
                .then().log().all().extract().asString();
        System.out.println(issueDetails);

        JsonPath js1 = new JsonPath(issueDetails);
        int commentsCount = js1.getInt("fields.comment.comments.size()");
        for (int i = 0; i < commentsCount; i++) {
            String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
            if (commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = js1.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(message, expectedMessage);
            }

        }

    }

}