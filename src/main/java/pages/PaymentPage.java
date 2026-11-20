package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class PaymentPage extends BasePage {

    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By orderSuccessMessage = By.cssSelector("#form p");
    private final By downloadInvoiceButton = By.cssSelector("a[href^='/download_invoice/']");

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
    public void clickDownloadInvoice() {
        safeClick(downloadInvoiceButton);
    }

    public boolean isInvoiceDownloaded() {

        String downloadPath = System.getProperty("user.dir")
                + File.separator + "downloads";

        File downloadFolder = new File(downloadPath);

        WebDriverWait downloadWait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        return downloadWait.until(d -> {

            File[] files = downloadFolder.listFiles(
                    file -> file.isFile()
                            && file.getName().startsWith("invoice")
                            && file.getName().endsWith(".txt")
            );

            return files != null && files.length > 0;
        });
    }
}