package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.HomePage;
import pageObject.LogoutPage;

public class TC_01_LoginPage extends BaseClass {

    @Test
    public void testLogin() throws InterruptedException {
        // Get credentials from config file
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        LogoutPage logoutPage = new LogoutPage(driver);

        // Perform login (will skip if already logged in)
        loginPage.performLogin(email, password);

        // Wait for home page to load
        homePage.waitForPageLoad();

        // Verify logo is present - This will PASS or FAIL the test
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "DocSila Logo is NOT displayed on the home page!");
        System.out.println("✓ Test PASSED - Logo verified successfully!");

        // Test logout functionality with different scenarios
        testLogoutScenarios(logoutPage, homePage);
    }

    private void testLogoutScenarios(LogoutPage logoutPage, HomePage homePage) throws InterruptedException {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   TESTING LOGOUT SCENARIOS");
        System.out.println("════════════════════════════════════════\n");

        // Scenario 1: Click logout, verify tooltip, then click "No" button
        System.out.println("--- Scenario 1: Testing 'No' Button ---");

        // Get and verify logout button tooltip
        String tooltip = logoutPage.getLogoutTooltip();
        Assert.assertNotNull(tooltip, "Logout button tooltip is missing!");
        Assert.assertEquals(tooltip, "Logout", "Logout button tooltip is incorrect!");

        // Click logout button
        logoutPage.clickLogoutButton();

        // Verify modal is displayed
        boolean isModalDisplayed = logoutPage.isLogoutModalDisplayed();
        Assert.assertTrue(isModalDisplayed, "Logout confirmation modal did NOT appear!");

        // Verify modal title
        String modalTitle = logoutPage.getModalTitle();
        Assert.assertEquals(modalTitle, "Confirm Logout", "Modal title is incorrect!");

        // Verify modal message
        String modalMessage = logoutPage.getModalMessage();
        Assert.assertEquals(modalMessage, "Do you want to Log Out?", "Modal message is incorrect!");

        // Click No button
        logoutPage.clickNoButton();

        // Verify modal closed and still on same page
        boolean isModalClosed = logoutPage.isModalClosed();
        Assert.assertTrue(isModalClosed, "Modal did not close after clicking No!");

        // Verify still on home page (logo should still be visible)
        boolean logoStillPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(logoStillPresent, "User was logged out even after clicking No!");
        System.out.println("✓ Scenario 1 PASSED - Clicked No, stayed on same page\n");

        // Scenario 2: Click logout, then click "Close (X)" button
        System.out.println("--- Scenario 2: Testing 'Close (X)' Button ---");

        logoutPage.clickLogoutButton();
        Assert.assertTrue(logoutPage.isLogoutModalDisplayed(), "Modal did not appear for second test!");

        // Click Close (X) button
        logoutPage.clickCloseButton();

        // Verify modal closed and still on same page
        Assert.assertTrue(logoutPage.isModalClosed(), "Modal did not close after clicking X!");
        Assert.assertTrue(homePage.isLogoDisplayed(), "User was logged out even after clicking X!");
        System.out.println("✓ Scenario 2 PASSED - Clicked Close (X), stayed on same page\n");

        // Scenario 3: Click logout, then click "Yes" button to actually logout
        System.out.println("--- Scenario 3: Testing 'Yes' Button (Actual Logout) ---");

        logoutPage.clickLogoutButton();
        Assert.assertTrue(logoutPage.isLogoutModalDisplayed(), "Modal did not appear for logout!");

        // Click Yes button to logout
        logoutPage.clickYesButton();

        // Verify user is logged out
        boolean isLoggedOut = logoutPage.isLoggedOut();
        Assert.assertTrue(isLoggedOut, "User was NOT logged out after clicking Yes!");
        System.out.println("✓ Scenario 3 PASSED - Clicked Yes, successfully logged out\n");

        System.out.println("════════════════════════════════════════");
        System.out.println("   ✓ ALL LOGOUT SCENARIOS PASSED!");
        System.out.println("════════════════════════════════════════\n");
    }
}
