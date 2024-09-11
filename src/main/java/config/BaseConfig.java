package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static constant.BaseUrl.HOST;
import static io.restassured.http.ContentType.JSON;

public class BaseConfig {
    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(HOST)
                .log(LogDetail.ALL)
                .build();
    }
}
