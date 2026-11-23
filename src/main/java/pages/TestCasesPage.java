
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object representing the Test Cases page.
 *
 * Provides methods for verifying that the
 * Test Cases page has loaded successfully.
 */
public class TestCasesPage extends BasePage {

    // Main heading of the Test Cases page.
    private final By testCasesTitle =
            By.xpath("//h2/b[normalize-space()='Test Cases']");

    /**
     * Initializes the Test Cases Page Object.
     *
     * @param driver the WebDriver instance used by the test
     */
    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits for the Test Cases heading to become visible
     * and returns its text.
     *
     * @return the Test Cases page heading
     */
    public String getTestCasesTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        testCasesTitle
                )
        ).getText();
    }
}
