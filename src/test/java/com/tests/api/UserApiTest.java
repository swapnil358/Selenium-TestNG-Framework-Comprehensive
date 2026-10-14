package com.tests.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.config.ConfigReader;
import com.framework.models.User;

import com.tests.base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserApiTest extends BaseApiTest {

    @Test(groups = {"regression"})
    public void createUserShouldReturnUserDetails() throws Exception {

        // 1. Create the request POJO
        User requestUser = new User("testuser123", "TestPassword123!");

        // 2. Configure Jackson
        ObjectMapper mapper = new ObjectMapper();

        // 3. Serialization: Java object -> JSON
        String requestBody = mapper.writeValueAsString(requestUser);

        System.out.println("Request JSON: " + requestBody);

        // 4. Send POST request using REST Assured
        Response response =
                given()
                            .spec(apiRequestSpec())
                            .basePath(ConfigReader.get("api.user.create.path"))
                            .contentType("application/json")
                            .body(requestBody)
                        .when()
                            .post()
                            .then()
                            .extract()
                            .response();

        System.out.println("Response body: " + response.asString());

        // 5. Validate HTTP status
        Assert.assertEquals(response.getStatusCode(),201,"User creation should return HTTP 201");

        // 6. Deserialization: JSON -> Java object
        User responseUser = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(response.asString(), User.class);

        // 7. Validate response data
        Assert.assertEquals(responseUser.getUsername(),requestUser.getUsername(),"Username should match");
        Assert.assertEquals(responseUser.getPassword(),requestUser.getPassword(),"Password should match");
    }
}
