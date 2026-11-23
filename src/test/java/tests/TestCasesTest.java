
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestCasesPage;

/**
 * Test class for verifying navigation to the Test Cases page.
 *
 * Covers Test Case 7 of the Automation Exercise website.
 */
public class TestCasesTest extends BaseTest {

    private static final String TEST_CASES_URL =
            "https://automationexercise.com/test_cases";

    private static final String TEST_CASES_TITLE =
            "TEST CASES";

    /**
     * TC07: Verifies that the Test Cases page opens successfully.
     *
     * Navigates from the home page to the Test Cases page,
     * handles a possible Google Vignette overlay,
     * and verifies the URL and page heading.
     */
    @Test(description = "Test Case 7: Verify Test Cases Page")
    public void verifyTestCasePage() {

        HomePage homePage = new HomePage(driver);

        // Navigate to the Test Cases page.
        homePage.clickTestCases();

        // Handle a possible Google Vignette overlay.
        closeGoogleVignetteIfPresent();

        // Wait for and verify navigation to the Test Cases page.
        successfullyChangePage(TEST_CASES_URL);

        TestCasesPage testCasesPage = new TestCasesPage(driver);

        // Verify the Test Cases page heading.
        Assert.assertEquals(
                testCasesPage.getTestCasesTitle(),
                TEST_CASES_TITLE,
                "Test Cases page heading is incorrect"
        );
    }
}
