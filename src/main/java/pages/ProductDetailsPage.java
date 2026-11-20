package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage extends BasePage{

    private final By productName =
            By.xpath("//div[contains(@class,'product-information')]/h2");
    private final By productCategory =
            By.xpath("//div[contains(@class,'product-information')]/p[contains(.,'Category:')]");
    private final By productPrice =
            By.xpath("//div[contains(@class,'product-information')]/span/span");
    private final By productAvailability =
            By.xpath("//div[contains(@class,'product-information')]/p[b[contains(text(),'Availability:')]]");
    private final By productCondition =
            By.xpath("//div[contains(@class,'product-information')]/p[b[contains(text(),'Condition:')]]");
    private final By productBrand =
            By.xpath("//div[contains(@class,'product-information')]/p[b[contains(text(),'Brand:')]]");
    private final By quantityInput = By.id("quantity");
    public final By addCartButton = By.xpath("//button[contains(@class,'cart') and normalize-space()='Add to cart']");
    private final By viewCartButton = By.xpath("//u[normalize-space()='View Cart']");
    private final By writeYourReview =
            By.xpath("//a[@href='#reviews']");

    private final By reviewName = By.id("name");
    private final By reviewEmail = By.id("email");
    private final By reviewText = By.id("review");
    private final By submitReview = By.id("button-review");

    private final By reviewSuccessMessage =
            By.cssSelector("#review-section .alert-success");



    public ProductDetailsPage(WebDriver driver){
        super(driver);
    }

    public boolean isProductNameDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productName
        )).isDisplayed();
    }

    public boolean isProductCategoryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productCategory
        )).isDisplayed();
    }

    public boolean isProductPriceDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productPrice
        )).isDisplayed();
    }

    public boolean isProductAvailabilityDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productAvailability
        )).isDisplayed();
    }

    public boolean isProductConditionDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productCondition
        )).isDisplayed();
    }

    public boolean isProductBrandDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                productBrand
        )).isDisplayed();
    }

    public void setQuantity(int quantity){
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        input.clear();
        input.sendKeys(String.valueOf(quantity));
    }

    public void addCart(){
        safeClick(addCartButton);
    }

    public void viewCart(){
        wait.until(
                ExpectedConditions.elementToBeClickable(viewCartButton)
        ).click();
    }

    public boolean isWriteYourReviewVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(writeYourReview)
        ).isDisplayed();
    }

    public void submitReview(String name, String email, String review) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(reviewName))
                .sendKeys(name);

        driver.findElement(reviewEmail).sendKeys(email);
        driver.findElement(reviewText).sendKeys(review);

        safeClick(submitReview);
    }

    public String getReviewSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(reviewSuccessMessage)
        ).getText();
    }


}
