package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PDFCotentView extends BasePage {

    // ==================== 1. File List (Left Panel) ====================
    private final By fileListPanel = By.xpath("//div[contains(@class,'file-tree')]");

    // ==================== 2. Collapsed File Indicator (Bar when file list is collapsed) ====================
    private final By collapsedFileIndicator = By.xpath("//div[contains(@class,'collapsed-file-indicator')]");
    private final By collapsedFileNameVertical = By.xpath("//div[contains(@class,'selected-file-name-vertical')]");

    // ==================== 3. Expand Button (on collapsed bar) ====================
    private final By expandFileListButton = By.xpath("//button[@title='Expand file list']");
    private final By expandButtonSvg = By.xpath("//button[@title='Expand file list']//svg");
    private final By expandButtonPolyline = By.xpath("//polyline[@points='9 18 15 12 9 6']");

    // ==================== 4. Collapse Button (on file list panel) ====================
    private final By collapseFileListButton = By.xpath("//button[.//polyline[@points='15 18 9 12 15 6']]");
    private final By collapseButtonPolyline = By.xpath("//polyline[@points='15 18 9 12 15 6']");

    // Alternative collapse button locator for tooltip (in case primary fails)
    private final By collapseButtonAlternative = By.xpath("//button[@title='Collapse file list']");

    public PDFCotentView(WebDriver driver) {
        super(driver);
    }

    // ══════════════════════════════════════════════════════════════
    //  CLICK PDF FILE IN LIST
    // ══════════════════════════════════════════════════════════════

    /**
     * Get the first (top) file name from the file list.
     * This is typically the most recently uploaded file.
     */
    public String getFirstFileName() {
        try {
            System.out.println("📋 Getting first file name from the file list...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Get the first file in the tree
            By firstFileLocator = By.xpath("(//span[contains(@class,'tree-label')])[1]");
            WebElement firstFileElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstFileLocator));

            String fileName = firstFileElement.getText().trim();
            System.out.println("✓ First file name: '" + fileName + "'");
            return fileName;
        } catch (Exception e) {
            System.out.println("✗ Error getting first file name: " + e.getMessage());
            return null;
        }
    }

    /**
     * Click on a specific PDF file in the file list to open it.
     * @param fileName - The name of the file to click (e.g., "Note.pdf")
     */
    public void clickPDFFileInList(String fileName) {
        try {
            System.out.println("📄 Clicking on PDF file: '" + fileName + "' in the file list...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate the file by its name in the file tree
            By fileLocator = By.xpath("//span[contains(@class,'tree-label') and text()='" + fileName + "']");
            WebElement fileElement = wait.until(ExpectedConditions.elementToBeClickable(fileLocator));

            fileElement.click();
            System.out.println("✓ Successfully clicked on PDF file: '" + fileName + "'");
            Thread.sleep(2000); // Wait for PDF to load and UI to transition

        } catch (Exception e) {
            System.out.println("✗ Error clicking PDF file '" + fileName + "': " + e.getMessage());
            throw new RuntimeException("Failed to click PDF file: " + fileName, e);
        }
    }

    /**
     * Click on the first (top) file in the file list.
     * This is typically the most recently uploaded file.
     * @return The name of the clicked file
     */
    public String clickFirstPDFFile() {
        try {
            String fileName = getFirstFileName();
            if (fileName == null || fileName.isEmpty()) {
                throw new RuntimeException("No file found in the file list!");
            }

            System.out.println("📄 Clicking on first PDF file: '" + fileName + "' in the file list...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Click the first file in the tree
            By firstFileLocator = By.xpath("(//span[contains(@class,'tree-label')])[1]");
            WebElement fileElement = wait.until(ExpectedConditions.elementToBeClickable(firstFileLocator));

            fileElement.click();
            System.out.println("✓ Successfully clicked on first PDF file: '" + fileName + "'");
            Thread.sleep(2000); // Wait for PDF to load and UI to transition

            return fileName;
        } catch (Exception e) {
            System.out.println("✗ Error clicking first PDF file: " + e.getMessage());
            throw new RuntimeException("Failed to click first PDF file", e);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  COLLAPSED FILE INDICATOR (BAR) - VALIDATION
    // ══════════════════════════════════════════════════════════════

    /**
     * Check if the collapsed file indicator bar is displayed.
     */
    public boolean isCollapsedFileIndicatorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement indicator = wait.until(ExpectedConditions.visibilityOfElementLocated(collapsedFileIndicator));
            System.out.println("✓ Collapsed file indicator bar is displayed");
            return indicator.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ Collapsed file indicator bar is NOT displayed");
            return false;
        }
    }

    /**
     * Get the tooltip of the collapsed file indicator bar.
     * This should contain the PDF file name.
     */
    public String getCollapsedFileIndicatorTooltip() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement indicator = wait.until(ExpectedConditions.visibilityOfElementLocated(collapsedFileIndicator));
            String tooltip = indicator.getAttribute("title");
            System.out.println("✓ Collapsed file indicator tooltip: '" + tooltip + "'");
            return tooltip;
        } catch (Exception e) {
            System.out.println("✗ Error getting collapsed file indicator tooltip: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validate that the tooltip matches the expected file name.
     */
    public boolean validateCollapsedFileIndicatorTooltip(String expectedFileName) {
        try {
            String tooltip = getCollapsedFileIndicatorTooltip();
            if (tooltip != null && tooltip.equals(expectedFileName)) {
                System.out.println("✓ Tooltip matches expected file name: '" + expectedFileName + "'");
                return true;
            } else {
                System.out.println("✗ Tooltip does NOT match! Expected: '" + expectedFileName + "', Actual: '" + tooltip + "'");
                return false;
            }
        } catch (Exception e) {
            System.out.println("✗ Error validating tooltip: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the file name text displayed vertically on the collapsed bar.
     */
    public String getCollapsedFileNameText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By fileNameSpan = By.xpath("//div[contains(@class,'collapsed-file-indicator')]//span");
            WebElement fileNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fileNameSpan));
            String fileName = fileNameElement.getText().trim();
            System.out.println("✓ Collapsed file name text: '" + fileName + "'");
            return fileName;
        } catch (Exception e) {
            System.out.println("✗ Error getting collapsed file name text: " + e.getMessage());
            return null;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  EXPAND BUTTON (on collapsed bar) - VALIDATION & CLICK
    // ══════════════════════════════════════════════════════════════

    /**
     * Check if the expand button is displayed on the collapsed bar.
     */
    public boolean isExpandButtonDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(expandFileListButton));
            System.out.println("✓ Expand file list button is displayed");
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ Expand file list button is NOT displayed");
            return false;
        }
    }

    /**
     * Get the tooltip of the expand button.
     * Should be "Expand file list"
     */
    public String getExpandButtonTooltip() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(expandFileListButton));
            String tooltip = button.getAttribute("title");
            System.out.println("✓ Expand button tooltip: '" + tooltip + "'");
            return tooltip;
        } catch (Exception e) {
            System.out.println("✗ Error getting expand button tooltip: " + e.getMessage());
            return null;
        }
    }

    /**
     * Click the expand button to re-open the file list panel.
     */
    public void clickExpandButton() {
        try {
            System.out.println("🔽 Clicking Expand file list button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(expandFileListButton));
            button.click();
            System.out.println("✓ Clicked Expand button - File list should now be visible");
            Thread.sleep(1000); // Wait for animation
        } catch (Exception e) {
            System.out.println("✗ Error clicking expand button: " + e.getMessage());
            throw new RuntimeException("Failed to click expand button", e);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  FILE LIST PANEL - VALIDATION
    // ══════════════════════════════════════════════════════════════

    /**
     * Check if the file list panel is displayed.
     */
    public boolean isFileListPanelDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(fileListPanel));
            System.out.println("✓ File list panel is displayed");
            return panel.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ File list panel is NOT displayed");
            return false;
        }
    }

    /**
     * Check if the file list panel is hidden/collapsed.
     */
    public boolean isFileListPanelHidden() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(fileListPanel));
            System.out.println("✓ File list panel is hidden/collapsed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ File list panel is still visible (not hidden)");
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  COLLAPSE BUTTON (on file list panel) - VALIDATION & CLICK
    // ══════════════════════════════════════════════════════════════

    /**
     * Check if the collapse button is displayed on the file list panel.
     */
    public boolean isCollapseButtonDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Try primary locator (polyline-based)
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(collapseFileListButton));
            System.out.println("✓ Collapse file list button is displayed");
            return button.isDisplayed();
        } catch (Exception e1) {
            try {
                // Try alternative locator (title-based)
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(collapseButtonAlternative));
                System.out.println("✓ Collapse file list button is displayed (using alternative locator)");
                return button.isDisplayed();
            } catch (Exception e2) {
                System.out.println("✗ Collapse file list button is NOT displayed");
                return false;
            }
        }
    }

    /**
     * Get the tooltip of the collapse button.
     * Should be "Collapse file list"
     */
    public String getCollapseButtonTooltip() {
        try {
            System.out.println("🔍 Getting collapse button tooltip...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try primary locator (button with polyline)
            try {
                WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(collapseFileListButton));
                String tooltip = button.getAttribute("title");
                System.out.println("✓ Collapse button tooltip: '" + tooltip + "'");
                return tooltip;
            } catch (Exception e1) {
                System.out.println("⚠ Primary locator failed, trying alternative...");

                // Try alternative locator (button with title attribute)
                WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(collapseButtonAlternative));
                String tooltip = button.getAttribute("title");
                System.out.println("✓ Collapse button tooltip (alternative): '" + tooltip + "'");
                return tooltip;
            }
        } catch (Exception e) {
            System.out.println("✗ Error getting collapse button tooltip: " + e.getMessage());
            return null;
        }
    }

    /**
     * Click the collapse button to hide the file list panel.
     */
    public void clickCollapseButton() {
        try {
            System.out.println("🔼 Clicking Collapse file list button...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try primary locator (button with polyline)
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(collapseFileListButton));
                button.click();
                System.out.println("✓ Clicked Collapse button - File list should now be hidden");
                Thread.sleep(1000); // Wait for animation
            } catch (Exception e1) {
                System.out.println("⚠ Primary locator failed, trying alternative...");

                // Try alternative locator (button with title attribute)
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(collapseButtonAlternative));
                button.click();
                System.out.println("✓ Clicked Collapse button (alternative) - File list should now be hidden");
                Thread.sleep(1000); // Wait for animation
            }
        } catch (Exception e) {
            System.out.println("✗ Error clicking collapse button: " + e.getMessage());
            throw new RuntimeException("Failed to click collapse button", e);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  HELPER METHODS
    // ══════════════════════════════════════════════════════════════

    /**
     * Verify that collapsed file indicator is NOT displayed (file list is expanded).
     */
    public boolean isCollapsedFileIndicatorHidden() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(collapsedFileIndicator));
            System.out.println("✓ Collapsed file indicator bar is hidden");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Collapsed file indicator bar is still visible");
            return false;
        }
    }

    /**
     * Dynamic method to click a file by name with tooltip validation.
     */
    public void clickPDFFileAndValidateTooltip(String fileName) {
        clickPDFFileInList(fileName);

        // Wait for collapsed indicator to appear
        if (isCollapsedFileIndicatorDisplayed()) {
            validateCollapsedFileIndicatorTooltip(fileName);
        }
    }
}
