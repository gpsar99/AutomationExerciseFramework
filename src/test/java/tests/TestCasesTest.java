package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TestCasesPage;

public class TestCasesTest extends BaseTest {

    @Test(description = "Test Case 7: Verify Test Cases Page")
    public void verifyTestCasePage() {
        HomePage homePage = new HomePage(driver);
        homePage.clickTestCases();
        closeGoogleVignetteIfPresent();
        wait.until(
                ExpectedConditions.urlToBe(
                        "https://automationexercise.com/test_cases"
                )
        );
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/test_cases"
        );
        TestCasesPage testCasesPage = new TestCasesPage(driver);
        Assert.assertEquals(
                testCasesPage.getTestCasesTitle(),
                "TEST CASES"
        );
    }
}