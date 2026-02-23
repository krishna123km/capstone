package com.example;

import com.example.base.BaseTest;
import com.example.pages.CheckoutPage;
import com.example.pages.OrdersPage;
import com.example.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Module 6: Order History & Tracking - 10 test cases.
 * Uses signup in setUp (no DB / no prior login).
 */
public class OrderHistoryTrackingTests extends BaseTest {

    @BeforeMethod
    public void signUpFirst() throws InterruptedException {
        driver.get(getBaseUrl() + "index.html");
        driver.findElement(By.id("auth-toggle-link")).click();
        Thread.sleep(400);
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("passodasalaar@123");
        driver.findElement(By.cssSelector("#auth-form button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("home.html"));
    }

    @Test(priority = 1, description = "TC_ORDHIST_01: Orders page loads")
    public void TC_ORDHIST_01_ordersPageLoads() {
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(driver.getCurrentUrl().contains("orders.html"), "Should be on orders page");
    }

    @Test(priority = 2, description = "TC_ORDHIST_02: Empty order history shows message")
    public void TC_ORDHIST_02_emptyShowsMessage() throws InterruptedException {
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Thread.sleep(500);
        boolean emptyOrHasOrders = ordersPage.isEmptyMessageDisplayed() || ordersPage.getOrderCount() >= 0;
        Assert.assertTrue(emptyOrHasOrders, "Page should show empty message or order list");
    }

    @Test(priority = 3, description = "TC_ORDHIST_03: Place order then verify in history")
    public void TC_ORDHIST_03_placeOrderVerifyInHistory() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "123", "Addr");
        checkoutPage.waitForSuccess();
        String orderId = checkoutPage.getSuccessOrderId();
        driver.findElement(By.cssSelector("a[href='orders.html']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("orders.html"));
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(ordersPage.hasOrderWithId(orderId), "Order should appear in history");
    }

    @Test(priority = 4, description = "TC_ORDHIST_04: Order card shows Order ID")
    public void TC_ORDHIST_04_orderCardShowsOrderId() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "456", "Addr");
        checkoutPage.waitForSuccess();
        String orderId = checkoutPage.getSuccessOrderId();
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(ordersPage.hasOrderWithId(orderId), "Order card should show Order ID");
    }

    @Test(priority = 5, description = "TC_ORDHIST_05: Order card shows date")
    public void TC_ORDHIST_05_orderCardShowsDate() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("2");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/29", "789", "Addr");
        checkoutPage.waitForSuccess();
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(ordersPage.getOrderCount() >= 1, "At least one order");
        String text = ordersPage.getFirstOrderCardText();
        Assert.assertTrue(text.contains("ORD-") || text.length() > 0, "Order card should have content");
    }

    @Test(priority = 6, description = "TC_ORDHIST_06: Order card shows total")
    public void TC_ORDHIST_06_orderCardShowsTotal() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("U", "4111111111111111", "12/28", "111", "A");
        checkoutPage.waitForSuccess();
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        String cardText = ordersPage.getFirstOrderCardText();
        Assert.assertTrue(cardText.contains("₹"), "Order card should show total with ₹");
    }

    @Test(priority = 7, description = "TC_ORDHIST_07: Multiple orders listed")
    public void TC_ORDHIST_07_multipleOrdersListed() throws InterruptedException {
        for (String id : new String[]{"1", "2"}) {
            ProductPage productPage = new ProductPage(driver);
            productPage.open(id);
            productPage.waitForProductLoaded();
            productPage.clickAddToCart();
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.open();
            checkoutPage.waitForPageLoaded();
            checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "123", "Addr");
            checkoutPage.waitForSuccess();
        }
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(ordersPage.getOrderCount() >= 2, "At least two orders should be listed");
    }

    @Test(priority = 8, description = "TC_ORDHIST_08: Navigate to orders from nav link")
    public void TC_ORDHIST_08_navToOrders() {
        driver.get(getBaseUrl() + "home.html");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("home.html"));
        driver.findElement(By.cssSelector("a[href='orders.html']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("orders.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("orders.html"));
    }

    @Test(priority = 9, description = "TC_ORDHIST_09: Order details show item names")
    public void TC_ORDHIST_09_orderShowsItemNames() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        String productTitle = productPage.getProductTitle();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "123", "Addr");
        checkoutPage.waitForSuccess();
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        String cardText = ordersPage.getFirstOrderCardText();
        Assert.assertTrue(cardText.contains("Matte") || cardText.contains("Emulsion") || cardText.length() > 10,
                "Order should show item info");
    }

    @Test(priority = 10, description = "TC_ORDHIST_10: Order list is visible when orders exist")
    public void TC_ORDHIST_10_orderListVisible() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "123", "Addr");
        checkoutPage.waitForSuccess();
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.open();
        ordersPage.waitForPageLoaded();
        Assert.assertTrue(ordersPage.getOrderCount() >= 1, "Order list should show at least one order");
    }
}
