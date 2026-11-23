
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the product details page.
 *
 * Provides methods for verifying product information,
 * updating product quantity, adding products to the cart,
 * and submitting product reviews.
 */
public class ProductDetailsPage extends BasePage {

    // Product information locators.
    private final By productName =
            By.xpath(
                    "//div[contains(@class,'product-information')]/h2"
            );

    private final By productCategory =
            By.xpath(
                    "//div[contains(@class,'product-information')]" +
                            "/p[contains(.,'Category:')]"
            );

    private final By productPrice =
            By.xpath(
                    "//div[contains(@class,'product-information')]" +
                            "/span/span"
            );

    private final By productAvailability =
            By.xpath(
                    "//div[contains(@class,'product-information')]" +
                            "/p[b[contains(text(),'Availability:')]]"
            );

    private final By productCondition =
            By.xpath(
                    "//div[contains(@class,'product-information')]" +
                            "/p[b[contains(text(),'Condition:')]]"
            );

    private final By productBrand =
            By.xpath(
                    "//div[contains(@class,'product-information')]" +
                            "/p[b[contains(text(),'Brand:')]]"
            );

    // Product quantity and cart actions.
    private final By quantityInput =
            By.id("quantity");

    // Public visibility preserved for compatibility with existing tests.
    public final By addCartButton =
            By.xpath(
                    "//button[contains(@class,'cart') " +
                            "and normalize-space()='Add to cart']"
            );

    private final By viewCartButton =
            By.xpath("//u[normalize-space()='View Cart']");

    // Product review section.
    private final By writeYourReview =
            By.xpath("//a[@href='#reviews']");

    private final By reviewName =
            By.id("name");

    private final By reviewEmail =
            By.id("email");

    private final By reviewText =
            By.id("review");

    private final By submitReview =
            By.id("button-review");

    private final By reviewSuccessMessage =
            By.cssSelector("#review-section .alert-success");

    /**
     * Initializes the Product Details Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks whether the product name is displayed.
     *
     * @return true if the product name is visible
     */
    public boolean isProductNameDisplayed() {
        return isElementVisible(productName);
    }

    /**
     * Checks whether the product category is displayed.
     *
     * @return true if the product category is visible
     */
    public boolean isProductCategoryDisplayed() {
        return isElementVisible(productCategory);
    }

    /**
     * Checks whether the product price is displayed.
     *
     * @return true if the product price is visible
     */
    public boolean isProductPriceDisplayed() {
        return isElementVisible(productPrice);
    }

    /**
     * Checks whether product availability information is displayed.
     *
     * @return true if availability information is visible
     */
    public boolean isProductAvailabilityDisplayed() {
        return isElementVisible(productAvailability);
    }

    /**
     * Checks whether product condition information is displayed.
     *
     * @return true if condition information is visible
     */
    public boolean isProductConditionDisplayed() {
        return isElementVisible(productCondition);
    }

    /**
     * Checks whether the product brand is displayed.
     *
     * @return true if the product brand is visible
     */
    public boolean isProductBrandDisplayed() {
        return isElementVisible(productBrand);
    }

    /**
     * Updates the quantity of the selected product.
     *
     * Clears the existing value before entering the new quantity.
     *
     * @param quantity the quantity to enter
     */
    public void setQuantity(int quantity) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        quantityInput
                )
        );

        input.clear();
        input.sendKeys(String.valueOf(quantity));
    }

    /**
     * Adds the selected product to the shopping cart.
     */
    public void addCart() {
        safeClick(addCartButton);
    }

    /**
     * Opens the shopping cart from the product-added modal.
     */
    public void viewCart() {
        safeClick(viewCartButton);
    }

    /**
     * Checks whether the Write Your Review section is visible.
     *
     * @return true if the review section is visible
     */
    public boolean isWriteYourReviewVisible() {
        return isElementVisible(writeYourReview);
    }

    /**
     * Submits a product review using the provided details.
     *
     * @param name the reviewer's name
     * @param email the reviewer's email address
     * @param review the review content
     */
    public void submitReview(
            String name,
            String email,
            String review
    ) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewName
                )
        ).sendKeys(name);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewEmail
                )
        ).sendKeys(email);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewText
                )
        ).sendKeys(review);

        safeClick(submitReview);
    }

    /**
     * Returns the success message displayed after
     * submitting a product review.
     *
     * @return the product review confirmation message
     */
    public String getReviewSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        reviewSuccessMessage
                )
        ).getText();
    }

    /**
     * Waits for an element to become visible.
     *
     * @param locator the locator of the element to verify
     * @return true if the element becomes visible
     */
    private boolean isElementVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        ).isDisplayed();
    }
}
