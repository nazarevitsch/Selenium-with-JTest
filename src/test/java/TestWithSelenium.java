import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.SignUpPage;
import pages.UserMainPhotoPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestWithSelenium {

    private static final String email = "";
    private static final String invalidEmail = "";
    private static final String invalidEmail2 = "";
    private static final String password = "";
    private static final String name = "Саня";
    private static final String surname = "Красівий";
    private static final String searchedPerson = "Володимир Зеленський";

    private static final long timeOutInSeconds = 10;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        System.out.println("Test start");
    }

    @Test
    public void openUserPageFromLinkObjectPage() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        UserMainPhotoPage userPage = new UserMainPhotoPage(driver, waiter, "https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        String username = userPage.searchUserByUsernameInLink(searchedPerson).getUsername();

        Assert.assertTrue(username.contains(username));

        driver.quit();
    }

    @Test
    public void testLikeAvatarWhileNotLoginObjectPage() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        UserMainPhotoPage userPage = new UserMainPhotoPage(driver, waiter, "https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        String loginMessage = userPage.giveLikeToPhotoUnauthorized();

        Assert.assertEquals(loginMessage, "Войдите на Facebook");

        driver.quit();
    }

    @Test
    public void testSignUPWithInvalidUsedEmailPageObject() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        SignUpPage signUpPage = new SignUpPage(driver, waiter);
        String errorMessage = signUpPage.signUpWithInvalidUsedEmail(name, surname, invalidEmail, password);

        Assert.assertEquals(errorMessage, "Введите действительный номер мобильного телефона или эл. адрес.");

        driver.quit();
    }

    @Test
    public void testSignUPWithAlreadyUsedEmailObjectPage() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        SignUpPage signUpPage = new SignUpPage(driver, waiter);
        String errorMessage = signUpPage.signUpWithAlreadyUsedEmail(name, surname, email, password).getErrorMessage();

        Assert.assertEquals(errorMessage, "Извините, мы не можем вас зарегистрировать.");

        driver.quit();
    }

    @Test
    public void testLoginWithValidDataObjectPage() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        LoginPage loginPage = new LoginPage(driver, waiter);
        String greeting = loginPage.loginValidUser(email, password).getGreeting();

        Assert.assertEquals(greeting, name);

        driver.quit();
    }

    @Test
    public void testLoginWithInvalidDataObjectPage() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        LoginPage loginPage = new LoginPage(driver, waiter);
        String error = loginPage.loginInvalidUser(invalidEmail2, password);

        Assert.assertEquals(error, "Неверное имя пользователя или пароль");

        driver.quit();
    }

    @Test
    public void testLoginWithValidData() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);

        driver.findElement(By.name("login")).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]/div/div[3]/div[1]/div[2]/span/span[2]/span")));
        String greeting = driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[3]/div[1]/div[2]/span/span[2]/span")).getAttribute("innerText");

        Assert.assertEquals(greeting, name);

        driver.quit();
    }

    @Test
    public void testLoginWithInvalidData() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();
        driver.findElement(By.id("email")).sendKeys(invalidEmail2);
        driver.findElement(By.id("pass")).sendKeys(password + "1");

        driver.findElement(By.name("login")).click();
        try {
            waiter.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("._9ay7")));
            String errorMessage = driver.findElement(By.cssSelector("._9ay7")).getAttribute("innerText");
            Assert.assertEquals("Вы ввели неверный пароль. Забыли пароль?", errorMessage);
        } catch (Exception e) {
            List<WebElement> elements = driver.findElement(By.id("error_box")).findElements(By.tagName("div"));
            Assert.assertEquals(elements.get(0).getAttribute("innerText"), "Неверные данные");
            Assert.assertEquals(elements.get(1).getAttribute("innerText"), "Неверное имя пользователя или пароль");
        }

        driver.quit();
    }

    @Test
    public void testSignUPWithAlreadyUsedEmail() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/r.php?locale=ru_RU&display=page");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();

        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        for (WebElement el : inputs) {
            switch (el.getAttribute("name")) {
                case "firstname":
                    el.sendKeys(name);
                    break;
                case "lastname":
                    el.sendKeys(surname);
                    break;
                case "reg_email__":
                    el.sendKeys(email);
                    break;
                case "reg_passwd__":
                    el.sendKeys(password);
                    break;
                default:
                    break;
            }
        }
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("reg_email_confirmation__")));
        driver.findElement(By.name("reg_email_confirmation__")).sendKeys(email);

        Select select = new Select(driver.findElement(By.name("birthday_day")));
        select.selectByVisibleText("20");

        select = new Select(driver.findElement(By.name("birthday_month")));
        select.selectByVisibleText("мая");

        select = new Select(driver.findElement(By.name("birthday_year")));
        select.selectByVisibleText("2014");

        List<WebElement> radioButtons = driver.findElements(By.cssSelector("._8esa"));

        for (WebElement el : radioButtons) {
            if (el.getAttribute("value").equals("2")) el.click();
        }

        WebElement registrationButton = driver.findElement(By.name("websubmit"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", registrationButton);

        registrationButton.click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String errorMessage = driver.findElement(By.id("error")).findElement(By.tagName("h2")).getAttribute("innerText");
        Assert.assertEquals(errorMessage, "Извините, мы не можем вас зарегистрировать.");

        driver.quit();
    }


    @Test
    public void testSignUPWithInvalidUsedEmail() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/r.php?locale=ru_RU&display=page");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();

        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        for (WebElement el : inputs) {
            switch (el.getAttribute("name")) {
                case "firstname":
                    el.sendKeys(name);
                    break;
                case "lastname":
                    el.sendKeys(surname);
                    break;
                case "reg_email__":
                    el.sendKeys(invalidEmail);
                    break;
                case "reg_passwd__":
                    el.sendKeys(password);
                    break;
                default:
                    break;
            }
        }
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("reg_email_confirmation__")));
        driver.findElement(By.name("reg_email_confirmation__")).sendKeys(invalidEmail);

        Select select = new Select(driver.findElement(By.name("birthday_day")));
        select.selectByVisibleText("20");

        select = new Select(driver.findElement(By.name("birthday_month")));
        select.selectByVisibleText("мая");

        select = new Select(driver.findElement(By.name("birthday_year")));
        select.selectByVisibleText("2014");

        List<WebElement> radioButtons = driver.findElements(By.cssSelector("._8esa"));

        for (WebElement el : radioButtons) {
            if (el.getAttribute("value").equals("2")) el.click();
        }

        WebElement registrationButton = driver.findElement(By.name("websubmit"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", registrationButton);

        registrationButton.click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_0")));
        String errorMessage = driver.findElement(By.id("js_0")).getAttribute("innerText");
        Assert.assertEquals(errorMessage, "Введите действительный номер мобильного телефона или эл. адрес.");

        driver.quit();
    }

    @Test
    public void testLikeAvatarWhileNotLogin() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hu5pjgll.lzf7d6o1.sp_67hOLVoSKzI.sx_0c1a09")));
        driver.findElement(By.cssSelector(".hu5pjgll.lzf7d6o1.sp_67hOLVoSKzI.sx_0c1a09")).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oajrlxb2.gs1a9yip.g5ia77u1.mtkw9kbi.tlpljxtp.qensuy8j.ppp5ayq2.goun2846.ccm00jje.s44p3ltw.mk2mc5f4.rt8b4zig.n8ej3o3l.agehan2d.sk4xxmp2.rq0escxv.nhd2j8a9.j83agx80.mg4g778l.btwxx1t3.pfnyh3mw.p7hjln8o.kvgmc6g5.cxmmr5t8.oygrvhab.hcukyx3x.tgvbjcpo.hpfvmrgz.jb3vyjys.rz4wbd8a.qt6c0cv9.a8nywdso.l9j0dhe7.i1ao9s8h.esuyzwwr.f1sip0of.du4w35lb.lzcic4wl.abiwlrkh.p8dawk7l")));
        driver.findElement(By.cssSelector(".oajrlxb2.gs1a9yip.g5ia77u1.mtkw9kbi.tlpljxtp.qensuy8j.ppp5ayq2.goun2846.ccm00jje.s44p3ltw.mk2mc5f4.rt8b4zig.n8ej3o3l.agehan2d.sk4xxmp2.rq0escxv.nhd2j8a9.j83agx80.mg4g778l.btwxx1t3.pfnyh3mw.p7hjln8o.kvgmc6g5.cxmmr5t8.oygrvhab.hcukyx3x.tgvbjcpo.hpfvmrgz.jb3vyjys.rz4wbd8a.qt6c0cv9.a8nywdso.l9j0dhe7.i1ao9s8h.esuyzwwr.f1sip0of.du4w35lb.lzcic4wl.abiwlrkh.p8dawk7l")).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a8c37x1j.ni8dbmo4.stjgntxs.l9j0dhe7.pby63qed")));
        String loginMessage = driver.findElement(By.cssSelector(".a8c37x1j.ni8dbmo4.stjgntxs.l9j0dhe7.pby63qed")).getAttribute("innerText");

        Assert.assertEquals(loginMessage, "Войдите на Facebook");

        driver.quit();
    }

    @Test
    public void openUserPageFromLink() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/photo/?fbid=2431988927051469&set=a.1376998622550510");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hu5pjgll.lzf7d6o1.sp_67hOLVoSKzI.sx_0c1a09")));
        driver.findElement(By.cssSelector(".hu5pjgll.lzf7d6o1.sp_67hOLVoSKzI.sx_0c1a09")).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(searchedPerson)));
        driver.findElement(By.linkText(searchedPerson)).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gmql0nx0.l94mrbxd.p1ri9a11.lzcic4wl.bp9cbjyn.j83agx80")));
        String username = driver.findElement(By.cssSelector(".gmql0nx0.l94mrbxd.p1ri9a11.lzcic4wl.bp9cbjyn.j83agx80")).getAttribute("innerText");

        Assert.assertTrue(username.contains(username));

        driver.quit();
    }


    @After
    public void close() {
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
        return options;
    }
}
