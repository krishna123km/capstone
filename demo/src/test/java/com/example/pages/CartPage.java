package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object for Cart (cart.html).
 */
public class CartPage extends BasePage {

    private static final By CART_CONTENT = By.id("cart-content");
    private static final By CART_TOTAL_SECTION = By.id("cart-total-section");
    private static final By GRAND_TOTAL = By.id("grand-total");
    private static final By PROCEED_CHECKOUT = By.cssSelector("a[href='checkout.html']");
    private static final By CART_TABLE_ROWS = By.cssSelector(".cart-table tbody tr");
    private static final By MINUS_BUTTONS = By.cssSelector("[data-action='minus']");
    private static final By PLUS_BUTTONS = By.cssSelector("[data-action='plus']");
    private static final By REMOVE_BUTTONS = By.cssSelector("[data-action='remove']");
    private static final By EMPTY_CART_MESSAGE = By.cssSelector(".empty-cart");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL + "cart.html");
    }

    public void waitForCartLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CART_CONTENT));
    }

    public boolean isTotalSectionDisplayed() {
        try {
            return driver.findElement(CART_TOTAL_SECTION).isDisplayed()
                    && !driver.findElement(CART_TOTAL_SECTION).getAttribute("style").contains("display: none");
        } catch (Exception e) {
            return false;
        }
    }

    public String getGrandTotalText() {
        return getText(GRAND_TOTAL);
    }

    public void clickProceedToCheckout() {
        click(PROCEED_CHECKOUT);
    }

    public int getCartRowCount() {
        return driver.findElements(CART_TABLE_ROWS).size();
    }

    public void clickPlusForRow(int index) {
        List<WebElement> rows = driver.findElements(CART_TABLE_ROWS);
        if (index < rows.size()) {
            rows.get(index).findElement(By.cssSelector("[data-action='plus']")).click();
        }
    }

    public void clickMinusForRow(int index) {
        List<WebElement> rows = driver.findElements(CART_TABLE_ROWS);
        if (index < rows.size()) {
            rows.get(index).findElement(By.cssSelector("[data-action='minus']")).click();
        }
    }

    public void clickRemoveForRow(int index) {
        List<WebElement> rows = driver.findElements(CART_TABLE_ROWS);
        if (index < rows.size()) {
            rows.get(index).findElement(By.cssSelector("[data-action='remove']")).click();
        }
    }

    public boolean isEmptyCartMessageDisplayed() {
        try {
            List<WebElement> empty = driver.findElements(EMPTY_CART_MESSAGE);
            return !empty.isEmpty() && empty.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
