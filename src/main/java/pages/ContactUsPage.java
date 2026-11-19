package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactUsPage extends BasePage {

    private final By getInTouchTitle = By.xpath("//h2[normalize-space()='Get In Touch']");
    private final By nameInput = By.cssSelector("[data-qa='name']");
    private final By emailInput = By.cssSelector("[data-qa='email']");
    private final By subjectInput = By.cssSelector("[data-qa='subject']");
    private final By messageInput = By.cssSelector("[data-qa='message']");
    private final By uploadFileInput = By.name("upload_file");
    private final By submitButton = By.cssSelector("[data-qa='submit-button']");
    private final By successMessage = By.cssSelector("div.status.alert.alert-success");
    private final By homeButton = By.xpath("//span[normalize-space()='Home']");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    public String getGetInTouchTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(getInTouchTitle)
        ).getText();
    }

    public void fillContactForm(
            String name,
            String email,
            String subject,
            String message
    ) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);

        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(subjectInput).sendKeys(subject);
        driver.findElement(messageInput).sendKeys(message);
    }

    public void uploadFile(String absoluteFilePath) {
        driver.findElement(uploadFileInput)
                .sendKeys(absoluteFilePath);
    }

    public String getUploadedFileValue() {
        return driver.findElement(uploadFileInput)
                .getAttribute("value");
    }

    public void submitContactForm() {
        safeClick(submitButton);
    }

    public void acceptConfirmationAlert() {
        wait.until(
                ExpectedConditions.alertIsPresent()
        ).accept();
    }

    public String getSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
        ).getText();
    }

    public void clickHome() {
        safeClick(homeButton);
    }
}