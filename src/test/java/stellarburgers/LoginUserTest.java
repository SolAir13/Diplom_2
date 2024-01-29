package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import junitparams.JUnitParamsRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stellarburgers.client.UserClient;
import stellarburgers.generator.UserGenerator;
import stellarburgers.pojo.LoginUser;
import stellarburgers.pojo.RegisterUser;

import java.util.List;

import static org.apache.http.HttpStatus.*;

@RunWith(JUnitParamsRunner.class)
public class LoginUserTest {

    private final RegisterUser registerData = UserGenerator.getDefaultRegistrationData();
    private LoginUser loginData;
    private String token = "";
    private int statusCode;
    private boolean isLoggedIn;

    @Before
    public void setUp(){
        ValidatableResponse responseRegister = UserClient.registerUser(registerData);
        token = responseRegister.extract().path("accessToken");
    }

    @After
    public void tearDown(){
        ValidatableResponse responseDelete = UserClient.deleteUser(token);
    }

    @Test
    @DisplayName("Логин пользователя с валидными данными")
    public void loginUserWithValidData() {
        loginData = UserGenerator.getDefaultLoginData();
        UserClient.registerUser(new RegisterUser(loginData.getEmail(), loginData.getPassword(), "Alex"));
        ValidatableResponse responseLogin = UserClient.loginUser(loginData);



        statusCode = responseLogin.extract().statusCode();
        isLoggedIn = responseLogin.extract().path("success");

        Assert.assertEquals("Ошибка в коде или теле ответа", List.of(SC_OK, true),
                List.of(statusCode, isLoggedIn));
    }

    @Test
    @DisplayName("Логин пользователя с неверным логином и паролем")
    public void loginFakeUser() {
        loginData = UserGenerator.getFakeLoginData();
        ValidatableResponse responseLogin = UserClient.loginUser(loginData);

        statusCode = responseLogin.extract().statusCode();
        isLoggedIn = responseLogin.extract().path("success");

        Assert.assertEquals("Ошибка в коде или теле ответа", List.of(SC_UNAUTHORIZED, false),
                List.of(statusCode, isLoggedIn));
    }
}
