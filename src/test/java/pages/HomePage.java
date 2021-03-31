package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By byXpathGreeting = By.xpath("/html/body/div[1]/div[3]/div[1]/div/form/div/div[1]/strong");


    public HomePage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
    }

    public String getGreeting(){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byXpathGreeting));
        return driver.findElement(byXpathGreeting).getAttribute("innerText");
    }
}
