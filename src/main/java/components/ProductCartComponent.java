
package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

/**
 * Reusable component for adding products to the shopping cart
 * and interacting with the cart confirmation modal.
 */
public class ProductCartComponent extends BasePage {

    // Locators for adding the first two products to the cart.
    private final By addFirstItemCartButton =
            By.xpath("//a[@data-product-id='1' and contains(@class,'add-to-cart')]");

    private final By addSecondItemCartButton =
            By.xpath("//a[@data-product-id='2' and contains(@class,'add-to-cart')]");

    // Cart confirmation modal and its action buttons.
    private final By cartModal = By.id("cartModal");

    private final By addedToCartModal =
            By.cssSelector("#cartModal .modal-content");

    private final By continueShoppingButton =
            By.cssSelector("#cartModal button[data-dismiss='modal']");

    private final By viewCartButton =
            By.cssSelector("#cartModal a[href='/view_cart']");

    // Add-to-cart button for the first product in search results.
    private final By firstSearchedProductAddToCart =
            By.cssSelector(
                    ".features_items .product-image-wrapper:first-child " +
                            ".productinfo a.add-to-cart"
            );

    /**
     * Initializes the component with the current WebDriver instance.
     *
     * @param driver the WebDriver used by the test
     */
    public ProductCartComponent(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits until the cart confirmation modal becomes visible.
     */
    public void waitForProductAddedToCart() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addedToCartModal
                )
        );
    }

    /**
     * Adds the first product to the shopping cart.
     */
    public void addFirstItemToCart() {
        safeClick(addFirstItemCartButton);
    }

    /**
     * Closes the cart modal and continues shopping.
     */
    public void clickContinueShopping() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartModal)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueShoppingButton
                )
        ).click();
    }

    /**
     * Adds the second product to the shopping cart.
     */
    public void addSecondItemToCart() {
        safeClick(addSecondItemCartButton);
    }

    /**
     * Opens the shopping cart from the confirmation modal.
     */
    public void viewCart() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartModal)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(viewCartButton)
        ).click();
    }

    /**
     * Adds the first product displayed in the search results
     * to the shopping cart.
     */
    public void addFirstSearchedProductToCart() {
        safeClick(firstSearchedProductAddToCart);
    }
}
