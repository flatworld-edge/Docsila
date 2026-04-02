package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import utilities.FileUploadHelper;

public class PDFViewPage extends BasePage {

    // ==================== 1. Main Container ====================
    private final By mainContainer = By.xpath("//div[contains(@class,'card')]");

    // ==================== 2. LEFT PANEL - Document Header ====================
    private final By documentTitle = By.xpath("//h3[contains(@class,'document-title')]");
    private final By uploadButton = By.xpath("//button[contains(@class,'btn-add-document-small')]");
    private final By hiddenFileInput = By.xpath("//input[@type='file']");

    // ==================== 3. LEFT PANEL - File Tree ====================
    private final By fileTreeContainer = By.xpath("//div[contains(@class,'file-tree')]");
    private final By allFileRows = By.xpath("//div[contains(@class,'tree-item file-item')]");
    private final By fileNames = By.xpath("//span[contains(@class,'tree-label')]");

    // ==================== 4. LEFT PANEL - Status Icons ====================
    private final By statusDots = By.xpath("//span[contains(@class,'status-dot')]");

    // ==================== 5. MIDDLE PANEL - PDF Viewer ====================
    private final By previousPageButton = By.xpath("//button[@title='Previous Page']");
    private final By nextPageButton = By.xpath("//button[@title='Next Page']");
    private final By zoomOutButton = By.xpath("//button[@title='Zoom Out']");
    private final By zoomInButton = By.xpath("//button[@title='Zoom In']");
    private final By rotateButton = By.xpath("//button[@title='Rotate 90° clockwise']");

    // ==================== 6. MIDDLE PANEL - Empty State ====================
    private final By emptyMessage = By.xpath("//p[text()='No document loaded']");

    // ==================== 7. RIGHT PANEL - Truth Values ====================
    private final By selectFileMessage = By.xpath("//p[text()='Select a file to view attributes']");

    // ==================== 8. UPLOAD MODAL Locators ====================
    private final By uploadModalDialog = By.xpath("//div[@role='dialog']");
    private final By uploadModalTitle = By.xpath("//h3[@id='modalTitle']");
    private final By uploadModalMessage = By.xpath("//p[@id='modalMessage']");
    private final By uploadModalFileName = By.xpath("//strong[text()='File:']/following-sibling::span");
    private final By uploadModalCloseButton = By.xpath("//button[@aria-label='Close modal']");
    private final By uploadModalNoButton = By.xpath("//button[normalize-space()='No']");
    private final By uploadModalYesButton = By.xpath("//button[normalize-space()='Yes']");

    // ==================== 9. ERROR MODAL Locators ====================
    private final By errorModalDialog = By.xpath("//div[@role='dialog']");
    private final By errorModalTitle = By.xpath("//h3[@id='modalTitle']");
    private final By errorModalMessage = By.xpath("//p[@id='modalMessage']");
    private final By errorModalCloseButton = By.xpath("//button[@aria-label='Close modal']");
    private final By errorModalOkButton = By.xpath("//button[normalize-space()='OK']");
    private final By duplicateFileMessage = By.xpath("//p[contains(text(),'already exists')]");
    private final By invalidFileTypeMessage = By.xpath("//p[normalize-space()='Please select a PDF file']");
    private final By errorDetailsMessage = By.xpath("//strong[normalize-space()='Details:']/following-sibling::span");

    // ==================== 10. POST-UPLOAD Locators ====================
    private final By firstFileRowName = By.xpath("(//div[contains(@class,'tree-item')])[1]//span[@class='tree-label']");
    private final By firstFileRowStatus = By.xpath("(//div[contains(@class,'tree-item')])[1]//span[contains(@class,'status-dot')]");

    public PDFViewPage(WebDriver driver) {
        super(driver);
    }

    // ══════════════════════════════════════════════════════════════
    //  PAGE LOAD
    // ══════════════════════════════════════════════════════════════

    public void waitForPageLoad() {
        try {
            System.out.println("⏳ Waiting for PDF View page to load...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainContainer));
            System.out.println("✓ PDF View page loaded!");
        } catch (Exception e) {
            System.out.println("⚠ PDF View page container not found - Proceeding...");
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  LEFT PANEL - Document Title
    // ══════════════════════════════════════════════════════════════

    public boolean isDocumentTitleDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(documentTitle));
            System.out.println("✓ Document title is displayed: '" + title.getText() + "'");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Document title is NOT displayed");
            return false;
        }
    }

    public String getDocumentTitleText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(documentTitle));
            String titleText = title.getText().trim();
            System.out.println("Document title text: '" + titleText + "'");
            return titleText;
        } catch (Exception e) {
            System.out.println("✗ Error getting document title text: " + e.getMessage());
            return null;
        }
    }

    public String getDocumentTitleTooltip() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(documentTitle));
            String tooltip = title.getAttribute("title");
            System.out.println("Document title tooltip: '" + tooltip + "'");
            return tooltip;
        } catch (Exception e) {
            System.out.println("✗ Error getting document title tooltip: " + e.getMessage());
            return null;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  LEFT PANEL - Upload Button
    // ══════════════════════════════════════════════════════════════

    public boolean isUploadButtonDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(uploadButton));
            System.out.println("✓ Upload button is displayed");
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ Upload button is NOT displayed");
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  LEFT PANEL - File Tree
    // ══════════════════════════════════════════════════════════════

    public boolean isFileTreeDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement tree = wait.until(ExpectedConditions.visibilityOfElementLocated(fileTreeContainer));
            System.out.println("✓ File tree is displayed");
            return tree.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ File tree is NOT displayed");
            return false;
        }
    }

    public int getFileCount() {
        try {
            List<WebElement> files = driver.findElements(allFileRows);
            System.out.println("Total files in file tree: " + files.size());
            return files.size();
        } catch (Exception e) {
            System.out.println("Error getting file count: " + e.getMessage());
            return 0;
        }
    }

    public List<String> getAllFileNames() {
        List<String> names = new ArrayList<>();
        try {
            List<WebElement> fileLabels = driver.findElements(fileNames);
            for (WebElement label : fileLabels) {
                String name = label.getText().trim();
                if (!name.isEmpty()) {
                    names.add(name);
                }
            }
            System.out.println("✓ Total file names found: " + names.size());
        } catch (Exception e) {
            System.out.println("✗ Error getting file names: " + e.getMessage());
        }
        return names;
    }

    public boolean isFileInTree(String fileName) {
        try {
            System.out.println("🔍 Checking if file '" + fileName + "' exists in file tree...");
            List<String> allFiles = getAllFileNames();
            boolean exists = allFiles.contains(fileName);
            System.out.println(exists ? "   ✓ File found" : "   ✗ File NOT found");
            return exists;
        } catch (Exception e) {
            System.out.println("✗ Error checking if file is in tree: " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  MIDDLE PANEL - Empty State & Buttons
    // ══════════════════════════════════════════════════════════════

    public boolean isNoDocumentLoadedMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyMessage));
            System.out.println("✓ 'No document loaded' message is displayed");
            return msg.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ 'No document loaded' message is NOT displayed");
            return false;
        }
    }

    public boolean areAllViewerButtonsDisabled() {
        System.out.println("\n🔍 Checking if all viewer buttons are disabled...");
        boolean allDisabled = true;

        allDisabled &= isButtonDisabled(previousPageButton, "Previous Page");
        allDisabled &= isButtonDisabled(nextPageButton, "Next Page");
        allDisabled &= isButtonDisabled(zoomInButton, "Zoom In");
        allDisabled &= isButtonDisabled(zoomOutButton, "Zoom Out");
        allDisabled &= isButtonDisabled(rotateButton, "Rotate");

        System.out.println(allDisabled
                ? "✓ All viewer buttons are disabled"
                : "✗ Some viewer buttons are NOT disabled!");
        return allDisabled;
    }

    private boolean isButtonDisabled(By locator, String name) {
        try {
            WebElement button = driver.findElement(locator);
            boolean disabled = !button.isEnabled();
            System.out.println("   " + (disabled ? "✓" : "✗") + " " + name + " button disabled: " + disabled);
            return disabled;
        } catch (Exception e) {
            System.out.println("   ✗ Error checking " + name + " button: " + e.getMessage());
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  RIGHT PANEL - Truth Values
    // ══════════════════════════════════════════════════════════════

    public boolean isSelectFileMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(selectFileMessage));
            System.out.println("✓ 'Select a file to view attributes' message is displayed");
            return msg.isDisplayed();
        } catch (Exception e) {
            System.out.println("✗ 'Select a file to view attributes' message is NOT displayed");
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  FILE INPUT HELPERS
    // ══════════════════════════════════════════════════════════════

    private void resetFileInput() {
        try {
            System.out.println("🔄 Resetting file input...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement fileInput = driver.findElement(hiddenFileInput);
            js.executeScript("arguments[0].value = '';", fileInput);
            System.out.println("✓ File input reset successfully");
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("⚠ Could not reset file input: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  UPLOAD - Initiate Methods
    // ══════════════════════════════════════════════════════════════

    /**
     * Create a unique PDF and send it to the hidden file input.
     * Triggers the "Confirm Upload" modal.
     */
    public String initiateUpload(String baseFileName) {
        try {
            System.out.println("\n🔼 Starting PDF upload process...");
            String uniquePdfPath = FileUploadHelper.createUniquePDF(baseFileName);

            if (!FileUploadHelper.isPDF(uniquePdfPath)) {
                throw new RuntimeException("Only PDF files are allowed! File: " + uniquePdfPath);
            }

            resetFileInput();

            System.out.println("📎 Sending file path to hidden file input...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(hiddenFileInput));
            fileInput.sendKeys(uniquePdfPath);
            System.out.println("✓ File path sent: " + uniquePdfPath);

            Thread.sleep(1000);
            return uniquePdfPath;

        } catch (Exception e) {
            System.out.println("✗ Error initiating upload: " + e.getMessage());
            throw new RuntimeException("Failed to initiate upload", e);
        }
    }

    /**
     * Upload a file by its full absolute path (used for duplicate upload test).
     */
    public String initiateUploadByFullPath(String fullFilePath) {
        try {
            System.out.println("\n🔼 Attempting to upload file by full path...");
            System.out.println("   File path: " + fullFilePath);

            File file = new File(fullFilePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found at: " + fullFilePath);
            }

            resetFileInput();

            System.out.println("📎 Sending file path to hidden file input...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(hiddenFileInput));
            fileInput.sendKeys(fullFilePath);
            System.out.println("✓ File path sent: " + fullFilePath);

            Thread.sleep(1000);
            return fullFilePath;

        } catch (Exception e) {
            System.out.println("✗ Error initiating upload by full path: " + e.getMessage());
            throw new RuntimeException("Failed to initiate upload by full path", e);
        }
    }

    /**
     * Upload a non-PDF file (used for invalid file type error test).
     */
    public String initiateUploadNonPDFFile(String nonPdfFileName) {
        try {
            System.out.println("\n🔼 Attempting to upload NON-PDF file...");
            String nonPdfFilePath = FileUploadHelper.getTestDataFilePath(nonPdfFileName);

            if (FileUploadHelper.isPDF(nonPdfFilePath)) {
                throw new RuntimeException("File is a PDF! Expected non-PDF file: " + nonPdfFilePath);
            }

            resetFileInput();

            System.out.println("📎 Sending NON-PDF file path to hidden file input...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(hiddenFileInput));
            fileInput.sendKeys(nonPdfFilePath);
            System.out.println("✓ Non-PDF file path sent: " + nonPdfFilePath);

            Thread.sleep(1000);
            return nonPdfFilePath;

        } catch (Exception e) {
            System.out.println("✗ Error initiating upload of non-PDF file: " + e.getMessage());
            throw new RuntimeException("Failed to initiate upload of non-PDF file", e);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  UPLOAD MODAL Methods (Confirm Upload dialog)
    // ══════════════════════════════════════════════════════════════

    public boolean isUploadModalDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(uploadModalDialog));
            System.out.println("✓ Upload confirmation modal is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Upload confirmation modal is NOT displayed");
            return false;
        }
    }

    public String getUploadModalTitle() {
        try {
            WebElement title = driver.findElement(uploadModalTitle);
            String text = title.getText().trim();
            System.out.println("Upload modal title: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error getting upload modal title: " + e.getMessage());
            return null;
        }
    }

    public String getUploadModalMessage() {
        try {
            WebElement message = driver.findElement(uploadModalMessage);
            String text = message.getText().trim();
            System.out.println("Upload modal message: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error getting upload modal message: " + e.getMessage());
            return null;
        }
    }

    public String getUploadModalFileName() {
        try {
            WebElement fileNameElement = driver.findElement(uploadModalFileName);
            String text = fileNameElement.getText().trim();
            System.out.println("Upload modal file name: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error getting upload modal file name: " + e.getMessage());
            return null;
        }
    }

    public void clickUploadModalNoButton() {
        try {
            System.out.println("Clicking 'No' button on upload modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement noBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadModalNoButton));
            noBtn.click();
            System.out.println("✓ Clicked 'No' button - Upload cancelled");
            Thread.sleep(500);
            resetFileInput();
        } catch (Exception e) {
            System.out.println("✗ Error clicking No button: " + e.getMessage());
            throw new RuntimeException("Failed to click No button on upload modal", e);
        }
    }

    public void clickUploadModalCloseButton() {
        try {
            System.out.println("Clicking 'Close (X)' button on upload modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadModalCloseButton));
            closeBtn.click();
            System.out.println("✓ Clicked 'Close (X)' button - Upload cancelled");
            Thread.sleep(500);
            resetFileInput();
        } catch (Exception e) {
            System.out.println("✗ Error clicking Close button: " + e.getMessage());
            throw new RuntimeException("Failed to click Close button on upload modal", e);
        }
    }

    /**
     * Click "Yes" on upload modal for a REAL upload.
     * Waits for success popup to disappear, then refreshes page so title/file tree update.
     */
    public void clickUploadModalYesButton() {
        try {
            System.out.println("Clicking 'Yes' button on upload modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadModalYesButton));
            yesBtn.click();
            System.out.println("✓ Clicked 'Yes' button - Upload confirmed!");

            Thread.sleep(1000);
            waitForUploadSuccessConfirmation();
            refreshAndWaitForPageLoad();

        } catch (Exception e) {
            System.out.println("✗ Error clicking Yes button: " + e.getMessage());
            throw new RuntimeException("Failed to click Yes button on upload modal", e);
        }
    }

    /**
     * Click "Yes" on upload modal WITHOUT waiting for success or refreshing.
     * Used for duplicate upload test where clicking Yes triggers an Error modal instead.
     */
    public void clickUploadModalYesButtonOnly() {
        try {
            System.out.println("Clicking 'Yes' button on upload modal (no refresh)...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadModalYesButton));
            yesBtn.click();
            System.out.println("✓ Clicked 'Yes' button");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking Yes button: " + e.getMessage());
            throw new RuntimeException("Failed to click Yes button on upload modal", e);
        }
    }

    public boolean isUploadModalClosed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(uploadModalDialog));
            System.out.println("✓ Upload modal is closed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Upload modal is still visible");
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  UPLOAD SUCCESS HELPERS
    // ══════════════════════════════════════════════════════════════

    private void waitForUploadSuccessConfirmation() {
        try {
            System.out.println("⏳ Waiting for upload success confirmation popup...");

            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(uploadModalMessage));
                System.out.println("✓ Success confirmation popup detected");
            } catch (Exception e) {
                System.out.println("⚠ Success popup not detected (may have already closed) - continuing...");
            }

            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(uploadModalDialog));
                System.out.println("✓ Confirmation popup has disappeared");
            } catch (Exception e) {
                System.out.println("⚠ Modal may still be visible - continuing...");
            }

            Thread.sleep(1000);
            System.out.println("✓ Upload success confirmation wait complete");

        } catch (Exception e) {
            System.out.println("⚠ Error waiting for success confirmation: " + e.getMessage());
        }
    }

    /**
     * Refresh the page and wait for the PDF View page to fully reload.
     * Ensures document title (with file count) and file tree are updated after upload.
     */
    public void refreshAndWaitForPageLoad() {
        try {
            System.out.println("🔄 Refreshing page to ensure updated data...");
            driver.navigate().refresh();
            Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainContainer));

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(documentTitle));
                System.out.println("✓ Page refreshed and document title is visible");
            } catch (Exception e) {
                System.out.println("⚠ Document title not visible after refresh, continuing...");
            }

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(fileTreeContainer));
                System.out.println("✓ File tree is loaded after refresh");
            } catch (Exception e) {
                System.out.println("⚠ File tree not visible after refresh, continuing...");
            }

            Thread.sleep(1000);
            System.out.println("✓ Page refresh complete");
        } catch (Exception e) {
            System.out.println("⚠ Error during page refresh: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  ERROR MODAL Methods (Duplicate & Invalid File Type)
    // ══════════════════════════════════════════════════════════════

    public boolean isErrorModalDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorModalDialog));
            System.out.println("✓ ERROR modal is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ ERROR modal is NOT displayed");
            return false;
        }
    }

    public String getErrorModalTitle() {
        try {
            WebElement title = driver.findElement(errorModalTitle);
            String text = title.getText().trim();
            System.out.println("Error modal title: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error getting error modal title: " + e.getMessage());
            return null;
        }
    }

    public String getErrorModalMessage() {
        try {
            WebElement message = driver.findElement(errorModalMessage);
            String text = message.getText().trim();
            System.out.println("Error modal message: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error getting error modal message: " + e.getMessage());
            return null;
        }
    }

    public boolean isDuplicateFileErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(duplicateFileMessage));
            System.out.println("✓ Duplicate file error message: '" + errorMsg.getText().trim() + "'");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Duplicate file error message NOT displayed");
            return false;
        }
    }

    public boolean isInvalidFileTypeErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(invalidFileTypeMessage));
            System.out.println("✓ Invalid file type error message: '" + errorMsg.getText().trim() + "'");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Invalid file type error message NOT displayed");
            return false;
        }
    }

    public String getErrorDetailsMessage() {
        try {
            WebElement details = driver.findElement(errorDetailsMessage);
            String text = details.getText().trim();
            System.out.println("Error details message: '" + text + "'");
            return text;
        } catch (Exception e) {
            System.out.println("✗ Error details message not found: " + e.getMessage());
            return null;
        }
    }

    public String extractFileNameFromDuplicateError() {
        try {
            String fullMessage = getErrorModalMessage();
            if (fullMessage != null && fullMessage.contains("already exists")) {
                String fileName = fullMessage.replace("already exists", "").trim();
                fileName = fileName.replace("\"", "").trim();
                System.out.println("Extracted file name from error: '" + fileName + "'");
                return fileName;
            }
            System.out.println("⚠ Could not extract file name from message: " + fullMessage);
            return null;
        } catch (Exception e) {
            System.out.println("✗ Error extracting file name: " + e.getMessage());
            return null;
        }
    }

    public void clickErrorModalOkButton() {
        try {
            System.out.println("Clicking 'OK' button on error modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(errorModalOkButton));
            okBtn.click();
            System.out.println("✓ Clicked 'OK' button - Error modal closed");
            Thread.sleep(500);
            resetFileInput();
        } catch (Exception e) {
            System.out.println("✗ Error clicking OK button: " + e.getMessage());
            throw new RuntimeException("Failed to click OK button on error modal", e);
        }
    }

    public void clickErrorModalCloseButton() {
        try {
            System.out.println("Clicking 'Close (X)' button on error modal...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(errorModalCloseButton));
            closeBtn.click();
            System.out.println("✓ Clicked 'Close (X)' button - Error modal closed");
            Thread.sleep(500);
            resetFileInput();
        } catch (Exception e) {
            System.out.println("✗ Error clicking Close button on error modal: " + e.getMessage());
            throw new RuntimeException("Failed to click Close button on error modal", e);
        }
    }

    public boolean isErrorModalClosed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(errorModalDialog));
            System.out.println("✓ Error modal is closed");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Error modal is still visible");
            return false;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  POST-UPLOAD VERIFICATION
    // ══════════════════════════════════════════════════════════════

    /**
     * Extract file count from title or tooltip.
     * e.g. "asset_gift_letter (19 files)" → 19
     * Falls back to tooltip, then to file tree count.
     */
    public int extractFileCountFromTitle() {
        try {
            String titleText = getDocumentTitleText();
            int count = parseFileCountFromText(titleText);
            if (count != -1) return count;

            System.out.println("⚠ Could not extract from title text, trying tooltip...");
            String tooltipText = getDocumentTitleTooltip();
            count = parseFileCountFromText(tooltipText);
            if (count != -1) return count;

            System.out.println("⚠ Could not extract from tooltip either, using file tree count...");
            int fileTreeCount = getFileCount();
            if (fileTreeCount > 0) {
                System.out.println("   Fallback file count from tree: " + fileTreeCount);
                return fileTreeCount;
            }

            return -1;
        } catch (Exception e) {
            System.out.println("✗ Error extracting file count from title: " + e.getMessage());
            return -1;
        }
    }

    private int parseFileCountFromText(String text) {
        try {
            if (text != null && text.contains("(")) {
                String countPart = null;
                if (text.contains("files)")) {
                    countPart = text.substring(text.indexOf("(") + 1, text.indexOf("files)")).trim();
                } else if (text.contains("file)")) {
                    countPart = text.substring(text.indexOf("(") + 1, text.indexOf("file)")).trim();
                }
                if (countPart != null) {
                    int count = Integer.parseInt(countPart.trim());
                    System.out.println("   Extracted file count: " + count + " from text: '" + text + "'");
                    return count;
                }
            }
        } catch (Exception e) {
            System.out.println("   Could not parse file count from: '" + text + "' - " + e.getMessage());
        }
        return -1;
    }

    public String getFirstFileName() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement firstFile = wait.until(ExpectedConditions.visibilityOfElementLocated(firstFileRowName));
            String fileName = firstFile.getText().trim();
            System.out.println("   First file in tree: '" + fileName + "'");
            return fileName;
        } catch (Exception e) {
            System.out.println("✗ Error getting first file name: " + e.getMessage());
            return null;
        }
    }

    public boolean isFirstFileStatusNotStarted() {
        try {
            WebElement statusDot = driver.findElement(firstFileRowStatus);
            String classAttr = statusDot.getAttribute("class");
            boolean isRed = classAttr != null && classAttr.contains("status-not-started");
            System.out.println(isRed
                    ? "   ✓ First file has 'Not Started' (red) status"
                    : "   ✗ First file does NOT have 'Not Started' status. Class: " + classAttr);
            return isRed;
        } catch (Exception e) {
            System.out.println("✗ Error checking first file status: " + e.getMessage());
            return false;
        }
    }

    public String getFirstFileStatusTooltip() {
        try {
            WebElement statusDot = driver.findElement(firstFileRowStatus);
            String tooltip = statusDot.getAttribute("title");
            System.out.println("   First file status tooltip: '" + tooltip + "'");
            return tooltip;
        } catch (Exception e) {
            System.out.println("✗ Error getting first file status tooltip: " + e.getMessage());
            return null;
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  TOOLTIP VALIDATION METHODS
    // ══════════════════════════════════════════════════════════════

    /**
     * Validate Document Title Tooltip
     * XPath: //h3[contains(@class,'document-title')]
     */
    public boolean validateDocumentTitleTooltip() {
        try {
            System.out.println("\n🔍 Validating Document Title Tooltip...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(documentTitle));

            String tooltip = titleElement.getAttribute("title");
            if (tooltip != null && !tooltip.trim().isEmpty()) {
                System.out.println("   ✅ Document Title Tooltip: '" + tooltip + "'");
                return true;
            } else {
                System.out.println("   ✗ Document Title Tooltip is missing or empty!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("   ✗ Error validating Document Title Tooltip: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate all File Item (Document Name) Tooltips
     * XPath: //div[contains(@class,'file-item')]
     */
    public boolean validateAllFileItemTooltips() {
        try {
            System.out.println("\n🔍 Validating File Item (Document Name) Tooltips...");
            By fileItemsLocator = By.xpath("//div[contains(@class,'file-item')]");

            List<WebElement> fileItems = driver.findElements(fileItemsLocator);
            if (fileItems.isEmpty()) {
                System.out.println("   ⚠ No file items found to validate tooltips");
                return false;
            }

            System.out.println("   Found " + fileItems.size() + " file items");
            int tooltipCount = 0;
            int missingTooltipCount = 0;

            for (int i = 0; i < fileItems.size(); i++) {
                WebElement fileItem = fileItems.get(i);
                String tooltip = fileItem.getAttribute("title");

                // Try to get file name from the label inside
                String fileName = "";
                try {
                    WebElement label = fileItem.findElement(By.xpath(".//span[contains(@class,'tree-label')]"));
                    fileName = label.getText().trim();
                } catch (Exception e) {
                    fileName = "File " + (i + 1);
                }

                if (tooltip != null && !tooltip.trim().isEmpty()) {
                    System.out.println("   ✅ File Item #" + (i + 1) + " (" + fileName + ") - Tooltip: '" + tooltip + "'");
                    tooltipCount++;
                } else {
                    System.out.println("   ✗ File Item #" + (i + 1) + " (" + fileName + ") - Tooltip is missing!");
                    missingTooltipCount++;
                }
            }

            System.out.println("\n   📊 Summary: " + tooltipCount + " tooltips present, " + missingTooltipCount + " missing");
            return missingTooltipCount == 0;

        } catch (Exception e) {
            System.out.println("   ✗ Error validating File Item Tooltips: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate all Status Dot Tooltips
     * XPath: //span[contains(@class,'status-dot')]
     */
    public boolean validateAllStatusDotTooltips() {
        try {
            System.out.println("\n🔍 Validating Status Dot Tooltips...");
            By statusDotsLocator = By.xpath("//span[contains(@class,'status-dot')]");

            List<WebElement> statusDots = driver.findElements(statusDotsLocator);
            if (statusDots.isEmpty()) {
                System.out.println("   ⚠ No status dots found to validate tooltips");
                return false;
            }

            System.out.println("   Found " + statusDots.size() + " status dots");
            int tooltipCount = 0;
            int missingTooltipCount = 0;

            for (int i = 0; i < statusDots.size(); i++) {
                WebElement statusDot = statusDots.get(i);
                String tooltip = statusDot.getAttribute("title");
                String statusClass = statusDot.getAttribute("class");

                if (tooltip != null && !tooltip.trim().isEmpty()) {
                    System.out.println("   ✅ Status Dot #" + (i + 1) + " - Tooltip: '" + tooltip + "' (Class: " + statusClass + ")");
                    tooltipCount++;
                } else {
                    System.out.println("   ✗ Status Dot #" + (i + 1) + " - Tooltip is missing! (Class: " + statusClass + ")");
                    missingTooltipCount++;
                }
            }

            System.out.println("\n   📊 Summary: " + tooltipCount + " tooltips present, " + missingTooltipCount + " missing");
            return missingTooltipCount == 0;

        } catch (Exception e) {
            System.out.println("   ✗ Error validating Status Dot Tooltips: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate ALL tooltips on PDF View page (comprehensive check)
     */
    public boolean validateAllTooltips() {
        System.out.println("\n════════════════════════════════════════════════════════");
        System.out.println("   COMPREHENSIVE TOOLTIP VALIDATION");
        System.out.println("════════════════════════════════════════════════════════");

        boolean documentTitleValid = validateDocumentTitleTooltip();
        boolean fileItemsValid = validateAllFileItemTooltips();
        boolean statusDotsValid = validateAllStatusDotTooltips();

        System.out.println("\n════════════════════════════════════════════════════════");
        System.out.println("   TOOLTIP VALIDATION SUMMARY");
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   Document Title Tooltip: " + (documentTitleValid ? "✅ PASS" : "❌ FAIL"));
        System.out.println("   File Item Tooltips:     " + (fileItemsValid ? "✅ PASS" : "❌ FAIL"));
        System.out.println("   Status Dot Tooltips:    " + (statusDotsValid ? "✅ PASS" : "❌ FAIL"));
        System.out.println("════════════════════════════════════════════════════════\n");

        return documentTitleValid && fileItemsValid && statusDotsValid;
    }
}
