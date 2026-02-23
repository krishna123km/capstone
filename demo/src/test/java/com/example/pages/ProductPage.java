package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for Product detail (product.html).
 */
public class ProductPage extends BasePage {

    private static final By PRODUCT_ROOT = By.id("product-root");
    private static final By ADD_TO_CART_BTN = By.id("add-to-cart");
    private static final By PRODUCT_NOT_FOUND = By.id("product-not-found");
    private static final By PRODUCT_PRICE = By.cssSelector(".product-price");
    private static final By PRODUCT_TITLE = By.cssSelector(".product-info h1");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void open(String productId) {
        driver.get(BASE_URL + "product.html?id=" + productId);
    }

    public void openWithQuery(String query) {
        driver.get(BASE_URL + "product.html?" + query);
    }

    public void waitForProductLoaded() {
        wait.until(ExpectedConditions.urlContains("product.html"));
        wait.until(ExpectedConditions.presenceOfElementLocated(PRODUCT_ROOT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_TO_CART_BTN));
    }

    public boolean isProductDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_ROOT)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNotFoundDisplayed() {
        try {
            return driver.findElement(PRODUCT_NOT_FOUND).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddToCart() {
        click(ADD_TO_CART_BTN);
    }

    public String getAddToCartButtonText() {
        return driver.findElement(ADD_TO_CART_BTN).getText();
    }

    public String getProductTitle() {
        return getText(PRODUCT_TITLE);
    }

    public String getProductPriceText() {
        return getText(PRODUCT_PRICE);
    }
}
