package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;

import java.nio.file.Files;
import java.nio.file.Path;

public class ContactUsTests extends BaseTest {

    @Test(description = "Test Case 6: Contact Us Form")
    public void contactUsFormTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickContactUs();
        ContactUsPage contactUsPage = new ContactUsPage(driver);
        Assert.assertEquals(
                contactUsPage.getGetInTouchTitle(),
                "GET IN TOUCH"
        );
        contactUsPage.fillContactForm(
                "George",
                "George@geroge.com",
                "Subject",
                "An Issue For George Georgopoulos"
        );
        Path filePath = Path.of(
                "src", "test", "resources", "test-upload.txt"
        ).toAbsolutePath();
        Assert.assertTrue(
                Files.isRegularFile(filePath),
                "Test file does not exist: " + filePath
        );
        contactUsPage.uploadFile(filePath.toString());
        Assert.assertTrue(
                contactUsPage.getUploadedFileValue()
                        .endsWith("test-upload.txt"),
                "File was not selected correctly"
        );
        contactUsPage.submitContactForm();
        contactUsPage.acceptConfirmationAlert();
        Assert.assertEquals(
                contactUsPage.getSuccessMessage(),
                "Success! Your details have been submitted successfully."
        );
        contactUsPage.clickHome();
        closeGoogleVignetteIfPresent();
        wait.until(
                ExpectedConditions.urlToBe(
                        "https://automationexercise.com/"
                )
        );
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
    }
}