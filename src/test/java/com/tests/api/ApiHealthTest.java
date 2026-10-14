package com.tests.api;

import com.framework.config.ConfigReader;
import com.framework.models.TestData;
import com.tests.base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static io.restassured.module.jsv.JsonSchemaValidator
        .matchesJsonSchemaInClasspath;

public class ApiHealthTest extends BaseApiTest {

    @Test(groups = {"smoke"})
    public void applicationEndpointShouldRespond() {

        TestData testData = new TestData();

        System.out.println("========== API HEALTH TEST STARTED ==========");

        Response response =
                given()
                        .spec(apiRequestSpec())
                        .basePath(ConfigReader.get("api.user.create.path"))
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        System.out.println("Response body: " + response.asString());
        System.out.println(response.jsonPath().getString("userId[1]"));
        System.out.println("**** Setting user id in context ****");
        String userId = response.jsonPath().getString("userId[1]");
        testData.setUserId(Integer.parseInt(userId));
        System.out.println("**** getting user id in context ****");
        System.out.println(testData.getUserId(Integer.parseInt(userId)));


        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response time: " + response.timeIn(TimeUnit.MILLISECONDS));

        response.then()
                .statusCode(200)
                .time(lessThan(10000L));

    }



    // Separate reusable method for schema validation

    @Test(groups = {"smoke"})
    public void validateResponseSchema() {

        Response response =
                given()
                        .baseUri(ConfigReader.get("api.base.url"))
                        .when()
                        .get("/posts/1")
                        .then()
                        .extract()
                        .response();

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Content-Type: " + response.getContentType());
        System.out.println("Response body: " + response.asString());

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/health-response-schema.json"
                ));

        System.out.println("JSON schema validation passed.");
    }
}
