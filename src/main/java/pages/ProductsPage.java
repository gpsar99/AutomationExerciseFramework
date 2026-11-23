
package pages;

import components.ProductCartComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object representing the Products page.
 *
 * Provides methods for searching products, verifying
 * product information, opening product details,
 * and adding products to the shopping cart.
 */
public class ProductsPage extends BasePage {

    // Product details navigation.
    private final By firstProductButton =
            By.cssSelector("a[href='/product_details/1']");

    // Product search elements.
    private final By searchInput =
            By.id("search_product");

    private final By searchButton =
            By.id("submit_search");

    // Product names displayed in the products section.
    private final By productNames =
            By.cssSelector(".productinfo p");

    // Page headings.
    private final By searchedProductsTitle =
            By.xpath("//h2[normalize-space()='Searched Products']");

    private final By allProductsTitle =
            By.xpath("//h2[normalize-space()='All Products']");

    // Product cards displayed on the page.
    private final By allProducts =
            By.cssSelector(".features_items .product-image-wrapper");

    // Reusable component for shopping cart interactions.
    private final ProductCartComponent productCart;

    /**
     * Initializes the Products Page Object
     * and its reusable cart component.
     *
     * @param driver the WebDriver instance used by the test
     */
    public ProductsPage(WebDriver driver) {
        super(driver);
        productCart = new ProductCartComponent(driver);
    }

    /**
     * Searches for products using the provided name.
     *
     * Clears the search field before entering a new query.
     *
     * @param productName the product name or keyword to search
     */
    public void searchProduct(String productName) {
        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        searchInput
                )
        );

        searchField.clear();
        searchField.sendKeys(productName);

        safeClick(searchButton);
    }

    /**
     * Returns the visible product name elements.
     *
     * @return a list of WebElements containing product names
     */
    public List<WebElement> getProductNames() {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        productNames
                )
        );
    }

    /**
     * Returns the Searched Products page heading.
     *
     * @return the visible searched products heading
     */
    public String getSearchedProductsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        searchedProductsTitle
                )
        ).getText();
    }

    /**
     * Checks whether a product matching the expected name
     * is displayed in the current product results.
     *
     * Normalizes whitespace before comparing product names.
     *
     * @param expectedProductName the product name to search for
     * @return true if a matching product is found
     */
    public boolean isProductDisplayed(String expectedProductName) {
        List<WebElement> displayedProducts = getProductNames();

        for (WebElement product : displayedProducts) {
            String actualProductName = product.getText()
                    .replaceAll("\\s+", " ")
                    .trim();

            if (actualProductName.contains(expectedProductName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Opens the details page for the first product.
     */
    public void clickFirstProduct() {
        safeClick(firstProductButton);
    }

    /**
     * Returns the All Products page heading.
     *
     * @return the visible all products heading
     */
    public String getAllProductsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        allProductsTitle
                )
        ).getText();
    }

    /**
     * Returns all visible product cards.
     *
     * @return a list of displayed product WebElements
     */
    public List<WebElement> getAllProducts() {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        allProducts
                )
        );
    }

    /**
     * Adds the first product to the shopping cart.
     */
    public void addFirstItemToCart() {
        productCart.addFirstItemToCart();
    }

    /**
     * Closes the product-added modal to continue shopping.
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
     * Opens the shopping cart from the product-added modal.
     */
    public void viewCart() {
        productCart.viewCart();
    }

    /**
     * Adds the first product from the search results
     * to the shopping cart.
     */
    public void addFirstSearchedProductToCart() {
        productCart.addFirstSearchedProductToCart();
    }

    /**
     * Waits until the product-added confirmation
     * is displayed.
     */
    public void waitForProductAddedToCart() {
        productCart.waitForProductAddedToCart();
    }
}
