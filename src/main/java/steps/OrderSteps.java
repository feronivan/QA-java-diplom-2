package steps;

import config.BaseConfig;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Burger;

import java.util.List;

import static constant.EndPoints.INGREDIENTS;
import static constant.EndPoints.ORDER;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderWithAuth(Burger burger, String accessToken) {
        return given()
                .spec(BaseConfig.spec())
                .body(burger)
                .headers("Authorization", accessToken)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Создание заказа без авторизацией")
    public ValidatableResponse createOrderWithoutAuth(Burger burger) {
        return given()
                .spec(BaseConfig.spec())
                .body(burger)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Получение заказов конкретного пользователя с авторизацией")
    public ValidatableResponse getUserListOrdersWithAuth(String accessToken) {
        return given()
                .spec(BaseConfig.spec())
                .headers("Authorization", accessToken)
                .when()
                .get(ORDER)
                .then();
    }

    @Step("Получение заказов конкретного пользователя без авторизацией")
    public ValidatableResponse getUserListOrdersWithoutAuth() {
        return given()
                .spec(BaseConfig.spec())
                .when()
                .get(ORDER)
                .then();
    }
}
