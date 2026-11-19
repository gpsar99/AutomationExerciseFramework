package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaymentPage extends BasePage {

    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By orderSuccessMessage = By.cssSelector("#form p");

    private final By payButton =
            By.id("submit");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void fillPaymentDetails(
            String name,
            String number,
            String securityCode,
            String month,
            String year) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameOnCard)
        ).sendKeys(name);

        driver.findElement(cardNumber).sendKeys(number);
        driver.findElement(cvc).sendKeys(securityCode);
        driver.findElement(expiryMonth).sendKeys(month);
        driver.findElement(expiryYear).sendKeys(year);
    }

    public void clickPayAndConfirmOrder() {
        safeClick(payButton);
    }
    public String getOrderSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderSuccessMessage
                )
        ).getText();
    }
}