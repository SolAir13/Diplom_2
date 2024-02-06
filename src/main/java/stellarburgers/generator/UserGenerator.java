package stellarburgers.generator;

import org.apache.commons.lang3.RandomStringUtils;
import stellarburgers.pojo.LoginUser;
import stellarburgers.pojo.RegisterUser;

public class UserGenerator {

    final static String DEFAULT_EMAIL = RandomStringUtils.random(10);
    final static String DEFAULT_PASSWORD = RandomStringUtils.random(10);
    final static String DEFAULT_NAME = RandomStringUtils.random(10);
    final static String FAKE_EMAIL = RandomStringUtils.random(10);
    final static String NEW_EMAIL = RandomStringUtils.random(10) +DEFAULT_EMAIL;
    final static String NEW_PASSWORD = RandomStringUtils.random(10);
    final static String NEW_NAME = RandomStringUtils.random(10);

    public enum UserField {
        EMAIL,
        PASSWORD,
        NAME
    }

    public static RegisterUser getDefaultRegistrationData() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);
        String firstName = RandomStringUtils.random(10);
        return new RegisterUser(login + "@mail.ru", password, firstName);
    }

    public static RegisterUser getRegistrationDataWithOneEmptyField(UserField emptyField) {
        RegisterUser data = null;
        switch (emptyField) {
            case EMAIL:
                data = new RegisterUser("", DEFAULT_PASSWORD, DEFAULT_NAME);
                break;
            case PASSWORD:
                data = new RegisterUser(DEFAULT_EMAIL, "", DEFAULT_NAME);
                break;
            case NAME:
                data = new RegisterUser(DEFAULT_EMAIL, DEFAULT_PASSWORD, "");
                break;
        }
        return data;
    }

    public static LoginUser getDefaultLoginData() {
        String login = RandomStringUtils.random(10);
        String password = RandomStringUtils.random(10);

        return new LoginUser(login + "@mail.ru", password);
    }

    public static LoginUser getNewLoginData() {
        return new LoginUser(NEW_EMAIL, DEFAULT_PASSWORD);
    }

    public static LoginUser getFakeLoginData() {
        return new LoginUser(FAKE_EMAIL, DEFAULT_PASSWORD);
    }

    public static RegisterUser getDefaultUpdateData() {
        return new RegisterUser(NEW_EMAIL, NEW_PASSWORD, NEW_NAME);
    }
}

