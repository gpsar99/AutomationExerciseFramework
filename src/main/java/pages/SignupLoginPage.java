package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignupLoginPage extends BasePage {

    private final By signupTitle = By.xpath("//h2[normalize-space()='New User Signup!']");
    private final By signupName = By.cssSelector("[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By existingEmailError = By.xpath("//p[normalize-space()='Email Address already exist!']");

    public SignupLoginPage(WebDriver driver) {
        super(driver);
    }

    public String getSignupTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(signupTitle)
        ).getText();
    }

    public void signup(String name, String email) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(signupName)
        ).sendKeys(name);

        driver.findElement(signupEmail).sendKeys(email);

        safeClick(signupButton);
    }

    public String getExistingEmailError() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(existingEmailError)
        ).getText();
    }
}