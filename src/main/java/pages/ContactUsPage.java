
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the Contact Us page.
 *
 * Provides methods for completing the contact form,
 * uploading a file, submitting the form, handling
 * the confirmation alert, and verifying the success message.
 */
public class ContactUsPage extends BasePage {

    // Contact form heading.
    private final By getInTouchTitle =
            By.xpath("//h2[normalize-space()='Get In Touch']");

    // Contact form input fields.
    private final By nameInput =
            By.cssSelector("[data-qa='name']");

    private final By emailInput =
            By.cssSelector("[data-qa='email']");

    private final By subjectInput =
            By.cssSelector("[data-qa='subject']");

    private final By messageInput =
            By.cssSelector("[data-qa='message']");

    // File upload input.
    private final By uploadFileInput =
            By.name("upload_file");

    // Contact form submit button.
    private final By submitButton =
            By.cssSelector("[data-qa='submit-button']");

    // Success message displayed after form submission.
    private final By successMessage =
            By.cssSelector("div.status.alert.alert-success");

    // Home navigation button.
    private final By homeButton =
            By.xpath("//span[normalize-space()='Home']");

    /**
     * Initializes the Contact Us Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the Contact Us page heading.
     *
     * @return the visible Get In Touch heading
     */
    public String getGetInTouchTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        getInTouchTitle
                )
        ).getText();
    }

    /**
     * Fills the contact form with the provided information.
     *
     * @param name the sender's name
     * @param email the sender's email address
     * @param subject the subject of the message
     * @param message the message content
     */
    public void fillContactForm(
            String name,
            String email,
            String subject,
            String message
    ) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).sendKeys(name);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput)
        ).sendKeys(email);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(subjectInput)
        ).sendKeys(subject);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageInput)
        ).sendKeys(message);
    }

    /**
     * Uploads a file using its absolute file system path.
     *
     * Selenium sends the file path directly to the file input,
     * without interacting with the operating system file dialog.
     *
     * @param absoluteFilePath the absolute path of the file to upload
     */
    public void uploadFile(String absoluteFilePath) {
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        uploadFileInput
                )
        ).sendKeys(absoluteFilePath);
    }

    /**
     * Returns the current value of the file upload input.
     *
     * Browsers may display a fake path for security reasons.
     *
     * @return the file input value
     */
    public String getUploadedFileValue() {
        return driver.findElement(uploadFileInput)
                .getAttribute("value");
    }

    /**
     * Submits the completed contact form.
     */
    public void submitContactForm() {
        safeClick(submitButton);
    }

    /**
     * Waits for the JavaScript confirmation alert
     * and accepts it.
     */
    public void acceptConfirmationAlert() {
        wait.until(
                ExpectedConditions.alertIsPresent()
        ).accept();
    }

    /**
     * Returns the success message displayed after
     * submitting the contact form.
     *
     * @return the contact form confirmation message
     */
    public String getSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        successMessage
                )
        ).getText();
    }

    /**
     * Clicks the Home button to return to the home page.
     */
    public void clickHome() {
        safeClick(homeButton);
    }
}
