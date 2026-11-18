package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void safeClick(By locator) {

        WebDriverWait clickWait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        clickWait.ignoring(
                ElementClickInterceptedException.class
        ).until(d -> {

            WebElement element = d.findElement(locator);

            ((JavascriptExecutor) d).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );

            element.click();
            return true;
        });
    }
}