package tests;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ExtractPriceWhenTitleOfTheCourseMatches {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.CoursePrices());

        int coursesSize = js.getInt("courses.size()");
        System.out.println("Total Number of Courses are : " + coursesSize);

        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Total Amount Is : " + totalAmount);

        String courseTitleFirst = js.get("courses[0].title");
        System.out.println("Title of the first course Is : " + courseTitleFirst);

        for (int i = 0; i < coursesSize; i++) {
            String courseTitles = js.get("courses[" + i + "].title");
            if (courseTitles.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses[" + i + "].copies");
                System.out.println("Total Copies for the respective course is : " + copies);
                break;
            }

        }

    }
}
