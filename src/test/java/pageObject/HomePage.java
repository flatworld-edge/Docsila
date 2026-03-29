package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage extends BasePage {

    // Locators
    private By docsilaLogo = By.xpath("//img[@alt='DocSila']");
    private By loader = By.xpath("//div[@class='docsila-loader']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void waitForPageLoad() {
        try {
            System.out.println("⏳ Waiting for loader to disappear...");

            // Wait for loader to disappear (max 30 seconds)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
            System.out.println("✓ Loader disappeared - Page loaded!");

        } catch (Exception e) {
            System.out.println("⚠ Loader not found or already gone - Proceeding...");
        }
    }

    public boolean isLogoDisplayed() {
        try {
            System.out.println("🔍 Checking logo immediately...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(docsilaLogo));

            if(logo.isDisplayed()) {
                System.out.println("✓ Logo is present on the page");
                return true;
            }
        } catch (Exception e) {
            System.out.println("✗ Logo is NOT present on the page");
        }
        return false;
    }
}
