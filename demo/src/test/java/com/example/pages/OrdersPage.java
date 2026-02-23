package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object for Order History (orders.html).
 */
public class OrdersPage extends BasePage {

    private static final By ORDERS_LIST = By.id("orders-list");
    private static final By ORDERS_EMPTY = By.id("orders-empty");
    private static final By ORDER_CARDS = By.cssSelector("#orders-list .order-card");

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL + "orders.html");
    }

    public void waitForPageLoaded() {
        wait.until(ExpectedConditions.urlContains("orders.html"));
        wait.until(driver -> driver.findElement(ORDERS_LIST).isDisplayed() || driver.findElement(ORDERS_EMPTY).isDisplayed());
    }

    public boolean isEmptyMessageDisplayed() {
        try {
            return driver.findElement(ORDERS_EMPTY).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Wait for empty message to be visible (when user has no orders). */
    public void waitForEmptyMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ORDERS_EMPTY));
    }

    public List<WebElement> getOrderCards() {
        return driver.findElements(ORDER_CARDS);
    }

    public int getOrderCount() {
        return getOrderCards().size();
    }

    public String getFirstOrderCardText() {
        List<WebElement> cards = getOrderCards();
        return cards.isEmpty() ? "" : cards.get(0).getText();
    }

    public boolean hasOrderWithId(String orderId) {
        return driver.findElement(ORDERS_LIST).getText().contains(orderId);
    }
}
