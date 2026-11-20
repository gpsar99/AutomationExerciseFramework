
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object representing the Enter Account Information page.
 *
 * Provides methods for completing the registration form,
 * selecting user preferences, entering address details,
 * and submitting the account creation request.
 */
public class AccountInformationPage extends BasePage {

    // Account information section.
    private final By accountInformationTitle =
            By.xpath("//h2/b[normalize-space()='Enter Account Information']");

    private final By genderMale = By.id("id_gender1");
    private final By passwordInput = By.id("password");

    // Date of birth dropdowns.
    private final By daysDropdown = By.id("days");
    private final By monthsDropdown = By.id("months");
    private final By yearsDropdown = By.id("years");

    // Newsletter and special offers preferences.
    private final By newsletterCheckbox = By.id("newsletter");
    private final By specialOffersCheckbox = By.id("optin");

    // Address information fields.
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");

    // Account creation button.
    private final By createAccountButton =
            By.cssSelector("[data-qa='create-account']");

    /**
     * Initializes the Account Information page.
     *
     * @param driver the WebDriver instance used by the test
     */
    public AccountInformationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the account information page heading.
     *
     * @return the visible heading text
     */
    public String getAccountInformationTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        accountInformationTitle
                )
        ).getText();
    }

    /**
     * Selects the male gender option and enters the password.
     *
     * @param password the password for the new account
     */
    public void enterPassword(String password) {
        safeClick(genderMale);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordInput
                )
        ).sendKeys(password);
    }

    /**
     * Selects the user's date of birth from dropdown menus.
     *
     * @param day the day displayed in the dropdown
     * @param month the month displayed in the dropdown
     * @param year the year displayed in the dropdown
     */
    public void selectDateOfBirth(String day, String month, String year) {
        new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                daysDropdown
                        )
                )
        ).selectByVisibleText(day);

        new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                monthsDropdown
                        )
                )
        ).selectByVisibleText(month);

        new Select(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                yearsDropdown
                        )
                )
        ).selectByVisibleText(year);
    }

    /**
     * Selects the newsletter and special offers checkboxes.
     */
    public void selectNewsletterAndOffers() {
        safeClick(newsletterCheckbox);
        safeClick(specialOffersCheckbox);
    }

    /**
     * Fills the address information fields using predefined
     * test data.
     *
     * The same data is reused by registration and checkout tests.
     */
    public void fillAddressInformation() {
        driver.findElement(firstName).sendKeys("George");
        driver.findElement(lastName).sendKeys("Papadopoulos");
        driver.findElement(company).sendKeys("QA Automation");
        driver.findElement(address1).sendKeys("123 Test Street");
        driver.findElement(address2).sendKeys("Apartment 4");

        new Select(driver.findElement(country))
                .selectByVisibleText("United States");

        driver.findElement(state).sendKeys("California");
        driver.findElement(city).sendKeys("Los Angeles");
        driver.findElement(zipcode).sendKeys("90001");
        driver.findElement(mobileNumber).sendKeys("5551234567");
    }

    /**
     * Submits the registration form by clicking
     * the Create Account button.
     */
    public void clickCreateAccount() {
        safeClick(createAccountButton);
    }
}
