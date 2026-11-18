
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
import pages.AccountInformationPage;
import pages.AccountStatusPage;
import pages.HomePage;
import pages.SignupLoginPage;

import java.time.Duration;

public class RegisterTests extends BaseTest {

    @Test(description = "Test Case 1: Register User")
    public void registerUserTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        Assert.assertEquals(
                signupLoginPage.getSignupTitle(),
                "New User Signup!"
        );
        String username = "GeorgeP";
        String email = "testuser" + System.currentTimeMillis()
                + "@example.com";

        signupLoginPage.signup( username,email);
        AccountInformationPage accountInformationPage = new AccountInformationPage(driver);
        String signUpTitle = accountInformationPage.getAccountInformationTitle();

        Assert.assertEquals(
                signUpTitle,
                "ENTER ACCOUNT INFORMATION"
        );

        accountInformationPage.enterPassword("TestPassword123!");

        accountInformationPage.selectDateOfBirth(
                "15",
                "May",
                "1995"
        );
        accountInformationPage.selectNewsletterAndOffers();
        accountInformationPage.fillAddressInformation();
        accountInformationPage.clickCreateAccount();
        AccountStatusPage accountStatusPage = new AccountStatusPage(driver);
        Assert.assertEquals(
                accountStatusPage.getAccountCreatedMessage(),
                "ACCOUNT CREATED!"
        );
        accountStatusPage.clickContinue();
        Assert.assertEquals(
                homePage.getLoggedInUser(),
                "Logged in as " + username
        );
        homePage.clickDeleteAccount();
        closeGoogleVignetteIfPresent();
        wait.until(
                ExpectedConditions.urlContains("/delete_account")
        );

        Assert.assertEquals(
                accountStatusPage.getAccountDeletedMessage(),
                "ACCOUNT DELETED!"
        );
        accountStatusPage.clickContinue();
    }

    @Test(description = "Test Case 5: Register User with existing email")
    public void registerWithExistingEmailTest(){
        //Click Login SignUp
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        String email = System.getenv("TEST_EMAIL");
        Assert.assertNotNull(email, "TEST_EMAIL is not configured");
        SignupLoginPage signupLoginPage = new SignupLoginPage(driver);
        Assert.assertEquals(
                signupLoginPage.getSignupTitle(),
                "New User Signup!"
        );
        signupLoginPage.signup("George Georgoglou", email);
        Assert.assertEquals(
                signupLoginPage.getExistingEmailError(),
                "Email Address already exist!"
        );
    }
}
