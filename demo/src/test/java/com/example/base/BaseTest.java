package com.example.base;

import com.example.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base test - driver setup and teardown for all test classes.
 */
public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--start-maximized");
        
        // CI/headless mode flags
        if (System.getenv("CI") != null) {
            opts.addArguments("--headless=new");
            opts.addArguments("--no-sandbox");
            opts.addArguments("--disable-dev-shm-usage");
            opts.addArguments("--disable-gpu");
            opts.addArguments("--window-size=1920,1080");
        }
        
        driver = new ChromeDriver(opts);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected String getBaseUrl() {
        return Config.BASE_URL;
    }
}
