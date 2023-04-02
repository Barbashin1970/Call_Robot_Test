package pojo;

import org.apache.commons.lang3.RandomStringUtils;

import static secret.Secret.*;

public class User {

    private String email;
    private String name;
    private String password;
    private String tel;
    private String username;

    public User(String email, String name, String password, String tel, String username) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.tel = tel;
        this.username = username;
    }

    public static User getExistUser() {
        return new User(emailTest, nameTest, passwordTest, telTest, usernameTest);
    }

    public static User createRandomUser() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                RandomStringUtils.randomNumeric(8),
                "79" + RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }

    public static User createRandomUserWithMyNumber() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                RandomStringUtils.randomNumeric(8),
                myTelNumber,
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }

    public static User createUserWithEmptyPassword() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                " ",
                "79" + RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }

    public static User createUserWithEmptyEmail() {
        return new User(" ",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                RandomStringUtils.randomNumeric(8),
                "79" + RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }
    public static User createUserWithEmptyUsername() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                RandomStringUtils.randomNumeric(8),
                "79" + RandomStringUtils.randomNumeric(9),
                " ");
    }
    public static User createUserWithEmptyName() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                " ",
                RandomStringUtils.randomNumeric(8),
                "79" + RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }
    public static User createUserWithEmptyTel() {
        return new User(RandomStringUtils.randomAlphabetic(4).toLowerCase() + RandomStringUtils.randomNumeric(4).toLowerCase() + "@tipay.ru",
                RandomStringUtils.randomAlphabetic(1).toUpperCase() + RandomStringUtils.randomAlphabetic(7).toLowerCase(),
                RandomStringUtils.randomNumeric(8),
                "           ",
                RandomStringUtils.randomAlphabetic(8).toLowerCase());
    }


    public static User createEmptyUser() {
        return new User(null, null, null, null, null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}