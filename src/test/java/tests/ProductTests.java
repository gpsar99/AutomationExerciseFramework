package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.ProductsPage;

import java.util.List;

public class ProductTests extends BaseTest {

    @Test(description = "TC08 - Verify All Products and Product Detail Page")
    public void viewProductsTest(){
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        successfullyChangePage("https://automationexercise.com/products");
        Assert.assertEquals(
                productsPage.getAllProductsTitle(),
                "ALL PRODUCTS"
        );
        Assert.assertFalse(productsPage.getAllProducts().isEmpty(), "Products list is empty");
        closeGoogleVignetteIfPresent();
        productsPage.clickFirstProduct();
        closeGoogleVignetteIfPresent();
        successfullyChangePage("https://automationexercise.com/product_details/1");
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        //Label
        Assert.assertTrue(productDetailsPage.isProductNameDisplayed() ,  "Product name is not displayed");
        //Category
        Assert.assertTrue(productDetailsPage.isProductCategoryDisplayed() ,  "Product Category is not displayed");
        //Price
        Assert.assertTrue(productDetailsPage.isProductPriceDisplayed(), "Product Price is not displayed");
        //Availability
        Assert.assertTrue(productDetailsPage.isProductAvailabilityDisplayed(), "Product Availability is not displayed");
        //Condition
        Assert.assertTrue(productDetailsPage.isProductConditionDisplayed(), "Product Condition is not displayed");
        //Brand
        Assert.assertTrue(productDetailsPage.isProductBrandDisplayed(), "Product Brand is not displayed");
    }
    @Test (description = "TC09 - Search Product")
    public void searchProduct(){
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        successfullyChangePage("https://automationexercise.com/products");
        closeGoogleVignetteIfPresent();
        Assert.assertEquals(productsPage.getAllProductsTitle(),
                "ALL PRODUCTS"
        );
        productsPage.searchProduct("Stylish Dress");
        closeGoogleVignetteIfPresent();
        Assert.assertEquals(
                productsPage.getSearchedProductsTitle(),
                "SEARCHED PRODUCTS"
        );
        Assert.assertTrue(
                productsPage.isProductDisplayed("Stylish Dress"),
                "Stylish Dress was not found in search results"
        );
    }


}
