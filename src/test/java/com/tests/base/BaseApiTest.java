
package com.tests.base;

import com.framework.config.ConfigReader;
import com.tests.listeners.AllureRestAssuredFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiTest {

    protected RequestSpecification apiRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("api.base.url"))
                .addFilter(new AllureRestAssuredFilter())
                .build();
    }
}
