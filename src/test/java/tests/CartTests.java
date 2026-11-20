package tests;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.io.File;
import java.util.List;
import java.util.UUID;

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
                driver.getCurrentUrl().contains("https://automationexercise.com/view_cart"),
                "Home page was not loaded"
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
                driver.getCurrentUrl().contains("https://automationexercise.com/login"),
                "Home page was not loaded"
        );
        closeGoogleVignetteIfPresent();
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);
        String uniqueEmail = "test_" +
                UUID.randomUUID().toString().replace("-", "") +
                "@gmail.com";
        signupLoginPage.signup(
                "George",
                uniqueEmail
        );

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);

        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );

        accountInformationPage.enterPassword("TEvsivis@");
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
        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );
        Assert.assertEquals(
                checkoutPage.getReviewOrderTitle(),
                "Review Your Order"
        );
        String deliveryAddress = checkoutPage.getDeliveryAddress();
        String billingAddress = checkoutPage.getBillingAddress();

        Assert.assertTrue(
                deliveryAddress.contains("George"),
                "Delivery address does not contain the expected name"
        );

        Assert.assertTrue(
                billingAddress.contains("George"),
                "Billing address does not contain the expected name"
        );
        checkoutPage.enterOrderComment(
                "Please deliver the order carefully."
        );

        checkoutPage.clickPlaceOrder();
        closeGoogleVignetteIfPresent();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment"),
                "Payment page was not loaded. Current URL: "
                        + driver.getCurrentUrl()
        );

        PaymentPage paymentPage = new PaymentPage(driver);

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
                "Congratulations! Your order has been confirmed!"
        );
        closeGoogleVignetteIfPresent();

        homePage.clickDeleteAccount();

        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );

        accountStatusPage.clickContinue();


    }

    @Test(description = "Test Case 15: Place Order: Register before Checkout")
    public void placeOrderRegisterBeforeCheckoutTest() {
        HomePage homePage = new HomePage(driver);
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
        closeGoogleVignetteIfPresent();
        homePage.clickSignupLogin();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
        String uniqueEmail = "test_" +
                UUID.randomUUID().toString().replace("-", "") +
                "@gmail.com";
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.signup("George", uniqueEmail);
        AccountInformationPage accountInformationPage = new AccountInformationPage(driver);
        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );
        accountInformationPage.enterPassword("TEvsivis@");
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();
        AccountStatusPage accountStatusPage = new AccountStatusPage(driver);
        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );
        accountStatusPage.clickContinue();
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                homePage.getLoggedInUser().contains("George")
        );
        homePage.addFirstItemToCart();
        homePage.viewCart();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart")
        );
        closeGoogleVignetteIfPresent();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickproceedToCheckOut();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );
        Assert.assertEquals(
                checkoutPage.getReviewOrderTitle(),
                "Review Your Order"
        );
        Assert.assertTrue(
                checkoutPage.getDeliveryAddress().contains("George")
        );
        Assert.assertTrue(
                checkoutPage.getBillingAddress().contains("George")
        );
        checkoutPage.enterOrderComment(
                "Please deliver the order carefully."
        );
        checkoutPage.clickPlaceOrder();
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment")
        );
        PaymentPage paymentPage = new PaymentPage(driver);
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
                "Congratulations! Your order has been confirmed!"
        );
        closeGoogleVignetteIfPresent();
        homePage.clickDeleteAccount();
        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );
        accountStatusPage.clickContinue();
    }

    @Test(description = "Test Case 16: Place Order: Login before Checkout")
    public void placeOrderLoginBeforeCheckoutTest() {

        HomePage homePage = new HomePage(driver);
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
        closeGoogleVignetteIfPresent();
        homePage.clickSignupLogin();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login")
        );
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);
        signupLoginPage.login(
                System.getenv("TEST_EMAIL"),
                System.getenv("TEST_PASSWORD")
        );
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                homePage.getLoggedInUser().contains("Georgios"),
                "User is not logged in as George"
        );
        homePage.addFirstItemToCart();
        homePage.viewCart();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/view_cart")
        );
        closeGoogleVignetteIfPresent();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickproceedToCheckOut();
        CheckoutPage checkoutPage =
                new CheckoutPage(driver);
        Assert.assertEquals(
                checkoutPage.getAddressDetailsTitle(),
                "Address Details"
        );
        Assert.assertEquals(
                checkoutPage.getReviewOrderTitle(),
                "Review Your Order"
        );
        Assert.assertTrue(
                checkoutPage.getDeliveryAddress().contains("GEORGIOS")
        );
        Assert.assertTrue(
                checkoutPage.getBillingAddress().contains("GEORGIOS")
        );
        checkoutPage.enterOrderComment(
                "Please deliver the order carefully."
        );
        checkoutPage.clickPlaceOrder();
        closeGoogleVignetteIfPresent();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/payment")
        );
        PaymentPage paymentPage =
                new PaymentPage(driver);
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
                "Congratulations! Your order has been confirmed!"
        );
        closeGoogleVignetteIfPresent();
        homePage.clickDeleteAccount();
        AccountStatusPage accountStatusPage =
                new AccountStatusPage(driver);

        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );
        accountStatusPage.clickContinue();
    }

    @Test(description = "Test Case 17: Remove Products From Cart")
    public void removeProductsFromCartTest(){
        HomePage homePage = new HomePage(driver);
        homePage.addFirstItemToCart();
        homePage.viewCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                1);
        closeGoogleVignetteIfPresent();
        cartPage.removeFirstProductsFromCart();
        Assert.assertEquals(
                cartPage.getCartProductsCount(),
                0
        );
    }

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

    @Test(description = "Test Case 23: Verify Address Details in Checkout")
    public void verifyAddressDetailsInCheckoutTest() {

        HomePage homePage = new HomePage(driver);
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
        closeGoogleVignetteIfPresent();
        homePage.clickSignupLogin();
        String uniqueEmail = "test_" +
                UUID.randomUUID().toString().replace("-", "") +
                "@gmail.com";
        SignupLoginPage signupLoginPage =
                new SignupLoginPage(driver);
        signupLoginPage.signup("George", uniqueEmail);
        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);
        Assert.assertEquals(
                accountInformationPage.getAccountInformationTitle(),
                "ENTER ACCOUNT INFORMATION"
        );
        accountInformationPage.enterPassword("TEvsivis@");
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
                homePage.getLoggedInUser().contains("George")
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

    @Test(description = "Test Case 24: Download Invoice after Purchase Order")
    public void downloadInvoiceAfterPurchaseOrderTest() {

        HomePage homePage = new HomePage(driver);

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );

        // Clear previous invoice files
        File downloadFolder = new File(
                System.getProperty("user.dir")
                        + File.separator + "downloads"
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
                        "Could not delete old invoice: " + invoice.getName()
                );
            }
        }
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
        String uniqueEmail = "test_" +
                UUID.randomUUID().toString().replace("-", "") +
                "@gmail.com";

        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.signup("George", uniqueEmail);

        AccountInformationPage accountInformationPage =
                new AccountInformationPage(driver);
        accountInformationPage.enterPassword("TEvsivis@");
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
        checkoutPage.enterOrderComment(
                "Please deliver the order carefully."
        );
        checkoutPage.clickPlaceOrder();
        closeGoogleVignetteIfPresent();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.fillPaymentDetails(
                "George",
                "4111111111111111",
                "123",
                "12",
                "2030"
        );

        paymentPage.clickPayAndConfirmOrder();
        Assert.assertEquals(
                paymentPage.getOrderSuccessMessage(),
                "Congratulations! Your order has been confirmed!"
        );
        closeGoogleVignetteIfPresent();
        paymentPage.clickDownloadInvoice();
        Assert.assertTrue(
                paymentPage.isInvoiceDownloaded(),
                "Invoice was not downloaded successfully"
        );
        System.out.println("Invoice downloaded successfully!");
        closeGoogleVignetteIfPresent();
        homePage.clickDeleteAccount();
        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );
        accountStatusPage.clickContinue();
    }

}
