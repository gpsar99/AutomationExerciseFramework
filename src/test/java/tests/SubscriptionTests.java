package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

public class SubscriptionTests extends BaseTest {

    @Test(description = "Test Case 10: Verify Subscription in home page")
    public void verifySubscriptionInHomePage(){
        HomePage homePage = new HomePage(driver);
        Assert.assertEquals(homePage.getSubscription().getSubscriptionText(), "SUBSCRIPTION");
        homePage.getSubscription().fillEmailText("gpsar@gpsar.com");
        homePage.getSubscription().clickArrow();
        Assert.assertEquals(homePage.getSubscription().getSuccessfullySubscribeText(),"You have been successfully subscribed!");
    }

    @Test(description = "Test Case 11: Verify Subscription in Cart page")
    public void verifySubscriptionInCartPage(){
        HomePage homePage = new HomePage(driver);
        homePage.clickCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getSubscription().getSubscriptionText(), "SUBSCRIPTION");
        cartPage.getSubscription().fillEmailText("gpsar@gpsar.com");
        cartPage.getSubscription().clickArrow();
        Assert.assertEquals(cartPage.getSubscription().getSuccessfullySubscribeText(),"You have been successfully subscribed!");
    }
}
