package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BrandPage;
import pages.HomePage;

public class BrandTests extends BaseTest {

    @Test(description = "Test Case 19: View & Cart Brand Products")
    public void viewAndCartBrandProductsTest() {

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        BrandPage brandPage = new BrandPage(driver);
        Assert.assertTrue(
                brandPage.isBrandsSidebarVisible(),
                "Brands sidebar is not visible"
        );
        brandPage.clickPoloBrand();
        closeGoogleVignetteIfPresent();
        brandPage.clickPoloBrand();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/brand_products/Polo")
        );
        Assert.assertEquals(
                brandPage.getBrandTitle()
                        .replaceAll("\\s+", " ")
                        .trim()
                        .toUpperCase(),
                "BRAND - POLO PRODUCTS"
        );
        Assert.assertTrue(
                brandPage.getBrandProductsCount() > 0,
                "No Polo products displayed"
        );
        closeGoogleVignetteIfPresent();
        brandPage.clickHmBrand();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/brand_products/H&M")
        );
        Assert.assertEquals(
                brandPage.getBrandTitle()
                        .replaceAll("\\s+", " ")
                        .trim()
                        .toUpperCase(),
                "BRAND - H&M PRODUCTS"
        );
        Assert.assertTrue(
                brandPage.getBrandProductsCount() > 0,
                "No H&M products displayed"
        );
    }
}