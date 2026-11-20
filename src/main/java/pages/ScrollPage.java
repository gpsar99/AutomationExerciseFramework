package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ScrollPage extends BasePage {

    private final By subscriptionTitle =
            By.xpath("//h2[normalize-space()='Subscription']");

    private final By scrollUpButton =
            By.id("scrollUp");
    private final By homePageBannerText =
            By.xpath(
                    "//h2[contains(normalize-space(), " +
                            "'Full-Fledged practice website for Automation Engineers')]"
            );

    public ScrollPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSubscriptionVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        subscriptionTitle
                )
        ).isDisplayed();
    }

    public void clickScrollUpButton() {
        safeClick(scrollUpButton);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }
    public boolean isHomePageBannerVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        homePageBannerText
                )
        ).isDisplayed();
    }
    public boolean isPageAtTop() {

        return wait.until(driver -> {

            Long scrollPosition = (Long)
                    ((JavascriptExecutor) driver)
                            .executeScript("return window.scrollY;");

            return scrollPosition <= 5;
        });
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, 0);"
        );
    }


}