
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ScrollPage;

/**
 * Test class for verifying page scrolling functionality.
 *
 * Covers Automation Exercise test cases:
 * TC25 - Verify Scroll Up using Arrow button
 * TC26 - Verify Scroll Up without Arrow button
 */
public class ScrollTests extends BaseTest {

    private static final String HOME_URL =
            "https://automationexercise.com/";

    /**
     * TC25: Verifies that the Scroll Up arrow button
     * returns the page to the top.
     *
     * Checks the subscription section at the bottom
     * and the homepage banner after scrolling up.
     */
    @Test(description = "Test Case 25: Verify Scroll Up using Arrow button")
    public void verifyScrollUpUsingArrowButtonTest() {

        // Verify that the home page is loaded.
        verifyHomePageUrl();

        ScrollPage scrollPage = new ScrollPage(driver);

        // Scroll to the bottom of the page.
        scrollPage.scrollToBottom();

        // Verify that the subscription section is visible.
        Assert.assertTrue(
                scrollPage.isSubscriptionVisible(),
                "Subscription section is not visible"
        );

        // Handle a possible Google Vignette overlay.
        closeGoogleVignetteIfPresent();

        // Scroll to the top using the arrow button.
        scrollPage.clickScrollUpButton();

        // Verify that the page returned to the top.
        verifyPageAtTop(scrollPage);

        System.out.println(
                "TC25: Scroll Up verified successfully!"
        );
    }

    /**
     * TC26: Verifies that the page can be scrolled
     * back to the top without using the arrow button.
     *
     * Checks the subscription section at the bottom
     * and the homepage banner after scrolling up.
     */
    @Test(description = "Test Case 26: Verify Scroll Up without Arrow button")
    public void verifyScrollUpWithoutArrowButtonTest() {

        // Verify that the home page is loaded.
        verifyHomePageUrl();

        ScrollPage scrollPage = new ScrollPage(driver);

        // Scroll to the bottom of the page.
        scrollPage.scrollToBottom();

        // Verify that the subscription section is visible.
        Assert.assertTrue(
                scrollPage.isSubscriptionVisible(),
                "Subscription section is not visible"
        );

        // Scroll to the top without using the arrow button.
        scrollPage.scrollToTop();

        // Verify that the page returned to the top.
        verifyPageAtTop(scrollPage);

        System.out.println(
                "TC26: Scroll Up without Arrow verified successfully!"
        );
    }

    /**
     * Verifies that the browser is currently
     * displaying the Automation Exercise home page.
     */
    private void verifyHomePageUrl() {

        Assert.assertEquals(
                driver.getCurrentUrl(),
                HOME_URL,
                "Home page URL is incorrect"
        );
    }

    /**
     * Verifies that the page has returned to the top
     * and the homepage banner is visible.
     *
     * @param scrollPage the Scroll Page Object
     */
    private void verifyPageAtTop(ScrollPage scrollPage) {

        Assert.assertTrue(
                scrollPage.isPageAtTop(),
                "Page did not scroll back to the top"
        );

        Assert.assertTrue(
                scrollPage.isHomePageBannerVisible(),
                "Home page banner is not visible after scrolling up"
        );
    }
}
