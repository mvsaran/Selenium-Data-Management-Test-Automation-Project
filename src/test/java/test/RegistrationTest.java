package test;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import pojo.UserData;
import utils.DataGenerator;

import java.time.Duration;

public class RegistrationTest extends BaseTest {

    @Test
    public void testSmartDataRegistration() {
        System.out.println("===== Test Started: Smart Test Data Registration =====");

        UserData user = DataGenerator.generateUserData();
        
        // Print all generated test data
        System.out.println("\n📋 Generated Test Data:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("First Name    : " + user.getFirstName());
        System.out.println("Last Name     : " + user.getLastName());
        System.out.println("Address       : " + user.getAddress());
        System.out.println("City          : " + user.getCity());
        System.out.println("State         : " + user.getState());
        System.out.println("Zip Code      : " + user.getZipCode());
        System.out.println("Phone         : " + user.getPhone());
        System.out.println("SSN           : " + user.getSsn());
        System.out.println("Username      : " + user.getUsername());
        System.out.println("Password      : " + user.getPassword());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        System.out.println("🌐 Navigated to ParaBank registration page.");

        RegistrationPage regPage = new RegistrationPage(driver);
        
        System.out.println("\n✍️ Filling registration form...");
        regPage.fillRegistrationForm(user);
        System.out.println("✅ Registration form filled successfully with smart data.");

        System.out.println("\n🖱️ Clicking Register button...");
        regPage.clickRegister();

        // Wait for page to load and check for success or error message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Wait for either success or error message to appear
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("overview.htm"),
                ExpectedConditions.urlContains("register.htm")
            ));
            
            // Add a small delay to ensure page content is fully loaded
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.out.println("⚠️ Timeout waiting for page to load: " + e.getMessage());
        }

        String pageSource = driver.getPageSource();
        boolean isSuccess = pageSource.contains("Your account was created successfully") || 
                           driver.getCurrentUrl().contains("overview.htm");
        
        System.out.println("\n📊 Registration Result:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        if (isSuccess) {
            System.out.println("✅ Registration completed successfully!");
            System.out.println("✅ User '" + user.getUsername() + "' has been registered.");
        } else {
            System.out.println("❌ Registration failed!");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title: " + driver.getTitle());
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Assert.assertTrue(isSuccess, "Registration should succeed with valid data.");
        System.out.println("===== Test Completed: Smart Test Data Registration =====\n");
    }
}