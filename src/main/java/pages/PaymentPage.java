
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * Page Object representing the payment page.
 *
 * Provides methods for entering payment information,
 * confirming an order, verifying the success message,
 * and downloading the order invoice.
 */
public class PaymentPage extends BasePage {

    // Payment form input fields.
    private final By nameOnCard =
            By.name("name_on_card");

    private final By cardNumber =
            By.name("card_number");

    private final By cvc =
            By.name("cvc");

    private final By expiryMonth =
            By.name("expiry_month");

    private final By expiryYear =
            By.name("expiry_year");

    // Payment confirmation button.
    private final By payButton =
            By.id("submit");

    // Order confirmation message.
    private final By orderSuccessMessage =
            By.cssSelector("#form p");

    // Invoice download link.
    private final By downloadInvoiceButton =
            By.cssSelector("a[href^='/download_invoice/']");

    // Directory used for downloaded invoice files.
    private final File downloadFolder;

    // Time when the invoice download was requested.
    private long invoiceDownloadStartTime;

    /**
     * Initializes the Payment Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public PaymentPage(WebDriver driver) {
        super(driver);

        downloadFolder = new File(
                System.getProperty("user.dir"),
                "downloads"
        );
    }

    /**
     * Fills the payment form with the provided card details.
     *
     * @param name the cardholder's name
     * @param number the card number
     * @param securityCode the card security code
     * @param month the card expiration month
     * @param year the card expiration year
     */
    public void fillPaymentDetails(
            String name,
            String number,
            String securityCode,
            String month,
            String year
    ) {
        enterPaymentField(nameOnCard, name);
        enterPaymentField(cardNumber, number);
        enterPaymentField(cvc, securityCode);
        enterPaymentField(expiryMonth, month);
        enterPaymentField(expiryYear, year);
    }

    /**
     * Clicks the Pay and Confirm Order button.
     */
    public void clickPayAndConfirmOrder() {
        safeClick(payButton);
    }

    /**
     * Returns the order confirmation message
     * displayed after a successful payment.
     *
     * @return the order success message
     */
    public String getOrderSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        orderSuccessMessage
                )
        ).getText();
    }

    /**
     * Starts downloading the order invoice.
     *
     * Records the download request time so the subsequent
     * verification can distinguish a new invoice from
     * files left by previous test executions.
     */
    public void clickDownloadInvoice() {
        invoiceDownloadStartTime = System.currentTimeMillis();

        safeClick(downloadInvoiceButton);
    }

    /**
     * Checks whether a new invoice text file has been downloaded.
     *
     * Waits up to 20 seconds for an invoice file created
     * or modified after the download was requested.
     *
     * @return true if a matching invoice file is found
     */
    public boolean isInvoiceDownloaded() {

        WebDriverWait downloadWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );

        return downloadWait.until(d -> {

            File[] files = downloadFolder.listFiles(
                    file -> file.isFile()
                            && file.getName().startsWith("invoice")
                            && file.getName().endsWith(".txt")
                            && file.lastModified()
                            >= invoiceDownloadStartTime
            );

            return files != null && files.length > 0;
        });
    }

    /**
     * Waits for a payment field to become visible
     * and enters the provided value.
     *
     * @param locator the payment field locator
     * @param value the text to enter
     */
    private void enterPaymentField(By locator, String value) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );

        field.sendKeys(value);
    }
}
