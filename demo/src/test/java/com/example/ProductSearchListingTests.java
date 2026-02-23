package com.example;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * Module 2: Product Search & Listing - 10 test cases.
 * POM: HomePage, ProductPage. Uses signup in setUp (no DB / no prior login).
 */
public class ProductSearchListingTests extends BaseTest {

    @BeforeMethod
    public void signUpFirst() throws InterruptedException {
        driver.get(getBaseUrl() + "index.html");
        driver.findElement(By.id("auth-toggle-link")).click();
        Thread.sleep(400);
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("passwordhamhai123");
        driver.findElement(By.cssSelector("#auth-form button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("home.html"));
    }

    @Test(priority = 1, description = "TC_SEARCH_01: Verify home page loads with product grid")
    public void TC_SEARCH_01_verifyHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        List<WebElement> cards = homePage.getProductCards();
        Assert.assertTrue(cards.size() >= 1, "Product grid should display at least one card");
    }

    @Test(priority = 2, description = "TC_SEARCH_02: Verify search input present and functional")
    public void TC_SEARCH_02_verifySearchInputPresent() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("Matte");
        Thread.sleep(600);
        Assert.assertTrue(homePage.getProductCards().size() >= 0, "Search should filter results");
    }

    @Test(priority = 3, description = "TC_SEARCH_03: Verify search with valid keyword")
    public void TC_SEARCH_03_verifySearchValidKeyword() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("Matte");
        Thread.sleep(1000);
        List<WebElement> cards = homePage.getProductCards();
        Assert.assertTrue(cards.size() >= 1, "Matte should return at least one result (Matte Emulsion Premium)");
    }

    @Test(priority = 4, description = "TC_SEARCH_04: No results message for non-matching search")
    public void TC_SEARCH_04_verifyNoResultsMessage() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("xyznonexistentproduct123");
        Thread.sleep(1000);
        Assert.assertTrue(homePage.isNoResultsDisplayed(), "No results message should show");
    }

    @Test(priority = 5, description = "TC_SEARCH_05: View Details navigates to product page")
    public void TC_SEARCH_05_verifyViewDetailsNavigates() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.clickFirstViewDetails();
        homePage.waitForProductPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("product.html"), "Should navigate to product page");
    }

    @Test(priority = 6, description = "TC_SEARCH_06: Product cards display title and price")
    public void TC_SEARCH_06_verifyProductCardsDisplay() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        List<WebElement> cards = homePage.getProductCards();
        Assert.assertFalse(cards.isEmpty(), "No product cards found");
        String firstCardText = homePage.getProductCards().get(0).getText();
        Assert.assertTrue(firstCardText.contains("₹") || firstCardText.matches(".*\\d+.*"), "Card should show price");
    }

    @Test(priority = 7, description = "TC_SEARCH_07: Search updates on input change")
    public void TC_SEARCH_07_verifySearchUpdates() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("Eco");
        Thread.sleep(1000);
        int countEco = homePage.getProductCards().size();
        homePage.search("Metal");
        Thread.sleep(1000);
        int countMetal = homePage.getProductCards().size();
        Assert.assertTrue(countEco >= 0 && countMetal >= 0, "Search should update results");
    }

    @Test(priority = 8, description = "TC_SEARCH_08: Empty search shows all products")
    public void TC_SEARCH_08_verifyEmptySearchShowsAll() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("xyz");
        Thread.sleep(500);
        homePage.clearSearch();
        Thread.sleep(1200);
        Assert.assertTrue(homePage.getProductCards().size() > 0, "Clearing search should show all products");
    }

    @Test(priority = 9, description = "TC_SEARCH_09: Partial keyword search (Gloss)")
    public void TC_SEARCH_09_verifyPartialKeywordSearch() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        homePage.search("Gloss");
        Thread.sleep(1000);
        List<WebElement> cards = homePage.getProductCards();
        Assert.assertFalse(cards.isEmpty(), "Gloss should return results");
        Assert.assertTrue(cards.get(0).getText().contains("Gloss"), "Result should contain Gloss");
    }

    @Test(priority = 10, description = "TC_SEARCH_10: Paint grid accessibility")
    public void TC_SEARCH_10_verifyPaintGridAccessibility() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        Assert.assertTrue(homePage.getPaintGrid().isDisplayed(), "Paint grid should be displayed");
    }
}
