
package base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all TestNG test classes.
 *
 * Manages WebDriver initialization, browser configuration,
 * cookie consent, Google Vignette overlays, navigation
 * verification, and browser cleanup.
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final String BASE_URL =
            "https://automationexercise.com/";

    private static final int DEFAULT_WAIT_SECONDS = 10;
    private static final int CLICK_WAIT_SECONDS = 15;
    private static final int MAX_FRAME_DEPTH = 4;

    private static final By CONSENT_BUTTON =
            By.cssSelector(".fc-cta-consent");

    private static final By VIGNETTE_CLOSE_BUTTON =
            By.cssSelector(
                    "[id='dismiss-button'][aria-label='Close ad']"
            );

    private static final By IFRAMES =
            By.tagName("iframe");

    /**
     * Initializes Chrome before each test method.
     *
     * Configures browser preferences, prepares the download
     * directory, opens the application, and handles
     * the cookie consent popup when present.
     */
    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Configure Chrome preferences.
        Map<String, Object> prefs = new HashMap<>();

        // Disable Chrome Autofill.
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);

        // Prepare the invoice download directory.
        File downloadFolder = new File(
                System.getProperty("user.dir"),
                "downloads"
        );

        if (!downloadFolder.exists()
                && !downloadFolder.mkdirs()) {
            throw new IllegalStateException(
                    "Unable to create download directory: "
                            + downloadFolder.getAbsolutePath()
            );
        }

        // Configure automatic downloads.
        prefs.put(
                "download.default_directory",
                downloadFolder.getAbsolutePath()
        );
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);

        options.setExperimentalOption("prefs", prefs);

        // Start Chrome.
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize the shared explicit wait.
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_WAIT_SECONDS)
        );

        // Open the application.
        driver.get(BASE_URL);

        // Accept cookie consent when displayed.
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            CONSENT_BUTTON
                    )
            ).click();

        } catch (TimeoutException e) {
            System.out.println(
                    "Consent popup was not displayed."
            );
        }
    }

    /**
     * Clicks an element while retrying temporary click failures.
     *
     * Waits until the element becomes clickable and retries
     * intercepted or stale element interactions.
     *
     * @param locator the locator of the element to click
     */
    protected void safeClick(By locator) {

        WebDriverWait clickWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(CLICK_WAIT_SECONDS)
        );

        clickWait.ignoring(
                ElementClickInterceptedException.class
        );

        clickWait.ignoring(
                StaleElementReferenceException.class
        );

        clickWait.until(d -> {
            WebElement element = d.findElement(locator);

            if (!element.isDisplayed() || !element.isEnabled()) {
                return false;
            }

            element.click();
            return true;
        });
    }

    /**
     * Attempts to close a Google Vignette advertisement
     * when the current URL contains the vignette fragment.
     *
     * Searches the main document and nested iframes,
     * then restores the default browsing context.
     */
    protected void closeGoogleVignetteIfPresent() {

        driver.switchTo().defaultContent();

        if (!driver.getCurrentUrl().contains("#google_vignette")) {
            return;
        }

        try {
            boolean closed = findAndCloseVignette(0);

            if (closed) {
                System.out.println("Google Vignette closed.");
            } else {
                System.out.println(
                        "Google Vignette Close not found in frames."
                );
            }

        } finally {
            driver.switchTo().defaultContent();
        }
    }

    /**
     * Recursively searches the current document and its
     * child iframes for the Google Vignette close button.
     *
     * @param depth the current iframe nesting depth
     * @return true if the close button was clicked
     */
    private boolean findAndCloseVignette(int depth) {

        if (depth > MAX_FRAME_DEPTH) {
            return false;
        }

        List<WebElement> buttons =
                driver.findElements(VIGNETTE_CLOSE_BUTTON);

        for (WebElement button : buttons) {
            try {
                if (button.isDisplayed()) {
                    button.click();
                    return true;
                }
            } catch (WebDriverException e) {
                // The element may have changed or become obstructed.
            }
        }

        List<WebElement> frames =
                driver.findElements(IFRAMES);

        for (WebElement frame : frames) {
            boolean switchedToFrame = false;

            try {
                driver.switchTo().frame(frame);
                switchedToFrame = true;

                if (findAndCloseVignette(depth + 1)) {
                    return true;
                }

            } catch (WebDriverException e) {
                // The iframe may no longer be available.
            } finally {
                if (switchedToFrame) {
                    driver.switchTo().parentFrame();
                }
            }
        }

        return false;
    }

    /**
     * Waits for navigation to the expected URL
     * and verifies that the current URL matches.
     *
     * @param page the expected destination URL
     */
    protected void successfullyChangePage(String page) {

        wait.until(
                ExpectedConditions.urlToBe(page)
        );

        Assert.assertEquals(
                driver.getCurrentUrl(),
                page
        );
    }

    /**
     * Closes the browser after each test method.
     *
     * Always executes, even if the test fails.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
