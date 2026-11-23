
package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountInformationPage;
import pages.AccountStatusPage;
import pages.HomePage;
import pages.SignupLoginPage;

import java.util.UUID;

/**
 * Test class for verifying user registration functionality.
 *
 * Covers Automation Exercise test cases:
 * TC01 - Register User
 * TC05 - Register User with Existing Email
 */
public class RegisterTests extends BaseTest {

    private static final String TEST_USERNAME = "GeorgeP";

    private static final String TEST_PASSWORD =
            "TestPassword123!";

    private static final String SIGNUP_TITLE =
            "New User Signup!";

    private static final String ACCOUNT_INFORMATION_TITLE =
            "ENTER ACCOUNT INFORMATION";

    private static final String ACCOUNT_CREATED_MESSAGE =
            "ACCOUNT CREATED!";

    private static final String ACCOUNT_DELETED_MESSAGE =
            "ACCOUNT DELETED!";

    private static final String EXISTING_EMAIL_ERROR =
            "Email Address already exist!";

    /**
     * TC01: Verifies that a new user can register successfully.
     *
     * Completes the registration form, verifies account
     * creation and login, and deletes the test account.
     */
    @Test(description = "Test Case 1: Register User")
    public void registerUserTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Verify the signup section heading.
        Assert.assertEquals(
                signupLoginPage.getSignupTitle(),
                SIGNUP_TITLE,
                "Signup section heading is incorrect"
        );

        // Generate a unique email for the new test account.
        String email = generateUniqueEmail();

        // Submit the initial signup form.
        signupLoginPage.signup(TEST_USERNAME, email);

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        // Verify navigation to the account information form.
        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                ACCOUNT_INFORMATION_TITLE,
                "Account information heading is incorrect"
        );

        // Complete the account information form.
        accountInformationPage.enterPassword(TEST_PASSWORD);

        accountInformationPage.selectDateOfBirth(
                "15",
                "May",
                "1995"
        );

        accountInformationPage.selectNewsletterAndOffers();
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();

        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        // Verify successful account creation.
        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                ACCOUNT_CREATED_MESSAGE,
                "Account creation message is incorrect"
        );

        accountStatusPage.clickContinue();

        // Verify that the newly registered user is logged in.
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                "Logged in as " + TEST_USERNAME,
                "The newly registered user is not logged in"
        );

        // Delete the test account.
        homePage.clickDeleteAccount();

        closeGoogleVignetteIfPresent();

        // Wait for navigation to the account deletion page.
        wait.until(
                ExpectedConditions.urlContains("/delete_account")
        );

        // Verify successful account deletion.
        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                ACCOUNT_DELETED_MESSAGE,
                "Account deletion message is incorrect"
        );

        accountStatusPage.clickContinue();
    }

    /**
     * TC05: Verifies that registering with an existing
     * email address displays the expected error message.
     *
     * Requires TEST_EMAIL to reference an existing account.
     */
    @Test(description = "Test Case 5: Register User with existing email")
    public void registerWithExistingEmailTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        // Read the existing account email.
        String email = System.getenv("TEST_EMAIL");

        Assert.assertNotNull(
                email,
                "TEST_EMAIL is not configured"
        );

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Verify the signup section heading.
        Assert.assertEquals(
                signupLoginPage.getSignupTitle(),
                SIGNUP_TITLE,
                "Signup section heading is incorrect"
        );

        // Attempt registration with an existing email.
        signupLoginPage.signup(
                "George Georgoglou",
                email
        );

        // Verify the duplicate email error message.
        Assert.assertEquals(
                signupLoginPage.getExistingEmailError(),
                EXISTING_EMAIL_ERROR,
                "Existing email error message is incorrect"
        );
    }

    /**
     * Generates a unique email address for test registration.
     *
     * @return a randomly generated test email address
     */
    private String generateUniqueEmail() {

        return "testuser"
                + UUID.randomUUID().toString().replace("-", "")
                + "@example.com";
    }
}
