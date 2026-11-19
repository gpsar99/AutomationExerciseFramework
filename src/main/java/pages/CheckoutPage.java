package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By addressDetailsTitle =
            By.xpath("//h2[normalize-space()='Address Details']");

    private final By reviewOrderTitle =
            By.xpath("//h2[normalize-space()='Review Your Order']");
    private final By orderComment =
            By.name("message");

    private final By placeOrderButton =
            By.cssSelector("a[href='/payment']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getAddressDetailsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addressDetailsTitle
                )
        ).getText();
    }

    public String getReviewOrderTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewOrderTitle
                )
        ).getText();
    }
    private final By deliveryAddress =
            By.id("address_delivery");

    private final By billingAddress =
            By.id("address_invoice");

    public String getDeliveryAddress() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(deliveryAddress)
        ).getText();
    }

    public String getBillingAddress() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(billingAddress)
        ).getText();
    }
    public void enterOrderComment(String comment) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderComment)
        ).sendKeys(comment);
    }

    public void clickPlaceOrder() {
        safeClick(placeOrderButton);
    }
}