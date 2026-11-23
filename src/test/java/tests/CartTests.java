
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountInformationPage;
import pages.AccountStatusPage;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.PaymentPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;
import pages.SignupLoginPage;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Test class for shopping cart, checkout, payment,
 * product quantity, address verification, and invoice download.
 *
 * Covers Automation Exercise test cases:
 * TC12, TC13, TC14, TC15, TC16, TC17, TC22, TC23, and TC24.
 */
public class CartTests extends BaseTest {

    private static final String BASE_URL =
            "https://automationexercise.com/";

    private static final String TEST_USER_NAME = "George";
    private static final String TEST_PASSWORD = "TEvsivis@";

    private static final String ORDER_COMMENT =
            "Please deliver the order carefully.";

    private static final String ORDER_SUCCESS_MESSAGE =
            "Congratulations! Your order has been confirmed!";

    /**
     * TC12: Verifies that two products can be added to the cart.
     *
     * Checks product count, quantities, prices, and total prices.
     */
    @Test(description = "Test Case 12: Add Products in Cart")
    public void addProductInCart() {

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);

        closeGoogleVignetteIfPresent();

        productsPage.addFirstItemToCart();
        productsPage.setAddContinue();
        productsPage.addSecondItemToCart();
        productsPage.viewCart();

        CartPage cartPage = new CartPage(driver);

        closeGoogleVignetteIfPresent();

        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                2,
                "The cart should contain two products"
        );

        Assert.assertTrue(
                cartPage.areAllQuantitiesOne(),
                "All products should have quantity 1"
        );

        List<String> prices = cartPage.getCartPrices();

        Assert.assertEquals(
                prices.size(),
                2,
                "Expected two product prices"
        );

        Assert.assertEquals(prices.get(0), "Rs. 500");
        Assert.assertEquals(prices.get(1), "Rs. 400");

        List<String> totalPrices =
                cartPage.getCartTotalPrices();

        Assert.assertEquals(
                totalPrices.size(),
                2,
                "Expected two product total prices"
        );

        Assert.assertEquals(totalPrices.get(0), "Rs. 500");
        Assert.assertEquals(totalPrices.get(1), "Rs. 400");
    }

    /**
     * TC13: Verifies that the selected product quantity
     * is correctly reflected in the shopping cart.
     */
    @Test(description = "Test Case 13: Verify Product Quantity in Cart")
    public void verifyProductQuantityInCart() {

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        closeGoogleVignetteIfPresent();

        ProductsPage productsPage = new ProductsPage(driver);

        closeGoogleVignetteIfPresent();

        productsPage.clickFirstProduct();

        closeGoogleVignetteIfPresent();

        ProductDetailsPage productDetailsPage =
                new ProductDetailsPage(driver);

        closeGoogleVignetteIfPresent();

        productDetailsPage.setQuantity(5);
        productDetailsPage.addCart();
        productDetailsPage.viewCart();

        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                1,
                "The cart should contain one product"
        );

        Assert.assertEquals(
                cartPage.getFirstProductQuantity(),
                "5",
                "Product quantity should be 5"
        );
    }

    /**
     * TC14: Verifies order placement when the user
     * registers during the checkout process.
     */
    @Test(description = "Test Case 14: Place Order - Register while Checkout")
    public void placeOrderRegisterWhileCheckoutTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("automationexercise.com"),
                "Home page was not loaded"
        );

        homePage.addFirstItemToCart();
        homePage.viewCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart"),
                "Cart page was not loaded"
        );

        CartPage cartPage = new CartPage(driver);

        closeGoogleVignetteIfPresent();

        cartPage.clickproceedToCheckOut();

        Assert.assertTrue(
                cartPage.isCheckoutModalVisible(),
                "Checkout modal did not open"
        );

        cartPage.clickRegisterLoginButton();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Signup/Login page was not loaded"
        );

        closeGoogleVignetteIfPresent();

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        signupLoginPage.signup(
                TEST_USER_NAME,
                generateUniqueEmail()
        );

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );

        accountInformationPage.enterPassword(TEST_PASSWORD);
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();

        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );

        accountStatusPage.clickContinue();

        closeGoogleVignetteIfPresent();

        Assert.assertEquals(
                homePage.getLoggedInUser(),
                "Logged in as George"
        );

        homePage.clickCart();

        closeGoogleVignetteIfPresent();

        cartPage.clickproceedToCheckOut();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        verifyCheckoutHeadings(checkoutPage);

        Assert.assertTrue(
                checkoutPage.getDeliveryAddress().contains("George"),
                "Delivery address does not contain George"
        );

        Assert.assertTrue(
                checkoutPage.getBillingAddress().contains("George"),
                "Billing address does not contain George"
        );

        checkoutPage.enterOrderComment(ORDER_COMMENT);
        checkoutPage.clickPlaceOrder();

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment"),
                "Payment page was not loaded. Current URL: "
                        + driver.getCurrentUrl()
        );

        PaymentPage paymentPage = new PaymentPage(driver);

        completePayment(paymentPage);

        closeGoogleVignetteIfPresent();

        deleteAccount(homePage, accountStatusPage);
    }

    /**
     * TC15: Verifies order placement when the user
     * registers before adding products to the cart.
     */
    @Test(description = "Test Case 15: Place Order: Register before Checkout")
    public void placeOrderRegisterBeforeCheckoutTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                driver.getCurrentUrl(),
                BASE_URL
        );

        closeGoogleVignetteIfPresent();

        homePage.clickSignupLogin();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Signup/Login page was not loaded"
        );

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        signupLoginPage.signup(
                TEST_USER_NAME,
                generateUniqueEmail()
        );

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );

        accountInformationPage.enterPassword(TEST_PASSWORD);
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();

        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );

        accountStatusPage.clickContinue();

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                homePage.getLoggedInUser().contains("George"),
                "User is not logged in as George"
        );

        homePage.addFirstItemToCart();
        homePage.viewCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart"),
                "Cart page was not loaded"
        );

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickproceedToCheckOut();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        verifyCheckoutHeadings(checkoutPage);

        Assert.assertTrue(
                checkoutPage.getDeliveryAddress().contains("George"),
                "Delivery address does not contain George"
        );

        Assert.assertTrue(
                checkoutPage.getBillingAddress().contains("George"),
                "Billing address does not contain George"
        );

        checkoutPage.enterOrderComment(ORDER_COMMENT);
        checkoutPage.clickPlaceOrder();

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment"),
                "Payment page was not loaded"
        );

        PaymentPage paymentPage = new PaymentPage(driver);

        completePayment(paymentPage);

        closeGoogleVignetteIfPresent();

        deleteAccount(homePage, accountStatusPage);
    }


    /**
     * TC16: Verifies order placement after logging in
     * with an existing registered account.
     *
     * Requires TEST_EMAIL and TEST_PASSWORD environment variables.
     *
     * The shared test account is preserved for other test cases.
     */
    @Test(description = "Test Case 16: Place Order: Login before Checkout")
    public void placeOrderLoginBeforeCheckoutTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                driver.getCurrentUrl(),
                BASE_URL
        );

        closeGoogleVignetteIfPresent();

        homePage.clickSignupLogin();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Login page was not loaded"
        );

        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");

        Assert.assertNotNull(
                email,
                "TEST_EMAIL environment variable is missing"
        );

        Assert.assertNotNull(
                password,
                "TEST_PASSWORD environment variable is missing"
        );

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        signupLoginPage.login(email, password);

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                homePage.getLoggedInUser().contains("Georgios"),
                "User is not logged in as Georgios"
        );

        homePage.addFirstItemToCart();
        closeGoogleVignetteIfPresent();
        homePage.viewCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart"),
                "Cart page was not loaded"
        );

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickproceedToCheckOut();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        verifyCheckoutHeadings(checkoutPage);

        Assert.assertTrue(
                checkoutPage.getDeliveryAddress().contains("GEORGIOS"),
                "Delivery address does not contain GEORGIOS"
        );

        Assert.assertTrue(
                checkoutPage.getBillingAddress().contains("GEORGIOS"),
                "Billing address does not contain GEORGIOS"
        );

        checkoutPage.enterOrderComment(ORDER_COMMENT);
        checkoutPage.clickPlaceOrder();

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment"),
                "Payment page was not loaded"
        );

        PaymentPage paymentPage = new PaymentPage(driver);

        completePayment(paymentPage);

        closeGoogleVignetteIfPresent();

        // Preserve the shared account for other regression tests.
    }


    /**
     * TC17: Verifies that a product can be removed
     * from the shopping cart.
     */
    @Test(description = "Test Case 17: Remove Products From Cart")
    public void removeProductsFromCartTest() {

        HomePage homePage = new HomePage(driver);

        homePage.addFirstItemToCart();
        homePage.viewCart();

        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                1,
                "The cart should initially contain one product"
        );

        closeGoogleVignetteIfPresent();

        cartPage.removeFirstProductsFromCart();

        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                0,
                "The cart should be empty after removing the product"
        );
    }

    /**
     * TC22: Verifies that a recommended product
     * can be added to the shopping cart.
     */
    @Test(description = "Test Case 22: Add to Cart from Recommended Items")
    public void addToCartFromRecommendedItemsTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(
                homePage.isRecommendedItemsVisible(),
                "Recommended Items section is not visible"
        );

        String recommendedProductName =
                homePage.getFirstRecommendedProductName();

        closeGoogleVignetteIfPresent();

        homePage.addFirstRecommendedProductToCart();
        homePage.clickViewCartFromModal();

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(
                cartPage.getCartProductNames()
                        .contains(recommendedProductName),
                "Recommended product was not found in Cart: "
                        + recommendedProductName
        );
    }

    /**
     * TC23: Verifies that delivery and billing addresses
     * match the address information entered during signup.
     */
    @Test(description = "Test Case 23: Verify Address Details in Checkout")
    public void verifyAddressDetailsInCheckoutTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                driver.getCurrentUrl(),
                BASE_URL
        );

        closeGoogleVignetteIfPresent();

        homePage.clickSignupLogin();

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        signupLoginPage.signup(
                TEST_USER_NAME,
                generateUniqueEmail()
        );

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );

        accountInformationPage.enterPassword(TEST_PASSWORD);
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();

        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );

        accountStatusPage.clickContinue();

        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                homePage.getLoggedInUser().contains("George"),
                "User is not logged in as George"
        );

        homePage.addFirstItemToCart();
        homePage.viewCart();

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickproceedToCheckOut();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );

        String deliveryAddress =
                checkoutPage.getDeliveryAddress();

        String billingAddress =
                checkoutPage.getBillingAddress();

        String[] expectedAddressDetails = {
                "George",
                "Papadopoulos",
                "QA Automation",
                "123 Test Street",
                "Apartment 4",
                "United States",
                "California",
                "Los Angeles",
                "90001"
        };

        for (String expectedDetail : expectedAddressDetails) {

            Assert.assertTrue(
                    deliveryAddress.contains(expectedDetail),
                    "Delivery Address missing: " + expectedDetail
            );

            Assert.assertTrue(
                    billingAddress.contains(expectedDetail),
                    "Billing Address missing: " + expectedDetail
            );
        }

        System.out.println(
                "Delivery and Billing Address verified successfully."
        );

        homePage.clickDeleteAccount();

        closeGoogleVignetteIfPresent();

        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );

        accountStatusPage.clickContinue();
    }

    /**
     * TC24: Verifies that an invoice can be downloaded
     * after successfully placing an order.
     */
    @Test(description = "Test Case 24: Download Invoice after Purchase Order")
    public void downloadInvoiceAfterPurchaseOrderTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                driver.getCurrentUrl(),
                BASE_URL
        );

        // Remove old invoice files before the test.
        clearPreviousInvoices();

        closeGoogleVignetteIfPresent();

        homePage.addFirstItemToCart();
        homePage.viewCart();

        closeGoogleVignetteIfPresent();

        CartPage cartPage = new CartPage(driver);

        cartPage.clickproceedToCheckOut();
        cartPage.clickRegisterLoginFromCheckoutModal();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Login page was not opened"
        );

        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);

        signupLoginPage.signup(
                TEST_USER_NAME,
                generateUniqueEmail()
        );

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        accountInformationPage.enterPassword(TEST_PASSWORD);
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();

        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );

        accountStatusPage.clickContinue();

        closeGoogleVignetteIfPresent();

        homePage.clickCart();

        closeGoogleVignetteIfPresent();

        cartPage.clickproceedToCheckOut();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );

        checkoutPage.enterOrderComment(ORDER_COMMENT);
        checkoutPage.clickPlaceOrder();

        closeGoogleVignetteIfPresent();

        PaymentPage paymentPage = new PaymentPage(driver);

        completePayment(paymentPage);

        closeGoogleVignetteIfPresent();

        paymentPage.clickDownloadInvoice();

        Assert.assertTrue(
                paymentPage.isInvoiceDownloaded(),
                "Invoice was not downloaded successfully"
        );

        System.out.println("Invoice downloaded successfully!");

        closeGoogleVignetteIfPresent();

        deleteAccount(homePage, accountStatusPage);
    }

    /**
     * Generates a unique email address for test registration.
     *
     * @return a randomly generated test email address
     */
    private String generateUniqueEmail() {
        return "test_"
                + UUID.randomUUID().toString().replace("-", "")
                + "@gmail.com";
    }

    /**
     * Verifies the Address Details and Review Your Order
     * headings on the checkout page.
     *
     * @param checkoutPage the Checkout Page Object
     */
    private void verifyCheckoutHeadings(CheckoutPage checkoutPage) {

        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );

        Assert.assertEquals(
                checkoutPage.getReviewOrderTitle(),
                "Review Your Order"
        );
    }

    /**
     * Enters test payment information, submits the order,
     * and verifies the order confirmation message.
     *
     * @param paymentPage the Payment Page Object
     */
    private void completePayment(PaymentPage paymentPage) {

        paymentPage.fillPaymentDetails(
                "George",
                "4111111111111111",
                "123",
                "12",
                "2030"
        );

        paymentPage.clickPayAndConfirmOrder();

        closeGoogleVignetteIfPresent();

        Assert.assertEquals(
                paymentPage.getOrderSuccessMessage(),
                ORDER_SUCCESS_MESSAGE,
                "Order confirmation message is incorrect"
        );
    }

    /**
     * Deletes the registered test account and verifies
     * the account deletion confirmation.
     *
     * @param homePage the Home Page Object
     * @param accountStatusPage the Account Status Page Object
     */
    private void deleteAccount(
            HomePage homePage,
            AccountStatusPage accountStatusPage
    ) {

        homePage.clickDeleteAccount();

        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );

        accountStatusPage.clickContinue();
    }

    /**
     * Deletes previously downloaded invoice text files
     * to avoid false positives during invoice verification.
     */
    private void clearPreviousInvoices() {

        File downloadFolder = new File(
                System.getProperty("user.dir"),
                "downloads"
        );

        File[] oldInvoices = downloadFolder.listFiles(
                file -> file.isFile()
                        && file.getName().startsWith("invoice")
                        && file.getName().endsWith(".txt")
        );

        if (oldInvoices != null) {
            for (File invoice : oldInvoices) {

                Assert.assertTrue(
                        invoice.delete(),
                        "Could not delete old invoice: "
                                + invoice.getName()
                );
            }
        }
    }
}
