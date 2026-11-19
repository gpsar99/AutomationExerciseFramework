package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestCasesPage extends BasePage {

    private final By testCasesTitle =
            By.xpath("//h2/b[normalize-space()='Test Cases']");

    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    public String getTestCasesTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(testCasesTitle)
        ).getText();
    }
}