
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignupLoginPage;

/**
 * Test class for verifying user authentication.
 *
 * Covers Automation Exercise test cases:
 * TC02 - Login with valid credentials
 * TC03 - Login with invalid credentials
 * TC04 - Logout user
 */
public class LoginTests extends BaseTest {

    private static final String LOGIN_URL =
            "https://automationexercise.com/login";

    private static final String LOGIN_TITLE =
            "Login to your account";

    private static final String EXPECTED_LOGGED_IN_USER =
            "Logged in as Georgios Psaroudis";

    private static final String INVALID_LOGIN_MESSAGE =
            "Your email or password is incorrect!";

    /**
     * TC02: Verifies successful login using valid credentials.
     *
     * Reads credentials from environment variables and
     * checks that the expected user is logged in.
     */
    @Test(description = "Test Case 2: Login User with correct email and password")
    public void loginWithValidCredentialsTest() {

        HomePage homePage = new HomePage(driver);
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        // Verify the login section heading.
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                LOGIN_TITLE,
                "Login section heading is incorrect"
        );

        // Log in using the configured test account.
        loginWithConfiguredCredentials(signupLoginPage);

        // Verify successful authentication.
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                EXPECTED_LOGGED_IN_USER,
                "The expected user is not logged in"
        );
    }

    /**
     * TC03: Verifies that invalid login credentials
     * produce the expected authentication error message.
     */
    @Test(description = "Test Case 3: Login User with incorrect email and password")
    public void invalidLoginTest() {

        HomePage homePage = new HomePage(driver);
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        // Submit invalid credentials.
        signupLoginPage.login(
                "invalid@example.com",
                "wrongPassword123"
        );

        // Verify the invalid login error message.
        Assert.assertEquals(
                signupLoginPage.getInvalidLoginError(),
                INVALID_LOGIN_MESSAGE,
                "Invalid login error message is incorrect"
        );
    }

    /**
     * TC04: Verifies that a logged-in user can log out.
     *
     * Checks successful login, logout navigation,
     * and the reappearance of the login section.
     */
    @Test(description = "Test Case 4: Logout User")
    public void logoutUserTest() {

        HomePage homePage = new HomePage(driver);
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        // Verify the login section heading.
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                LOGIN_TITLE,
                "Login section heading is incorrect"
        );

        // Log in using the configured test account.
        loginWithConfiguredCredentials(signupLoginPage);

        // Handle a possible Google Vignette overlay.
        closeGoogleVignetteIfPresent();

        // Verify successful authentication.
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                EXPECTED_LOGGED_IN_USER,
                "The expected user is not logged in"
        );

        // Log out of the account.
        homePage.clickLogout();

        // Verify navigation back to the login page.
        successfullyChangePage(LOGIN_URL);

        // Verify the login section is displayed again.
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                LOGIN_TITLE,
                "Login section was not displayed after logout"
        );
    }

    /**
     * Logs in using credentials stored in environment variables.
     *
     * Validates that both required variables are configured
     * before submitting the login form.
     *
     * @param signupLoginPage the Signup/Login Page Object
     */

    private void loginWithConfiguredCredentials(
            SignupLoginPage signupLoginPage
    ) {

        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");

        Assert.assertNotNull(
                email,
                "TEST_EMAIL is not configured"
        );

        Assert.assertNotNull(
                password,
                "TEST_PASSWORD is not configured"
        );

        signupLoginPage.login(email, password);
    }
}
