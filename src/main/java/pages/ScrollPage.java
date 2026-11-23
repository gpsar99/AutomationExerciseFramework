
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object responsible for scrolling interactions
 * on the Automation Exercise home page.
 *
 * Provides methods for scrolling to the top and bottom,
 * clicking the Scroll Up button, and verifying
 * the page position and visible content.
 */
public class ScrollPage extends BasePage {

    // Subscription heading displayed near the page footer.
    private final By subscriptionTitle =
            By.xpath("//h2[normalize-space()='Subscription']");

    // Scroll Up arrow button.
    private final By scrollUpButton =
            By.id("scrollUp");

    // Main banner heading displayed near the top of the home page.
    private final By homePageBannerText =
            By.xpath(
                    "//h2[contains(normalize-space(), " +
                            "'Full-Fledged practice website for Automation Engineers')]"
            );

    // Maximum allowed vertical offset when checking the page top.
    private static final long TOP_SCROLL_TOLERANCE = 5;

    /**
     * Initializes the Scroll Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public ScrollPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks whether the Subscription heading is visible.
     *
     * @return true if the Subscription heading is displayed
     */
    public boolean isSubscriptionVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subscriptionTitle
                )
        ).isDisplayed();
    }

    /**
     * Clicks the Scroll Up arrow button.
     *
     * Uses the shared safeClick method to scroll the button
     * into view and handle intercepted or stale clicks.
     */
    public void clickScrollUpButton() {
        safeClick(scrollUpButton);
    }

    /**
     * Scrolls the browser window to the bottom of the page.
     *
     * Uses JavaScript to set the vertical scroll position
     * to the document's full scroll height.
     */
    public void scrollToBottom() {
        executeJavaScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }

    /**
     * Checks whether the main home page banner is visible.
     *
     * @return true if the banner heading is displayed
     */
    public boolean isHomePageBannerVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        homePageBannerText
                )
        ).isDisplayed();
    }

    /**
     * Waits until the browser window reaches the top of the page.
     *
     * Allows a small tolerance of five pixels to account
     * for minor scrolling differences.
     *
     * @return true when the vertical scroll position is
     *         within the allowed tolerance
     */
    public boolean isPageAtTop() {
        return wait.until(d -> {

            Number scrollPosition = (Number)
                    ((JavascriptExecutor) d).executeScript(
                            "return window.scrollY;"
                    );

            return scrollPosition != null
                    && scrollPosition.longValue()
                    <= TOP_SCROLL_TOLERANCE;
        });
    }

    /**
     * Scrolls the browser window directly to the top.
     *
     * This method is used to verify scrolling without
     * clicking the Scroll Up arrow button.
     */
    public void scrollToTop() {
        executeJavaScript(
                "window.scrollTo(0, 0);"
        );
    }

    /**
     * Executes a JavaScript command in the current browser window.
     *
     * @param script the JavaScript code to execute
     */
    private void executeJavaScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }
}
