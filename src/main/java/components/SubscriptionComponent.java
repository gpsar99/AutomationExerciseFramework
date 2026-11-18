package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class SubscriptionComponent extends BasePage {


    private final By subsciptionField =
            By.id("susbscribe_email");
    private final By arrowButton =
            By.id("subscribe");
    private final By emailText =
            By.xpath("//h2[normalize-space()='Subscription']");
    private final By successfullySubscribeText =
            By.cssSelector("div.alert-success.alert");

    public SubscriptionComponent(WebDriver driver) {
        super(driver);
    }

    public String getSubscriptionText(){
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emailText)).getText();
    }

    public void fillEmailText(String email){
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subsciptionField)).sendKeys(email);
    }
    public void clickArrow (){
        safeClick(arrowButton);
    }
    public String getSuccessfullySubscribeText(){
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        successfullySubscribeText)).getText();
    }

}