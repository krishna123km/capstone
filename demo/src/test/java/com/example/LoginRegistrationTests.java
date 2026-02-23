package com.example;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Module 1: User Registration (Signup only) - 10 test cases.
 * No login validation - no DB to store/validate users. All tests use signup flow only.
 */
public class LoginRegistrationTests extends BaseTest {

    @Test(priority = 1, description = "TC_SIGNUP_01: Auth page loads with form")
    public void TC_SIGNUP_01_authPageLoads() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        Assert.assertTrue(loginPage.isAuthFormDisplayed(), "Auth form should be displayed");
        Assert.assertEquals(loginPage.getTitleText(), "Login", "Default title is Login");
    }

    @Test(priority = 2, description = "TC_SIGNUP_02: Toggle to Sign up mode")
    public void TC_SIGNUP_02_toggleToSignup() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(500);
        Assert.assertEquals(loginPage.getTitleText(), "Sign up", "Title should switch to Sign up");
    }

    @Test(priority = 3, description = "TC_SIGNUP_03: Signup with valid data redirects to home")
    public void TC_SIGNUP_03_signupWithValidDataRedirectsToHome() throws InterruptedException {
        String email = "user" + System.currentTimeMillis() + "@example.com";
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail(email);
        loginPage.enterPassword("password123");
        loginPage.clickSubmit();
        loginPage.waitForRedirectToHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("home.html"), "Should redirect to home after signup");
    }

    @Test(priority = 4, description = "TC_SIGNUP_04: Signup with spaces-only email shows error (app validation)")
    public void TC_SIGNUP_04_signupInvalidEmailShowsError() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("   ");
        loginPage.enterPassword("password123");
        loginPage.bypassRequiredValidation();
        loginPage.clickSubmit();
        Thread.sleep(800);
        Assert.assertTrue(loginPage.isOnLoginPage(), "Should remain on auth page");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be shown (email effectively empty after trim)");
    }

    @Test(priority = 5, description = "TC_SIGNUP_05: Signup with empty email shows error")
    public void TC_SIGNUP_05_signupEmptyEmailShowsError() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterPassword("password123");
        loginPage.bypassRequiredValidation();
        loginPage.clickSubmit();
        Thread.sleep(800);
        Assert.assertTrue(loginPage.isOnLoginPage(), "Should remain on auth page");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be shown for empty email on signup");
    }

    @Test(priority = 6, description = "TC_SIGNUP_06: Signup with empty password shows error")
    public void TC_SIGNUP_06_signupEmptyPasswordShowsError() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("newuser@test.com");
        loginPage.bypassRequiredValidation();
        loginPage.clickSubmit();
        Thread.sleep(800);
        Assert.assertTrue(loginPage.isOnLoginPage(), "Should remain on auth page");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be shown for empty password on signup");
    }

    @Test(priority = 7, description = "TC_SIGNUP_07: Signup with password less than 6 chars shows error")
    public void TC_SIGNUP_07_signupShortPasswordShowsError() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("newuser@test.com");
        loginPage.enterPassword("12345");
        loginPage.clickSubmit();
        Thread.sleep(800);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be shown for short password on signup");
    }

    @Test(priority = 8, description = "TC_SIGNUP_08: Duplicate email on signup shows error")
    public void TC_SIGNUP_08_duplicateEmailShowsError() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password123");
        loginPage.clickSubmit();
        loginPage.waitForRedirectToHome();
        loginPage.clickLogout();
        loginPage.waitForRedirectToLogin();
        Thread.sleep(400);
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password123");
        loginPage.clickSubmit();
        Thread.sleep(800);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Duplicate email should show error on signup");
    }

    @Test(priority = 9, description = "TC_SIGNUP_09: Signup then verify on home page")
    public void TC_SIGNUP_09_signupThenVerifyHome() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password123");
        loginPage.clickSubmit();
        loginPage.waitForRedirectToHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("home.html"), "Should be on home after signup");
        Assert.assertTrue(driver.getPageSource().contains("Paint Store") || driver.getPageSource().contains("Home"),
                "Home page content should be visible");
    }

    @Test(priority = 10, description = "TC_SIGNUP_10: Signup then logout redirects to auth page")
    public void TC_SIGNUP_10_signupThenLogoutRedirects() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickSignUpLink();
        Thread.sleep(400);
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("password123");
        loginPage.clickSubmit();
        loginPage.waitForRedirectToHome();
        loginPage.clickLogout();
        loginPage.waitForRedirectToLogin();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on auth page after logout");
    }
}
