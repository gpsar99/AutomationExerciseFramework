
package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

/**
 * Reusable component for interacting with the newsletter
 * subscription section in the website footer.
 */
public class SubscriptionComponent extends BasePage {

    // Email input field in the subscription section.
    // Note: "susbscribe_email" is the actual HTML element ID.
    private final By subscriptionEmailField =
            By.id("susbscribe_email");

    // Button used to submit the subscription email.
    private final By subscribeButton =
            By.id("subscribe");

    // Heading displayed above the subscription form.
    private final By subscriptionHeading =
            By.xpath("//h2[normalize-space()='Subscription']");

    // Success message displayed after subscribing.
    private final By subscriptionSuccessMessage =
            By.cssSelector("div.alert-success.alert");

    /**
     * Initializes the subscription component.
     *
     * @param driver the WebDriver instance used by the test
     */
    public SubscriptionComponent(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the heading text of the subscription section.
     *
     * @return the visible subscription heading
     */
    public String getSubscriptionText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subscriptionHeading
                )
        ).getText();
    }

    /**
     * Enters an email address into the subscription field.
     *
     * @param email the email address to subscribe
     */
    public void fillEmailText(String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subscriptionEmailField
                )
        ).sendKeys(email);
    }

    /**
     * Clicks the subscription submit button.
     */
    public void clickArrow() {
        safeClick(subscribeButton);
    }

    /**
     * Returns the success message displayed after
     * submitting the subscription form.
     *
     * @return the subscription confirmation message
     */
    public String getSuccessfullySubscribeText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subscriptionSuccessMessage
                )
        ).getText();
    }
}
