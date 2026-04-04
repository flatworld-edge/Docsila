package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class EvaluationRunsPage extends BasePage {

    // ==================== Evaluation Runs Page Locators ====================
    private By loader = By.xpath("//div[@class='docsila-loader']");
    private By pageTitle = By.xpath("//h1 | //h2 | //*[contains(text(),'Evaluation') or contains(text(),'Runs')]");

    // View Validation Results button in Actions column
    private By viewValidationResultsButton = By.xpath("//button[@title='View Validation Results' and contains(@class,'btn-results')]");

    // View Validation Results button for first row (or any specific row)
    private By firstViewValidationResultsButton = By.xpath("(//button[@title='View Validation Results' and contains(@class,'btn-results')])[1]");

    public EvaluationRunsPage(WebDriver driver) {
        super(driver);
    }

    // ==================== Evaluation Runs Page Methods ====================

    /**
     * Wait for the Evaluation Runs page to load
     */
    public void waitForPageLoad() {
        try {
            System.out.println("⏳ Waiting for Evaluation Runs page to load...");

            // Wait for loader to disappear (if present)
            try {
                WebDriverWait loaderWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                loaderWait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
                System.out.println("✓ Loader disappeared");
            } catch (Exception e) {
                System.out.println("⚠ Loader not found or already gone");
            }

            // Small wait for page to stabilize
            Thread.sleep(1000);
            System.out.println("✓ Evaluation Runs page loaded");

        } catch (Exception e) {
            System.out.println("⚠ Error waiting for page load: " + e.getMessage());
        }
    }

    /**
     * Click on the first "View Validation Results" button in the Actions column
     */
    public void clickFirstViewValidationResults() {
        try {
            System.out.println("🔍 Clicking first View Validation Results button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(firstViewValidationResultsButton));
            button.click();
            System.out.println("✓ Clicked View Validation Results button");

            // Wait for navigation
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking View Validation Results button: " + e.getMessage());
            throw new RuntimeException("Failed to click View Validation Results button", e);
        }
    }

    /**
     * Click on any "View Validation Results" button (if multiple exist)
     */
    public void clickViewValidationResults() {
        try {
            System.out.println("🔍 Clicking View Validation Results button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(viewValidationResultsButton));
            button.click();
            System.out.println("✓ Clicked View Validation Results button");

            // Wait for navigation
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking View Validation Results button: " + e.getMessage());
            throw new RuntimeException("Failed to click View Validation Results button", e);
        }
    }

    /**
     * Verify that the Evaluation Runs page is displayed
     */
    public boolean isPageDisplayed() {
        try {
            System.out.println("🔍 Verifying Evaluation Runs page is displayed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(viewValidationResultsButton));
            boolean isDisplayed = element.isDisplayed();
            System.out.println(isDisplayed ? "✓ Evaluation Runs page is displayed" : "✗ Evaluation Runs page is NOT displayed");
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("✗ Evaluation Runs page is NOT displayed: " + e.getMessage());
            return false;
        }
    }
}
