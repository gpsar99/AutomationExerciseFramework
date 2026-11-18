package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.ProductsPage;

import java.util.List;

public class CartTests extends BaseTest {

    @Test(description = "Test Case 12: Add Products in Cart")
    public void addProductInCart(){
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        ProductsPage productpage = new ProductsPage(driver);
        closeGoogleVignetteIfPresent();
        productpage.addFirstItemToCart();
        productpage.setAddContinue();
        productpage.addSecondItemToCart();
        productpage.viewCart();
        CartPage cartPage = new CartPage(driver);
        closeGoogleVignetteIfPresent();
        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                2);
        Assert.assertTrue(
                cartPage.areAllQuantitiesOne(),
                "All products should have quantity 1");

        List<String> prices = cartPage.getCartPrices();

        Assert.assertEquals(prices.size(), 2);
        Assert.assertEquals(prices.get(0), "Rs. 500");
        Assert.assertEquals(prices.get(1), "Rs. 400");

        List<String> totalPrices = cartPage.getCartTotalPrices();

        Assert.assertEquals(totalPrices.size(), 2);
        Assert.assertEquals(totalPrices.get(0), "Rs. 500");
        Assert.assertEquals(totalPrices.get(1), "Rs. 400");
    }

    @Test(description = "Test Case 13: Verify Product Quantity in Cart")
    public void verifyProductQuantityInCart() {
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        closeGoogleVignetteIfPresent();
        ProductsPage productsPage = new ProductsPage(driver);
        closeGoogleVignetteIfPresent();
        productsPage.clickFirstProduct();
        closeGoogleVignetteIfPresent();
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        closeGoogleVignetteIfPresent();
        productDetailsPage.setQuantity(5);
        productDetailsPage.addCart();
        productDetailsPage.viewCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                1,
                "Count Is Not The Same");
        Assert.assertEquals(
                cartPage.getFirstProductQuantity(),
                "5",
                "Product quantity should be 5"
        );
    }

}
