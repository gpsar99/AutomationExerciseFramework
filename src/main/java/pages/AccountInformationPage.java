package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AccountInformationPage extends BasePage {

    private final By accountInformationTitle = By.xpath("//h2/b[normalize-space()='Enter Account Information']");
    private final By genderMale = By.id("id_gender1");
    private final By passwordInput = By.id("password");
    private final By daysDropdown = By.id("days");
    private final By monthsDropdown = By.id("months");
    private final By yearsDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By specialOffersCheckbox = By.id("optin");
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
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");

    public AccountInformationPage(WebDriver driver) {
        super(driver);
    }

    public String getAccountInformationTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(accountInformationTitle)
        ).getText();
    }
    public void enterPassword(String password) {
        safeClick(genderMale);

        driver.findElement(passwordInput).sendKeys(password);
    }

    public void selectDateOfBirth(String day, String month, String year) {
        new Select(driver.findElement(daysDropdown))
                .selectByVisibleText(day);

        new Select(driver.findElement(monthsDropdown))
                .selectByVisibleText(month);

        new Select(driver.findElement(yearsDropdown))
                .selectByVisibleText(year);
    }
    public void selectNewsletterAndOffers() {
        safeClick(newsletterCheckbox);
        safeClick(specialOffersCheckbox);
    }
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

    public void clickCreateAccount() {
        safeClick(createAccountButton);
    }

}