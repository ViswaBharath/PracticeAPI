package tests;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.CoursePrices());

        int coursesSize = js.getInt("courses.size()");
        System.out.println("Total Number of Courses are : " + coursesSize);

        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Total Amount Is : " + totalAmount);

        String courseTitleFirst = js.get("courses[0].title");
        System.out.println("Title of the first course Is : " + courseTitleFirst);

        // To Print all the available course titles and price
        for (int i = 0; i < coursesSize; i++) {
            String allTitles = js.get("courses[" + i + "].title");
            System.out.println("Course Title is : " + allTitles);
            System.out.println(js.get("courses[" + i + "].price").toString());
        }

    }
}