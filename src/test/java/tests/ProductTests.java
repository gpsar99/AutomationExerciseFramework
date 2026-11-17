package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductTests extends BaseTest {

    @Test
    public void viewProductsTest(){
        safeClick(By.xpath("//a[@href='/products']"));

        successfullyChangePage("https://automationexercise.com/products");

        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[normalize-space()='All Products']"))).getText(),
                "ALL PRODUCTS");

        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".features_items .product-image-wrapper")
                )
        );

        Assert.assertFalse(products.isEmpty(), "Products list is empty");

        safeClick(By.xpath("//a[@href='/product_details/1']"));
        closeGoogleVignetteIfPresent();

        successfullyChangePage("https://automationexercise.com/product_details/1");

        //Label
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'product-information')]/h2")
        )).isDisplayed());
        //Category
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'product-information')]/p[1]")
        )).isDisplayed());
        //Price
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'product-information')]/span/span")
        )).isDisplayed());
        //Availability
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[normalize-space()='Availability:']")
        )).isDisplayed());
        //Condition
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'product-information')]//b[normalize-space()='Condition:']")
        )).isDisplayed());
        //Brand
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[normalize-space()='Brand:']")
        )).isDisplayed());


    }
    @Test
    public void searchProduct(){
        safeClick(By.xpath("//a[@href='/products']"));

        successfullyChangePage("https://automationexercise.com/products");
        closeGoogleVignetteIfPresent();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='All Products']"))).getText(),
                "ALL PRODUCTS"
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product"))).sendKeys("Stylish Dress");
        safeClick(By.id("submit_search"));

        Assert.assertEquals(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='Searched Products']")
                )).getText(),
                "SEARCHED PRODUCTS"
        );
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//p[normalize-space()='Stylish Dress']")
                )).isDisplayed(),
                "Stylish Dress was not found in search results"
        );
    }




}
