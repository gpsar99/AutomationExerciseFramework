package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountStatusPage extends BasePage {

    private final By accountCreated =
            By.cssSelector("[data-qa='account-created']");

    private final By accountDeleted =
            By.cssSelector("[data-qa='account-deleted']");

    private final By continueButton =
            By.cssSelector("[data-qa='continue-button']");

    public AccountStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getAccountCreatedMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountCreated)
        ).getText();
    }

    public String getAccountDeletedMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountDeleted)
        ).getText();
    }

    public void clickContinue() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }
}