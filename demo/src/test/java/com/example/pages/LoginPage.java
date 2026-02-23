package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for Login / Sign up (index.html).
 */
public class LoginPage extends BasePage {

    private static final By AUTH_TITLE = By.id("auth-title");
    private static final By AUTH_ERROR = By.id("auth-error");
    private static final By AUTH_FORM = By.id("auth-form");
    private static final By EMAIL = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By SUBMIT_BUTTON = By.cssSelector("#auth-form button[type='submit']");
    private static final By AUTH_TOGGLE_LINK = By.id("auth-toggle-link");
    private static final By NAV_LOGOUT = By.id("nav-logout");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL + "index.html");
    }

    public String getTitleText() {
        return getText(AUTH_TITLE);
    }

    public boolean isAuthFormDisplayed() {
        return isDisplayed(AUTH_FORM);
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AUTH_ERROR))
                    .getAttribute("class").contains("visible");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        try {
            return driver.findElement(AUTH_ERROR).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void enterEmail(String email) {
        type(EMAIL, email);
    }

    public void enterPassword(String password) {
        type(PASSWORD, password);
    }

    public void clickSubmit() {
        click(SUBMIT_BUTTON);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }

    public void clickSignUpLink() {
        click(AUTH_TOGGLE_LINK);
    }

    public void waitForRedirectToHome() {
        wait.until(ExpectedConditions.urlContains("home.html"));
    }

    public void waitForRedirectToLogin() {
        wait.until(ExpectedConditions.urlContains("index.html"));
    }

    public void clickLogout() {
        click(NAV_LOGOUT);
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("index.html");
    }

    /** Bypass browser HTML5 validation so form submit runs app JS (e.g. to see app error messages). */
    public void bypassRequiredValidation() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "var f = document.getElementById('auth-form');" +
            "if (f) { var i = f.querySelectorAll('input[required]'); for (var j = 0; j < i.length; j++) i[j].removeAttribute('required'); }"
        );
    }
}
