
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BrandPage;
import pages.HomePage;

/**
 * Test class for verifying brand-based product navigation.
 *
 * Covers Test Case 19 of the Automation Exercise website.
 */
public class BrandTests extends BaseTest {

    /**
     * TC19: Verifies that users can navigate between
     * different brands and view their associated products.
     *
     * Validates the Brands sidebar, brand page URLs,
     * page headings, and product availability.
     */
    @Test(description = "Test Case 19: View & Cart Brand Products")
    public void viewAndCartBrandProductsTest() {

        HomePage homePage = new HomePage(driver);
        BrandPage brandPage = new BrandPage(driver);

        // Navigate to the Products page.
        homePage.clickProducts();

        // Verify that the Brands sidebar is displayed.
        Assert.assertTrue(
                brandPage.isBrandsSidebarVisible(),
                "Brands sidebar is not visible"
        );

        // Navigate to Polo brand products.
        brandPage.clickPoloBrand();

        // Handle a possible Google Vignette interruption.
        closeGoogleVignetteIfPresent();

        // Preserve the existing second click workaround.
        brandPage.clickPoloBrand();

        // Verify Polo brand products.
        verifyBrandPage(
                brandPage,
                "/brand_products/Polo",
                "BRAND - POLO PRODUCTS",
                "Polo"
        );

        // Handle a possible overlay before changing brands.
        closeGoogleVignetteIfPresent();

        // Navigate to H&M brand products.
        brandPage.clickHmBrand();

        // Verify H&M brand products.
        verifyBrandPage(
                brandPage,
                "/brand_products/H&M",
                "BRAND - H&M PRODUCTS",
                "H&M"
        );
    }

    /**
     * Verifies the URL, heading, and product availability
     * of the currently displayed brand page.
     *
     * @param brandPage the Brand Page Object
     * @param expectedUrlPart the expected brand URL fragment
     * @param expectedTitle the expected brand page heading
     * @param brandName the brand name used in assertion messages
     */
    private void verifyBrandPage(
            BrandPage brandPage,
            String expectedUrlPart,
            String expectedTitle,
            String brandName
    ) {

        // Verify navigation to the expected brand page.
        Assert.assertTrue(
                driver.getCurrentUrl().contains(expectedUrlPart),
                "Incorrect URL for brand: " + brandName
        );

        // Verify the brand page heading.
        String actualTitle = brandPage.getBrandTitle()
                .replaceAll("\\s+", " ")
                .trim()
                .toUpperCase();

        Assert.assertEquals(
                actualTitle,
                expectedTitle,
                "Incorrect page title for brand: " + brandName
        );

        // Verify that at least one product is displayed.
        Assert.assertTrue(
                brandPage.getBrandProductsCount() > 0,
                "No products displayed for brand: " + brandName
        );
    }
}
