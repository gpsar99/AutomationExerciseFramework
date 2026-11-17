package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import java.time.Duration;

public class LoginTests extends BaseTest{

    @Test
    public void invalidLoginTest (){
            //Click Login SignUp
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']"))).click();

            driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys("invalid@example.com");
            driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("wrongPassword123");
            driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();
            String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Your email or password is incorrect!')]"))).getText();
            Assert.assertEquals("Your email or password is incorrect!", errorMessage);
    }

    @Test
    public void loginWithValidCredentialsTest (){
        //Click Login SignUp
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']"))).click();
        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");
        Assert.assertNotNull(password, "TEST_PASSWORD is not configured");

        String loginTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='Login to your account']")
                )
        ).getText();

        Assert.assertEquals(loginTitle, "Login to your account");

        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        String loginAsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[.//i[contains(@class,'fa-user')]]"))).getText();
        Assert.assertEquals(loginAsUsername,"Logged in as Georgios Psaroudis");
    }


    @Test
    public void logoutUserTest(){
        //Click Login SignUp
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']"))).click();
        //*[@id="form"]/div/div/div[1]/div/h2
        String email = System.getenv("TEST_EMAIL");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");

        String password = System.getenv("TEST_PASSWORD");
        Assert.assertNotNull(password, "TEST_PASSWORD is not configured");

        String loginTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[normalize-space()='Login to your account']")
                )
        ).getText();

        Assert.assertEquals(loginTitle, "Login to your account");

        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        String loginAsUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[.//i[contains(@class,'fa-user')]]"))).getText();
        Assert.assertEquals(loginAsUsername,"Logged in as Georgios Psaroudis");

        // Click Logout
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/logout']"))).click();

    // Verify redirection to Login page
        wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/login"));

        Assert.assertEquals(                driver.getCurrentUrl(), "https://automationexercise.com/login");
    // Verify Login form is displayed again
        String loginTitleAfterLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Login to your account']"))).getText();

        Assert.assertEquals(loginTitleAfterLogout, "Login to your account");

    }


}
