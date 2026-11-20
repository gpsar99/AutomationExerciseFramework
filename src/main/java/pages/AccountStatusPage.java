
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the account status pages.
 *
 * Handles confirmation messages displayed after account
 * creation or deletion and provides navigation through
 * the Continue button.
 */
public class AccountStatusPage extends BasePage {

    // Confirmation message displayed after successful account creation.
    private final By accountCreated =
            By.cssSelector("[data-qa='account-created']");

    // Confirmation message displayed after successful account deletion.
    private final By accountDeleted =
            By.cssSelector("[data-qa='account-deleted']");

    // Continue button displayed on account status pages.
    private final By continueButton =
            By.cssSelector("[data-qa='continue-button']");

    /**
     * Initializes the Account Status page.
     *
     * @param driver the WebDriver instance used by the test
     */
    public AccountStatusPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the confirmation message displayed after
     * successfully creating an account.
     *
     * @return the account creation confirmation text
     */
    public String getAccountCreatedMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountCreated
                )
        ).getText();
    }

    /**
     * Returns the confirmation message displayed after
     * successfully deleting an account.
     *
     * @return the account deletion confirmation text
     */
    public String getAccountDeletedMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountDeleted
                )
        ).getText();
    }

    /**
     * Clicks the Continue button to navigate away
     * from the account status page.
     */
    public void clickContinue() {
        safeClick(continueButton);
    }
}
