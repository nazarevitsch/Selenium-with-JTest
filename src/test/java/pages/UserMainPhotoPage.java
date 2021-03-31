package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserMainPhotoPage {
    private WebDriver driver;
    private WebDriverWait waiter;

    private By byCssSelectorAcceptCookies = By.cssSelector(".hu5pjgll.lzf7d6o1.sp_67hOLVoSKzI.sx_0c1a09");
    private By byCssSelectorLoginMessage = By.cssSelector(".a8c37x1j.ni8dbmo4.stjgntxs.l9j0dhe7.pby63qed");

    private By byCssSelectorLikeButton = By.cssSelector(".oajrlxb2.gs1a9yip.g5ia77u1.mtkw9kbi.tlpljxtp.qensuy8j." +
            "ppp5ayq2.goun2846.ccm00jje.s44p3ltw.mk2mc5f4.rt8b4zig.n8ej3o3l.agehan2d.sk4xxmp2.rq0escxv." +
            "nhd2j8a9.j83agx80.mg4g778l.btwxx1t3.pfnyh3mw.p7hjln8o.kvgmc6g5.cxmmr5t8.oygrvhab.hcukyx3x." +
            "tgvbjcpo.hpfvmrgz.jb3vyjys.rz4wbd8a.qt6c0cv9.a8nywdso.l9j0dhe7.i1ao9s8h.esuyzwwr.f1sip0of." +
            "du4w35lb.lzcic4wl.abiwlrkh.p8dawk7l");


    public UserMainPhotoPage(WebDriver driver, WebDriverWait waiter, String userLink){
        this.driver = driver;
        this.waiter = waiter;
        driver.get(userLink);
        acceptCookies();
    }

    public String giveLikeToPhotoUnauthorized(){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorLikeButton));
        driver.findElement(byCssSelectorLikeButton).click();

        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorLoginMessage));
        return driver.findElement(byCssSelectorLoginMessage).getAttribute("innerText");
    }

    public UserPage searchUserByUsernameInLink(String username){
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(username)));
        driver.findElement(By.linkText(username)).click();
        return new UserPage(driver, waiter);
    }

    private void acceptCookies(){
//        waiter.until(ExpectedConditions.visibilityOfElementLocated(byCssSelectorAcceptCookies));
//        driver.findElement(byCssSelectorAcceptCookies).click();
    }
}
