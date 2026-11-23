
package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;

/**
 * Test class for verifying product category navigation.
 *
 * Covers Test Case 18 of the Automation Exercise website.
 */
public class CategoryTests extends BaseTest {

    /**
     * TC18: Verifies that users can navigate through
     * product categories and view the corresponding products.
     *
     * Validates the category sidebar, Women/Dress category,
     * and Men/Jeans category using URLs and page headings.
     */
    @Test(description = "Test Case 18: View Category Products")
    public void viewCategoryProductsTest() {

        CategoryPage categoryPage = new CategoryPage(driver);

        // Verify that the category sidebar is displayed.
        Assert.assertTrue(
                categoryPage.isCategorySidebarVisible(),
                "Category sidebar is not visible"
        );

        // Navigate to Women > Dress.
        closeGoogleVignetteIfPresent();

        categoryPage.clickWomenCategory();
        categoryPage.clickDressSubcategory();

        closeGoogleVignetteIfPresent();

        // Verify the Women/Dress category page.
        verifyCategoryPage(
                categoryPage,
                "/category_products/1",
                "WOMEN -  Dress PRODUCTS",
                "Women - Dress"
        );

        // Navigate to Men > Jeans.
        closeGoogleVignetteIfPresent();

        categoryPage.clickMenCategory();

        closeGoogleVignetteIfPresent();

        categoryPage.clickJeansSubcategory();

        closeGoogleVignetteIfPresent();

        // Verify the Men/Jeans category page.
        verifyCategoryPage(
                categoryPage,
                "/category_products/6",
                " Men -  Jeans PRODUCTS",
                "Men - Jeans"
        );
    }

    /**
     * Verifies that the expected category page is displayed.
     *
     * Waits for the URL to contain the expected category path
     * and compares the visible page heading with the expected text.
     *
     * @param categoryPage the Category Page Object
     * @param expectedUrlPart the expected category URL fragment
     * @param expectedTitle the expected category page heading
     * @param categoryName the category name used in assertion messages
     */
    private void verifyCategoryPage(
            CategoryPage categoryPage,
            String expectedUrlPart,
            String expectedTitle,
            String categoryName
    ) {

        // Wait for navigation to the expected category page.
        wait.until(
                ExpectedConditions.urlContains(expectedUrlPart)
        );

        // Verify the category URL.
        Assert.assertTrue(
                driver.getCurrentUrl().contains(expectedUrlPart),
                "Incorrect URL for category: " + categoryName
        );

        // Verify the category heading.
        Assert.assertEquals(
                categoryPage.getCategoryTitle(),
                expectedTitle,
                "Incorrect page title for category: " + categoryName
        );
    }
}
