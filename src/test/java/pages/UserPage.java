package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage {

    private WebDriver driver;
    private WebDriverWait waiter;

    private By byCssSelectorUsername = By.cssSelector(".gmql0nx0.l94mrbxd.p1ri9a11.lzcic4wl.bp9cbjyn.j83agx80");


    public UserPage(WebDriver driver, WebDriverWait waiter){
        this.driver = driver;
        this.waiter = waiter;
    }

    public String getUsername() {
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorUsername));
        return driver.findElement(byCssSelectorUsername).getAttribute("innerText");
    }
}
