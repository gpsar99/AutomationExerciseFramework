
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterTests extends BaseTest {

    @Test
    public void registerUserTest() {

        // Step 1: Navigate to Signup / Login
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='/login']")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[normalize-space()='New User Signup!']")
        ));

        // Step 2: Enter name and unique email
        String username = "GeorgeP";
        String email = "testuser" + System.currentTimeMillis()
                + "@example.com";

        driver.findElement(By.cssSelector("[data-qa='signup-name']"))
                .sendKeys(username);

        driver.findElement(By.cssSelector("[data-qa='signup-email']"))
                .sendKeys(email);

        driver.findElement(By.cssSelector("[data-qa='signup-button']"))
                .click();

        // Step 3: Verify account information page
        String signUpTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//h2/b[normalize-space()='Enter Account Information']"
                        )
                )
        ).getText();

        Assert.assertEquals(
                signUpTitle,
                "ENTER ACCOUNT INFORMATION"
        );

        // Step 4: Fill account details
        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password"))
                .sendKeys("TestPassword123!");

        new Select(driver.findElement(By.id("days")))
                .selectByVisibleText("15");

        new Select(driver.findElement(By.id("months")))
                .selectByVisibleText("May");

        new Select(driver.findElement(By.id("years")))
                .selectByVisibleText("1995");

        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        // Step 5: Fill address information
        driver.findElement(By.id("first_name"))
                .sendKeys("George");

        driver.findElement(By.id("last_name"))
                .sendKeys("Papadopoulos");

        driver.findElement(By.id("company"))
                .sendKeys("QA Automation");

        driver.findElement(By.id("address1"))
                .sendKeys("123 Test Street");

        driver.findElement(By.id("address2"))
                .sendKeys("Apartment 4");

        new Select(driver.findElement(By.id("country")))
                .selectByVisibleText("United States");

        driver.findElement(By.id("state"))
                .sendKeys("California");

        driver.findElement(By.id("city"))
                .sendKeys("Los Angeles");

        driver.findElement(By.id("zipcode"))
                .sendKeys("90001");

        // Step 6: Submit registration form using Enter
        WebElement mobileNumber = driver.findElement(
                By.id("mobile_number")
        );

        mobileNumber.sendKeys("5551234567");
        mobileNumber.sendKeys(Keys.ENTER);

        // Step 7: Verify account creation
        String createdMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-qa='account-created']")
                )
        ).getText();

        Assert.assertEquals(
                createdMessage,
                "ACCOUNT CREATED!"
        );

        // Step 8: Continue to home page
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[data-qa='continue-button']")
        )).click();

        // Step 9: Verify logged-in user
        String loggedInText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//a[contains(normalize-space(.),'Logged in as')]"
                        )
                )
        ).getText();

        Assert.assertEquals(
                loggedInText.trim(),
                "Logged in as " + username
        );

        // Step 10: Delete account using Enter
        WebElement deleteAccountLink = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a[href='/delete_account']")
                )
        );

        deleteAccountLink.sendKeys(Keys.ENTER);

        // Step 11: Handle Google Vignette if displayed
        if (driver.getCurrentUrl().contains("#google_vignette")) {

            boolean adClosed = false;

            for (WebElement iframe : driver.findElements(
                    By.cssSelector("iframe[id^='aswift_']")
            )) {
                try {
                    driver.switchTo().frame(iframe);

                    WebDriverWait adWait = new WebDriverWait(
                            driver,
                            Duration.ofSeconds(2)
                    );

                    WebElement closeButton = adWait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.id("dismiss-button")
                            )
                    );

                    closeButton.click();
                    adClosed = true;

                    System.out.println(
                            "Google Vignette closed successfully."
                    );

                    break;

                } catch (TimeoutException e) {
                    // Close button not found in this iframe
                } finally {
                    driver.switchTo().defaultContent();
                }
            }

            if (!adClosed) {
                System.out.println(
                        "Google Vignette close button not found."
                );
            }
        }

        // Step 12: Verify navigation to deletion page
        wait.until(
                ExpectedConditions.urlContains("/delete_account")
        );

        // Step 13: Verify account deletion
        String deletedMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-qa='account-deleted']")
                )
        ).getText();

        Assert.assertEquals(
                deletedMessage,
                "ACCOUNT DELETED!"
        );

        // Step 14: Continue after deletion
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-qa='continue-button']")
        )).click();
    }
    @Test
    public void registerWithExistingEmailTest(){
        //Click Login SignUp
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']"))).click();
        String email = System.getenv("TEST_EMAIL");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");

        String signupTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='New User Signup!']")
                )
        ).getText();

        Assert.assertEquals(signupTitle, "New User Signup!");

        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("George Georgoglou");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        Assert.assertEquals(wait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[normalize-space()='Email Address already exist!']")))).getText(),"Email Address already exist!");


    }
}
