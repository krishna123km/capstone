package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object for Home / Product listing (home.html).
 */
public class HomePage extends BasePage {

    private static final By SEARCH_INPUT = By.id("search");
    private static final By PAINT_GRID = By.id("paint-grid");
    private static final By PRODUCT_CARDS = By.cssSelector("#paint-grid .card");
    private static final By VIEW_DETAILS_LINKS = By.cssSelector("#paint-grid .card a[href*='product.html']");
    private static final By NO_RESULTS = By.id("no-results");
    private static final By NAV_LOGOUT = By.id("nav-logout");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL + "home.html");
    }

    public void waitForGridLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAINT_GRID));
    }

    public void search(String keyword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        driver.findElement(SEARCH_INPUT).clear();
        driver.findElement(SEARCH_INPUT).sendKeys(keyword);
    }

    public void clearSearch() {
        WebElement searchEl = driver.findElement(SEARCH_INPUT);
        searchEl.clear();
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "var el = arguments[0]; el.value = ''; el.dispatchEvent(new Event('input', { bubbles: true })); el.dispatchEvent(new Event('change', { bubbles: true }));",
            searchEl
        );
    }

    public List<WebElement> getProductCards() {
        return driver.findElements(PRODUCT_CARDS);
    }

    public boolean isNoResultsDisplayed() {
        try {
            WebElement el = driver.findElement(NO_RESULTS);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstViewDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(VIEW_DETAILS_LINKS));
        driver.findElements(VIEW_DETAILS_LINKS).get(0).click();
    }

    public void waitForProductPage() {
        wait.until(ExpectedConditions.urlContains("product.html"));
    }

    public WebElement getPaintGrid() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAINT_GRID));
    }
}
