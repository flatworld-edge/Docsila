package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LogoutPage extends BasePage {

    // Logout button with tooltip
    private By logoutButton = By.xpath("//button[@title='Logout']");

    // Modal overlay and container
    private By modalOverlay = By.xpath("//div[contains(@class,'modal-overlay')]");
    private By modalDialog = By.xpath("//div[@role='dialog']");
    private By modalContainer = By.xpath("//div[contains(@class,'modal-container')]");

    // Modal header elements
    private By modalHeader = By.xpath("//div[contains(@class,'modal-header')]");
    private By modalTitle = By.xpath("//h3[@id='modalTitle']");
    private By modalTitleByText = By.xpath("//h3[normalize-space()='Confirm Logout']");
    private By closeButton = By.xpath("//button[@aria-label='Close modal']");

    // Modal body elements
    private By modalBody = By.xpath("//div[contains(@class,'modal-body')]");
    private By modalMessage = By.xpath("//p[@id='modalMessage']");
    private By modalMessageByText = By.xpath("//p[normalize-space()='Do you want to Log Out?']");
    private By questionIcon = By.xpath("//div[contains(@class,'modal-icon')]//*[name()='svg']");

    // Modal footer elements (buttons)
    private By modalFooter = By.xpath("//div[contains(@class,'modal-footer')]");
    private By noButton = By.xpath("//button[normalize-space()='No']");
    private By noButtonByClass = By.xpath("//button[contains(@class,'modal-btn-cancel')]");
    private By yesButton = By.xpath("//button[normalize-space()='Yes']");
    private By yesButtonByClass = By.xpath("//button[contains(@class,'modal-btn-confirm')]");

    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    // Get logout button tooltip
    public String getLogoutTooltip() {
        try {
            WebElement logout = driver.findElement(logoutButton);
            String tooltip = logout.getAttribute("title");
            System.out.println("Logout button tooltip: " + tooltip);
            return tooltip;
        } catch (Exception e) {
            System.out.println("Error getting logout tooltip: " + e.getMessage());
            return null;
        }
    }

    // Click logout button
    public void clickLogoutButton() {
        try {
            System.out.println("🔍 Looking for Logout button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

            // Verify tooltip before clicking
            String tooltip = logout.getAttribute("title");
            System.out.println("✓ Found Logout button with tooltip: '" + tooltip + "'");

            logout.click();
            System.out.println("✓ Clicked Logout button");

            // Small wait for modal to appear
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("✗ Error clicking logout button: " + e.getMessage());
            throw new RuntimeException("Failed to click logout button", e);
        }
    }

    // Check if logout confirmation modal is displayed
    public boolean isLogoutModalDisplayed() {
        try {
            System.out.println("🔍 Checking if logout confirmation modal is displayed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            System.out.println("✓ Logout confirmation modal is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Logout confirmation modal is NOT displayed");
            return false;
        }
    }

    // Get modal title text
    public String getModalTitle() {
        try {
            WebElement title = driver.findElement(modalTitle);
            String titleText = title.getText();
            System.out.println("Modal title: " + titleText);
            return titleText;
        } catch (Exception e) {
            System.out.println("Error getting modal title: " + e.getMessage());
            return null;
        }
    }

    // Get modal message text
    public String getModalMessage() {
        try {
            WebElement message = driver.findElement(modalMessage);
            String messageText = message.getText();
            System.out.println("Modal message: " + messageText);
            return messageText;
        } catch (Exception e) {
            System.out.println("Error getting modal message: " + e.getMessage());
            return null;
        }
    }

    // Click Close (X) button on modal
    public void clickCloseButton() {
        try {
            System.out.println("Clicking Close (X) button on modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(closeButton));
            close.click();
            System.out.println("✓ Clicked Close (X) button");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("✗ Error clicking close button: " + e.getMessage());
            throw new RuntimeException("Failed to click close button", e);
        }
    }

    // Click No button on modal
    public void clickNoButton() {
        try {
            System.out.println("Clicking 'No' button on modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try both locators
            WebElement no = null;
            try {
                no = wait.until(ExpectedConditions.elementToBeClickable(noButton));
            } catch (Exception e) {
                no = wait.until(ExpectedConditions.elementToBeClickable(noButtonByClass));
            }

            no.click();
            System.out.println("✓ Clicked 'No' button");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("✗ Error clicking No button: " + e.getMessage());
            throw new RuntimeException("Failed to click No button", e);
        }
    }

    // Click Yes button on modal
    public void clickYesButton() {
        try {
            System.out.println("Clicking 'Yes' button on modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try both locators
            WebElement yes = null;
            try {
                yes = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
            } catch (Exception e) {
                yes = wait.until(ExpectedConditions.elementToBeClickable(yesButtonByClass));
            }

            yes.click();
            System.out.println("✓ Clicked 'Yes' button - Logging out...");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking Yes button: " + e.getMessage());
            throw new RuntimeException("Failed to click Yes button", e);
        }
    }

    // Verify modal disappeared after clicking No or Close
    public boolean isModalClosed() {
        try {
            System.out.println("🔍 Verifying modal is closed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalDialog));
            System.out.println("✓ Modal is closed - Still on the same page");
            return true;
        } catch (Exception e) {
            System.out.println("⚠ Modal is still visible");
            return false;
        }
    }

    // Verify user is logged out (redirected to login page)
    public boolean isLoggedOut() {
        try {
            System.out.println("🔍 Verifying user is logged out...");
            Thread.sleep(2000);

            // Check if we're back on login page (Flatworld button should be visible)
            By flatworldButton = By.xpath("//button[@id='Flatworld']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(flatworldButton));

            System.out.println("✓ User is logged out - Redirected to login page");
            return true;
        } catch (Exception e) {
            System.out.println("✗ User is NOT logged out");
            return false;
        }
    }

    // Complete logout flow - Click Yes and verify logout
    public void performLogout() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   PERFORMING LOGOUT");
        System.out.println("════════════════════════════════════════\n");

        // Step 1: Click logout button
        clickLogoutButton();

        // Step 2: Verify modal appeared
        if (!isLogoutModalDisplayed()) {
            throw new RuntimeException("Logout confirmation modal did not appear!");
        }

        // Step 3: Display modal content
        getModalTitle();
        getModalMessage();

        // Step 4: Click Yes to logout
        clickYesButton();

        // Step 5: Verify logout successful
        if (!isLoggedOut()) {
            throw new RuntimeException("Logout failed - User still on the same page!");
        }

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ✓ LOGOUT SUCCESSFUL");
        System.out.println("════════════════════════════════════════\n");
    }
}

