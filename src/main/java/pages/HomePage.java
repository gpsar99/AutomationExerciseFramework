package pages;

import components.SubscriptionComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import components.ProductCartComponent;

public class HomePage extends BasePage{

    private final By productsButton  = By.xpath("//a[@href='/products']");
    private final By cartButton  = By.xpath("//a[.//i[contains(@class,'fa-shopping-cart')]]");
    private final By signUpLoginButton = By.cssSelector("a[href='/login']");
    private final By loggedInUser = By.xpath("//a[contains(normalize-space(.),'Logged in as')]");
    private final By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By contactUsLink = By.cssSelector("a[href='/contact_us']");
    private final By testCasesLink = By.cssSelector("a[href='/test_cases']");
    private final SubscriptionComponent subscription;
    private final ProductCartComponent productCart;


    public HomePage(WebDriver driver) {
        super(driver);
        subscription = new SubscriptionComponent(driver);
        productCart = new ProductCartComponent(driver);
    }
    public SubscriptionComponent getSubscription() {
        return subscription;
    }
    public void clickCart(){
        safeClick(cartButton);
    }
    public void clickProducts() {
        safeClick(productsButton);
    }
    public void clickSignupLogin() {
        safeClick(signUpLoginButton);
    }

    public String getLoggedInUser() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loggedInUser)
        ).getText().trim();
    }

    public void clickDeleteAccount() {
        wait.until(
                ExpectedConditions.presenceOfElementLocated(deleteAccountLink)
        ).sendKeys(Keys.ENTER);
    }
    public void clickLogout() {
        safeClick(logoutLink);
    }
    public void clickContactUs() {
        safeClick(contactUsLink);
    }
    public void clickTestCases() {
        safeClick(testCasesLink);
    }
    public void addFirstItemToCart() {
        productCart.addFirstItemToCart();
    }

    public void setAddContinue() {
        productCart.clickContinueShopping();
    }

    public void addSecondItemToCart() {
        productCart.addSecondItemToCart();
    }

    public void viewCart() {
        productCart.viewCart();
    }
}