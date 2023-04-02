import io.restassured.response.Response;
import pojo.Login;
import pojo.User;
import token.Token;
import userdata.DataUser;

import java.util.Scanner;

import static api.UserSteps.*;
import static api.UserSteps.getOkMessagePhoneVerify;
import static org.junit.Assert.assertTrue;
import static pojo.User.createRandomUserWithMyNumber;

public class CallRobotTestMain {

    public static void main(String[] args){

        System.out.println("====== Полуавтоматический тест голосового робота и API ================= ");
        System.out.println("======================================================================== ");
        System.out.println("====== Для запроса звонка с 6-значным кодом для верификации  =========== ");
        System.out.println("====== введите ваш телефон в формате 79990000001  ====================== ");
        System.out.println("======================================================================== ");
        String newTel = "12345678901";
        Scanner scan_tel = new Scanner(System.in);  // запускаем первый сканер для телефона
        newTel = scan_tel.nextLine(); // сохраняем введенный номер телефона
        User user = createRandomUserWithMyNumber();    // создали данные для профиля со своим номером
        user.setTel(newTel); // меняем номер телефона на введенный с консоли
        createUser(user); // отправили POST запрос на регистрацию
        printUserData(user); // распечатали данные нового пользователя
        String email = user.getEmail(); // сохраним логин
        String password = user.getPassword(); // сохраним пароль
        Login login = new Login(email, password); // создали json для авторизации в профиле
        printLoginData(login); // распечатка данных для авторизации
        Token token = loginUserAndGetToken(login); // запросили токен для дальнейших действий
        String accessToken = token.getData().getAccessToken(); // сохранили токен
        DataUser dataUser = loginUserAndGetData(login, accessToken);
        printUserIdentificationData(dataUser);

        Response response = getOkMessagePhoneVerify(accessToken); // отправили POST запрос на звонок с кодом
        String message = response.then().extract().body().path("message");
        System.out.println("======================================================================== ");
        System.out.println("====== Ваш запрос прошел --->  " + message); // распечатали результат запроса
        System.out.println("======================================================================== ");
        if (message.equals("OK")) {
            System.out.println("====== Примите звонок от номера + 7 (383) 363-02-35 --->  ");
        }
        String code = " "; //  инициализируем переменные для ввода с консоли
        Scanner scan_code = new Scanner(System.in);  // запускаем второй сканер для кода

        System.out.println("======================================================================== ");
        System.out.println("====== Введите 6 цифр, которые продиктовал робот --->    ");
        code = scan_code.nextLine(); // сохраняем введенный код

        System.out.println("======================================================================== ");
        System.out.println("===== Отправка 6-значного кода для верификации нового пользователя   === ");

        printLoginData(login);
        token = loginUserAndGetToken(login); // запросили новый токен
        accessToken = token.getData().getAccessToken(); // вытащили токен
        response = getOkMessagePhoneVerifyAndCode(accessToken, code); // отправили запрос с кодом от робота
        message = response.then().extract().body().path("message");
        System.out.println("========================================================================");
        System.out.println("===========   Ваш запрос на верификацию прошел --->  " + message);
        System.out.println("========================================================================");

        token = loginUserAndGetToken(login);
        accessToken = token.getData().getAccessToken();
        dataUser = loginUserAndGetData(login, accessToken);
        printUserIdentificationData(dataUser);
        assertTrue(dataUser.getData().isPhoneVerified());
        System.out.println("=========================== конец теста ================================");
    }
}
