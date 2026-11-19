package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class ProductCartComponent extends BasePage {
    private final By addFirstItemCartButton = By.xpath("//a[@data-product-id='1' and contains(@class,'add-to-cart')]");
    private final By addSecondItemCartButton = By.xpath("//a[@data-product-id='2' and contains(@class,'add-to-cart')]");
    private final By addContinueButton = By.cssSelector("#cartModal button[data-dismiss='modal']");
    private final By viewCartButton = By.cssSelector("#cartModal a[href='/view_cart']");
    public ProductCartComponent (WebDriver driver){
        super(driver);
    }

    public void addFirstItemToCart(){
        safeClick(addFirstItemCartButton);
    }
    public void clickContinueShopping() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("cartModal")
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(addContinueButton)
        ).click();
    }
    public void addSecondItemToCart(){
        safeClick(addSecondItemCartButton);
    }
    public void viewCart() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("cartModal")
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(viewCartButton)
        ).click();
    }
}
