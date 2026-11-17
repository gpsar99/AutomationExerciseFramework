
package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Disable Chrome Autofill
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Start browser
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Explicit wait
        wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        // Open Automation Exercise
        driver.get("https://automationexercise.com/");

        // Accept cookies if displayed
        try {
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".fc-cta-consent")
                    )
            ).click();

        } catch (TimeoutException e) {
            System.out.println(
                    "Consent popup was not displayed."
            );
        }
    }

    // Retry clicks when temporary overlays intercept them
    protected void safeClick(By locator) {

        WebDriverWait clickWait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        clickWait.ignoring(
                ElementClickInterceptedException.class
        ).until(d -> {

            d.findElement(locator).click();
            return true;

        });
    }

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

    private boolean findAndCloseVignette(int depth) {

        if (depth > 4) {
            return false;
        }

        By closeButton = By.cssSelector(
                "[id='dismiss-button'][aria-label='Close ad']"
        );

        List<WebElement> buttons = driver.findElements(closeButton);

        for (WebElement button : buttons) {
            try {
                if (button.isDisplayed()) {
                    button.click();
                    return true;
                }
            } catch (WebDriverException e) {
                // Element may have changed or become obstructed
            }
        }

        List<WebElement> frames =
                driver.findElements(By.tagName("iframe"));

        for (WebElement frame : frames) {

            try {
                driver.switchTo().frame(frame);

                if (findAndCloseVignette(depth + 1)) {
                    return true;
                }

            } catch (WebDriverException e) {
                // Frame may be unavailable
            } finally {
                if (depth == 0) {
                    driver.switchTo().defaultContent();
                } else {
                    driver.switchTo().parentFrame();
                }
            }
        }

        return false;
    }


    protected void successfullyChangePage(String page){
        wait.until(ExpectedConditions.urlToBe(page));

        Assert.assertEquals(
                driver.getCurrentUrl(),
                page
        );
    }




    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
