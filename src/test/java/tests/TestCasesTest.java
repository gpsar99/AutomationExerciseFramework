package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCasesTest extends BaseTest {

    @Test
    public void verifyTestCasePage(){

        safeClick(By.xpath("//a[@href='/test_cases']"));

        closeGoogleVignetteIfPresent();
        wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/test_cases"));

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/test_cases"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2/b[normalize-space()='Test Cases']"))).getText(),
                "TEST CASES");

    }

}
