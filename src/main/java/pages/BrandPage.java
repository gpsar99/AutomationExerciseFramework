
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the brand products pages.
 *
 * Provides methods for navigating between brands,
 * verifying the brands sidebar, and retrieving
 * information about displayed products.
 */
public class BrandPage extends BasePage {

    // Sidebar containing the available product brands.
    private final By brandsSidebar =
            By.cssSelector(".brands_products");

    // Brand navigation links.
    private final By poloBrand =
            By.cssSelector("a[href='/brand_products/Polo']");

    private final By hmBrand =
            By.cssSelector("a[href='/brand_products/H&M']");

    // Heading displayed on the selected brand page.
    private final By brandTitle =
            By.cssSelector(".features_items h2.title");

    // Product cards displayed for the selected brand.
    private final By brandProducts =
            By.cssSelector(".features_items .product-image-wrapper");

    /**
     * Initializes the Brand Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public BrandPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks whether the brands sidebar is visible.
     *
     * @return true if the brands sidebar is visible
     */
    public boolean isBrandsSidebarVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        brandsSidebar
                )
        ).isDisplayed();
    }

    /**
     * Navigates to the Polo brand products page.
     */
    public void clickPoloBrand() {
        safeClick(poloBrand);
    }

    /**
     * Navigates to the H&M brand products page.
     */
    public void clickHmBrand() {
        safeClick(hmBrand);
    }

    /**
     * Returns the heading of the selected brand page.
     *
     * @return the visible brand page heading
     */
    public String getBrandTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        brandTitle
                )
        ).getText();
    }

    /**
     * Returns the number of products displayed
     * on the selected brand page.
     *
     * Waits until at least one product is visible
     * before counting the product cards.
     *
     * @return the number of displayed product cards
     */
    public int getBrandProductsCount() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        brandProducts
                )
        );

        return driver.findElements(brandProducts).size();
    }
}
