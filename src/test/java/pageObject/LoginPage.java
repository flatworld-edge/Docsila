package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {

    // Locators
    private By flatworldButton = By.xpath("//button[@id='Flatworld']");
    private By emailField = By.xpath("//input[@type='email']");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By passwordField = By.xpath("//input[@name='passwd']");
    private By yesButton = By.xpath("//input[@id='idSIButton9']");
    private By docsilaLogo = By.xpath("//img[@alt='DocSila']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isFlatworldButtonPresent() {
        try {
            System.out.println("Checking if Flatworld button is present...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement flatworldBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(flatworldButton));
            return flatworldBtn.isDisplayed();
        } catch (Exception e) {
            System.out.println("Flatworld button not found - Already logged in!");
            return false;
        }
    }

    public void clickFlatworldButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement flatworldBtn = wait.until(ExpectedConditions.elementToBeClickable(flatworldButton));
            System.out.println("Clicking 'Sign in with Flatworld' button...");
            flatworldBtn.click();
            System.out.println("✓ Flatworld button clicked");
        } catch (Exception e) {
            System.out.println("Error clicking Flatworld button: " + e.getMessage());
            throw new RuntimeException("Failed to click Flatworld button", e);
        }
    }

    public boolean isEmailFieldPresent() {
        try {
            System.out.println("Checking if email field is present (First time login)...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            System.out.println("Email field found - First time login required");
            return true;
        } catch (Exception e) {
            System.out.println("Email field NOT found - Already authenticated (cookies saved)!");
            return false;
        }
    }

    public void enterEmail(String email) {
        try {
            System.out.println("Step 1: Entering email address...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
            WebElement emailFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            emailFieldElement.clear();
            emailFieldElement.sendKeys(email);
            System.out.println("✓ Email entered: " + email);
        } catch (Exception e) {
            System.out.println("ERROR: Email field not found after 40 seconds!");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            throw new RuntimeException("Failed to find email field", e);
        }
    }

    public void clickNextButton() {
        try {
            System.out.println("Step 2: Clicking 'Next' button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            submitBtn.click();
            System.out.println("✓ 'Next' button clicked");
        } catch (Exception e) {
            System.out.println("ERROR: Next button not found!");
            throw new RuntimeException("Failed to click Next button", e);
        }
    }

    public void enterPassword(String password) {
        try {
            System.out.println("Step 3: Entering password...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement passwordFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            passwordFieldElement.clear();
            passwordFieldElement.sendKeys(password);
            System.out.println("✓ Password entered successfully");
        } catch (Exception e) {
            System.out.println("ERROR: Password field not found!");
            throw new RuntimeException("Failed to find password field", e);
        }
    }

    public void clickSignInButton() {
        try {
            System.out.println("Step 4: Clicking 'Sign In' button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            submitBtn.click();
            System.out.println("✓ 'Sign In' button clicked");
        } catch (Exception e) {
            System.out.println("ERROR: Sign In button not found!");
            throw new RuntimeException("Failed to click Sign In button", e);
        }
    }

    public void approveMFAAndClickYes() {
        try {
            System.out.println("Step 5: Waiting for MFA approval...");
            System.out.println("📱 Please approve the authentication request on your mobile device...");
            System.out.println("⏳ Waiting up to 60 seconds for approval...");

            WebDriverWait mfaWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement yesBtn = mfaWait.until(ExpectedConditions.elementToBeClickable(yesButton));
            System.out.println("✓ MFA approved! Clicking 'Yes' button to save cookies...");
            yesBtn.click();
            System.out.println("✓ 'Yes' button clicked - Session saved!");
            System.out.println("✓ First time login completed! Session saved for future use.");
        } catch (Exception e) {
            System.out.println("ERROR: MFA approval timeout or Yes button not found!");
            throw new RuntimeException("MFA approval failed", e);
        }
    }

    public void performLogin(String email, String password) {
        // Step 1: Check if Flatworld button is present
        if (isFlatworldButtonPresent()) {
            // Step 2: Click Flatworld button
            clickFlatworldButton();

            // Step 3: Check if email field appears (first time login vs. saved session)
            if (isEmailFieldPresent()) {
                System.out.println("\n════════════════════════════════════════");
                System.out.println("   FIRST TIME LOGIN - MFA REQUIRED");
                System.out.println("════════════════════════════════════════\n");

                // First time login - Complete all steps
                enterEmail(email);
                clickNextButton();
                enterPassword(password);
                clickSignInButton();
                approveMFAAndClickYes();

                System.out.println("\n════════════════════════════════════════");
                System.out.println("   ✓ LOGIN SUCCESSFUL - SESSION SAVED");
                System.out.println("   Next time, login will be automatic!");
                System.out.println("════════════════════════════════════════\n");
            } else {
                System.out.println("\n════════════════════════════════════════");
                System.out.println("   ✓ ALREADY AUTHENTICATED!");
                System.out.println("   Using saved cookies - No MFA needed");
                System.out.println("════════════════════════════════════════\n");

                // Session already saved - Redirecting to home page automatically
                System.out.println("⏳ Redirecting to home page...");
            }
        } else {
            System.out.println("\n════════════════════════════════════════");
            System.out.println("   ✓ ALREADY LOGGED IN!");
            System.out.println("   Flatworld button not present");
            System.out.println("════════════════════════════════════════\n");
        }
    }
}
