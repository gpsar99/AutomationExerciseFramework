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
import pages.HomePage;
import pages.SignupLoginPage;
import java.time.Duration;

public class LoginTests extends BaseTest{


    @Test(description = "Test Case 2: Login User with correct email and password")
    public void loginWithValidCredentialsTest (){
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");
        Assert.assertNotNull(password, "TEST_PASSWORD is not configured");
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                "Login to your account"
        );
        signupLoginPage.login(email, password);
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                "Logged in as Georgios Psaroudis"
        );
    }

    @Test(description = "Test Case 3: Login User with incorrect email and password")
    public void invalidLoginTest (){
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        signupLoginPage.login("invalid@example.com", "wrongPassword123");
        Assert.assertEquals(
                signupLoginPage.getInvalidLoginError(),
                "Your email or password is incorrect!"
        );
    }

    @Test(description = "Test Case 4: Logout User")
    public void logoutUserTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        String email = System.getenv("TEST_EMAIL");
        String password = System.getenv("TEST_PASSWORD");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");
        Assert.assertNotNull(password, "TEST_PASSWORD is not configured");
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                "Login to your account"
        );
        signupLoginPage.login(email, password);
        closeGoogleVignetteIfPresent();
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                "Logged in as Georgios Psaroudis"
        );
        homePage.clickLogout();
        wait.until(ExpectedConditions.urlContains("/login"));
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/login"
        );
        Assert.assertEquals(
                signupLoginPage.getLoginTitle(),
                "Login to your account"
        );
    }

}
