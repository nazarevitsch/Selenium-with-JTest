package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SignUpPage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By byNameWebSubmit = By.name("websubmit");
    private By byTagNameInput = By.tagName("input");
    private By byNameEmailConfirmation = By.name("reg_email_confirmation__");
    private By byNameBirthdayDay = By.name("birthday_day");
    private By byNameBirthdayMonth = By.name("birthday_month");
    private By byNameBirthdayYear = By.name("birthday_year");
    private By byCssSelectorRadioButton =  By.cssSelector("._8esa");
    private By byIdError = By.id("js_0");


    private By byCssSelectorAcceptCookies = By.cssSelector("._42ft._4jy0._9o-t._4jy3._4jy1.selected._51sy");

    public SignUpPage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
        driver.get("https://www.facebook.com/r.php?locale=ru_RU&display=page");
        acceptCookies();
    }

    public ErrorSignUpPage signUpWithAlreadyUsedEmail(String name, String surname, String email, String password){
        sighUp(name, surname, email, password);
        return new ErrorSignUpPage(driver, waiter);
    }

    public String signUpWithInvalidUsedEmail(String name, String surname, String email, String password){
        sighUp(name, surname, email, password);
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byIdError));
        return driver.findElement(byIdError).getAttribute("innerText");
    }

    private void sighUp(String name, String surname, String email, String password){
        List<WebElement> inputs = driver.findElements(byTagNameInput);
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
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byNameEmailConfirmation));
        driver.findElement(byNameEmailConfirmation).sendKeys(email);

        Select select = new Select(driver.findElement(byNameBirthdayDay));
        select.selectByVisibleText("20");

        select = new Select(driver.findElement(byNameBirthdayMonth));
        select.selectByVisibleText("мая");

        select = new Select(driver.findElement(byNameBirthdayYear));
        select.selectByVisibleText("2014");

        List<WebElement> radioButtons = driver.findElements(byCssSelectorRadioButton);

        for (WebElement el : radioButtons) {
            if (el.getAttribute("value").equals("2")) el.click();
        }

        WebElement registrationButton = driver.findElement(byNameWebSubmit);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", registrationButton);

        registrationButton.click();
    }

    private void acceptCookies(){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorAcceptCookies));
        driver.findElement(byCssSelectorAcceptCookies).click();
    }
}
