
package com.tests.listeners;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.nio.charset.StandardCharsets;

public class AllureRestAssuredFilter implements Filter {

    @Override
    public Response filter(
            FilterableRequestSpecification request,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        Response response = context.next(request, responseSpec);

        String requestDetails =
                "Method: " + request.getMethod() + "\n" +
                        "URI: " + request.getURI() + "\n" +
                        "Headers: " + request.getHeaders() + "\n" +
                        "Body: " + request.getBody();

        Allure.addAttachment(
                "API Request",
                "text/plain",
                requestDetails,
                ".txt"
        );

        String responseDetails =
                "Status Code: " + response.statusCode() + "\n" +
                        "Headers: " + response.getHeaders() + "\n" +
                        "Body: " + response.asPrettyString();

        Allure.addAttachment(
                "API Response",
                "text/plain",
                responseDetails,
                ".txt"
        );

        return response;
    }
}
