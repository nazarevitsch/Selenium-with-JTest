package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By byIdEmail = By.id("email");
    private By byIdPassword = By.id("pass");
    private By byNameLogin = By.name("login");
    private By byIdError = By.id("error_box");

    private By byTagDiv = By.tagName("div");

    private By byCssSelectorAcceptCookies = By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy");

    public LoginPage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
        driver.get("https://www.facebook.com/");
        acceptCookies();
    }


    public HomePage loginValidUser(String email, String password) {
        driver.findElement(byIdEmail).sendKeys(email);
        driver.findElement(byIdPassword).sendKeys(password);
        driver.findElement(byNameLogin).click();
        return new HomePage(driver, waiter);
    }

    public String loginInvalidUser(String email, String password) {
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byIdEmail));
        driver.findElement(byIdEmail).sendKeys(email);
        driver.findElement(byIdPassword).sendKeys(password);
        driver.findElement(byNameLogin).click();
//        try {
//            Thread.sleep(200000);
//        } catch (Exception e){}
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".pam._3-95._9ay3.uiBoxRed")));
        return driver.findElement(By.cssSelector(".pam._3-95._9ay3.uiBoxRed")).findElements(byTagDiv).get(1).getAttribute("innerText");
    }

    private void acceptCookies(){
//        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorAcceptCookies));
//        driver.findElement(byCssSelectorAcceptCookies).click();
    }

}
