package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By buXpathGreeting = By.xpath("/html/body/div/div/div[1]/div/div[3]/div[1]/div[2]/span/span[2]/span");


    public HomePage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
    }

    public String getGreeting(){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(buXpathGreeting));
        return driver.findElement(buXpathGreeting).getAttribute("innerText");
    }
}
