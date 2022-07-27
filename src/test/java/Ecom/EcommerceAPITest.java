package Ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;

public class EcommerceAPITest {

    public static void main(String[] args) {

        // Login
        RequestSpecification reqSubSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest lR = new LoginRequest();
        lR.setUserEmail("viswabharath.lalam@gmail.com");
        lR.setUserPassword("Test@123");


        RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(reqSubSpec).body(lR);
        LoginResponse loginRes = reqLogin.when().post("/api/ecom/auth/login")
                .then().extract().as(LoginResponse.class);
        String token = loginRes.getToken();
        System.out.println("Toke : " + token);
        String userId = loginRes.getUserId();
        System.out.println("UserId : " + loginRes.getUserId());


        // Add Product
        RequestSpecification addProductBaseSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseSpec)
                .param("productName", "Shirts")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "Shirts")
                .param("productPrice", "1150")
                .param("productDescription", "Arrow")
                .param("productFor", "Men")
                .multiPart("productImage", new File("C://Users//002TOJ744//Downloads//Shirts.jpg"));


        String addProductResSpec = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResSpec);
        String productId = js.get("productId");
        System.out.println("Product Id : " + productId);


        //Place Or Create Order
        RequestSpecification createOrderSubSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON).build();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderId(productId);

        // Below list will help if we have multiple orders
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq=given().log().all().spec(createOrderSubSpec).body(orders);

        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();
        System.out.println(responseAddOrder);


        //Delete Product
        /*RequestSpecification deleteProductSubReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON).build();

        RequestSpecification deleteReqSpec = given().log().all().spec(deleteProductSubReq)
                .pathParam("productId", productId);

        String deleteProductRes = deleteReqSpec.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        System.out.println(deleteProductRes);

        JsonPath js1 = new JsonPath(deleteProductRes);
        Assert.assertEquals("Product Deleted Successfully", js1.get("message"));*/

    }


}