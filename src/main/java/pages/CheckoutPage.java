
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the checkout page.
 *
 * Provides methods for verifying delivery and billing
 * addresses, reviewing order details, entering an order
 * comment, and proceeding to the payment page.
 */
public class CheckoutPage extends BasePage {

    // Section headings displayed during checkout.
    private final By addressDetailsTitle =
            By.xpath("//h2[normalize-space()='Address Details']");

    private final By reviewOrderTitle =
            By.xpath("//h2[normalize-space()='Review Your Order']");

    // Delivery and billing address containers.
    private final By deliveryAddress =
            By.id("address_delivery");

    private final By billingAddress =
            By.id("address_invoice");

    // Text area for adding an order comment.
    private final By orderComment =
            By.name("message");

    // Button that navigates to the payment page.
    private final By placeOrderButton =
            By.cssSelector("a[href='/payment']");

    /**
     * Initializes the Checkout Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the Address Details section heading.
     *
     * @return the visible address details heading
     */
    public String getAddressDetailsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addressDetailsTitle
                )
        ).getText();
    }

    /**
     * Returns the Review Your Order section heading.
     *
     * @return the visible review order heading
     */
    public String getReviewOrderTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewOrderTitle
                )
        ).getText();
    }

    /**
     * Returns the delivery address displayed during checkout.
     *
     * @return the delivery address as text
     */
    public String getDeliveryAddress() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        deliveryAddress
                )
        ).getText();
    }

    /**
     * Returns the billing address displayed during checkout.
     *
     * @return the billing address as text
     */
    public String getBillingAddress() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        billingAddress
                )
        ).getText();
    }

    /**
     * Enters a comment for the current order.
     *
     * Clears any existing text before entering the comment.
     *
     * @param comment the comment to include with the order
     */
    public void enterOrderComment(String comment) {
        WebElement commentField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderComment
                )
        );

        commentField.clear();
        commentField.sendKeys(comment);
    }

    /**
     * Clicks the Place Order button to proceed
     * to the payment page.
     */
    public void clickPlaceOrder() {
        safeClick(placeOrderButton);
    }
}
