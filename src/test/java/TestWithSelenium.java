import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.SignUpPage;
import pages.UserMainPhotoPage;

import java.util.HashMap;
import java.util.Map;


public class TestWithSelenium {

    private static final String email = "bidaritterhm@gmail.com";
    private static final String invalidEmail = "bidarit(terhm@gmail.com";
    private static final String invalidEmail2 = "tbiddwarit222terhqm@gmail.com";
    private static final String password = "Nazar2021KPI2021";
    private static final String name = "Саня";
    private static final String surname = "Красівий";
    private static final String searchedPerson = "Володимир Зеленський";

    private static final long timeOutInSeconds = 10;

    private WebDriverWait waiter;
    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver2.exe");
        driver = createWebDriver();
        waiter = createWebDriverWait(driver);
        System.out.println("Test start");
    }

    @Test
    public void openUserPageFromLinkObjectPage() {
        UserMainPhotoPage userPage = new UserMainPhotoPage(driver, waiter, "https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        String username = userPage.searchUserByUsernameInLink(searchedPerson).getUsername();

//        Assert.assertEquals(username, searchedPerson);
        Assert.assertTrue(username.contains(searchedPerson));
    }

    @Test
    public void testLikeAvatarWhileNotLoginObjectPage() {
        UserMainPhotoPage userPage = new UserMainPhotoPage(driver, waiter, "https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        String loginMessage = userPage.giveLikeToPhotoUnauthorized();

        Assert.assertEquals(loginMessage, "Войдите на Facebook");
    }

    @Test
    public void testSignUPWithInvalidUsedEmailPageObject() {
        SignUpPage signUpPage = new SignUpPage(driver, waiter);
        String errorMessage = signUpPage.signUpWithInvalidUsedEmail(name, surname, invalidEmail, password);

        Assert.assertTrue(errorMessage.equals("Введите действительный номер мобильного телефона или эл. адрес.") ||
                errorMessage.equals("Введите действительный электронный адрес."));
    }

    @Test
    public void testSignUPWithAlreadyUsedEmailObjectPage() {
        SignUpPage signUpPage = new SignUpPage(driver, waiter);
        String errorMessage = signUpPage.signUpWithAlreadyUsedEmail(name, surname, email, password).getErrorMessage();

        Assert.assertEquals(errorMessage, "Извините, мы не можем вас зарегистрировать.");
    }

    @Test
    public void testLoginWithValidDataObjectPage() {
        LoginPage loginPage = new LoginPage(driver, waiter);
        String greeting = loginPage.loginValidUser(email, password).getGreeting();

        Assert.assertEquals(greeting, "Ваш аккаунт отключен");
    }

    @Test
    public void testLoginWithInvalidDataObjectPage() {
        LoginPage loginPage = new LoginPage(driver, waiter);
        String error = loginPage.loginInvalidUser(invalidEmail2, password);

        Assert.assertEquals(error, "Неверное имя пользователя или пароль");
    }


    @After
    public void close() {
        driver.quit();
        System.out.println("Test end");
    }

    private WebDriver createWebDriver() {
        return new ChromeDriver(createChromeOptions());
    }

    private WebDriverWait createWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, timeOutInSeconds);
    }

    private ChromeOptions createChromeOptions() {

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }
}
