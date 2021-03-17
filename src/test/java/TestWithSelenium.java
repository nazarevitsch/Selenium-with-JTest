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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestWithSelenium {

    private static final String email = "bidaritterhm@gmail.com";
    private static final String password = "Nazar2021KPI2021";
    private static final String name = "Саня";
    private static final String surname = "Красівий";

    private static final long timeOutInSeconds = 10;

    @Before
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        System.out.println("Test start");
    }

    @Test
    public void testLogin1WithData() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);

        driver.findElement(By.name("login")).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div[2]/div/div/div[1]/span")));
        String greeting = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div/div[2]/div/div/div[1]/span")).getAttribute("innerText");

        Assert.assertEquals(greeting, "Добро пожаловать на Facebook, "+ name + "!");

        driver.quit();
    }

    @Test
    public void testLogin2WitheData() {
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();
        driver.findElement(By.id("email")).sendKeys(email);
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
    public void testSignUPWithAlreadyUsedEmail() throws Exception{
        WebDriver driver = createWebDriver();
        WebDriverWait waiter = createWebDriverWait(driver);

        driver.get("https://www.facebook.com/r.php?locale=ru_RU&display=page");
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")));
        driver.findElement(By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy")).click();

        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        for (WebElement el : inputs){
            switch (el.getAttribute("name")) {
                case "firstname": el.sendKeys(name);
                break;
                case "lastname": el.sendKeys(surname);
                break;
                case "reg_email__": el.sendKeys(email);
                break;
                case "reg_passwd__": el.sendKeys(password);
                break;
                default: break;
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



    @After
    public void close() {
        System.out.println("Test end");
    }

    private WebDriver createWebDriver() {
        return new ChromeDriver(createChromeOptions());
    }

    private WebDriverWait createWebDriverWait(WebDriver driver){
        return new WebDriverWait(driver, timeOutInSeconds);
    }

    private ChromeOptions createChromeOptions(){
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        return options;
    }
}
