import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.ErrorData;
import pojo.Login;
import pojo.RegData;
import pojo.User;
import token.Token;
import userdata.DataUser;

import static api.UserSteps.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static pojo.User.*;


public class RegistrationTest {
    private Response response;
    private User user;

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание профиля пользователя с рандомными данными - ожидаем статус 200")
    @Description("Проверяем что ответ 200 и в теле ответа есть поле data не пустое")
    public void successRegistrationTest() {
        user = createRandomUser();
        printUserData(user);
        response = createUser(user);
        response.then()
                .body("data", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание профиля пользователя с рандомными данными - и запрашиваем в базе его логин")
    @Description("Проверяем что адрес почты введеный при регистрации - это логин пользователя сохраненный в базе")
    public void checkUserInfoTest() {
        user = createRandomUser();
        createUser(user);
        String expectedEmail = user.getEmail();
        Login login = new Login(user.getEmail(), user.getPassword());
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        DataUser dataUser = loginUserAndGetData(login, accessToken);
        printUserIdentificationData(dataUser);
        assertEquals(expectedEmail, dataUser.getData().getEmail());

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание профиля пользователя с рандомными данными - и запращиваем в базе его номер телефона")
    @Description("Проверяем что msisdn - это номер телефона пользователя введенный при регистрации")
    public void checkUserTelephoneTest() {
        user = createRandomUser();
        createUser(user);
        String expectedTelephone = user.getTel();
        printUserData(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        printLoginData(login);
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        DataUser dataUser = loginUserAndGetData(login, accessToken);
        printUserIdentificationData(dataUser);
        assertEquals(expectedTelephone, dataUser.getData().getMsisdn());

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание профиля пользователя с рандомными данными и запрос номера телефона для верификации")
    @Description("Проверяем что в теле ответа есть номер телефона 79990001122")
    public void getUserRegistrationInfoTest() {
        user = createRandomUser();
        RegData regData = createAndGetUserInfo(user);
        String telephone = regData.getData().getDial_to_verify_number();
        assertEquals("79990001122", telephone);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание двух одинаковых профилей пользователя невозможно")
    @Description("Проверяем что ответ 409 и текст ошибки error")
    public void existRegistrationTest() {
        user = getExistUser();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(409);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем обязательность всех полей при регистрации пользователя")
    @Description("Проверяем что со всеми пустыми полями формы регистрации вернется текст ошибки - что поле обязательное")
    public void registrationWithEmptyUserTest() {
        user = createEmptyUser();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(400);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем обязательность поля email при регистрации пользователя")
    @Description("Проверяем что нельзя зарегистрировать пользователя с пробелом в поле email")
    public void registrationWithEmptyEmailUserTest() {
        user = createUserWithEmptyEmail();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(400);
    }
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем обязательность поля password при регистрации пользователя")
    @Description("Проверяем что нельзя зарегистрировать пользователя с пробелом в поле password")
    public void registrationWithEmptyPasswordUserTest() {
        user = createUserWithEmptyPassword();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(400);
    }
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем НЕобязательность поля name при регистрации пользователя")
    @Description("Проверяем анонимность - можно зарегистрировать пользователя с пробелом в поле name")
    public void registrationWithEmptyNameUserTest() {
        user = createUserWithEmptyName();
        response = createUser(user);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .statusCode(200);
    }
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем обязательность поля tel при регистрации пользователя")
    @Description("Проверяем что невозможно зарегистрировать пользователя с 11 пробелами в поле tel")
    public void registrationWithEmptyTelUserTest() {
        user = createUserWithEmptyTel();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(400);
    }
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверяем обязательность поля username (ник) при регистрации пользователя")
    @Description("Проверяем что невозможно зарегистрировать пользователя без никнейма - username")
    public void registrationWithEmptyUsernameTest() {
        user = createUserWithEmptyUsername();
        response = createUser(user);
        response.then()
                .body("message", equalTo("error"))
                .and()
                .statusCode(400);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создаем рандомного юзера и повторно регистрируем уже существующего пользователя - ожидаем текст ошибки")
    @Description("Проверяем ответ сервера - ошибка с текстом user already exists")
    public void signupExistUserResponseAlreadyExistsTest() {
        User user = createRandomUser();
        createUser(user);
        String userLogin = user.getEmail();
        String userName = user.getName();
        String userPassword = user.getPassword();
        String userTel = user.getTel();
        String userNickName = user.getUsername();
        User newUser = new User(userLogin, userName, userPassword, userTel,userNickName);
        ErrorData errorData = getTextFromErrorMessageSignUp(newUser);
        assertEquals("user already exists", errorData.getError().getMessage());
    }
}