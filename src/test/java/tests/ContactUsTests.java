package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class ContactUsTests extends BaseTest {

    @Test
    public void contactUsFormTest(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/contact_us']"))).click();
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Get In Touch']"))).getText(), "GET IN TOUCH");

        driver.findElement(By.xpath("//input[@data-qa='name']")).sendKeys("George");
        driver.findElement(By.xpath("//input[@data-qa='email']")).sendKeys("George@geroge.com");
        driver.findElement(By.xpath("//input[@data-qa='subject']")).sendKeys("Subject");
        driver.findElement(By.xpath("//textarea[@data-qa='message']")).sendKeys("An Issue For George Georgopoulos");


        Path filePath = Path.of(
                "src", "test", "resources", "test-upload.txt"
        ).toAbsolutePath();

        System.out.println("File path: " + filePath);
        System.out.println("File exists: " + Files.isRegularFile(filePath));

        Assert.assertTrue(
                Files.isRegularFile(filePath),
                "Test file does not exist: " + filePath
        );

        driver.findElement(By.name("upload_file"))
                .sendKeys(filePath.toString());

        String uploadedFile = driver.findElement(By.name("upload_file"))
                .getAttribute("value");

        System.out.println("Uploaded file value: " + uploadedFile);

        Assert.assertTrue(
                uploadedFile.endsWith("test-upload.txt"),
                "File was not selected correctly"
        );

        safeClick(By.cssSelector("[data-qa='submit-button']"));


        wait.until(ExpectedConditions.alertIsPresent()).accept();

        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.status.alert.alert-success"))).getText(), "Success! Your details have been submitted successfully.");

        safeClick(By.xpath("//span[normalize-space()='Home']"));

        closeGoogleVignetteIfPresent();

        wait.until(ExpectedConditions.urlToBe(
                "https://automationexercise.com/"
        ));

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://automationexercise.com/"
        );


    }
}
