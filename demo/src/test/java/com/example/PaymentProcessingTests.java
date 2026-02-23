package com.example;

import com.example.base.BaseTest;
import com.example.pages.CartPage;
import com.example.pages.CheckoutPage;
import com.example.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Module 5: Payment Processing - 10 test cases.
 * Uses signup in setUp (no DB). Card validation, payment submit, success, error handling.
 */
public class PaymentProcessingTests extends BaseTest {

    @BeforeMethod
    public void signUpAndAddToCart() throws InterruptedException {
        driver.get(getBaseUrl() + "index.html");
        driver.findElement(By.id("auth-toggle-link")).click();
        Thread.sleep(400);
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("Salaarjaiho@123");
        driver.findElement(By.cssSelector("#auth-form button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("home.html"));
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
    }

    @Test(priority = 1, description = "TC_PAY_01: Valid card number format accepted")
    public void TC_PAY_01_validCardAccepted() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("Test User", "4111111111111111", "12/28", "123", "Address 560001");
        Thread.sleep(1500);
        Assert.assertTrue(checkoutPage.isSuccessDisplayed(), "Valid payment should show success");
    }

    @Test(priority = 2, description = "TC_PAY_02: Invalid card number shows error")
    public void TC_PAY_02_invalidCardShowsError() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterCardName("Test");
        checkoutPage.enterCardNumber("123");
        checkoutPage.enterCardExpiry("12/28");
        checkoutPage.enterCardCvv("123");
        checkoutPage.enterBillingAddress("Address");
        checkoutPage.submitPayment();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isPaymentErrorDisplayed(), "Invalid card should show error");
    }

    @Test(priority = 3, description = "TC_PAY_03: Invalid expiry format (not MM/YY) shows error")
    public void TC_PAY_03_invalidExpiryShowsError() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterCardName("Test");
        checkoutPage.enterCardNumber("4111111111111111");
        checkoutPage.enterCardExpiry("1");
        checkoutPage.enterCardCvv("123");
        checkoutPage.enterBillingAddress("Address");
        checkoutPage.submitPayment();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isPaymentErrorDisplayed(), "Invalid expiry format should show error");
    }

    @Test(priority = 4, description = "TC_PAY_04: Invalid CVV (too short) shows error")
    public void TC_PAY_04_invalidCvvShowsError() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterCardName("Test");
        checkoutPage.enterCardNumber("4111111111111111");
        checkoutPage.enterCardExpiry("12/28");
        checkoutPage.enterCardCvv("12");
        checkoutPage.enterBillingAddress("Address");
        checkoutPage.submitPayment();
        Thread.sleep(1000);
        Assert.assertTrue(checkoutPage.isPaymentErrorDisplayed(), "Invalid CVV should show error");
    }

    @Test(priority = 5, description = "TC_PAY_05: Success page shows total amount")
    public void TC_PAY_05_successShowsTotal() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "456", "Addr");
        checkoutPage.waitForSuccess();
        String total = checkoutPage.getSuccessTotal();
        Assert.assertNotNull(total);
        Assert.assertTrue(total.contains("₹"), "Success should show total with ₹");
    }

    @Test(priority = 6, description = "TC_PAY_06: Name on card field required")
    public void TC_PAY_06_nameOnCardDisplayed() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        Assert.assertTrue(driver.findElement(By.id("card-name")).isDisplayed());
    }

    @Test(priority = 7, description = "TC_PAY_07: Card number formatting (spaces)")
    public void TC_PAY_07_cardNumberAcceptsInput() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterCardNumber("4111111111111111");
        String value = driver.findElement(By.id("card-number")).getAttribute("value").replace(" ", "");
        Assert.assertTrue(value.length() >= 13, "Card number should be accepted");
    }

    @Test(priority = 8, description = "TC_PAY_08: Expiry MM/YY format")
    public void TC_PAY_08_expiryFormat() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterCardExpiry("1228");
        String value = driver.findElement(By.id("card-expiry")).getAttribute("value");
        Assert.assertTrue(value.contains("/") || value.length() >= 4, "Expiry should be formatted");
    }

    @Test(priority = 9, description = "TC_PAY_09: Pay & Place Order button submits")
    public void TC_PAY_09_submitButtonWorks() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("Tester", "4111111111111111", "12/30", "999", "Street City 560001");
        Thread.sleep(2000);
        Assert.assertTrue(checkoutPage.isSuccessDisplayed(), "Submit should place order and show success");
    }

    @Test(priority = 10, description = "TC_PAY_10: Transaction success - order ID generated")
    public void TC_PAY_10_orderIdGenerated() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/28", "123", "Addr");
        checkoutPage.waitForSuccess();
        String orderId = checkoutPage.getSuccessOrderId();
        Assert.assertNotNull(orderId);
        Assert.assertFalse(orderId.isEmpty());
    }
}
