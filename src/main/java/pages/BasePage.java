
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Objects in the automation framework.
 *
 * Provides shared WebDriver access, explicit waits,
 * and reusable Selenium interaction methods.
 */
public class BasePage {

    // Shared WebDriver instance for all Page Objects.
    protected final WebDriver driver;

    // Default explicit wait used by child Page Objects.
    protected final WebDriverWait wait;

    /**
     * Initializes the Page Object with the current WebDriver.
     *
     * @param driver the WebDriver instance used by the test
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    /**
     * Clicks an element with automatic scrolling and retry handling.
     *
     * Scrolls the target element into the center of the viewport
     * before attempting a standard Selenium click.
     *
     * Retries when the click is intercepted or the element
     * becomes stale.
     *
     * @param locator the locator of the element to click
     */
    protected void safeClick(By locator) {

        // Use a dedicated timeout for click operations.
        WebDriverWait clickWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        // Retry when an element is intercepted or becomes stale.
        clickWait.ignoring(ElementClickInterceptedException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {

                    // Locate the element on each attempt.
                    WebElement element = d.findElement(locator);

                    // Scroll the element into the center of the viewport.
                    ((JavascriptExecutor) d).executeScript(
                            "arguments[0].scrollIntoView({block: 'center'});",
                            element
                    );

                    // Perform a standard Selenium click.
                    element.click();

                    return true;
                });
    }
}
