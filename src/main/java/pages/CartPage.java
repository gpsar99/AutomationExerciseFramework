
package pages;

import components.SubscriptionComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object representing the shopping cart page.
 *
 * Provides methods for verifying cart products, quantities,
 * prices, and totals, as well as managing cart items
 * and interacting with the checkout modal.
 */
public class CartPage extends BasePage {

    // Reusable subscription component displayed in the footer.
    private final SubscriptionComponent subscription;

    // Shopping cart product rows.
    private final By cartProducts =
            By.cssSelector("#cart_info_table tbody tr");

    // Product quantity buttons.
    private final By cartQuantities =
            By.cssSelector("#cart_info_table tbody tr .cart_quantity button");

    // Individual product prices.
    private final By cartPrices =
            By.cssSelector("#cart_info_table tbody tr .cart_price p");

    // Total prices for individual cart products.
    private final By cartTotalPrices =
            By.cssSelector("#cart_info_table tbody tr .cart_total p");

    // Product names displayed in the cart.
    private final By cartProductNames =
            By.cssSelector("#cart_info_table .cart_description h4 a");

    // Checkout navigation.
    private final By proceedToCheckOut =
            By.xpath("//a[normalize-space()='Proceed To Checkout']");

    // Checkout modal and its action buttons.
    private final By checkoutModal =
            By.id("checkoutModal");

    private final By registerLoginButton =
            By.cssSelector("#checkoutModal a[href='/login']");

    private final By continueOnCartButton =
            By.cssSelector("button.close-checkout-modal");

    // Remove button for product with ID 1.
    private final By removeFirstProduct =
            By.xpath(
                    "//tr[@id='product-1']//a[@class='cart_quantity_delete']"
            );

    /**
     * Initializes the Cart Page Object and its subscription component.
     *
     * @param driver the WebDriver instance used by the test
     */
    public CartPage(WebDriver driver) {
        super(driver);
        subscription = new SubscriptionComponent(driver);
    }

    /**
     * Returns the subscription component associated with the cart page.
     *
     * @return the reusable SubscriptionComponent instance
     */
    public SubscriptionComponent getSubscription() {
        return subscription;
    }

    /**
     * Returns the current number of product rows in the cart.
     *
     * @return the number of cart products
     */
    public int getCartProductsCount() {
        return driver.findElements(cartProducts).size();
    }

    /**
     * Checks whether every product in the cart has quantity one.
     *
     * @return true if all displayed quantities equal one
     */
    public boolean areAllQuantitiesOne() {
        List<WebElement> quantities = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        cartQuantities
                )
        );

        for (WebElement quantity : quantities) {
            if (!quantity.getText().trim().equals("1")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the individual prices of all cart products.
     *
     * @return a list containing the displayed product prices
     */
    public List<String> getCartPrices() {
        List<WebElement> prices = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        cartPrices
                )
        );

        List<String> priceTexts = new ArrayList<>();

        for (WebElement price : prices) {
            priceTexts.add(price.getText().trim());
        }

        return priceTexts;
    }

    /**
     * Returns the total prices displayed for cart products.
     *
     * @return a list containing the displayed total prices
     */
    public List<String> getCartTotalPrices() {
        List<WebElement> totals = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        cartTotalPrices
                )
        );

        List<String> totalTexts = new ArrayList<>();

        for (WebElement total : totals) {
            totalTexts.add(total.getText().trim());
        }

        return totalTexts;
    }

    /**
     * Returns the quantity displayed for the first cart product.
     *
     * @return the first product's quantity as text
     */
    public String getFirstProductQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartQuantities
                )
        ).getText().trim();
    }

    /**
     * Clicks the Proceed To Checkout button.
     *
     * The existing method name is preserved for compatibility.
     */
    public void clickproceedToCheckOut() {
        safeClick(proceedToCheckOut);
    }

    /**
     * Checks whether the checkout modal is visible.
     *
     * @return true if the checkout modal is visible
     */
    public boolean isCheckoutModalVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        checkoutModal
                )
        ).isDisplayed();
    }

    /**
     * Clicks the Register/Login link inside the checkout modal.
     */
    public void clickRegisterLoginButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        registerLoginButton
                )
        ).click();
    }

    /**
     * Closes the checkout modal to continue shopping.
     */
    public void clickContinueOnCart() {
        safeClick(continueOnCartButton);
    }

    /**
     * Removes product ID 1 from the shopping cart.
     *
     * Waits until the removed product row becomes stale,
     * confirming that the original row is no longer attached
     * to the DOM.
     */
    public void removeFirstProductsFromCart() {
        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("product-1")
                )
        );

        safeClick(removeFirstProduct);

        wait.until(
                ExpectedConditions.stalenessOf(product)
        );
    }

    /**
     * Returns the names of products displayed in the cart.
     *
     * @return a list containing the displayed product names
     */
    public List<String> getCartProductNames() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        cartProductNames
                )
        );

        return driver.findElements(cartProductNames)
                .stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    /**
     * Clicks the Register/Login link in the checkout modal.
     *
     * This method is retained for compatibility with
     * existing checkout and invoice tests.
     */
    public void clickRegisterLoginFromCheckoutModal() {
        safeClick(registerLoginButton);
    }
}
