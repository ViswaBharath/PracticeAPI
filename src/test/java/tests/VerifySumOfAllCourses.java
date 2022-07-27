package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifySumOfAllCourses {

    @Test
    public void sumOfCourses(){

        int sum=0;

        JsonPath js = new JsonPath(payload.CoursePrices());

        int coursesSize = js.getInt("courses.size()");
        System.out.println("Total Number of Courses are : " + coursesSize);

        for (int i = 0; i < coursesSize; i++) {
            int coursePrice = js.getInt("courses[" + i + "].price");
            int courseCopies = js.getInt("courses[" + i + "].copies");
            int amount = coursePrice * courseCopies;
            System.out.println(amount);
            sum = sum+amount;
        }
        System.out.println("Total Purchase Amount Is :"+sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount,sum);
    }
}
