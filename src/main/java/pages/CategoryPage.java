
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the product category navigation.
 *
 * Provides methods for expanding product categories,
 * selecting subcategories, and verifying category pages.
 */
public class CategoryPage extends BasePage {

    // Main product categories.
    private final By womenCategory =
            By.cssSelector("a[href='#Women']");

    private final By menCategory =
            By.cssSelector("a[href='#Men']");

    private final By kidsCategory =
            By.cssSelector("a[href='#Kids']");

    // Women's subcategories.
    private final By dressCategory =
            By.cssSelector("a[href='/category_products/1']");

    private final By topsCategory =
            By.cssSelector("a[href='/category_products/2']");

    private final By sareeCategory =
            By.cssSelector("a[href='/category_products/7']");

    // Men's subcategories.
    private final By tshirtCategory =
            By.cssSelector("a[href='/category_products/3']");

    private final By jeansCategory =
            By.cssSelector("a[href='/category_products/6']");

    // Kids' subcategories.
    private final By kidsDressCategory =
            By.cssSelector("a[href='/category_products/4']");

    private final By topsAndShirtsCategory =
            By.cssSelector("a[href='/category_products/5']");

    // Category sidebar and category page heading.
    private final By categorySidebar =
            By.cssSelector(".left-sidebar #accordian");

    private final By categoryTitle =
            By.cssSelector(".features_items h2.title");

    /**
     * Initializes the Category Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Expands the Women's category in the sidebar.
     */
    public void clickWomenCategory() {
        safeClick(womenCategory);
    }

    /**
     * Opens the Women's Dress subcategory.
     */
    public void clickDressSubcategory() {
        clickSubcategory(dressCategory);
    }

    /**
     * Opens the Women's Tops subcategory.
     */
    public void clickTopsSubcategory() {
        clickSubcategory(topsCategory);
    }

    /**
     * Opens the Women's Saree subcategory.
     */
    public void clickSareeSubcategory() {
        clickSubcategory(sareeCategory);
    }

    /**
     * Expands the Men's category in the sidebar.
     */
    public void clickMenCategory() {
        safeClick(menCategory);
    }

    /**
     * Opens the Men's T-shirt subcategory.
     */
    public void clickTshirtSubcategory() {
        clickSubcategory(tshirtCategory);
    }

    /**
     * Opens the Men's Jeans subcategory.
     */
    public void clickJeansSubcategory() {
        clickSubcategory(jeansCategory);
    }

    /**
     * Expands the Kids' category in the sidebar.
     */
    public void clickKidsCategory() {
        safeClick(kidsCategory);
    }

    /**
     * Opens the Kids' Dress subcategory.
     */
    public void clickKidsDressSubcategory() {
        clickSubcategory(kidsDressCategory);
    }

    /**
     * Opens the Kids' Tops & Shirts subcategory.
     */
    public void clickTopsandshirtsSubcategory() {
        clickSubcategory(topsAndShirtsCategory);
    }

    /**
     * Checks whether the category sidebar is visible.
     *
     * @return true if the category sidebar is displayed
     */
    public boolean isCategorySidebarVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        categorySidebar
                )
        ).isDisplayed();
    }

    /**
     * Returns the heading displayed on the category products page.
     *
     * @return the visible category page title
     */
    public String getCategoryTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        categoryTitle
                )
        ).getText();
    }

    /**
     * Waits for a subcategory link to become visible
     * and then clicks it.
     *
     * @param subcategoryLocator the locator of the subcategory link
     */
    private void clickSubcategory(By subcategoryLocator) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subcategoryLocator
                )
        );

        safeClick(subcategoryLocator);
    }
}
