
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the Signup/Login page.
 *
 * Provides methods for registering a new user,
 * logging into an existing account, and verifying
 * authentication-related messages.
 */
public class SignupLoginPage extends BasePage {

    // New user signup section.
    private final By signupTitle =
            By.xpath("//h2[normalize-space()='New User Signup!']");

    private final By signupName =
            By.cssSelector("[data-qa='signup-name']");

    private final By signupEmail =
            By.cssSelector("[data-qa='signup-email']");

    private final By signupButton =
            By.cssSelector("[data-qa='signup-button']");

    private final By existingEmailError =
            By.xpath(
                    "//p[normalize-space()='Email Address already exist!']"
            );

    // Existing user login section.
    private final By loginTitle =
            By.xpath("//h2[normalize-space()='Login to your account']");

    private final By loginEmail =
            By.cssSelector("[data-qa='login-email']");

    private final By loginPassword =
            By.cssSelector("[data-qa='login-password']");

    private final By loginButton =
            By.cssSelector("[data-qa='login-button']");

    private final By invalidLoginError =
            By.xpath(
                    "//p[normalize-space()='Your email or password is incorrect!']"
            );

    /**
     * Initializes the Signup/Login Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public SignupLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the New User Signup heading.
     *
     * @return the visible signup section heading
     */
    public String getSignupTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        signupTitle
                )
        ).getText();
    }

    /**
     * Submits the signup form with the provided user details.
     *
     * @param name the name of the new user
     * @param email the email address of the new user
     */
    public void signup(String name, String email) {
        enterText(signupName, name);
        enterText(signupEmail, email);

        safeClick(signupButton);
    }

    /**
     * Returns the error message displayed when
     * attempting to register an existing email address.
     *
     * @return the existing email error message
     */
    public String getExistingEmailError() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        existingEmailError
                )
        ).getText();
    }

    /**
     * Submits the login form using the provided credentials.
     *
     * @param email the registered user's email address
     * @param password the user's password
     */
    public void login(String email, String password) {
        enterText(loginEmail, email);
        enterText(loginPassword, password);

        safeClick(loginButton);
    }

    /**
     * Returns the Login to your account heading.
     *
     * @return the visible login section heading
     */
    public String getLoginTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        loginTitle
                )
        ).getText();
    }

    /**
     * Returns the error message displayed after
     * submitting invalid login credentials.
     *
     * @return the invalid login error message
     */
    public String getInvalidLoginError() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        invalidLoginError
                )
        ).getText();
    }

    /**
     * Waits for an input field to become visible
     * and enters the provided text.
     *
     * Clears any existing value before typing.
     *
     * @param locator the locator of the input field
     * @param value the text to enter
     */
    private void enterText(By locator, String value) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );

        input.clear();
        input.sendKeys(value);
    }
}
