import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DisplayName("Создание пользователя")
public class UserCreateTests {
    private final UserSteps userSteps = new UserSteps();
    private User user = new User();
    private String accessToken;

    @Before
    public void setUpUserCreate() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        user = DataGenerator.randomUser();
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUserOkTrueCheck() {
        userSteps
                .userCreate(user)
                .assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createUserExistsCheck() {
        userSteps
                .userCreate(user);
        userSteps
                .userCreate(user)
                .assertThat()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля - email")
    public void createUserWithoutEmailCheck() {
        user.setEmail("");
        userSteps
                .userCreate(user)
                .assertThat()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля - name")
    public void createUserWithoutNameCheck() {
        user.setName("");
        userSteps
                .userCreate(user)
                .assertThat()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля - password")
    public void createUserWithoutPasswordCheck() {
        user.setPassword("");
        userSteps
                .userCreate(user)
                .assertThat()
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    @DisplayName("Удаление созданного пользователя")
    public void tearDownUserCreate() {
        userSteps
                .userDelete(accessToken);
    }

}
