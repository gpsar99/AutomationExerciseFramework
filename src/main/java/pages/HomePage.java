
package pages;

import components.ProductCartComponent;
import components.SubscriptionComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the website's home page.
 *
 * Provides methods for navigating to different pages,
 * managing user account actions, adding products to the cart,
 * and interacting with recommended products.
 */
public class HomePage extends BasePage {

    // Main navigation links.
    private final By productsButton =
            By.cssSelector("a[href='/products']");

    private final By cartButton =
            By.xpath("//a[.//i[contains(@class,'fa-shopping-cart')]]");

    private final By signUpLoginButton =
            By.cssSelector("a[href='/login']");

    private final By contactUsLink =
            By.cssSelector("a[href='/contact_us']");

    private final By testCasesLink =
            By.cssSelector("a[href='/test_cases']");

    // User account navigation.
    private final By loggedInUser =
            By.xpath("//a[contains(normalize-space(.),'Logged in as')]");

    private final By deleteAccountLink =
            By.cssSelector("a[href='/delete_account']");

    private final By logoutLink =
            By.cssSelector("a[href='/logout']");

    // Recommended products section.
    private final By recommendedItemsTitle =
            By.xpath(
                    "//h2[contains(normalize-space(),'recommended items')]"
            );

    private final By firstRecommendedProduct =
            By.cssSelector(
                    "#recommended-item-carousel .item.active .productinfo p"
            );

    private final By firstRecommendedAddToCart =
            By.cssSelector(
                    "#recommended-item-carousel .item.active " +
                            ".productinfo a.add-to-cart"
            );

    // View Cart link displayed in the cart confirmation modal.
    private final By viewCartFromModal =
            By.cssSelector("#cartModal a[href='/view_cart']");

    private final By cartModal =
            By.id("cartModal");

    // Reusable components.
    private final SubscriptionComponent subscription;
    private final ProductCartComponent productCart;

    /**
     * Initializes the Home Page Object and its reusable components.
     *
     * @param driver the WebDriver instance used by the test
     */
    public HomePage(WebDriver driver) {
        super(driver);
        subscription = new SubscriptionComponent(driver);
        productCart = new ProductCartComponent(driver);
    }

    /**
     * Returns the subscription component displayed in the footer.
     *
     * @return the SubscriptionComponent instance
     */
    public SubscriptionComponent getSubscription() {
        return subscription;
    }

    /**
     * Navigates to the shopping cart page.
     */
    public void clickCart() {
        safeClick(cartButton);
    }

    /**
     * Navigates to the products page.
     */
    public void clickProducts() {
        safeClick(productsButton);
    }

    /**
     * Navigates to the Signup/Login page.
     */
    public void clickSignupLogin() {
        safeClick(signUpLoginButton);
    }

    /**
     * Returns the text identifying the currently logged-in user.
     *
     * @return the logged-in user information
     */
    public String getLoggedInUser() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        loggedInUser
                )
        ).getText().trim();
    }

    /**
     * Deletes the currently logged-in account.
     *
     * Uses the Enter key to activate the Delete Account link,
     * preserving the existing test behavior.
     */
    public void clickDeleteAccount() {
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        deleteAccountLink
                )
        ).sendKeys(Keys.ENTER);
    }

    /**
     * Logs out the currently authenticated user.
     */
    public void clickLogout() {
        safeClick(logoutLink);
    }

    /**
     * Navigates to the Contact Us page.
     */
    public void clickContactUs() {
        safeClick(contactUsLink);
    }

    /**
     * Navigates to the Test Cases page.
     */
    public void clickTestCases() {
        safeClick(testCasesLink);
    }

    /**
     * Adds the first product to the shopping cart
     * using the reusable ProductCartComponent.
     */
    public void addFirstItemToCart() {
        productCart.addFirstItemToCart();
    }

    /**
     * Closes the cart confirmation modal to continue shopping.
     *
     * The existing method name is preserved for compatibility.
     */
    public void setAddContinue() {
        productCart.clickContinueShopping();
    }

    /**
     * Adds the second product to the shopping cart.
     */
    public void addSecondItemToCart() {
        productCart.addSecondItemToCart();
    }

    /**
     * Opens the shopping cart from the confirmation modal.
     */
    public void viewCart() {
        productCart.viewCart();
    }

    /**
     * Scrolls to the recommended items section and checks
     * whether its heading is visible.
     *
     * @return true if the recommended items heading is displayed
     */
    public boolean isRecommendedItemsVisible() {
        WebElement title = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        recommendedItemsTitle
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                title
        );

        return title.isDisplayed();
    }

    /**
     * Returns the name of the first recommended product
     * in the currently active carousel slide.
     *
     * @return the recommended product name
     */
    public String getFirstRecommendedProductName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstRecommendedProduct
                )
        ).getText().trim();
    }

    /**
     * Adds the first recommended product from the active
     * carousel slide to the shopping cart.
     */
    public void addFirstRecommendedProductToCart() {
        safeClick(firstRecommendedAddToCart);
    }

    /**
     * Opens the shopping cart from the product-added modal.
     */
    public void clickViewCartFromModal() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartModal
                )
        );

        safeClick(viewCartFromModal);
    }
}
