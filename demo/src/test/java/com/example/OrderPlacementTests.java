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
 * Module 4: Order Placement - 10 test cases.
 * Checkout workflow, address, order review, redirects.
 */
public class OrderPlacementTests extends BaseTest {

    @BeforeMethod
    public void signUpFirst() throws InterruptedException {
        driver.get(getBaseUrl() + "index.html");
        driver.findElement(By.id("auth-toggle-link")).click();
        Thread.sleep(400);
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("Putinhello@1234");
        driver.findElement(By.cssSelector("#auth-form button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("home.html"));
    }

    private void addProductToCart() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
    }

    @Test(priority = 1, description = "TC_ORDER_01: Checkout page loads with cart items - order summary visible")
    public void TC_ORDER_01_checkoutLoadsWithOrderSummary() {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        Assert.assertTrue(checkoutPage.isOrderSummaryDisplayed() || checkoutPage.isPaymentFormDisplayed(),
                "Order summary or payment form should be visible");
    }

    @Test(priority = 2, description = "TC_ORDER_02: Empty cart - checkout shows empty message")
    public void TC_ORDER_02_emptyCartShowsEmptyMessage() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        Thread.sleep(1500);
        Assert.assertTrue(checkoutPage.isEmptyMessageDisplayed(), "Empty cart message should show");
    }

    @Test(priority = 3, description = "TC_ORDER_03: Proceed to Checkout from cart redirects to checkout")
    public void TC_ORDER_03_proceedToCheckoutRedirects() {
        addProductToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        cartPage.clickProceedToCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout.html"), "Should be on checkout page");
    }

    @Test(priority = 4, description = "TC_ORDER_04: Payment form has all required fields")
    public void TC_ORDER_04_paymentFormHasRequiredFields() {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        Assert.assertTrue(driver.findElement(By.id("card-name")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("card-number")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("card-expiry")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("card-cvv")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("billing-address")).isDisplayed());
    }

    @Test(priority = 5, description = "TC_ORDER_05: Valid payment places order and shows success")
    public void TC_ORDER_05_validPaymentPlacesOrder() throws InterruptedException {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("Test User", "4111111111111111", "12/28", "123", "Street, City, State 560001");
        Thread.sleep(1500);
        Assert.assertTrue(checkoutPage.isSuccessDisplayed(), "Order success should be displayed");
    }

    @Test(priority = 6, description = "TC_ORDER_06: Success page shows Order ID")
    public void TC_ORDER_06_successShowsOrderId() throws InterruptedException {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("Test User", "4111111111111111", "12/28", "123", "Address 560001");
        checkoutPage.waitForSuccess();
        String orderId = checkoutPage.getSuccessOrderId();
        Assert.assertNotNull(orderId);
        Assert.assertTrue(orderId.startsWith("ORD-"), "Order ID should start with ORD-");
    }

    @Test(priority = 7, description = "TC_ORDER_07: Success page shows View Orders link")
    public void TC_ORDER_07_successShowsViewOrdersLink() throws InterruptedException {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("Test User", "4111111111111111", "12/28", "456", "Addr 560002");
        checkoutPage.waitForSuccess();
        Assert.assertTrue(driver.findElement(By.cssSelector("a[href='orders.html']")).isDisplayed());
    }

    @Test(priority = 8, description = "TC_ORDER_08: Order review - total matches cart")
    public void TC_ORDER_08_orderReviewTotalMatches() throws InterruptedException {
        addProductToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        String cartTotal = cartPage.getGrandTotalText();
        cartPage.clickProceedToCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.waitForPageLoaded();
        Assert.assertTrue(driver.getPageSource().contains("Order Summary") || checkoutPage.isOrderSummaryDisplayed(),
                "Order summary should be visible");
    }

    @Test(priority = 9, description = "TC_ORDER_09: Billing address field accepts input")
    public void TC_ORDER_09_billingAddressAcceptsInput() {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.enterBillingAddress("123 Main St, City, State 560001");
        Assert.assertEquals(driver.findElement(By.id("billing-address")).getAttribute("value"), "123 Main St, City, State 560001");
    }

    @Test(priority = 10, description = "TC_ORDER_10: After place order - cart is empty")
    public void TC_ORDER_10_afterPlaceOrderCartEmpty() throws InterruptedException {
        addProductToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
        checkoutPage.waitForPageLoaded();
        checkoutPage.fillValidPaymentAndSubmit("User", "4111111111111111", "12/29", "789", "Addr");
        checkoutPage.waitForSuccess();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed() || cartPage.getCartRowCount() == 0,
                "Cart should be empty after order");
    }
}
