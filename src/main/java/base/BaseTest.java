package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LoggerConfig;

import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected Logger logger = LoggerConfig.getLogger(BaseTest.class);  // ✅ keep only this line

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("🧭 Browser launched and maximized.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("🧹 Browser closed successfully.");
        }
    }
}
