package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for Checkout / Payment (checkout.html).
 */
public class CheckoutPage extends BasePage {

    private static final By CHECKOUT_EMPTY = By.id("checkout-empty");
    private static final By CHECKOUT_FORM_WRAP = By.id("checkout-form-wrap");
    private static final By PAYMENT_FORM = By.id("payment-form");
    private static final By CARD_NAME = By.id("card-name");
    private static final By CARD_NUMBER = By.id("card-number");
    private static final By CARD_EXPIRY = By.id("card-expiry");
    private static final By CARD_CVV = By.id("card-cvv");
    private static final By BILLING_ADDRESS = By.id("billing-address");
    private static final By PAY_SUBMIT_BUTTON = By.cssSelector("#payment-form button[type='submit']");
    private static final By CHECKOUT_SUCCESS = By.id("checkout-success");
    private static final By SUCCESS_ORDER_ID = By.id("success-order-id");
    private static final By SUCCESS_DATE = By.id("success-date");
    private static final By SUCCESS_TOTAL = By.id("success-total");
    private static final By PAYMENT_ERROR = By.cssSelector(".payment-error");
    private static final By ORDER_SUMMARY = By.id("order-summary");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL + "checkout.html");
    }

    public void waitForPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAYMENT_FORM));
    }

    public boolean isEmptyMessageDisplayed() {
        try {
            return driver.findElement(CHECKOUT_EMPTY).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaymentFormDisplayed() {
        try {
            return driver.findElement(CHECKOUT_FORM_WRAP).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterCardName(String name) {
        type(CARD_NAME, name);
    }

    public void enterCardNumber(String number) {
        type(CARD_NUMBER, number);
    }

    public void enterCardExpiry(String expiry) {
        type(CARD_EXPIRY, expiry);
    }

    public void enterCardCvv(String cvv) {
        type(CARD_CVV, cvv);
    }

    public void enterBillingAddress(String address) {
        type(BILLING_ADDRESS, address);
    }

    public void submitPayment() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(PAY_SUBMIT_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', behavior:'instant'});", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void fillValidPaymentAndSubmit(String name, String cardNo, String expiry, String cvv, String address) {
        enterCardName(name);
        enterCardNumber(cardNo);
        enterCardExpiry(expiry);
        enterCardCvv(cvv);
        enterBillingAddress(address);
        submitPayment();
    }

    public boolean isSuccessDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(CHECKOUT_SUCCESS)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessOrderId() {
        return getText(SUCCESS_ORDER_ID);
    }

    public String getSuccessTotal() {
        return getText(SUCCESS_TOTAL);
    }

    public boolean isPaymentErrorDisplayed() {
        try {
            return driver.findElement(PAYMENT_ERROR).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOrderSummaryDisplayed() {
        try {
            return driver.findElement(ORDER_SUMMARY).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForSuccess() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CHECKOUT_SUCCESS));
    }
}
