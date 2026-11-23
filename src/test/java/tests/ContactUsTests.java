
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test class for verifying the Contact Us form.
 *
 * Covers Test Case 6 of the Automation Exercise website.
 *
 * Validates form submission, file upload, confirmation
 * alert handling, success message, and navigation
 * back to the home page.
 */
public class ContactUsTests extends BaseTest {

    private static final String HOME_URL =
            "https://automationexercise.com/";

    private static final String CONTACT_NAME = "George";
    private static final String CONTACT_EMAIL = "George@geroge.com";
    private static final String CONTACT_SUBJECT = "Subject";

    private static final String CONTACT_MESSAGE =
            "An Issue For George Georgopoulos";

    private static final String UPLOAD_FILE_NAME =
            "test-upload.txt";

    private static final String SUCCESS_MESSAGE =
            "Success! Your details have been submitted successfully.";

    /**
     * TC06: Verifies successful submission of the Contact Us form.
     *
     * Checks the form heading, fills the contact details,
     * uploads a test file, accepts the confirmation alert,
     * verifies the success message, and returns to the home page.
     */
    @Test(description = "Test Case 6: Contact Us Form")
    public void contactUsFormTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Contact Us page.
        homePage.clickContactUs();

        ContactUsPage contactUsPage =
                new ContactUsPage(driver);

        // Verify the Contact Us form heading.
        Assert.assertEquals(
                contactUsPage.getGetInTouchTitle(),
                "GET IN TOUCH",
                "Contact Us heading is incorrect"
        );

        // Fill the contact form.
        contactUsPage.fillContactForm(
                CONTACT_NAME,
                CONTACT_EMAIL,
                CONTACT_SUBJECT,
                CONTACT_MESSAGE
        );

        // Resolve the test file path.
        Path filePath = Path.of(
                "src",
                "test",
                "resources",
                UPLOAD_FILE_NAME
        ).toAbsolutePath();

        // Verify that the upload file exists.
        Assert.assertTrue(
                Files.isRegularFile(filePath),
                "Test file does not exist: " + filePath
        );

        // Upload the test file.
        contactUsPage.uploadFile(filePath.toString());

        // Verify that the correct file was selected.
        Assert.assertTrue(
                contactUsPage.getUploadedFileValue()
                        .endsWith(UPLOAD_FILE_NAME),
                "File was not selected correctly"
        );

        // Submit the contact form.
        contactUsPage.submitContactForm();

        // Accept the JavaScript confirmation alert.
        contactUsPage.acceptConfirmationAlert();

        // Verify successful form submission.
        Assert.assertEquals(
                contactUsPage.getSuccessMessage(),
                SUCCESS_MESSAGE,
                "Contact form success message is incorrect"
        );

        // Return to the home page.
        contactUsPage.clickHome();

        // Handle a possible Google Vignette overlay.
        closeGoogleVignetteIfPresent();

        // Wait for and verify navigation to the home page.
        successfullyChangePage(HOME_URL);
    }
}
