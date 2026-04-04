package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class ValidationDetailsPage extends BasePage {

    // ==================== Validation Details Page Locators ====================
    private By loader = By.xpath("//div[@class='docsila-loader']");

    // Validation Details Tab/Button
    private By validationDetailsTab = By.xpath("//button[@type='button' and contains(@class,'matrix-view-btn') and contains(text(),'Validation Details')]");

    // Alternative locator if above doesn't work
    private By validationDetailsTabAlt = By.xpath("//button[normalize-space()='Validation Details']");

    // Validation Results Tab
    private By validationResultsTab = By.xpath("//button[contains(text(),'Validation Results')] | //*[contains(text(),'Validation Results')]");

    // Page title or header
    private By pageHeader = By.xpath("//h1 | //h2 | //*[contains(@class,'page-title')]");

    // ==================== Table Sorting Locators ====================
    private By tableHeaders = By.xpath("//thead//th | //table//th");
    private By sortableHeaders = By.xpath("//th[contains(@class,'sortable')]");
    private By sortIcon = By.xpath(".//span[@class='sort-icon']");
    private By tableRows = By.xpath("//tbody/tr");
    private String columnDataXPath = "//tbody/tr/td[%d]";

    // ==================== Filter Locators ====================
    private By fileNameFilterTextbox = By.xpath("//input[@placeholder='Filter by file name...']");
    private By fieldFilterTextbox = By.xpath("//input[@placeholder='Filter by field...']");
    private By expectedValueFilterTextbox = By.xpath("//input[@placeholder='Filter by expected value...']");
    private By actualValueFilterTextbox = By.xpath("//input[@placeholder='Filter by actual value...']");
    private By matchDropdown = By.xpath("//select[@class='filter-select ng-pristine ng-valid ng-touched'] | //select[contains(@class,'filter-select')]");
    private By clearFiltersButton = By.xpath("//button[@class='btn-clear-filters'] | //button[contains(text(),'Clear Filters')]");
    private By noResultsMessage = By.xpath("//td[@class='empty-row']");
    private By visibleRows = By.xpath("//tbody/tr[not(contains(@style,'display: none'))]");

    public ValidationDetailsPage(WebDriver driver) {
        super(driver);
    }

    // ==================== Validation Details Page Methods ====================

    /**
     * Wait for the Validation Details page to load
     */
    public void waitForPageLoad() {
        try {
            System.out.println("⏳ Waiting for Validation Details page to load...");

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
            System.out.println("✓ Validation Details page loaded");

        } catch (Exception e) {
            System.out.println("⚠ Error waiting for page load: " + e.getMessage());
        }
    }

    /**
     * Click on the Validation Details Tab
     */
    public void clickValidationDetailsTab() {
        try {
            System.out.println("🔍 Clicking Validation Details tab...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Try primary locator first
            WebElement tab;
            try {
                tab = wait.until(ExpectedConditions.elementToBeClickable(validationDetailsTab));
            } catch (Exception e) {
                System.out.println("⚠ Primary locator failed, trying alternative...");
                tab = wait.until(ExpectedConditions.elementToBeClickable(validationDetailsTabAlt));
            }

            tab.click();
            System.out.println("✓ Clicked Validation Details tab");

            // Wait for page to load
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking Validation Details tab: " + e.getMessage());
            throw new RuntimeException("Failed to click Validation Details tab", e);
        }
    }

    /**
     * Verify that the Validation Details tab is displayed
     */
    public boolean isValidationDetailsTabDisplayed() {
        try {
            System.out.println("🔍 Verifying Validation Details tab is displayed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement tab;
            try {
                tab = wait.until(ExpectedConditions.visibilityOfElementLocated(validationDetailsTab));
            } catch (Exception e) {
                tab = wait.until(ExpectedConditions.visibilityOfElementLocated(validationDetailsTabAlt));
            }

            boolean isDisplayed = tab.isDisplayed();
            System.out.println(isDisplayed ? "✓ Validation Details tab is displayed" : "✗ Validation Details tab is NOT displayed");
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("✗ Validation Details tab is NOT displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify that the Validation Results tab is displayed
     */
    public boolean isValidationResultsTabDisplayed() {
        try {
            System.out.println("🔍 Verifying Validation Results tab is displayed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(validationResultsTab));
            boolean isDisplayed = tab.isDisplayed();
            System.out.println(isDisplayed ? "✓ Validation Results tab is displayed" : "✗ Validation Results tab is NOT displayed");
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("✗ Validation Results tab is NOT displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click on the Validation Results tab
     */
    public void clickValidationResultsTab() {
        try {
            System.out.println("🔍 Clicking Validation Results tab...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(validationResultsTab));
            tab.click();
            System.out.println("✓ Clicked Validation Results tab");

            // Wait for content to load
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("✗ Error clicking Validation Results tab: " + e.getMessage());
            throw new RuntimeException("Failed to click Validation Results tab", e);
        }
    }

    /**
     * Verify that the page is loaded by checking for page elements
     */
    public boolean isPageDisplayed() {
        try {
            System.out.println("��� Verifying Validation Details page is displayed...");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Check if either Validation Details or Validation Results tab is visible
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(validationDetailsTab));
                return element.isDisplayed();
            } catch (Exception e) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(validationResultsTab));
                return element.isDisplayed();
            }
        } catch (Exception e) {
            System.out.println("✗ Validation page is NOT displayed: " + e.getMessage());
            return false;
        }
    }

    // ==================== Table Sorting Methods ====================

    /**
     * Get the count of all table headers
     */
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

    /**
     * Get the count of sortable headers
     */
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

    /**
     * Check if a specific header is present
     */
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

    /**
     * Check if a header has a sort icon
     */
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

    /**
     * Click on a header to sort
     */
    public void clickHeaderToSort(String headerName) {
        try {
            System.out.println("Clicking header '" + headerName + "' to sort...");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement header = null;

            // Strategy 1: Try exact text match
            try {
                System.out.println("   🔍 Strategy 1: Trying exact text match...");
                By locator1 = By.xpath("//th[normalize-space()='" + headerName + "']");
                header = wait.until(ExpectedConditions.elementToBeClickable(locator1));
                System.out.println("   ✓ Found with Strategy 1");
            } catch (Exception e1) {
                System.out.println("   ✗ Strategy 1 failed");

                // Strategy 2: Try contains text
                try {
                    System.out.println("   🔍 Strategy 2: Trying contains text...");
                    By locator2 = By.xpath("//th[contains(normalize-space(), '" + headerName + "')]");
                    header = wait.until(ExpectedConditions.elementToBeClickable(locator2));
                    System.out.println("   ✓ Found with Strategy 2");
                } catch (Exception e2) {
                    System.out.println("   ✗ Strategy 2 failed");

                    // Strategy 3: Try Angular Material table structure
                    try {
                        System.out.println("   🔍 Strategy 3: Trying Angular Material table...");
                        By locator3 = By.xpath("//mat-header-cell[contains(., '" + headerName + "')] | //th[@mat-header-cell and contains(., '" + headerName + "')]");
                        header = wait.until(ExpectedConditions.elementToBeClickable(locator3));
                        System.out.println("   ✓ Found with Strategy 3");
                    } catch (Exception e3) {
                        System.out.println("   ✗ Strategy 3 failed");

                        // Strategy 4: Try any th with the text anywhere
                        try {
                            System.out.println("   🔍 Strategy 4: Trying flexible search...");
                            By locator4 = By.xpath("//thead//th[contains(text(), '" + headerName + "')] | //table//th[contains(text(), '" + headerName + "')]");
                            header = wait.until(ExpectedConditions.elementToBeClickable(locator4));
                            System.out.println("   ✓ Found with Strategy 4");
                        } catch (Exception e4) {
                            System.out.println("   ✗ All strategies failed!");
                            // Debug: Show what headers exist
                            debugPrintAllHeaders();
                            throw new RuntimeException("Could not find header: " + headerName);
                        }
                    }
                }
            }

            if (header != null) {
                System.out.println("   🔍 DEBUG: Found header element - Class: '" + header.getAttribute("class") + "'");
                System.out.println("   🔍 DEBUG: Attempting click...");

                // Try regular click first
                try {
                    header.click();
                    System.out.println("   ✓ Clicked with regular click");
                } catch (Exception clickEx) {
                    // Try JavaScript click as fallback
                    System.out.println("   ⚠ Regular click failed, trying JavaScript click...");
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", header);
                    System.out.println("   ✓ Clicked with JavaScript click");
                }

                System.out.println("✓ Successfully clicked header '" + headerName + "'");

                // Wait for table to re-render after sorting
                Thread.sleep(2000);

                // Wait for any loader to disappear
                try {
                    WebDriverWait loaderWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                    loaderWait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
                    System.out.println("   ✓ Sorting loader disappeared");
                } catch (Exception e) {
                    System.out.println("   ⚠ No loader appeared (normal for sorting)");
                }
            }

        } catch (Exception e) {
            System.out.println("✗ Error clicking header '" + headerName + "': " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to click header: " + headerName, e);
        }
    }

    /**
     * Debug method to print all headers found on the page
     */
    private void debugPrintAllHeaders() {
        System.out.println("\n   🔍 DEBUG: Listing ALL TH elements found on page:");
        try {
            List<WebElement> allHeaders = driver.findElements(By.xpath("//th | //mat-header-cell"));
            System.out.println("   📋 Found " + allHeaders.size() + " header elements:");
            for (int i = 0; i < allHeaders.size(); i++) {
                String text = allHeaders.get(i).getText().trim();
                String tagName = allHeaders.get(i).getTagName();
                String className = allHeaders.get(i).getAttribute("class");
                boolean isDisplayed = allHeaders.get(i).isDisplayed();
                System.out.println("      [" + i + "] Tag: <" + tagName + ">, Text: '" + text + "', Class: '" + className + "', Visible: " + isDisplayed);
            }
        } catch (Exception ex) {
            System.out.println("   ❌ Could not retrieve header elements: " + ex.getMessage());
        }
        System.out.println();
    }

    /**
     * Get column data by index
     */
    public List<String> getColumnData(int columnIndex) {
        try {
            By columnLocator = By.xpath(String.format(columnDataXPath, columnIndex));

            System.out.println("   🔍 DEBUG: Looking for column data with XPath: " + String.format(columnDataXPath, columnIndex));

            // Wait for table rows to be present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(columnLocator));
            } catch (Exception e) {
                System.out.println("   ⚠ No elements found with XPath, trying direct find...");
            }

            List<WebElement> columnElements = driver.findElements(columnLocator);
            List<String> data = new ArrayList<>();

            System.out.println("   🔍 DEBUG: Found " + columnElements.size() + " elements");

            for (int i = 0; i < columnElements.size(); i++) {
                WebElement element = columnElements.get(i);
                String text = element.getText().trim();
                data.add(text);

                // Print first 3 values for debugging
                if (i < 3) {
                    System.out.println("   🔍 DEBUG: Row " + i + " = '" + text + "'");
                }
            }

            System.out.println("   ✓ Retrieved " + data.size() + " rows from column " + columnIndex);
            return data;
        } catch (Exception e) {
            System.out.println("   ❌ Error getting column data for index " + columnIndex + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get column index by header name for Validation Details table
     */
    private int getColumnIndexByHeaderName(String headerName) {
        switch (headerName) {
            case "File Name": return 1;
            case "Field": return 2;
            case "Expected Value": return 3;
            case "Actual Value": return 4;
            case "Match": return 5;
            case "Confidence": return 6;
            default: return -1;
        }
    }

    /**
     * Smart comparison for sorting validation (handles numbers, dates, text)
     */
    private int compareValues(String val1, String val2) {
        // Handle empty values
        if (val1 == null || val1.isEmpty()) return -1;
        if (val2 == null || val2.isEmpty()) return 1;

        // Try numeric comparison first (for Confidence, etc.)
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

    /**
     * Verify ascending sort order
     */
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
            System.out.println("   Total rows: " + columnData.size());
            System.out.println("   First value: '" + columnData.get(0) + "'");
            System.out.println("   Last value: '" + columnData.get(columnData.size() - 1) + "'");

            // Debug: Print first 5 values to see actual data
            if (columnData.size() > 0) {
                System.out.println("   First 5 values:");
                for (int i = 0; i < Math.min(5, columnData.size()); i++) {
                    System.out.println("      [" + i + "] = '" + columnData.get(i) + "'");
                }
            }

            boolean isSorted = true;
            for (int i = 0; i < columnData.size() - 1; i++) {
                int comparison = compareValues(columnData.get(i), columnData.get(i + 1));
                if (comparison > 0) {
                    System.out.println("   ❌ Not ascending at index " + i + ": [" + columnData.get(i) + "] > [" + columnData.get(i + 1) + "]");
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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verify descending sort order
     */
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
            System.out.println("   Total rows: " + columnData.size());
            System.out.println("   First value: '" + columnData.get(0) + "'");
            System.out.println("   Last value: '" + columnData.get(columnData.size() - 1) + "'");

            // Debug: Print first 5 values to see actual data
            if (columnData.size() > 0) {
                System.out.println("   First 5 values:");
                for (int i = 0; i < Math.min(5, columnData.size()); i++) {
                    System.out.println("      [" + i + "] = '" + columnData.get(i) + "'");
                }
            }

            boolean isSorted = true;
            for (int i = 0; i < columnData.size() - 1; i++) {
                int comparison = compareValues(columnData.get(i), columnData.get(i + 1));
                if (comparison < 0) {
                    System.out.println("   ❌ Not descending at index " + i + ": [" + columnData.get(i) + "] < [" + columnData.get(i + 1) + "]");
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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Test sorting for all sortable headers in Validation Details table
     * This method captures data BEFORE and AFTER each sort to validate proper sorting
     */
    public void testSortingForAllHeaders() {
        String[] sortableHeaderNames = {
            "File Name",
            "Field",
            "Expected Value",
            "Actual Value",
            "Match",
            "Confidence"
        };

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TESTING SORTING FOR ALL HEADERS WITH DATA CAPTURE");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        int passCount = 0;
        int failCount = 0;

        for (String headerName : sortableHeaderNames) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("   TESTING COLUMN: " + headerName);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            boolean headerTestPassed = true;

            try {
                int columnIndex = getColumnIndexByHeaderName(headerName);

                // ═══════════════════════════════════════════════════════════
                //  CAPTURE DATA BEFORE SORTING
                // ═══════════════════════════════════════════════════════════

                System.out.println("📊 STEP 1: Capturing data BEFORE sorting...");
                List<String> dataBeforeSort = getColumnData(columnIndex);

                if (dataBeforeSort.isEmpty()) {
                    System.out.println("⚠ No data found in column - skipping");
                    continue;
                }

                System.out.println("✓ Captured " + dataBeforeSort.size() + " rows");
                System.out.println("\n📋 DATA BEFORE SORTING (First 10 rows):");
                for (int i = 0; i < Math.min(10, dataBeforeSort.size()); i++) {
                    System.out.println("   [" + i + "] " + dataBeforeSort.get(i));
                }
                if (dataBeforeSort.size() > 10) {
                    System.out.println("   ... and " + (dataBeforeSort.size() - 10) + " more rows");
                }

                // ═══════════════════════════════════════════════════════════
                //  TEST ASCENDING SORT
                // ═══════════════════════════════════════════════════════════

                System.out.println("\n📊 STEP 2: Testing ASCENDING sort...");
                clickHeaderToSort(headerName);

                List<String> dataAfterAscending = getColumnData(columnIndex);

                System.out.println("\n📋 DATA AFTER ASCENDING SORT (First 10 rows):");
                for (int i = 0; i < Math.min(10, dataAfterAscending.size()); i++) {
                    System.out.println("   [" + i + "] " + dataAfterAscending.get(i));
                }
                if (dataAfterAscending.size() > 10) {
                    System.out.println("   ... and " + (dataAfterAscending.size() - 10) + " more rows");
                }

                // Verify data changed (unless already sorted)
                boolean dataChanged = !dataBeforeSort.equals(dataAfterAscending);
                if (dataChanged) {
                    System.out.println("\n✓ Data order CHANGED after clicking sort");
                } else {
                    System.out.println("\n⚠ Data order same (may have been pre-sorted ascending)");
                }

                boolean ascendingVerified = verifySortedAscending(headerName);

                if (!ascendingVerified) {
                    System.out.println("❌ Ascending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Ascending sort PASSED for: " + headerName);
                }

                // ═══════════════════════════════════════════════════════════
                //  TEST DESCENDING SORT
                // ═══════════════════════════════════════════════════════════

                System.out.println("\n📊 STEP 3: Testing DESCENDING sort...");
                clickHeaderToSort(headerName);

                List<String> dataAfterDescending = getColumnData(columnIndex);

                System.out.println("\n📋 DATA AFTER DESCENDING SORT (First 10 rows):");
                for (int i = 0; i < Math.min(10, dataAfterDescending.size()); i++) {
                    System.out.println("   [" + i + "] " + dataAfterDescending.get(i));
                }
                if (dataAfterDescending.size() > 10) {
                    System.out.println("   ... and " + (dataAfterDescending.size() - 10) + " more rows");
                }

                // Verify data changed from ascending to descending
                boolean descendingChanged = !dataAfterAscending.equals(dataAfterDescending);
                if (descendingChanged) {
                    System.out.println("\n✓ Data order CHANGED from ascending to descending");
                } else {
                    System.out.println("\n⚠ Data order same (possible if all values identical)");
                }

                boolean descendingVerified = verifySortedDescending(headerName);

                if (!descendingVerified) {
                    System.out.println("❌ Descending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Descending sort PASSED for: " + headerName);
                }

                // ═══════════════════════════════════════════════════════════
                //  SUMMARY FOR THIS COLUMN
                // ═══════════════════════════════════════════════════════════

                if (headerTestPassed) {
                    System.out.println("\n✅✅✅ PASSED: Sorting test for '" + headerName + "' completed successfully!");
                    passCount++;
                } else {
                    System.out.println("\n❌❌❌ FAILED: Sorting test for '" + headerName + "' had errors!");
                    failCount++;
                }

            } catch (Exception e) {
                System.out.println("\n❌❌❌ ERROR testing " + headerName + ": " + e.getMessage());
                e.printStackTrace();
                failCount++;
            }
        }

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   SORTING TEST SUMMARY");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   Total Headers Tested: " + sortableHeaderNames.length);
        System.out.println("   ✅ Passed: " + passCount);
        System.out.println("   ❌ Failed: " + failCount);
        System.out.println("════════════════════════════════════════════════════════════════\n");

        if (failCount > 0) {
            throw new AssertionError("Sorting verification failed for " + failCount + " header(s)");
        }
    }

    /**
     * FAST version: Test sorting for all headers with optimized performance
     * Now includes strict sorting order validation like HomePage
     */
    public void testSortingForAllHeadersFast() {
        String[] sortableHeaderNames = {
            "File Name",
            "Field",
            "Expected Value",
            "Actual Value",
            "Match",
            "Confidence"
        };

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TESTING SORTING FOR ALL HEADERS (STRICT VALIDATION)");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        int passCount = 0;
        int failCount = 0;

        for (String headerName : sortableHeaderNames) {
            System.out.println("\n━━━ [" + (passCount + failCount + 1) + "/6] Testing: " + headerName + " ━━━");
            boolean headerTestPassed = true;

            try {
                int columnIndex = getColumnIndexByHeaderName(headerName);

                // Capture data BEFORE sorting
                System.out.println("📊 Capturing initial data...");
                List<String> dataBeforeSort = getColumnData(columnIndex);

                if (dataBeforeSort.isEmpty()) {
                    System.out.println("⚠ No data - skipping");
                    continue;
                }

                System.out.println("   Captured " + dataBeforeSort.size() + " rows");
                System.out.println("   Initial: " + dataBeforeSort.get(0) + " ... " + dataBeforeSort.get(dataBeforeSort.size() - 1));

                // Test ASCENDING sort
                System.out.println("↑ Testing ascending...");
                clickHeaderToSortFast(headerName);
                List<String> dataAfterAscending = getColumnData(columnIndex);

                boolean ascChanged = !dataBeforeSort.equals(dataAfterAscending);
                if (ascChanged) {
                    System.out.println("   ✓ Data changed after clicking sort");
                    System.out.println("   After: " + dataAfterAscending.get(0) + " ... " + dataAfterAscending.get(dataAfterAscending.size() - 1));
                } else {
                    System.out.println("   ⚠ No change (may have been pre-sorted ascending)");
                }

                // STRICT VALIDATION: Verify ascending order
                boolean ascendingVerified = verifySortedAscending(headerName);
                if (!ascendingVerified) {
                    System.out.println("❌ Ascending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Ascending sort PASSED for: " + headerName);
                }

                // Test DESCENDING sort
                System.out.println("↓ Testing descending...");
                clickHeaderToSortFast(headerName);
                List<String> dataAfterDescending = getColumnData(columnIndex);

                boolean descChanged = !dataAfterAscending.equals(dataAfterDescending);
                if (descChanged) {
                    System.out.println("   ✓ Data changed from ascending to descending");
                    System.out.println("   After: " + dataAfterDescending.get(0) + " ... " + dataAfterDescending.get(dataAfterDescending.size() - 1));
                } else {
                    System.out.println("   ⚠ No change");
                }

                // STRICT VALIDATION: Verify descending order
                boolean descendingVerified = verifySortedDescending(headerName);
                if (!descendingVerified) {
                    System.out.println("❌ Descending sort FAILED for: " + headerName);
                    headerTestPassed = false;
                } else {
                    System.out.println("✅ Descending sort PASSED for: " + headerName);
                }

                // Summary for this column
                if (headerTestPassed) {
                    System.out.println("✅✅✅ PASSED: '" + headerName + "' sorting validated successfully!");
                    passCount++;
                } else {
                    System.out.println("❌❌❌ FAILED: '" + headerName + "' sorting validation failed!");
                    failCount++;
                }

            } catch (Exception e) {
                System.out.println("❌ ERROR testing " + headerName + ": " + e.getMessage());
                e.printStackTrace();
                failCount++;
            }
        }

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   SORTING TEST SUMMARY");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   Total Headers: " + sortableHeaderNames.length);
        System.out.println("   ✅ Passed: " + passCount);
        System.out.println("   ❌ Failed: " + failCount);
        System.out.println("════════════════════════════════════════════════════════════════\n");

        if (failCount > 0) {
            throw new AssertionError("Sorting verification failed for " + failCount + " header(s)");
        }
    }

    /**
     * FAST version: Click header with reduced wait time
     */
    private void clickHeaderToSortFast(String headerName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement header = null;

            // Try Strategy 2 first (it works for our table)
            try {
                By locator = By.xpath("//th[contains(normalize-space(), '" + headerName + "')]");
                header = wait.until(ExpectedConditions.elementToBeClickable(locator));
            } catch (Exception e1) {
                // Fallback to exact match
                By locator = By.xpath("//th[normalize-space()='" + headerName + "']");
                header = wait.until(ExpectedConditions.elementToBeClickable(locator));
            }

            if (header != null) {
                try {
                    header.click();
                } catch (Exception clickEx) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", header);
                }

                // Reduced wait time from 2000ms to 1000ms
                Thread.sleep(1000);

                // Quick loader check (reduced timeout)
                try {
                    WebDriverWait loaderWait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    loaderWait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
                } catch (Exception e) {
                    // Loader didn't appear or already gone - continue
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to click header: " + headerName, e);
        }
    }


    /**
     * Verify all headers are present and check sort icons
     */
    public void verifyAllHeaders() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   VERIFYING ALL TABLE HEADERS");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        String[] allHeaders = {
            "File Name",
            "Field",
            "Expected Value",
            "Actual Value",
            "Match",
            "Confidence"
        };

        // Verify all headers are present
        for (String header : allHeaders) {
            isHeaderPresent(header);
        }

        // Verify all headers have sort icons (all are sortable)
        System.out.println("\n--- Checking Sort Icons ---");
        for (String header : allHeaders) {
            hasSortIcon(header);
        }

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   ✓ HEADER VERIFICATION COMPLETED");
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }

    // ==================== FILTER METHODS ====================

    /**
     * Check if File Name filter is present
     */
    public boolean isFileNameFilterPresent() {
        try {
            WebElement element = driver.findElement(fileNameFilterTextbox);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ File Name filter is present" : "✗ File Name filter is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ File Name filter is NOT present");
            return false;
        }
    }

    /**
     * Check if Field filter is present
     */
    public boolean isFieldFilterPresent() {
        try {
            WebElement element = driver.findElement(fieldFilterTextbox);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ Field filter is present" : "✗ Field filter is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Field filter is NOT present");
            return false;
        }
    }

    /**
     * Check if Expected Value filter is present
     */
    public boolean isExpectedValueFilterPresent() {
        try {
            WebElement element = driver.findElement(expectedValueFilterTextbox);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ Expected Value filter is present" : "✗ Expected Value filter is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Expected Value filter is NOT present");
            return false;
        }
    }

    /**
     * Check if Actual Value filter is present
     */
    public boolean isActualValueFilterPresent() {
        try {
            WebElement element = driver.findElement(actualValueFilterTextbox);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ Actual Value filter is present" : "✗ Actual Value filter is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Actual Value filter is NOT present");
            return false;
        }
    }

    /**
     * Check if Match dropdown is present
     */
    public boolean isMatchDropdownPresent() {
        try {
            WebElement element = driver.findElement(matchDropdown);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ Match dropdown is present" : "✗ Match dropdown is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Match dropdown is NOT present");
            return false;
        }
    }

    /**
     * Check if Clear Filters button is present
     */
    public boolean isClearFiltersButtonPresent() {
        try {
            WebElement element = driver.findElement(clearFiltersButton);
            boolean isPresent = element.isDisplayed();
            System.out.println(isPresent ? "✓ Clear Filters button is present" : "✗ Clear Filters button is NOT present");
            return isPresent;
        } catch (Exception e) {
            System.out.println("✗ Clear Filters button is NOT present");
            return false;
        }
    }

    /**
     * Get placeholder text for File Name filter
     */
    public String getFileNameFilterPlaceholder() {
        try {
            WebElement element = driver.findElement(fileNameFilterTextbox);
            String placeholder = element.getAttribute("placeholder");
            System.out.println("✓ File Name filter placeholder: '" + placeholder + "'");
            return placeholder;
        } catch (Exception e) {
            System.out.println("✗ Error getting File Name filter placeholder");
            return "";
        }
    }

    /**
     * Get placeholder text for Field filter
     */
    public String getFieldFilterPlaceholder() {
        try {
            WebElement element = driver.findElement(fieldFilterTextbox);
            String placeholder = element.getAttribute("placeholder");
            System.out.println("✓ Field filter placeholder: '" + placeholder + "'");
            return placeholder;
        } catch (Exception e) {
            System.out.println("✗ Error getting Field filter placeholder");
            return "";
        }
    }

    /**
     * Get placeholder text for Expected Value filter
     */
    public String getExpectedValueFilterPlaceholder() {
        try {
            WebElement element = driver.findElement(expectedValueFilterTextbox);
            String placeholder = element.getAttribute("placeholder");
            System.out.println("✓ Expected Value filter placeholder: '" + placeholder + "'");
            return placeholder;
        } catch (Exception e) {
            System.out.println("✗ Error getting Expected Value filter placeholder");
            return "";
        }
    }

    /**
     * Get placeholder text for Actual Value filter
     */
    public String getActualValueFilterPlaceholder() {
        try {
            WebElement element = driver.findElement(actualValueFilterTextbox);
            String placeholder = element.getAttribute("placeholder");
            System.out.println("✓ Actual Value filter placeholder: '" + placeholder + "'");
            return placeholder;
        } catch (Exception e) {
            System.out.println("✗ Error getting Actual Value filter placeholder");
            return "";
        }
    }

    /**
     * Get selected value from Match dropdown
     */
    public String getMatchDropdownSelectedValue() {
        try {
            WebElement element = driver.findElement(matchDropdown);
            String selectedValue = element.getAttribute("value");
            System.out.println("✓ Match dropdown selected value: '" + selectedValue + "'");
            return selectedValue;
        } catch (Exception e) {
            System.out.println("✗ Error getting Match dropdown selected value");
            return "";
        }
    }

    /**
     * Get all options from Match dropdown
     */
    public List<String> getMatchDropdownOptions() {
        try {
            WebElement selectElement = driver.findElement(matchDropdown);
            List<WebElement> options = selectElement.findElements(By.tagName("option"));
            List<String> optionTexts = new ArrayList<>();

            for (WebElement option : options) {
                String text = option.getText().trim();
                optionTexts.add(text);
            }

            System.out.println("✓ Match dropdown options: " + optionTexts);
            return optionTexts;
        } catch (Exception e) {
            System.out.println("✗ Error getting Match dropdown options");
            return new ArrayList<>();
        }
    }

    /**
     * Enter filter value in File Name textbox
     */
    public void enterFileNameFilter(String value) {
        try {
            WebElement element = driver.findElement(fileNameFilterTextbox);
            element.clear();
            element.sendKeys(value);
            System.out.println("✓ Entered '" + value + "' in File Name filter");
        } catch (Exception e) {
            System.out.println("✗ Error entering File Name filter: " + e.getMessage());
            throw new RuntimeException("Failed to enter File Name filter", e);
        }
    }

    /**
     * Enter filter value in Field textbox
     */
    public void enterFieldFilter(String value) {
        try {
            WebElement element = driver.findElement(fieldFilterTextbox);
            element.clear();
            element.sendKeys(value);
            System.out.println("✓ Entered '" + value + "' in Field filter");
        } catch (Exception e) {
            System.out.println("✗ Error entering Field filter: " + e.getMessage());
            throw new RuntimeException("Failed to enter Field filter", e);
        }
    }

    /**
     * Enter filter value in Expected Value textbox
     */
    public void enterExpectedValueFilter(String value) {
        try {
            WebElement element = driver.findElement(expectedValueFilterTextbox);
            element.clear();
            element.sendKeys(value);
            System.out.println("✓ Entered '" + value + "' in Expected Value filter");
        } catch (Exception e) {
            System.out.println("✗ Error entering Expected Value filter: " + e.getMessage());
            throw new RuntimeException("Failed to enter Expected Value filter", e);
        }
    }

    /**
     * Enter filter value in Actual Value textbox
     */
    public void enterActualValueFilter(String value) {
        try {
            WebElement element = driver.findElement(actualValueFilterTextbox);
            element.clear();
            element.sendKeys(value);
            System.out.println("✓ Entered '" + value + "' in Actual Value filter");
        } catch (Exception e) {
            System.out.println("✗ Error entering Actual Value filter: " + e.getMessage());
            throw new RuntimeException("Failed to enter Actual Value filter", e);
        }
    }

    /**
     * Clear File Name filter
     */
    public void clearFileNameFilter() {
        try {
            WebElement element = driver.findElement(fileNameFilterTextbox);
            element.clear();
            System.out.println("✓ Cleared File Name filter");
        } catch (Exception e) {
            System.out.println("✗ Error clearing File Name filter");
        }
    }

    /**
     * Clear Field filter
     */
    public void clearFieldFilter() {
        try {
            WebElement element = driver.findElement(fieldFilterTextbox);
            element.clear();
            System.out.println("✓ Cleared Field filter");
        } catch (Exception e) {
            System.out.println("✗ Error clearing Field filter");
        }
    }

    /**
     * Clear Expected Value filter
     */
    public void clearExpectedValueFilter() {
        try {
            WebElement element = driver.findElement(expectedValueFilterTextbox);
            element.clear();
            System.out.println("✓ Cleared Expected Value filter");
        } catch (Exception e) {
            System.out.println("✗ Error clearing Expected Value filter");
        }
    }

    /**
     * Clear Actual Value filter
     */
    public void clearActualValueFilter() {
        try {
            WebElement element = driver.findElement(actualValueFilterTextbox);
            element.clear();
            System.out.println("✓ Cleared Actual Value filter");
        } catch (Exception e) {
            System.out.println("✗ Error clearing Actual Value filter");
        }
    }

    /**
     * Get value from File Name filter
     */
    public String getFileNameFilterValue() {
        try {
            WebElement element = driver.findElement(fileNameFilterTextbox);
            return element.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get value from Field filter
     */
    public String getFieldFilterValue() {
        try {
            WebElement element = driver.findElement(fieldFilterTextbox);
            return element.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get value from Expected Value filter
     */
    public String getExpectedValueFilterValue() {
        try {
            WebElement element = driver.findElement(expectedValueFilterTextbox);
            return element.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get value from Actual Value filter
     */
    public String getActualValueFilterValue() {
        try {
            WebElement element = driver.findElement(actualValueFilterTextbox);
            return element.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Select option from Match dropdown
     */
    public void selectMatchOption(String option) {
        try {
            WebElement selectElement = driver.findElement(matchDropdown);
            List<WebElement> options = selectElement.findElements(By.tagName("option"));

            for (WebElement opt : options) {
                if (opt.getAttribute("value").equalsIgnoreCase(option) || opt.getText().equalsIgnoreCase(option)) {
                    opt.click();
                    System.out.println("✓ Selected '" + option + "' from Match dropdown");
                    return;
                }
            }

            throw new RuntimeException("Option '" + option + "' not found in Match dropdown");
        } catch (Exception e) {
            System.out.println("✗ Error selecting Match option: " + e.getMessage());
            throw new RuntimeException("Failed to select Match option: " + option, e);
        }
    }

    /**
     * Click Clear Filters button
     */
    public void clickClearFilters() {
        try {
            WebElement element = driver.findElement(clearFiltersButton);
            element.click();
            System.out.println("✓ Clicked Clear Filters button");
        } catch (Exception e) {
            System.out.println("✗ Error clicking Clear Filters button: " + e.getMessage());
            throw new RuntimeException("Failed to click Clear Filters button", e);
        }
    }

    /**
     * Check if 'No results found' message is displayed
     */
    public boolean isNoResultsMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
            boolean isDisplayed = element.isDisplayed();
            String messageText = element.getText();
            System.out.println(isDisplayed ? "✓ No results message displayed: '" + messageText + "'" : "✗ No results message is NOT displayed");
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("✗ No results message is NOT displayed");
            return false;
        }
    }

    /**
     * Get visible row count (after filtering)
     */
    public int getVisibleRowCount() {
        try {
            // Try to find visible rows (not hidden by filters)
            List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
            int visibleCount = 0;

            for (WebElement row : rows) {
                // Check if row is visible (not hidden by display:none or empty row)
                String className = row.getAttribute("class");
                if (!className.contains("empty-row") && row.isDisplayed()) {
                    visibleCount++;
                }
            }

            System.out.println("✓ Visible rows: " + visibleCount);
            return visibleCount;
        } catch (Exception e) {
            System.out.println("✗ Error getting visible row count: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Get first value from a specific column
     */
    public String getFirstValueFromColumn(String columnName) {
        try {
            int columnIndex = getColumnIndexByHeaderName(columnName);
            if (columnIndex == -1) {
                throw new RuntimeException("Invalid column name: " + columnName);
            }

            By columnLocator = By.xpath(String.format(columnDataXPath, columnIndex));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement firstCell = wait.until(ExpectedConditions.visibilityOfElementLocated(columnLocator));

            String value = firstCell.getText().trim();
            System.out.println("✓ First value from '" + columnName + "': '" + value + "'");
            return value;
        } catch (Exception e) {
            System.out.println("✗ Error getting first value from column: " + e.getMessage());
            throw new RuntimeException("Failed to get first value from column: " + columnName, e);
        }
    }

    /**
     * Verify filtered results contain the expected value in the specified column
     */
    public boolean verifyFilteredResults(String columnName, String expectedValue) {
        try {
            int columnIndex = getColumnIndexByHeaderName(columnName);
            if (columnIndex == -1) {
                System.out.println("❌ Invalid column name: " + columnName);
                return false;
            }

            By columnLocator = By.xpath(String.format(columnDataXPath, columnIndex));
            List<WebElement> columnCells = driver.findElements(columnLocator);

            if (columnCells.isEmpty()) {
                System.out.println("⚠ No visible rows after filtering");
                return false;
            }

            System.out.println("📊 Verifying filtered results in '" + columnName + "' column:");
            System.out.println("   Total visible rows: " + columnCells.size());
            System.out.println("   Expected value: '" + expectedValue + "'");

            boolean allMatch = true;
            int matchCount = 0;
            int mismatchCount = 0;

            for (int i = 0; i < Math.min(10, columnCells.size()); i++) {
                String cellValue = columnCells.get(i).getText().trim();
                boolean contains = cellValue.toLowerCase().contains(expectedValue.toLowerCase());

                if (contains) {
                    matchCount++;
                    if (i < 3) {
                        System.out.println("   ✓ Row " + i + ": '" + cellValue + "' contains '" + expectedValue + "'");
                    }
                } else {
                    mismatchCount++;
                    System.out.println("   ✗ Row " + i + ": '" + cellValue + "' does NOT contain '" + expectedValue + "'");
                    allMatch = false;
                }
            }

            System.out.println("   Matches: " + matchCount + ", Mismatches: " + mismatchCount);

            if (allMatch) {
                System.out.println("   ✅ All filtered rows contain the expected value");
            } else {
                System.out.println("   ❌ Some rows do NOT contain the expected value");
            }

            return allMatch;
        } catch (Exception e) {
            System.out.println("❌ Error verifying filtered results: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all visible rows have the expected value in Match column
     */
    public boolean verifyMatchColumnValues(String expectedMatchValue) {
        try {
            int matchColumnIndex = 5; // Match column is index 5
            By columnLocator = By.xpath(String.format(columnDataXPath, matchColumnIndex));
            List<WebElement> matchCells = driver.findElements(columnLocator);

            if (matchCells.isEmpty()) {
                System.out.println("⚠ No visible rows to verify");
                return true;
            }

            System.out.println("📊 Verifying Match column values:");
            System.out.println("   Total visible rows: " + matchCells.size());
            System.out.println("   Expected value: '" + expectedMatchValue + "'");

            boolean allMatch = true;
            int matchCount = 0;
            int mismatchCount = 0;

            for (int i = 0; i < matchCells.size(); i++) {
                String cellValue = matchCells.get(i).getText().trim();
                boolean matches = cellValue.equalsIgnoreCase(expectedMatchValue);

                if (matches) {
                    matchCount++;
                    if (i < 5) {
                        System.out.println("   ✓ Row " + i + ": '" + cellValue + "' = '" + expectedMatchValue + "'");
                    }
                } else {
                    mismatchCount++;
                    if (i < 5) {
                        System.out.println("   ✗ Row " + i + ": '" + cellValue + "' != '" + expectedMatchValue + "'");
                    }
                    allMatch = false;
                }
            }

            System.out.println("   Correct: " + matchCount + ", Incorrect: " + mismatchCount);

            if (allMatch) {
                System.out.println("   ✅ All rows have '" + expectedMatchValue + "' in Match column");
            } else {
                System.out.println("   ❌ Some rows do NOT have '" + expectedMatchValue + "' in Match column");
            }

            return allMatch;
        } catch (Exception e) {
            System.out.println("❌ Error verifying Match column values: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all visible rows in File Name column contain the expected file name
     */
    public boolean verifyFileNameColumnContains(String expectedFileName) {
        try {
            int fileNameColumnIndex = 1;
            By columnLocator = By.xpath(String.format(columnDataXPath, fileNameColumnIndex));
            List<WebElement> fileNameCells = driver.findElements(columnLocator);

            if (fileNameCells.isEmpty()) {
                System.out.println("⚠ No visible rows to verify File Name");
                return false;
            }

            System.out.println("📊 Verifying File Name column contains: '" + expectedFileName + "'");
            boolean allContain = true;

            for (int i = 0; i < fileNameCells.size(); i++) {
                String cellText = fileNameCells.get(i).getText().trim();
                if (!cellText.contains(expectedFileName)) {
                    System.out.println("   ❌ Row " + i + ": '" + cellText + "' does not contain '" + expectedFileName + "'");
                    allContain = false;
                } else if (i < 3) {
                    System.out.println("   ✓ Row " + i + ": '" + cellText + "' contains '" + expectedFileName + "'");
                }
            }

            if (allContain) {
                System.out.println("   ✅ All File Name column values contain: '" + expectedFileName + "'");
            }
            return allContain;
        } catch (Exception e) {
            System.out.println("❌ Error verifying File Name column: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all visible rows in Field column match the expected field
     */
    public boolean verifyFieldColumnContains(String expectedField) {
        try {
            int fieldColumnIndex = 2;
            By columnLocator = By.xpath(String.format(columnDataXPath, fieldColumnIndex));
            List<WebElement> fieldCells = driver.findElements(columnLocator);

            if (fieldCells.isEmpty()) {
                System.out.println("⚠ No visible rows to verify Field");
                return false;
            }

            System.out.println("📊 Verifying Field column equals: '" + expectedField + "'");
            boolean allMatch = true;

            for (int i = 0; i < fieldCells.size(); i++) {
                String cellText = fieldCells.get(i).getText().trim();
                if (!cellText.equals(expectedField)) {
                    System.out.println("   ❌ Row " + i + ": '" + cellText + "' != '" + expectedField + "'");
                    allMatch = false;
                } else if (i < 3) {
                    System.out.println("   ✓ Row " + i + ": '" + cellText + "' = '" + expectedField + "'");
                }
            }

            if (allMatch) {
                System.out.println("   ✅ All Field column values match: '" + expectedField + "'");
            }
            return allMatch;
        } catch (Exception e) {
            System.out.println("❌ Error verifying Field column: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all visible rows in Expected Value column match the expected value
     */
    public boolean verifyExpectedValueColumnContains(String expectedValue) {
        try {
            int expectedValueColumnIndex = 3;
            By columnLocator = By.xpath(String.format(columnDataXPath, expectedValueColumnIndex));
            List<WebElement> expectedCells = driver.findElements(columnLocator);

            if (expectedCells.isEmpty()) {
                System.out.println("⚠ No visible rows to verify Expected Value");
                return false;
            }

            System.out.println("📊 Verifying Expected Value column equals: '" + expectedValue + "'");
            boolean allMatch = true;

            for (int i = 0; i < expectedCells.size(); i++) {
                String cellText = expectedCells.get(i).getText().trim();
                if (!cellText.equals(expectedValue)) {
                    System.out.println("   ❌ Row " + i + ": '" + cellText + "' != '" + expectedValue + "'");
                    allMatch = false;
                } else if (i < 3) {
                    System.out.println("   ✓ Row " + i + ": '" + cellText + "' = '" + expectedValue + "'");
                }
            }

            if (allMatch) {
                System.out.println("   ✅ All Expected Value column values match: '" + expectedValue + "'");
            }
            return allMatch;
        } catch (Exception e) {
            System.out.println("❌ Error verifying Expected Value column: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all visible rows in Actual Value column match the actual value
     */
    public boolean verifyActualValueColumnContains(String actualValue) {
        try {
            int actualValueColumnIndex = 4;
            By columnLocator = By.xpath(String.format(columnDataXPath, actualValueColumnIndex));
            List<WebElement> actualCells = driver.findElements(columnLocator);

            if (actualCells.isEmpty()) {
                System.out.println("⚠ No visible rows to verify Actual Value");
                return false;
            }

            System.out.println("📊 Verifying Actual Value column equals: '" + actualValue + "'");
            boolean allMatch = true;

            for (int i = 0; i < actualCells.size(); i++) {
                String cellText = actualCells.get(i).getText().trim();
                if (!cellText.equals(actualValue)) {
                    System.out.println("   ❌ Row " + i + ": '" + cellText + "' != '" + actualValue + "'");
                    allMatch = false;
                } else if (i < 3) {
                    System.out.println("   ✓ Row " + i + ": '" + cellText + "' = '" + actualValue + "'");
                }
            }

            if (allMatch) {
                System.out.println("   ✅ All Actual Value column values match: '" + actualValue + "'");
            }
            return allMatch;
        } catch (Exception e) {
            System.out.println("❌ Error verifying Actual Value column: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify all filter inputs are cleared/empty and dropdown reset to 'All'
     */
    public boolean areFiltersCleared() {
        try {
            String fileNameValue = driver.findElement(fileNameFilterTextbox).getAttribute("value").trim();
            String fieldValue = driver.findElement(fieldFilterTextbox).getAttribute("value").trim();
            String expectedValue = driver.findElement(expectedValueFilterTextbox).getAttribute("value").trim();
            String actualValue = driver.findElement(actualValueFilterTextbox).getAttribute("value").trim();
            String matchValue = driver.findElement(matchDropdown).getAttribute("value").trim();

            boolean fileNameEmpty = fileNameValue.isEmpty();
            boolean fieldEmpty = fieldValue.isEmpty();
            boolean expectedEmpty = expectedValue.isEmpty();
            boolean actualEmpty = actualValue.isEmpty();
            boolean matchReset = matchValue.equalsIgnoreCase("all");

            boolean allCleared = fileNameEmpty && fieldEmpty && expectedEmpty && actualEmpty && matchReset;

            System.out.println("📊 Checking if filters are cleared:");
            System.out.println("   File Name filter empty: " + (fileNameEmpty ? "✅" : "❌ '" + fileNameValue + "'"));
            System.out.println("   Field filter empty: " + (fieldEmpty ? "✅" : "❌ '" + fieldValue + "'"));
            System.out.println("   Expected Value filter empty: " + (expectedEmpty ? "✅" : "❌ '" + expectedValue + "'"));
            System.out.println("   Actual Value filter empty: " + (actualEmpty ? "✅" : "❌ '" + actualValue + "'"));
            System.out.println("   Match dropdown reset to 'All': " + (matchReset ? "✅" : "❌ '" + matchValue + "'"));

            if (allCleared) {
                System.out.println("   ✅ All filters are cleared");
            } else {
                System.out.println("   ❌ Some filters are NOT cleared");
            }

            return allCleared;
        } catch (Exception e) {
            System.out.println("❌ Error checking if filters are cleared: " + e.getMessage());
            return false;
        }
    }
}
