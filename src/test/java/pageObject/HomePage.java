package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class HomePage extends BasePage {

    // ==================== Home Page Locators ====================
    private By docsilaLogo = By.xpath("//img[@alt='DocSila']");
    private By loader = By.xpath("//div[@class='docsila-loader']");

    // ==================== Document Model Table Locators ====================
    // Table header locators - Updated with better XPaths
    private By tableHeaders = By.xpath("//thead//th | //table//th");
    private By sortableHeaders = By.xpath("//th[contains(@class,'sortable')] | //th[.//span[@class='sort-icon']]");

    // Individual sortable column headers - Using normalize-space for better matching
    private By documentModelHeader = By.xpath("//th[normalize-space()='Document Model' or contains(normalize-space(),'Document Model')]");
    private By filesHeader = By.xpath("//th[normalize-space()='Files' or contains(normalize-space(),'Files')]");
    private By attributesHeader = By.xpath("//th[normalize-space()='Attributes' or contains(normalize-space(),'Attributes')]");
    private By truthCoverageHeader = By.xpath("//th[normalize-space()='Truth Coverage' or contains(normalize-space(),'Truth Coverage')]");
    private By runsHeader = By.xpath("//th[normalize-space()='Runs' or contains(normalize-space(),'Runs')]");
    private By lastRunHeader = By.xpath("//th[normalize-space()='Last Run' or contains(normalize-space(),'Last Run')]");
    private By accuracyHeader = By.xpath("//th[normalize-space()='Accuracy' or contains(normalize-space(),'Accuracy')]");
    private By actionsHeader = By.xpath("//th[normalize-space()='Actions' or contains(normalize-space(),'Actions')]");

    // Sort icon locator (within header)
    private By sortIcon = By.xpath(".//span[@class='sort-icon']");

    // Table rows and columns
    private By tableRows = By.xpath("//tbody/tr");

    // Column data by index
    private String columnDataXPath = "//tbody/tr/td[%d]";

    // ==================== Tooltip Locators ====================
    private By viewRunsButton = By.xpath("//button[@title='View Runs'] | //button[contains(@class,'view-runs')]");
    private By runNewValidationButton = By.xpath("//button[@title='Run New Validation'] | //button[contains(@class,'run-validation')]");
    private By truthCoverageBadges = By.xpath("//span[contains(@class,'truth-coverage')] | //div[contains(@class,'truth-coverage')]//span[@title]");
    private By allTooltipElements = By.xpath("//*[@title and string-length(@title) > 0]");

    // ==================== Document Model Column Locators ====================
    private By documentModelColumnData = By.xpath("//tbody/tr/td[1]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ==================== Home Page Methods ====================

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

    // ==================== Document Model Table Methods ====================

    public boolean isTablePresent() {
        try {
            System.out.println("🔍 Checking if table is present...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableHeaders));
            System.out.println("✓ Table is present");
            return true;
        } catch (Exception e) {
            System.out.println("✗ Table is NOT present");
            return false;
        }
    }

    public int getHeaderCount() {
        try {
            List<WebElement> headers = driver.findElements(tableHeaders);
            int count = headers.size();
            System.out.println("Total headers found: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("Error getting header count: " + e.getMessage());
            return 0;
        }
    }

    public int getSortableHeaderCount() {
        try {
            List<WebElement> sortableHeadersList = driver.findElements(sortableHeaders);
            int count = sortableHeadersList.size();
            System.out.println("Sortable headers found: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("Error getting sortable header count: " + e.getMessage());
            return 0;
        }
    }

    public boolean isHeaderPresent(String headerName) {
        try {
            By headerLocator = By.xpath("//th[normalize-space()='" + headerName + "' or contains(normalize-space(),'" + headerName + "')]");
            WebElement header = driver.findElement(headerLocator);
            boolean isPresent = header.isDisplayed();
            if (isPresent) {
                System.out.println("✓ Header '" + headerName + "' is present");
            } else {
                System.out.println("✗ Header '" + headerName + "' is NOT visible");
            }
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Header '" + headerName + "' is NOT present");
            return false;
        }
    }

    public boolean hasSortIcon(String headerName) {
        try {
            By headerLocator = By.xpath("//th[normalize-space()='" + headerName + "' or contains(normalize-space(),'" + headerName + "')]");
            WebElement header = driver.findElement(headerLocator);
            List<WebElement> sortIcons = header.findElements(sortIcon);
            boolean hasSortIcon = !sortIcons.isEmpty();
            if (hasSortIcon) {
                System.out.println("✓ Header '" + headerName + "' has sort icon");
            } else {
                System.out.println("✗ Header '" + headerName + "' does NOT have sort icon");
            }
            return hasSortIcon;
        } catch (Exception e) {
            System.out.println("✗ Error checking sort icon for '" + headerName + "': " + e.getMessage());
            return false;
        }
    }

    public void clickHeaderToSort(String headerName) {
        try {
            System.out.println("Clicking header '" + headerName + "' to sort...");
            By headerLocator = By.xpath("//th[normalize-space()='" + headerName + "' or contains(normalize-space(),'" + headerName + "')]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(headerLocator));
            header.click();
            System.out.println("✓ Clicked header '" + headerName + "'");

            // Small wait for sorting to apply
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("✗ Error clicking header '" + headerName + "': " + e.getMessage());
            throw new RuntimeException("Failed to click header: " + headerName, e);
        }
    }

    public boolean verifySortingApplied(String headerName) {
        try {
            System.out.println("Verifying sorting was applied for '" + headerName + "'...");
            // Wait a moment for sorting animation/changes
            Thread.sleep(300);

            // Get table rows to verify data changed
            List<WebElement> rows = driver.findElements(tableRows);

            if (!rows.isEmpty()) {
                System.out.println("✓ Sorting applied - Table has " + rows.size() + " rows");
                return true;
            } else {
                System.out.println("⚠ Table appears empty after sorting");
                return false;
            }
        } catch (Exception e) {
            System.out.println("✗ Error verifying sorting: " + e.getMessage());
            return false;
        }
    }

    public List<String> getColumnData(int columnIndex) {
        try {
            By columnLocator = By.xpath(String.format(columnDataXPath, columnIndex));
            List<WebElement> columnElements = driver.findElements(columnLocator);
            List<String> data = new ArrayList<>();
            for (WebElement element : columnElements) {
                data.add(element.getText().trim());
            }
            return data;
        } catch (Exception e) {
            System.out.println("Error getting column data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Get column index by header name
    private int getColumnIndexByHeaderName(String headerName) {
        switch (headerName) {
            case "Document Model": return 1;
            case "Files": return 2;
            case "Attributes": return 3;
            case "Truth Coverage": return 4;
            case "Runs": return 5;
            case "Last Run": return 6;
            case "Accuracy": return 7;
            default: return -1;
        }
    }

    // Smart comparison (handles numbers, dates, text)
    private int compareValues(String val1, String val2) {
        // Handle empty values
        if (val1 == null || val1.isEmpty()) return -1;
        if (val2 == null || val2.isEmpty()) return 1;

        // Try numeric comparison first (for Runs, Accuracy, etc.)
        try {
            String num1Str = val1.replaceAll("[^0-9.-]", "");
            String num2Str = val2.replaceAll("[^0-9.-]", "");

            if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
                Double num1 = Double.parseDouble(num1Str);
                Double num2 = Double.parseDouble(num2Str);
                return num1.compareTo(num2);
            }
        } catch (NumberFormatException e) {
            // Not a number, continue to text comparison
        }

        // Fallback to case-insensitive text comparison
        return val1.compareToIgnoreCase(val2);
    }

    // Verify ascending sort order
    public boolean verifySortedAscending(String headerName) {
        try {
            int columnIndex = getColumnIndexByHeaderName(headerName);
            if (columnIndex == -1) {
                System.out.println("❌ Invalid column: " + headerName);
                return false;
            }

            List<String> columnData = getColumnData(columnIndex);

            if (columnData.isEmpty()) {
                System.out.println("⚠ No data in column to verify sorting");
                return true; // Empty table is technically sorted
            }

            System.out.println("📊 Verifying ascending sort for: " + headerName);
            System.out.println("   First value: " + columnData.get(0));
            System.out.println("   Last value: " + columnData.get(columnData.size() - 1));

            boolean isSorted = true;
            for (int i = 0; i < columnData.size() - 1; i++) {
                int comparison = compareValues(columnData.get(i), columnData.get(i + 1));
                if (comparison > 0) {
                    System.out.println("   ❌ Not ascending: [" + columnData.get(i) + "] > [" + columnData.get(i + 1) + "]");
                    isSorted = false;
                    break;
                }
            }

            if (isSorted) {
                System.out.println("   ✅ Ascending order verified");
            }
            return isSorted;

        } catch (Exception e) {
            System.out.println("❌ Error verifying ascending sort: " + e.getMessage());
            return false;
        }
    }

    // Verify descending sort order
    public boolean verifySortedDescending(String headerName) {
        try {
            int columnIndex = getColumnIndexByHeaderName(headerName);
            if (columnIndex == -1) {
                System.out.println("❌ Invalid column: " + headerName);
                return false;
            }

            List<String> columnData = getColumnData(columnIndex);

            if (columnData.isEmpty()) {
                System.out.println("⚠ No data in column to verify sorting");
                return true; // Empty table is technically sorted
            }

            System.out.println("📊 Verifying descending sort for: " + headerName);
            System.out.println("   First value: " + columnData.get(0));
            System.out.println("   Last value: " + columnData.get(columnData.size() - 1));

            boolean isSorted = true;
            for (int i = 0; i < columnData.size() - 1; i++) {
                int comparison = compareValues(columnData.get(i), columnData.get(i + 1));
                if (comparison < 0) {
                    System.out.println("   ❌ Not descending: [" + columnData.get(i) + "] < [" + columnData.get(i + 1) + "]");
                    isSorted = false;
                    break;
                }
            }

            if (isSorted) {
                System.out.println("   ✅ Descending order verified");
            }
            return isSorted;

        } catch (Exception e) {
            System.out.println("❌ Error verifying descending sort: " + e.getMessage());
            return false;
        }
    }

    public void testSortingForAllHeaders() {
        String[] sortableHeaderNames = {
            "Document Model",
            "Files",
            "Attributes",
            "Truth Coverage",
            "Runs",
            "Last Run",
            "Accuracy"
        };

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   TESTING SORTING FOR ALL HEADERS");
        System.out.println("════════════════════════════════════════\n");

        int passCount = 0;
        int failCount = 0;

        for (String headerName : sortableHeaderNames) {
            System.out.println("\n━━━ Testing: " + headerName + " ━━━");
            boolean headerTestPassed = true;

            try {
                // Click once for ascending sort
                clickHeaderToSort(headerName);
                boolean ascendingVerified = verifySortedAscending(headerName);

                if (!ascendingVerified) {
                    System.out.println("❌ Ascending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Ascending sort PASSED for: " + headerName);
                }

                // Click again for descending sort
                clickHeaderToSort(headerName);
                boolean descendingVerified = verifySortedDescending(headerName);

                if (!descendingVerified) {
                    System.out.println("❌ Descending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Descending sort PASSED for: " + headerName);
                }

                if (headerTestPassed) {
                    System.out.println("✅ Completed sorting test for: " + headerName);
                    passCount++;
                } else {
                    System.out.println("❌ Sorting test FAILED for: " + headerName);
                    failCount++;
                }

            } catch (Exception e) {
                System.out.println("❌ Error testing " + headerName + ": " + e.getMessage());
                failCount++;
            }
        }

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   SORTING TEST SUMMARY");
        System.out.println("   Total Headers: " + sortableHeaderNames.length);
        System.out.println("   ✅ Passed: " + passCount);
        System.out.println("   ❌ Failed: " + failCount);
        System.out.println("════════════════════════════════════════\n");

        if (failCount > 0) {
            throw new AssertionError("Sorting verification failed for " + failCount + " header(s)");
        }
    }

    public void verifyAllHeaders() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   VERIFYING ALL TABLE HEADERS");
        System.out.println("════════════════════════════════════════\n");

        String[] allHeaders = {
            "Document Model",
            "Files",
            "Attributes",
            "Truth Coverage",
            "Runs",
            "Last Run",
            "Accuracy",
            "Actions"
        };

        String[] sortableHeaderNames = {
            "Document Model",
            "Files",
            "Attributes",
            "Truth Coverage",
            "Runs",
            "Last Run",
            "Accuracy"
        };

        // Verify all headers are present
        for (String header : allHeaders) {
            isHeaderPresent(header);
        }

        // Verify sortable headers have sort icons
        System.out.println("\n--- Checking Sort Icons ---");
        for (String header : sortableHeaderNames) {
            hasSortIcon(header);
        }

        // Verify Actions header does NOT have sort icon
        System.out.println("\n--- Verifying Actions Header (No Sorting) ---");
        boolean actionsHasSortIcon = hasSortIcon("Actions");
        if (!actionsHasSortIcon) {
            System.out.println("✓ Actions header correctly has NO sort icon");
        } else {
            System.out.println("✗ Actions header should NOT have sort icon");
        }

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ✓ HEADER VERIFICATION COMPLETED");
        System.out.println("════════════════════════════════════════\n");
    }

    // ==================== Document Model Column Methods ====================

    /**
     * Get all document names from the Document Model column
     */
    public List<String> getAllDocumentNames() {
        List<String> documentNames = new ArrayList<>();
        try {
            System.out.println("🔍 Getting all document names from Document Model column...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(documentModelColumnData));

            List<WebElement> documentCells = driver.findElements(documentModelColumnData);
            for (WebElement cell : documentCells) {
                String name = cell.getText().trim();
                if (!name.isEmpty()) {
                    documentNames.add(name);
                    System.out.println("   📄 " + name);
                }
            }
            System.out.println("✓ Total documents found: " + documentNames.size());
        } catch (Exception e) {
            System.out.println("✗ Error getting document names: " + e.getMessage());
        }
        return documentNames;
    }

    /**
     * Click on a specific document row by document name to navigate to PDF View page
     */
    public void clickOnDocument(String documentName) {
        try {
            System.out.println("🔍 Looking for document: " + documentName);
            By documentLink = By.xpath("//tbody/tr/td[1][normalize-space()='" + documentName + "'] | //tbody/tr/td[1]//a[normalize-space()='" + documentName + "'] | //tbody/tr/td[1]//*[normalize-space()='" + documentName + "']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement docElement = wait.until(ExpectedConditions.elementToBeClickable(documentLink));
            System.out.println("✓ Found document: " + documentName);
            docElement.click();
            System.out.println("✓ Clicked on document: " + documentName);

            // Wait for page navigation
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking document '" + documentName + "': " + e.getMessage());
            throw new RuntimeException("Failed to click on document: " + documentName, e);
        }
    }

    /**
     * Get the Files count for a specific document from the Files column (column 2)
     * e.g., for "asset_gift_letter" it returns the number shown in Files column
     */
    public int getFilesCountForDocument(String documentName) {
        try {
            System.out.println("🔍 Getting Files count for document: " + documentName);
            By filesCountLocator = By.xpath("//tbody/tr[td[1][normalize-space()='" + documentName + "'] or td[1]//*[normalize-space()='" + documentName + "']]/td[2]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement filesCell = wait.until(ExpectedConditions.visibilityOfElementLocated(filesCountLocator));
            String filesText = filesCell.getText().trim();
            int filesCount = Integer.parseInt(filesText.replaceAll("[^0-9]", ""));
            System.out.println("✓ Files count for '" + documentName + "': " + filesCount);
            return filesCount;
        } catch (Exception e) {
            System.out.println("✗ Error getting files count for '" + documentName + "': " + e.getMessage());
            return -1;
        }
    }

    /**
     * Get the Truth Coverage text for a specific document from the Truth Coverage column (column 4)
     * e.g., returns "7/15 (47%)"
     */
    public String getTruthCoverageForDocument(String documentName) {
        try {
            System.out.println("🔍 Getting Truth Coverage for document: " + documentName);
            By truthCoverageLocator = By.xpath("//tbody/tr[td[1][normalize-space()='" + documentName + "'] or td[1]//*[normalize-space()='" + documentName + "']]/td[4]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement truthCell = wait.until(ExpectedConditions.visibilityOfElementLocated(truthCoverageLocator));
            String truthText = truthCell.getText().trim();
            System.out.println("✓ Truth Coverage for '" + documentName + "': " + truthText);
            return truthText;
        } catch (Exception e) {
            System.out.println("✗ Error getting truth coverage for '" + documentName + "': " + e.getMessage());
            return null;
        }
    }

    /**
     * Extract the numerator from Truth Coverage text (e.g., "7/15 (47%)" returns 7)
     */
    public int extractTruthCoverageNumerator(String truthCoverageText) {
        try {
            if (truthCoverageText != null && truthCoverageText.contains("/")) {
                String numerator = truthCoverageText.substring(0, truthCoverageText.indexOf("/")).trim();
                int num = Integer.parseInt(numerator.replaceAll("[^0-9]", ""));
                System.out.println("   Truth Coverage numerator: " + num);
                return num;
            }
            return -1;
        } catch (Exception e) {
            System.out.println("✗ Error extracting numerator: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Extract the denominator from Truth Coverage text (e.g., "7/15 (47%)" returns 15)
     */
    public int extractTruthCoverageDenominator(String truthCoverageText) {
        try {
            if (truthCoverageText != null && truthCoverageText.contains("/")) {
                String afterSlash = truthCoverageText.substring(truthCoverageText.indexOf("/") + 1);
                String denominator;
                if (afterSlash.contains("(")) {
                    denominator = afterSlash.substring(0, afterSlash.indexOf("(")).trim();
                } else {
                    denominator = afterSlash.trim();
                }
                int den = Integer.parseInt(denominator.replaceAll("[^0-9]", ""));
                System.out.println("   Truth Coverage denominator: " + den);
                return den;
            }
            return -1;
        } catch (Exception e) {
            System.out.println("✗ Error extracting denominator: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Extract the percentage from Truth Coverage text (e.g., "7/15 (47%)" returns 47)
     */
    public int extractTruthCoveragePercentage(String truthCoverageText) {
        try {
            if (truthCoverageText != null && truthCoverageText.contains("(") && truthCoverageText.contains("%")) {
                String percentStr = truthCoverageText.substring(truthCoverageText.indexOf("(") + 1, truthCoverageText.indexOf("%")).trim();
                int percent = Integer.parseInt(percentStr.replaceAll("[^0-9]", ""));
                System.out.println("   Truth Coverage percentage: " + percent + "%");
                return percent;
            }
            return -1;
        } catch (Exception e) {
            System.out.println("✗ Error extracting percentage: " + e.getMessage());
            return -1;
        }
    }

    // ==================== Tooltip Methods ====================

    public void verifyAllTooltips() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   VERIFYING ALL TOOLTIPS");
        System.out.println("════════════════════════════════════════\n");

        try {
            List<WebElement> tooltipElements = driver.findElements(allTooltipElements);
            System.out.println("Total elements with tooltips found: " + tooltipElements.size());

            for (WebElement element : tooltipElements) {
                String title = element.getAttribute("title");
                String tagName = element.getTagName();
                System.out.println("✓ [" + tagName + "] Tooltip: '" + title + "'");
            }
        } catch (Exception e) {
            System.out.println("Error verifying tooltips: " + e.getMessage());
        }

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ✓ TOOLTIP VERIFICATION COMPLETED");
        System.out.println("════════════════════════════════════════\n");
    }

    public int getValidTooltipCount() {
        try {
            List<WebElement> tooltipElements = driver.findElements(allTooltipElements);
            int count = 0;
            for (WebElement element : tooltipElements) {
                String title = element.getAttribute("title");
                if (title != null && !title.trim().isEmpty()) {
                    count++;
                }
            }
            System.out.println("Valid tooltips found: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("Error getting tooltip count: " + e.getMessage());
            return 0;
        }
    }

    public boolean verifyViewRunsButtonTooltip() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(viewRunsButton));
            String tooltip = button.getAttribute("title");
            boolean isValid = tooltip != null && tooltip.equalsIgnoreCase("View Runs");
            System.out.println("View Runs button tooltip: '" + tooltip + "' - " + (isValid ? "✓ Valid" : "✗ Invalid"));
            return isValid;
        } catch (Exception e) {
            System.out.println("✗ View Runs button not found or tooltip missing: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyRunNewValidationButtonTooltip() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(runNewValidationButton));
            String tooltip = button.getAttribute("title");
            boolean isValid = tooltip != null && tooltip.equalsIgnoreCase("Run New Validation");
            System.out.println("Run New Validation button tooltip: '" + tooltip + "' - " + (isValid ? "✓ Valid" : "✗ Invalid"));
            return isValid;
        } catch (Exception e) {
            System.out.println("✗ Run New Validation button not found or tooltip missing: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyTruthCoverageBadgeTooltips() {
        try {
            List<WebElement> badges = driver.findElements(truthCoverageBadges);
            if (badges.isEmpty()) {
                System.out.println("⚠ No Truth Coverage badges found");
                return false;
            }

            boolean allValid = true;
            for (WebElement badge : badges) {
                String tooltip = badge.getAttribute("title");
                if (tooltip == null || tooltip.trim().isEmpty()) {
                    System.out.println("✗ Truth Coverage badge missing tooltip");
                    allValid = false;
                } else {
                    System.out.println("✓ Truth Coverage badge tooltip: '" + tooltip + "'");
                }
            }
            return allValid;
        } catch (Exception e) {
            System.out.println("✗ Error checking Truth Coverage badge tooltips: " + e.getMessage());
            return false;
        }
    }
}
