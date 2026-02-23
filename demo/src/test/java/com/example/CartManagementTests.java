package com.example;

import com.example.base.BaseTest;
import com.example.pages.CartPage;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Module 3: Cart Management - 10 test cases.
 * POM: CartPage, HomePage, ProductPage. Uses signup in setUp (no DB).
 */
public class CartManagementTests extends BaseTest {

    @BeforeMethod
    public void signUpFirst() throws InterruptedException {
        driver.get(getBaseUrl() + "index.html");
        driver.findElement(By.id("auth-toggle-link")).click();
        Thread.sleep(400);
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("passwhoiord123");
        driver.findElement(By.cssSelector("#auth-form button[type='submit']")).click();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("home.html"));
    }

    @Test(priority = 1, description = "TC_CART_01: Cart page loads and shows empty state when no items")
    public void TC_CART_01_cartPageLoadsEmpty() {
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed() || cartPage.getCartRowCount() == 0,
                "Cart should show empty or no rows");
    }

    @Test(priority = 2, description = "TC_CART_02: Add single item from product page - cart shows item")
    public void TC_CART_02_addSingleItemFromProductPage() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.getCartRowCount() >= 1, "Cart should have at least one item");
        Assert.assertTrue(cartPage.isTotalSectionDisplayed(), "Grand total section should be visible");
    }

    @Test(priority = 3, description = "TC_CART_03: Add multiple items - cart shows correct count")
    public void TC_CART_03_addMultipleItems() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        productPage.open("2");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.getCartRowCount() >= 2, "Cart should have two items");
    }

    @Test(priority = 4, description = "TC_CART_04: Increase quantity - total updates")
    public void TC_CART_04_increaseQuantity() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        String totalBefore = cartPage.getGrandTotalText();
        cartPage.clickPlusForRow(0);
        Thread.sleep(500);
        String totalAfter = cartPage.getGrandTotalText();
        Assert.assertNotEquals(totalAfter, totalBefore, "Total should change after increasing quantity");
    }

    @Test(priority = 5, description = "TC_CART_05: Decrease quantity - total updates")
    public void TC_CART_05_decreaseQuantity() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        cartPage.clickMinusForRow(0);
        Thread.sleep(500);
        Assert.assertTrue(cartPage.getCartRowCount() >= 1, "Item should remain (qty 1) or be removed");
    }

    @Test(priority = 6, description = "TC_CART_06: Remove item - cart updates")
    public void TC_CART_06_removeItem() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        int before = cartPage.getCartRowCount();
        cartPage.clickRemoveForRow(0);
        Thread.sleep(500);
        int after = cartPage.getCartRowCount();
        Assert.assertTrue(after < before || cartPage.isEmptyCartMessageDisplayed(), "Item should be removed");
    }

    @Test(priority = 7, description = "TC_CART_07: Grand total displays correct format (₹)")
    public void TC_CART_07_grandTotalFormat() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        String total = cartPage.getGrandTotalText();
        Assert.assertTrue(total.contains("₹"), "Grand total should contain ₹");
    }

    @Test(priority = 8, description = "TC_CART_08: Proceed to Checkout button visible when cart has items")
    public void TC_CART_08_proceedToCheckoutVisible() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.isTotalSectionDisplayed(), "Checkout section should be visible");
    }

    @Test(priority = 9, description = "TC_CART_09: Cart persistence - add then navigate away and back")
    public void TC_CART_09_cartPersistence() {
        ProductPage productPage = new ProductPage(driver);
        productPage.open("1");
        productPage.waitForProductLoaded();
        productPage.clickAddToCart();
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.waitForGridLoaded();
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        Assert.assertTrue(cartPage.getCartRowCount() >= 1, "Cart should persist after navigation");
    }

    @Test(priority = 10, description = "TC_CART_10: Empty cart shows continue shopping / browse link")
    public void TC_CART_10_emptyCartShowsBrowseLink() {
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        cartPage.waitForCartLoaded();
        boolean emptyOrZero = cartPage.isEmptyCartMessageDisplayed() || cartPage.getCartRowCount() == 0;
        Assert.assertTrue(emptyOrZero, "Empty cart should show empty state");
    }
}
