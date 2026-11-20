package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ScrollPage;

public class ScrollTests extends BaseTest {

    @Test(description = "Test Case 25: Verify Scroll Up using Arrow button")
    public void verifyScrollUpUsingArrowButtonTest() {

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
        ScrollPage scrollPage = new ScrollPage(driver);
        // Scroll down to bottom
        scrollPage.scrollToBottom();
        Assert.assertTrue(
                scrollPage.isSubscriptionVisible(),
                "Subscription section is not visible"
        );
        closeGoogleVignetteIfPresent();
        // Click Scroll Up arrow
        scrollPage.clickScrollUpButton();
        Assert.assertTrue(
                scrollPage.isPageAtTop(),
                "Page did not scroll back to the top"
        );
        Assert.assertTrue(
                scrollPage.isHomePageBannerVisible(),
                "Home page banner is not visible after scrolling up"
        );
        System.out.println("TC25: Scroll Up verified successfully!");
    }

    @Test(description = "Test Case 26: Verify Scroll Up without Arrow button")
    public void verifyScrollUpWithoutArrowButtonTest() {
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );
        ScrollPage scrollPage = new ScrollPage(driver);
        scrollPage.scrollToBottom();
        Assert.assertTrue(
                scrollPage.isSubscriptionVisible(),
                "Subscription section is not visible"
        );
        scrollPage.scrollToTop();
        Assert.assertTrue(
                scrollPage.isPageAtTop(),
                "Page did not scroll back to the top"
        );
        Assert.assertTrue(
                scrollPage.isHomePageBannerVisible(),
                "Home page banner is not visible after scrolling up"
        );
        System.out.println("TC26: Scroll Up without Arrow verified successfully!");
    }
}