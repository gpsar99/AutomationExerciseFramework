package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import components.ProductCartComponent;

public class ProductsPage extends BasePage {

    private final By firstProductButton = By.cssSelector("a[href='/product_details/1']");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By productNames = By.cssSelector(".productinfo p");
    private final By searchedProductsTitle = By.xpath("//h2[normalize-space()='Searched Products']");
    private final By allProductsTitle = By.xpath("//h2[normalize-space()='All Products']");
    private final By allProducts = By.cssSelector(".features_items .product-image-wrapper");
    private final ProductCartComponent productCart;

    public ProductsPage(WebDriver driver) {
        super(driver);
        productCart = new ProductCartComponent(driver);
    }

    public void searchProduct(String productName){
        wait.until(ExpectedConditions.elementToBeClickable(
                searchInput
        )).sendKeys(productName);

        safeClick(searchButton);
    }

    public List<WebElement> getProductNames() {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        productNames
                ));
    }

    public  String getSearchedProductsTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                searchedProductsTitle
        )).getText();
    }

    public boolean isProductDisplayed(String expectedProductName){
        List<WebElement> productNames = getProductNames();

        for (WebElement product : productNames) {
            String productName = product.getText()
                    .replaceAll("\\s+", " ")
                    .trim();

            if (productName.contains(expectedProductName)) {
                return true;
            }
        }
        return false;
    }

    public void clickFirstProduct() {
        safeClick(firstProductButton);
    }

    public String getAllProductsTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(allProductsTitle)
        ).getText();
    }

    public List<WebElement> getAllProducts (){
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        allProducts
                ));
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