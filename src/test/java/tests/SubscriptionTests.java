
package tests;

import base.BaseTest;
import components.SubscriptionComponent;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

/**
 * Test class for verifying newsletter subscription functionality.
 *
 * Covers Automation Exercise test cases:
 * TC10 - Verify Subscription in Home Page
 * TC11 - Verify Subscription in Cart Page
 */
public class SubscriptionTests extends BaseTest {

    private static final String SUBSCRIPTION_EMAIL =
            "gpsar@gpsar.com";

    private static final String SUBSCRIPTION_TITLE =
            "SUBSCRIPTION";

    private static final String SUBSCRIPTION_SUCCESS_MESSAGE =
            "You have been successfully subscribed!";

    /**
     * TC10: Verifies newsletter subscription
     * from the home page.
     *
     * Checks the subscription section heading,
     * submits an email address, and verifies
     * the successful subscription message.
     */
    @Test(description = "Test Case 10: Verify Subscription in home page")
    public void verifySubscriptionInHomePage() {

        HomePage homePage = new HomePage(driver);

        // Verify subscription functionality on the home page.
        verifySubscription(homePage.getSubscription());
    }

    /**
     * TC11: Verifies newsletter subscription
     * from the shopping cart page.
     *
     * Navigates to the cart, checks the subscription
     * section, submits an email address, and verifies
     * the successful subscription message.
     */
    @Test(description = "Test Case 11: Verify Subscription in Cart page")
    public void verifySubscriptionInCartPage() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the shopping cart page.
        homePage.clickCart();

        CartPage cartPage = new CartPage(driver);

        // Verify subscription functionality on the cart page.
        verifySubscription(cartPage.getSubscription());
    }

    /**
     * Verifies newsletter subscription using
     * the shared SubscriptionComponent.
     *
     * Checks the subscription heading, submits
     * the test email, and verifies the success message.
     *
     * @param subscription the SubscriptionComponent to verify
     */
    private void verifySubscription(
            SubscriptionComponent subscription
    ) {

        // Verify the subscription section heading.
        Assert.assertEquals(
                subscription.getSubscriptionText(),
                SUBSCRIPTION_TITLE,
                "Subscription section heading is incorrect"
        );

        // Enter the subscription email.
        subscription.fillEmailText(SUBSCRIPTION_EMAIL);

        // Submit the subscription form.
        subscription.clickArrow();

        // Verify the successful subscription message.
        Assert.assertEquals(
                subscription.getSuccessfullySubscribeText(),
                SUBSCRIPTION_SUCCESS_MESSAGE,
                "Subscription success message is incorrect"
        );
    }
}
