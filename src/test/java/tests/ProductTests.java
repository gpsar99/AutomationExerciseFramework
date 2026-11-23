
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.SignupLoginPage;

/**
 * Test class for verifying product-related functionality.
 *
 * Covers Automation Exercise test cases:
 * TC08 - Verify All Products and Product Detail Page
 * TC09 - Search Product
 * TC20 - Search Products and Verify Cart After Login
 * TC21 - Add Review on Product
 */
public class ProductTests extends BaseTest {

    private static final String PRODUCTS_URL =
            "https://automationexercise.com/products";

    private static final String FIRST_PRODUCT_DETAILS_URL =
            "https://automationexercise.com/product_details/1";

    private static final String ALL_PRODUCTS_TITLE =
            "ALL PRODUCTS";

    private static final String SEARCHED_PRODUCTS_TITLE =
            "SEARCHED PRODUCTS";

    private static final String SEARCH_PRODUCT_NAME =
            "Stylish Dress";

    private static final String CART_SEARCH_PRODUCT_NAME =
            "Sleeveless Dress";

    private static final String REVIEW_SUCCESS_MESSAGE =
            "Thank you for your review.";

    /**
     * TC08: Verifies the All Products page and
     * the details of the first product.
     *
     * Checks product name, category, price,
     * availability, condition, and brand.
     */
    @Test(description = "TC08 - Verify All Products and Product Detail Page")
    public void viewProductsTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Products page.
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);

        // Verify navigation to the Products page.
        successfullyChangePage(PRODUCTS_URL);

        Assert.assertEquals(
                productsPage.getAllProductsTitle(),
                ALL_PRODUCTS_TITLE,
                "All Products page heading is incorrect"
        );

        // Verify that the product list is not empty.
        Assert.assertFalse(
                productsPage.getAllProducts().isEmpty(),
                "Products list is empty"
        );

        closeGoogleVignetteIfPresent();

        // Open the first product.
        productsPage.clickFirstProduct();

        closeGoogleVignetteIfPresent();

        // Verify navigation to the product details page.
        successfullyChangePage(FIRST_PRODUCT_DETAILS_URL);

        ProductDetailsPage productDetailsPage =
                new ProductDetailsPage(driver);

        // Verify product information.
        Assert.assertTrue(
                productDetailsPage.isProductNameDisplayed(),
                "Product name is not displayed"
        );

        Assert.assertTrue(
                productDetailsPage.isProductCategoryDisplayed(),
                "Product category is not displayed"
        );

        Assert.assertTrue(
                productDetailsPage.isProductPriceDisplayed(),
                "Product price is not displayed"
        );

        Assert.assertTrue(
                productDetailsPage.isProductAvailabilityDisplayed(),
                "Product availability is not displayed"
        );

        Assert.assertTrue(
                productDetailsPage.isProductConditionDisplayed(),
                "Product condition is not displayed"
        );

        Assert.assertTrue(
                productDetailsPage.isProductBrandDisplayed(),
                "Product brand is not displayed"
        );
    }

    /**
     * TC09: Verifies that searching for a product
     * displays the expected search results.
     */
    @Test(description = "TC09 - Search Product")
    public void searchProduct() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Products page.
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);

        successfullyChangePage(PRODUCTS_URL);

        closeGoogleVignetteIfPresent();

        Assert.assertEquals(
                productsPage.getAllProductsTitle(),
                ALL_PRODUCTS_TITLE,
                "All Products page heading is incorrect"
        );

        // Search for the specified product.
        productsPage.searchProduct(SEARCH_PRODUCT_NAME);

        closeGoogleVignetteIfPresent();

        // Verify the search results heading.
        Assert.assertEquals(
                productsPage.getSearchedProductsTitle(),
                SEARCHED_PRODUCTS_TITLE,
                "Searched Products heading is incorrect"
        );

        // Verify that the searched product is displayed.
        Assert.assertTrue(
                productsPage.isProductDisplayed(SEARCH_PRODUCT_NAME),
                SEARCH_PRODUCT_NAME + " was not found in search results"
        );
    }

    /**
     * TC20: Verifies that a product added to the cart
     * remains in the cart after user login.
     *
     * Requires TEST_EMAIL and TEST_PASSWORD
     * environment variables.
     */
    @Test(description = "Test Case 20: Search Products and Verify Cart After Login")
    public void searchProductsAndVerifyCartAfterLoginTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Products page.
        homePage.clickProducts();

        closeGoogleVignetteIfPresent();

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertEquals(
                productsPage.getAllProductsTitle(),
                ALL_PRODUCTS_TITLE,
                "All Products page heading is incorrect"
        );

        // Search for the product.
        productsPage.searchProduct(CART_SEARCH_PRODUCT_NAME);

        Assert.assertEquals(
                productsPage.getSearchedProductsTitle(),
                SEARCHED_PRODUCTS_TITLE,
                "Searched Products heading is incorrect"
        );

        // Add the searched product to the cart.
        productsPage.addFirstSearchedProductToCart();
        productsPage.waitForProductAddedToCart();

        closeGoogleVignetteIfPresent();

        // Open the cart before login.
        homePage.viewCart();

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(
                cartPage.getCartProductsCount() > 0,
                "Cart is empty after adding the searched product"
        );

        // Navigate to the Signup/Login page.
        homePage.clickSignupLogin();

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        // Read credentials from environment variables.
        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");

        Assert.assertNotNull(
                email,
                "TEST_EMAIL is not configured"
        );

        Assert.assertNotNull(
                password,
                "TEST_PASSWORD is not configured"
        );

        // Log in with the existing test account.
        signupLoginPage.login(email, password);

        // Return to the cart after login.
        homePage.clickCart();

        closeGoogleVignetteIfPresent();

        // Verify that the cart still contains products.
        Assert.assertTrue(
                cartPage.getCartProductsCount() > 0,
                "Cart is empty after login"
        );
    }

    /**
     * TC21: Verifies that a user can submit
     * a review for a product.
     *
     * Checks the review section and
     * the successful review submission message.
     */
    @Test(description = "Test Case 21: Add Review on Product")
    public void addReviewOnProductTest() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Products page.
        homePage.clickProducts();

        closeGoogleVignetteIfPresent();

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertEquals(
                productsPage.getAllProductsTitle(),
                ALL_PRODUCTS_TITLE,
                "All Products page heading is incorrect"
        );

        // Open the first product details page.
        productsPage.clickFirstProduct();

        ProductDetailsPage productDetailsPage =
                new ProductDetailsPage(driver);

        closeGoogleVignetteIfPresent();

        // Verify that the review section is displayed.
        Assert.assertTrue(
                productDetailsPage.isWriteYourReviewVisible(),
                "Write Your Review section is not visible"
        );

        // Submit a product review.
        productDetailsPage.submitReview(
                "Test User",
                "testreview@example.com",
                "Excellent product! Very good quality."
        );

        // Verify successful review submission.
        Assert.assertEquals(
                productDetailsPage.getReviewSuccessMessage(),
                REVIEW_SUCCESS_MESSAGE,
                "Product review success message is incorrect"
        );
    }
}
