package pages;

import components.SubscriptionComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage{

    private final SubscriptionComponent subscription;
    private final By shoppingCart =
            By.id("cart_info_table");
    private final By cartProducts =
            By.cssSelector("#cart_info_table tbody tr");
    private final By cartQuantities =
            By.cssSelector("#cart_info_table tbody tr .cart_quantity button");
    private final By cartPrices =
            By.cssSelector("#cart_info_table tbody tr .cart_price p");
    private final By cartTotalPrices =
            By.cssSelector("#cart_info_table tbody tr .cart_total p");

    public CartPage(WebDriver driver){
        super(driver);
        subscription = new SubscriptionComponent(driver);
    }

    public SubscriptionComponent getSubscription(){
        return subscription;
    }

    public int getCartProductsCount() {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        cartProducts
                )
        ).size();
    }

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

    public String getFirstProductQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartQuantities)
        ).getText().trim();
    }



}
