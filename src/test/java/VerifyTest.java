import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;
import pojo.ErrorData;
import pojo.Login;
import pojo.User;
import token.Token;

import static api.UserSteps.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static pojo.User.*;

public class VerifyTest {

    @Ignore // ВЕРИФИКАЦИЯ - ПЕРВЫЙ ТЕСТ ДЛЯ ПОЛУЧЕНИЯ КОДА от робота на свой номер телефона
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Запрос звонка от голосового робота с 6-значным кодом для верификации")
    @Description("Проверяем что приходит 200")
    public void loginAndAskVerifyCallNewUserTest() {
        User user = createRandomUserWithMyNumber();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        printLoginData(login); // распечатка в консоль данных нового пользователя для второго теста
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        Response response = getOkMessagePhoneVerify(accessToken);
        response.then()
                .statusCode(200)
                .and()
                .body("message", equalTo("OK"));
    }

    @Ignore // этот тест запускаем вручную чтобы отправить код от робота
    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка 6-значного кода для верификации нового пользователя")
    @Description("Проверяем что приходит 200 ОК")
    public void loginAndSendVerifyCodeNewUserTest() {
        String email = "rjfp7544@tipay.ru";
        String password = "12465671";
        String code = "869161";
        Login login = new Login(email, password);
        printLoginData(login);
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        Response response = getOkMessagePhoneVerifyAndCode(accessToken, code);
        response.then()
                .statusCode(200)
                .and()
                .body("message", equalTo("OK"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Запрос звонка с 6-значным кодом для верификации для уже верифицированного пользователя")
    @Description("Проверяем что приходит ошибка с текстом already verified")
    public void loginAndAskVerifyCallOldUserTest() {
        User user = getExistUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        ErrorData errorData = getTextFromErrorMessagePhoneVerify(accessToken);
        assertEquals("already verified", errorData.getError().getMessage());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Попытка верификации по неверному 6-значному коду для нового неверицицированного пользователя")
    @Description("Проверяем что приходит ошибка с текстом invalid code")
    public void loginAndAskVerifyUserWrongCodeTest() {
        User user = createRandomUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        ErrorData errorData = getTextFromErrorMessagePhoneVerifyAndCode(accessToken, "123456");
        printErrorMessage(errorData);
        assertEquals("invalid code", errorData.getError().getMessage());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Попытка верификации по 5-значному коду для нового неверицицированного пользователя")
    @Description("Проверяем что приходит ошибка с текстом invalid body")
    public void loginAndAskVerifyUserShortCodeTest() {
        User user = createRandomUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Token token = loginUserAndGetToken(login);
        String accessToken = token.getData().getAccessToken();
        ErrorData errorData = getTextFromErrorMessagePhoneVerifyAndCode(accessToken, "12345");
        printErrorMessage(errorData);
        assertEquals("invalid body: Key: 'OneTimeCode' Error:Field validation for 'OneTimeCode' failed on the 'len' tag", errorData.getError().getMessage());
    }

}
