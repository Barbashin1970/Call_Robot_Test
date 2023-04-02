package api;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.ErrorData;
import pojo.Login;
import pojo.RegData;
import pojo.User;
import token.RefreshToken;
import token.Token;
import userdata.DataUser;

import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static io.restassured.RestAssured.given;
import static url.Constants.API_V_1_USERS;
import static url.Constants.URL_USERS;

public class UserSteps {
    @Step("Создаём профиль пользователя")
    public static Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(user)
                .post(API_V_1_USERS + "signup");
    }

    @Step("Получение тела ответа при создании профиля пользователя")
    public static RegData createAndGetUserInfo(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(user)
                .post(API_V_1_USERS + "signup")
                .as(RegData.class);
    }
    @Step("Получение текста ошибки при регистрации нового пользователя с невалидными данными")
    public static ErrorData getTextFromErrorMessageSignUp(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(user)
                .post(API_V_1_USERS + "signup")
                .as(ErrorData.class);
    }

    @Step("Получение ответа что запрос звонка робота для верификации успешен - ожидаем 200 ОК")
    public static Response getOkMessagePhoneVerify(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_USERS)
                .post(API_V_1_USERS + "phone/verify");
    }

    @Step("Получение текста ОК при отправке валидного кода верификации")
    public static Response getOkMessagePhoneVerifyAndCode(@Param(mode = MASKED) String accessToken, String code) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_USERS)
                .post(API_V_1_USERS + "phone/verify/" + code);
    }
    @Step("Получение текста ошибки верификации")
    public static ErrorData getTextFromErrorMessagePhoneVerify(@Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_USERS)
                .post(API_V_1_USERS + "phone/verify")
                .as(ErrorData.class);
    }

    @Step("Получение текста ошибки верификации по 6-значному коду")
    public static ErrorData getTextFromErrorMessagePhoneVerifyAndCode(@Param(mode = MASKED) String accessToken, String code) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_USERS)
                .post(API_V_1_USERS + "phone/verify/" + code)
                .as(ErrorData.class);
    }
    @Step("Входим в профиль пользователя по email и password")
    public static Response loginUser(Login login) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(login)
                .post(API_V_1_USERS + "login");
    }

    @Step("Выходим мз профиля пользователя по refreshToken")
    public static Response logoutUser(RefreshToken refreshToken) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(refreshToken)
                .post(API_V_1_USERS + "logout");
    }

    @Step("Входим в профиль пользователя по email и password - получаем токен")
    public static Token loginUserAndGetToken(Login login) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL_USERS)
                .body(login)
                .post(API_V_1_USERS + "login")
                .as(Token.class);
    }

    @Step("По email, password и токену - получаем данные пользователя в json")
    public static DataUser loginUserAndGetData(Login login, @Param(mode = MASKED) String accessToken) {
        return given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .baseUri(URL_USERS)
                .body(login)
                .get(API_V_1_USERS + "me")
                .as(DataUser.class);
    }

    public static void printUserData(User user) {
        System.out.println("======== заполняем поля пользователя при регистрации signup ============ ");
        System.out.println("   email = " + user.getEmail());
        System.out.println("   name = " + user.getName());
        System.out.println("   password = " + user.getPassword());
        System.out.println("   tel = " + user.getTel());
        System.out.println("   username = " + user.getUsername());
    }

    public static void printUserIdentificationData(DataUser dataUser) {
        System.out.println("=========== запрашиваем что есть в базе по профилю клиента ============= ");
        System.out.println("   id = " + dataUser.getData().getId());
        System.out.println("   name = " + dataUser.getData().getName());
        System.out.println("   username = " + dataUser.getData().getUsername());
        System.out.println("   email = " + dataUser.getData().getEmail());
        System.out.println("   tel / msisdn = " + dataUser.getData().getMsisdn());
        System.out.println("   verified = " + dataUser.getData().isVerified());
        System.out.println("   phoneVerified = " + dataUser.getData().isPhoneVerified());
    }

    public static void printLoginData(Login login) {
        System.out.println("================== авторизация по логину и паролю ====================== ");
        System.out.println("   email = " + login.getLogin());
        System.out.println("   password = " + login.getPassword());
    }

    public static void printErrorMessage(ErrorData errorData) {
        System.out.println("================== текст сообщения об ошибке =========================== ");
        System.out.println("   message = " + errorData.getError().getMessage());
    }
}