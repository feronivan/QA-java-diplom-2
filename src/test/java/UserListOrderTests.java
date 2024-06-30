import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import model.Burger;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Получение заказов конкретного пользователя")
public class UserListOrderTests {
    private UserSteps userSteps = new UserSteps();
    private User user = DataGenerator.randomUser();;
    private OrderSteps orderSteps = new OrderSteps();
    private String accessToken;
    Burger burgerWithCorrectIngredients = Burger.burgerWithCorrectIngredients();

    @Before
    @DisplayName("Создание пользователя и его авторизация")
    public void setUpUserListOrder() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        userSteps
                .userCreate(user);
        ValidatableResponse validatableResponse = userSteps
                .userAuthorization(user);

        accessToken = validatableResponse
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void userListOrderWithAuthCheck() {
        //создаем заказ:
        orderSteps
                .createOrderWithAuth(burgerWithCorrectIngredients, accessToken);
        //получаем список заказов:
        ValidatableResponse validatableResponse = orderSteps
                .getUserListOrdersWithAuth(accessToken);
        validatableResponse
                .assertThat()
                .statusCode(200)
                .body("success", is(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void userListOrderWithoutAuthCheck() {
        orderSteps
                .getUserListOrdersWithoutAuth()
                .assertThat()
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }


    @After
    @DisplayName("Удаление созданного пользователя")
    public void tearDownOrderCreate() {
        userSteps
                .userDelete(accessToken)
                .statusCode(202);
    }

}
