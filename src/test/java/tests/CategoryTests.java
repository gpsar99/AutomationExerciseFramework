package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;

public class CategoryTests extends BaseTest {

    @Test(description = "Test Case 18: View Category Products")
    public void viewCategoryProductsTest() {
        CategoryPage categoryPage = new CategoryPage(driver);
        Assert.assertTrue(categoryPage.isCategorySidebarVisible(),"Category sidebar is not visible");
        closeGoogleVignetteIfPresent();
        categoryPage.clickWomenCategory();
        categoryPage.clickDressSubcategory();
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/category_products/1")
        );

        Assert.assertEquals(
                categoryPage.getCategoryTitle(),
                "WOMEN -  Dress PRODUCTS"
        );
        closeGoogleVignetteIfPresent();
        categoryPage.clickMenCategory();
        closeGoogleVignetteIfPresent();
        categoryPage.clickJeansSubcategory();
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/category_products/6")
        );

        Assert.assertEquals(
                categoryPage.getCategoryTitle(),
                " Men -  Jeans PRODUCTS"
        );


    }

}
