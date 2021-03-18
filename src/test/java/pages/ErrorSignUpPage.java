package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ErrorSignUpPage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By byIdError = By.id("error");
    private By byTagNameH2 = By.tagName("h2");


    public ErrorSignUpPage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
    }

    public String getErrorMessage(){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byIdError));
        return driver.findElement(byIdError).findElement(byTagNameH2).getAttribute("innerText");
    }
}
