import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.Login;
import pojo.User;
import token.RefreshToken;
import token.Token;

import static api.UserSteps.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static pojo.User.createRandomUser;

public class LoginTest {
    private User user;

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Авторизация в профиле пользователя по логину = email и паролю = password")
    @Description("Проверяем что ответ 200 и message = OK")
    public void loginTest() throws InterruptedException {
        user = createRandomUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Thread.sleep(2000);
        Response response = loginUser(login);
        response.then()
                .body("message", equalTo("OK"))
                .and()
                .statusCode(200);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Запрос токена accessToken по логину = email и паролю = password")
    @Description("Проверяем что accessToken и refreshToken достаточно длинные > 500")
    public void loginAndGetTokenTest() throws InterruptedException {
        user = createRandomUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Thread.sleep(2000);
        Token token = loginUserAndGetToken(login);
        assertTrue((token.getData().getAccessToken().length() > 1000) && (token.getData().getRefreshToken().length() > 500));

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправляем refreshToken в ручку выхода из профиля logout - ожидаем 200 ОК")
    @Description("Проверяем что refreshToken позволяет разлогиниться")
    public void loginAndLogOutTest() throws InterruptedException {
        user = createRandomUser();
        createUser(user);
        Login login = new Login(user.getEmail(), user.getPassword());
        Thread.sleep(2000);
        Token token = loginUserAndGetToken(login);
        String refToken = token.getData().getRefreshToken();
        RefreshToken refreshToken = new RefreshToken(refToken);
        Response response = logoutUser(refreshToken);
        response.then()
                .statusCode(200)
                .and()
                .body("message", equalTo("OK"));

    }

}
