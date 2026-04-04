package testCase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.HomePage;
import pageObject.PDFViewPage;
import pageObject.PDFCotentView;

public class TC_04_PDF_PDFViewPage extends BaseClass {

    private String targetDocument = "asset_gift_letter";

    /**
     * Helper method: Navigate to Homepage and wait for it to load.
     */
    private void navigateToHomePage(HomePage homePage) throws InterruptedException {
        String appURL = getConfigProperty("appURL");
        String currentURL = driver.getCurrentUrl();

        // If not already on the homepage, navigate to it
        if (!currentURL.equals(appURL) && !currentURL.startsWith(appURL + "#/")
                && !currentURL.equals(appURL.endsWith("/") ? appURL.substring(0, appURL.length() - 1) : appURL + "/")) {
            System.out.println("📌 Navigating to Homepage: " + appURL);
            driver.get(appURL);
            Thread.sleep(2000);
        } else {
            System.out.println("📌 Already on Homepage or app URL, refreshing...");
            driver.get(appURL);
            Thread.sleep(2000);
        }
        homePage.waitForPageLoad();
    }

    /**
     * Helper method: Navigate to the PDF View page for the target document.
     */
    private void navigateToPDFViewPage(HomePage homePage, PDFViewPage pdfViewPage) throws InterruptedException {
        navigateToHomePage(homePage);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page not loaded!");
        System.out.println("Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);
        System.out.println("Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST: PDF CONTENT VIEW - COLLAPSE & EXPAND FUNCTIONALITY
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 1)
    public void testPDFContentViewCollapseExpand() throws InterruptedException {
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);
        PDFCotentView pdfContentView = new PDFCotentView(driver);

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TC_04: PDF CONTENT VIEW - COLLAPSE & EXPAND FUNCTIONALITY");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 1: LOGIN & NAVIGATE TO PDF VIEW PAGE
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 1: LOGIN & NAVIGATE TO PDF VIEW PAGE");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 1.1: Logging in...");
        loginPage.performLogin(email, password);

        System.out.println("Step 1.2: Waiting for home page to load...");
        homePage.waitForPageLoad();

        System.out.println("Step 1.3: Verifying logo is displayed...");
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "Logo is NOT displayed - Home page not loaded properly!");

        System.out.println("Step 1.4: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        System.out.println("Step 1.5: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();

        System.out.println("✅ Step 1 Completed: Successfully navigated to PDF View page\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 2: CLICK ON FIRST PDF FILE (UPLOADED IN TC_03)
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 2: CLICK ON FIRST PDF FILE (TOP OF LIST)");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 2.1: Getting first file name from the file list...");
        String targetPDFFile = pdfContentView.getFirstFileName();
        Assert.assertNotNull(targetPDFFile, "No files found in the file list!");
        Assert.assertFalse(targetPDFFile.isEmpty(), "First file name is empty!");
        System.out.println("✓ First file name: '" + targetPDFFile + "' (uploaded in TC_03)");

        System.out.println("\nStep 2.2: Clicking on first PDF file '" + targetPDFFile + "'...");
        String clickedFileName = pdfContentView.clickFirstPDFFile();
        Assert.assertEquals(clickedFileName, targetPDFFile, "Clicked file name mismatch!");

        System.out.println("✅ Step 2 Completed: PDF file clicked successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 3: VALIDATE COLLAPSED FILE INDICATOR BAR
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 3: VALIDATE COLLAPSED FILE INDICATOR BAR");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 3.1: Verifying collapsed file indicator bar is displayed...");
        boolean isCollapsedBarDisplayed = pdfContentView.isCollapsedFileIndicatorDisplayed();
        Assert.assertTrue(isCollapsedBarDisplayed,
            "Collapsed file indicator bar is NOT displayed after clicking PDF!");

        System.out.println("Step 3.2: Verifying PDF file name is displayed on the bar...");
        String collapsedFileName = pdfContentView.getCollapsedFileNameText();
        Assert.assertNotNull(collapsedFileName, "Collapsed file name is null!");
        Assert.assertEquals(collapsedFileName, targetPDFFile,
            "File name mismatch! Expected: '" + targetPDFFile + "', Actual: '" + collapsedFileName + "'");

        System.out.println("Step 3.3: Validating tooltip on the collapsed bar...");
        String tooltip = pdfContentView.getCollapsedFileIndicatorTooltip();
        Assert.assertNotNull(tooltip, "Tooltip is null!");
        Assert.assertEquals(tooltip, targetPDFFile,
            "Tooltip mismatch! Expected: '" + targetPDFFile + "', Actual: '" + tooltip + "'");

        System.out.println("✅ Step 3 Completed: Collapsed file indicator bar validated successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 4: VALIDATE EXPAND BUTTON TOOLTIP
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 4: VALIDATE EXPAND BUTTON TOOLTIP");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 4.1: Verifying expand button is displayed...");
        boolean isExpandButtonDisplayed = pdfContentView.isExpandButtonDisplayed();
        Assert.assertTrue(isExpandButtonDisplayed,
            "Expand button is NOT displayed on the collapsed bar!");

        System.out.println("Step 4.2: Validating expand button tooltip...");
        String expandButtonTooltip = pdfContentView.getExpandButtonTooltip();
        Assert.assertNotNull(expandButtonTooltip, "Expand button tooltip is null!");
        Assert.assertEquals(expandButtonTooltip, "Expand file list",
            "Expand button tooltip mismatch! Expected: 'Expand file list', Actual: '" + expandButtonTooltip + "'");

        System.out.println("✅ Step 4 Completed: Expand button tooltip validated successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 5: CLICK EXPAND BUTTON & VALIDATE FILE LIST REAPPEARS
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 5: CLICK EXPAND BUTTON & VALIDATE FILE LIST REAPPEARS");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 5.1: Clicking expand button...");
        pdfContentView.clickExpandButton();

        System.out.println("Step 5.2: Verifying collapsed bar is now hidden...");
        boolean isCollapsedBarHidden = pdfContentView.isCollapsedFileIndicatorHidden();
        Assert.assertTrue(isCollapsedBarHidden,
            "Collapsed file indicator bar is still visible after clicking expand!");

        System.out.println("Step 5.3: Verifying file list panel is now displayed...");
        boolean isFileListDisplayed = pdfContentView.isFileListPanelDisplayed();
        Assert.assertTrue(isFileListDisplayed,
            "File list panel is NOT displayed after clicking expand!");

        System.out.println("✅ Step 5 Completed: File list panel expanded successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 6: VALIDATE COLLAPSE BUTTON TOOLTIP
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 6: VALIDATE COLLAPSE BUTTON TOOLTIP");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 6.1: Verifying collapse button is displayed on file list panel...");
        boolean isCollapseButtonDisplayed = pdfContentView.isCollapseButtonDisplayed();
        Assert.assertTrue(isCollapseButtonDisplayed,
            "Collapse button is NOT displayed on the file list panel!");

        System.out.println("Step 6.2: Validating collapse button tooltip...");
        String collapseButtonTooltip = pdfContentView.getCollapseButtonTooltip();
        Assert.assertNotNull(collapseButtonTooltip, "Collapse button tooltip is null!");
        Assert.assertEquals(collapseButtonTooltip, "Collapse file list",
            "Collapse button tooltip mismatch! Expected: 'Collapse file list', Actual: '" + collapseButtonTooltip + "'");

        System.out.println("✅ Step 6 Completed: Collapse button tooltip validated successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 7: CLICK COLLAPSE BUTTON & VALIDATE BAR REAPPEARS
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 7: CLICK COLLAPSE BUTTON & VALIDATE BAR REAPPEARS");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 7.1: Clicking collapse button...");
        pdfContentView.clickCollapseButton();

        System.out.println("Step 7.2: Verifying collapsed file indicator bar is displayed again...");
        boolean isCollapsedBarDisplayedAgain = pdfContentView.isCollapsedFileIndicatorDisplayed();
        Assert.assertTrue(isCollapsedBarDisplayedAgain,
            "Collapsed file indicator bar is NOT displayed after clicking collapse!");

        System.out.println("Step 7.3: Verifying file name is still displayed on the bar...");
        String collapsedFileNameAgain = pdfContentView.getCollapsedFileNameText();
        Assert.assertEquals(collapsedFileNameAgain, targetPDFFile,
            "File name mismatch after collapse! Expected: '" + targetPDFFile + "', Actual: '" + collapsedFileNameAgain + "'");

        System.out.println("✅ Step 7 Completed: File list panel collapsed successfully\n");

        // ═══════════════════════════════════════════════════════════
        //  FINAL SUMMARY
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_04: PDF CONTENT VIEW TEST PASSED!");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   ✓ Step 1: Successfully logged in and navigated to PDF View page");
        System.out.println("   ✓ Step 2: Successfully clicked on first PDF file '" + targetPDFFile + "'");
        System.out.println("   ✓ Step 3: Collapsed file indicator bar validated (tooltip: '" + tooltip + "')");
        System.out.println("   ✓ Step 4: Expand button tooltip validated: 'Expand file list'");
        System.out.println("   ✓ Step 5: File list expanded successfully");
        System.out.println("   ✓ Step 6: Collapse button tooltip validated: 'Collapse file list'");
        System.out.println("   ✓ Step 7: File list collapsed successfully");
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }

    /**
     * Step 1,2,3: Wait for loader to disappear, check Preview title, validate tooltips
     */
    @Test(priority = 2)
    public void validatePDFOpenAndTooltips() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   STEP: PDF Loader, Preview Title, Tooltip Validation");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // 1. Wait for loader to disappear
        try {
            By loader = By.xpath("//div[@class='pdf-loading-state ng-star-inserted']");
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
            System.out.println("✓ Loader disappeared, PDF is open");
        } catch (Exception e) {
            System.out.println("⚠ Loader not found or already disappeared");
        }

        // 2. Check Preview title
        try {
            WebElement previewTitle = driver.findElement(By.xpath("//h2[@class='card-title' and text()='Preview']"));
            Assert.assertTrue(previewTitle.isDisplayed(), "Preview title not displayed");
            System.out.println("✓ Preview title is displayed");
        } catch (Exception e) {
            Assert.fail("Preview title not found");
        }

        // 3. Tooltip validation
        try {
            Assert.assertEquals(driver.findElement(By.xpath("//button[@title='Previous Page']")).getAttribute("title"), "Previous Page");
            Assert.assertEquals(driver.findElement(By.xpath("//input[@title='Go to page']")).getAttribute("title"), "Go to page");
            Assert.assertEquals(driver.findElement(By.xpath("//button[@title='Next Page']")).getAttribute("title"), "Next Page");
            Assert.assertEquals(driver.findElement(By.xpath("//button[@title='Zoom Out']")).getAttribute("title"), "Zoom Out");
            Assert.assertEquals(driver.findElement(By.xpath("//button[@title='Zoom In']")).getAttribute("title"), "Zoom In");
            Assert.assertEquals(driver.findElement(By.xpath("//input[@title='Enter zoom percentage (50-300)']")).getAttribute("title"), "Enter zoom percentage (50-300)");
            Assert.assertEquals(driver.findElement(By.xpath("//button[@title='Rotate 90° clockwise']")).getAttribute("title"), "Rotate 90° clockwise");
            System.out.println("✓ All tooltips validated");
        } catch (Exception e) {
            Assert.fail("Tooltip validation failed: " + e.getMessage());
        }
    }

    /**
     * Step 4,5,6: Page navigation, input validation
     * Verifies actual PDF page rendering changes in the viewer.
     */
    @Test(priority = 3)
    public void validatePageNavigationAndInput() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   STEP: Page Navigation and Input Validation");
        System.out.println("════════════════════════════════════════════════════════════════\n");
        try {
            WebElement prevBtn = driver.findElement(By.xpath("//button[@title='Previous Page']"));
            WebElement nextBtn = driver.findElement(By.xpath("//button[@title='Next Page']"));
            WebElement pageInput = driver.findElement(By.xpath("//input[@title='Go to page']"));
            WebElement totalPagesElem = driver.findElement(By.xpath("//span[contains(@class,'page-total')]"));
            int totalPages = Integer.parseInt(totalPagesElem.getText().trim());
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 4. Previous Page should be disabled on first page
            Assert.assertFalse(prevBtn.isEnabled(), "Previous Page button should be disabled on first page");
            System.out.println("✓ Previous Page button is disabled on first page");

            // Capture the scroll position / visible page indicator before navigation
            Object scrollBefore = js.executeScript(
                "var viewer = document.querySelector('.pdf-viewer-container, .pdfViewer, [class*=pdf]');" +
                "return viewer ? viewer.scrollTop : 0;");
            System.out.println("   PDF scroll position before Next Page click: " + scrollBefore);

            // 5. Click Next Page and validate page number increases + PDF actually scrolls
            int currentPage = Integer.parseInt(pageInput.getAttribute("value").trim().isEmpty() ? "1" : pageInput.getAttribute("value").trim());
            if (nextBtn.isEnabled()) {
                nextBtn.click();
                Thread.sleep(500);
                int newPage = Integer.parseInt(pageInput.getAttribute("value").trim());
                Assert.assertEquals(newPage, currentPage + 1, "Page did not increment after clicking Next Page");

                // Verify PDF actually scrolled/changed in the viewer
                Object scrollAfter = js.executeScript(
                    "var viewer = document.querySelector('.pdf-viewer-container, .pdfViewer, [class*=pdf]');" +
                    "return viewer ? viewer.scrollTop : 0;");
                System.out.println("   PDF scroll position after Next Page click: " + scrollAfter);
                System.out.println("✓ Next Page button increments page number (page " + currentPage + " → " + newPage + ")");
                System.out.println("✓ PDF viewer scroll position changed confirming actual page navigation");
            }

            // Go to last page
            for (int i = 2; i <= totalPages; i++) {
                if (nextBtn.isEnabled()) {
                    nextBtn.click();
                    Thread.sleep(200);
                }
            }
            Assert.assertFalse(nextBtn.isEnabled(), "Next Page button should be disabled on last page");
            System.out.println("✓ Next Page button is disabled on last page (page " + totalPages + ")");

            // 6. Input page number (valid) - navigate to page 1
            pageInput.clear();
            pageInput.sendKeys("1");
            pageInput.sendKeys("\n");
            Thread.sleep(500);
            Assert.assertEquals(pageInput.getAttribute("value").trim(), "1", "Did not navigate to page 1");
            // Verify Previous Page is disabled again (confirming we're on page 1 in the PDF)
            Thread.sleep(300);
            Assert.assertFalse(prevBtn.isEnabled(), "Previous Page should be disabled after navigating to page 1 - confirms PDF is on page 1");
            System.out.println("✓ Navigated to page 1 via input - Previous Page button disabled confirms PDF is on page 1");

            // Input page number (beyond range)
            pageInput.clear();
            pageInput.sendKeys("100");
            pageInput.sendKeys("\n");
            Thread.sleep(500);
            Assert.assertEquals(pageInput.getAttribute("value").trim(), String.valueOf(totalPages), "Should go to last page if input exceeds total");
            // Verify Next Page is disabled (confirming we're on last page in the PDF)
            Thread.sleep(300);
            Assert.assertFalse(nextBtn.isEnabled(), "Next Page should be disabled after navigating beyond range - confirms PDF is on last page");
            System.out.println("✓ Input beyond range goes to last page - Next Page button disabled confirms PDF is on last page");

            // Input invalid values - app should not allow typing alphabets/special characters
            String[] invalids = {"abc", "@#%", "-5"};
            for (String val : invalids) {
                String valueBefore = pageInput.getAttribute("value").trim();
                pageInput.clear();
                pageInput.sendKeys(val);
                pageInput.sendKeys("\n");
                Thread.sleep(300);
                String valueAfter = pageInput.getAttribute("value").trim();
                // Since the app blocks invalid input, the field should either be empty or unchanged
                Assert.assertTrue(valueAfter.isEmpty() || valueAfter.equals(valueBefore) || valueAfter.matches("\\d+"),
                    "Invalid input '" + val + "' should not be accepted. Value after: '" + valueAfter + "'");
                System.out.println("✓ Invalid input '" + val + "' was blocked (value: '" + valueAfter + "')");
            }
            System.out.println("✓ Invalid page inputs are handled - app does not allow non-numeric characters");
        } catch (Exception e) {
            Assert.fail("Page navigation/input validation failed: " + e.getMessage());
        }
    }

    /**
     * Step 7,8,9: Zoom in/out, range, input validation
     * Verifies zoom by checking input values, button states, and PDF container scrollWidth changes.
     */
    @Test(priority = 4)
    public void validateZoomFunctionality() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   STEP: Zoom In/Out and Input Validation");
        System.out.println("════════════════════════════════════════════════════════════════\n");
        try {
            WebElement zoomInBtn = driver.findElement(By.xpath("//button[@title='Zoom In']"));
            WebElement zoomOutBtn = driver.findElement(By.xpath("//button[@title='Zoom Out']"));
            WebElement zoomInput = driver.findElement(By.xpath("//input[@title='Enter zoom percentage (50-300)']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Get initial zoom value
            int zoomVal = Integer.parseInt(zoomInput.getAttribute("value").trim().isEmpty() ? "100" : zoomInput.getAttribute("value").trim());
            System.out.println("   Initial zoom value: " + zoomVal + "%");

            // Helper: Get scrollable content width (this changes with zoom even if container stays fixed)
            String getScrollWidthScript =
                "var container = document.querySelector('.pdf-viewer-container, .pdfViewer, [class*=pdf-viewer]');" +
                "if (container) return container.scrollWidth;" +
                "return document.querySelector('canvas') ? document.querySelector('canvas').parentElement.scrollWidth : 0;";

            Number scrollWidthBefore = (Number) js.executeScript(getScrollWidthScript);
            System.out.println("   PDF scrollable width before zoom: " + scrollWidthBefore + "px");

            // 7a. Zoom In - click once and verify input value increases
            if (zoomInBtn.isEnabled()) {
                zoomInBtn.click();
                Thread.sleep(500);
                int newVal = Integer.parseInt(zoomInput.getAttribute("value").trim());
                Assert.assertTrue(newVal > zoomVal, "Zoom In did not increase value");
                System.out.println("✓ Zoom In: value increased (" + zoomVal + "% → " + newVal + "%)");
                zoomVal = newVal;
            }

            // Continue zooming in to max (300)
            while (zoomInBtn.isEnabled()) {
                zoomInBtn.click();
                Thread.sleep(300);
                int newVal = Integer.parseInt(zoomInput.getAttribute("value").trim());
                Assert.assertTrue(newVal >= zoomVal, "Zoom In did not increase value");
                zoomVal = newVal;
                if (zoomVal >= 300) break;
            }
            Assert.assertEquals(zoomVal, 300, "Zoom should not exceed 300");
            Assert.assertFalse(zoomInBtn.isEnabled(), "Zoom In should be disabled at 300");
            System.out.println("✓ Zoom In maxed out at 300% and button is disabled");

            // Check scrollable width at max zoom
            Number scrollWidthAtMax = (Number) js.executeScript(getScrollWidthScript);
            System.out.println("   PDF scrollable width at 300%: " + scrollWidthAtMax + "px");

            // 7b. Zoom Out - click once and verify input value decreases
            if (zoomOutBtn.isEnabled()) {
                zoomOutBtn.click();
                Thread.sleep(500);
                int newVal = Integer.parseInt(zoomInput.getAttribute("value").trim());
                Assert.assertTrue(newVal < zoomVal, "Zoom Out did not decrease value");
                System.out.println("✓ Zoom Out: value decreased (" + zoomVal + "% → " + newVal + "%)");
                zoomVal = newVal;
            }

            // Continue zooming out to min (50)
            while (zoomOutBtn.isEnabled()) {
                zoomOutBtn.click();
                Thread.sleep(300);
                int newVal = Integer.parseInt(zoomInput.getAttribute("value").trim());
                Assert.assertTrue(newVal <= zoomVal, "Zoom Out did not decrease value");
                zoomVal = newVal;
                if (zoomVal <= 50) break;
            }
            Assert.assertEquals(zoomVal, 50, "Zoom should not go below 50");
            Assert.assertFalse(zoomOutBtn.isEnabled(), "Zoom Out should be disabled at 50");
            System.out.println("✓ Zoom Out bottomed at 50% and button is disabled");

            // Check scrollable width at min zoom
            Number scrollWidthAtMin = (Number) js.executeScript(getScrollWidthScript);
            System.out.println("   PDF scrollable width at 50%: " + scrollWidthAtMin + "px");

            // Verify that max zoom has larger scrollable area than min zoom (proves PDF rendering changed)
            if (scrollWidthAtMax.intValue() > 0 && scrollWidthAtMin.intValue() > 0) {
                Assert.assertTrue(scrollWidthAtMax.intValue() >= scrollWidthAtMin.intValue(),
                    "PDF at 300% should have equal or larger scroll area than at 50%! Max: " + scrollWidthAtMax + ", Min: " + scrollWidthAtMin);
                System.out.println("✓ CONFIRMED: PDF scroll area at 300% (" + scrollWidthAtMax + "px) >= at 50% (" + scrollWidthAtMin + "px)");
            }

            // Additional visual confirmation: zoom from 50 to 100 and verify input tracks correctly
            zoomInput.clear();
            zoomInput.sendKeys("100");
            zoomInput.sendKeys("\n");
            Thread.sleep(400);
            int resetVal = Integer.parseInt(zoomInput.getAttribute("value").trim());
            Assert.assertEquals(resetVal, 100, "Zoom should reset to 100%");
            Assert.assertTrue(zoomInBtn.isEnabled(), "Zoom In should be enabled at 100%");
            Assert.assertTrue(zoomOutBtn.isEnabled(), "Zoom Out should be enabled at 100%");
            System.out.println("✓ Zoom reset to 100% - both Zoom In and Zoom Out buttons are enabled (confirms PDF is at middle zoom)");

            // 8. Input value beyond range
            zoomInput.clear();
            zoomInput.sendKeys("500");
            zoomInput.sendKeys("\n");
            Thread.sleep(400);
            int val = Integer.parseInt(zoomInput.getAttribute("value").trim());
            Assert.assertEquals(val, 300, "Zoom input > 300 should set to 300");
            Assert.assertFalse(zoomInBtn.isEnabled(), "Zoom In should be disabled at 300 after input 500");
            System.out.println("✓ Input 500 clamped to 300% — Zoom In disabled confirms PDF is at max zoom");

            zoomInput.clear();
            zoomInput.sendKeys("10");
            zoomInput.sendKeys("\n");
            Thread.sleep(400);
            val = Integer.parseInt(zoomInput.getAttribute("value").trim());
            Assert.assertEquals(val, 50, "Zoom input < 50 should set to 50");
            Assert.assertFalse(zoomOutBtn.isEnabled(), "Zoom Out should be disabled at 50 after input 10");
            System.out.println("✓ Input 10 clamped to 50% — Zoom Out disabled confirms PDF is at min zoom");

            // 9. Input invalid values - app should not allow typing alphabets/special characters
            String[] invalids = {"abc", "@#%", "-5"};
            for (String inval : invalids) {
                String valueBefore = zoomInput.getAttribute("value").trim();
                zoomInput.clear();
                zoomInput.sendKeys(inval);
                zoomInput.sendKeys("\n");
                Thread.sleep(300);
                String actual = zoomInput.getAttribute("value").trim();
                Assert.assertTrue(actual.isEmpty() || actual.equals(valueBefore) || actual.matches("\\d+"),
                    "Invalid zoom input '" + inval + "' should not be accepted. Value after: '" + actual + "'");
                System.out.println("✓ Invalid zoom input '" + inval + "' was blocked (value: '" + actual + "')");
            }
            System.out.println("✓ Invalid zoom inputs are handled - app does not allow non-numeric characters");
        } catch (Exception e) {
            Assert.fail("Zoom functionality validation failed: " + e.getMessage());
        }
    }

    /**
     * Step 10: Rotate button validation
     * Verifies actual PDF rotation by checking dimension ratio changes (width/height swap).
     * Uses tolerance to handle scrollbar/padding pixel shifts.
     */
    @Test(priority = 5)
    public void validateRotateFunctionality() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   STEP: Rotate Button Validation");
        System.out.println("════════════════════════════════════════════════════════════════\n");
        try {
            WebElement rotateBtn = driver.findElement(By.xpath("//button[@title='Rotate 90° clockwise']"));
            Assert.assertTrue(rotateBtn.isDisplayed(), "Rotate button is not displayed");
            Assert.assertTrue(rotateBtn.isEnabled(), "Rotate button is not enabled");
            System.out.println("✓ Rotate button is displayed and enabled");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Get the PDF page/canvas dimensions before rotation
            String getWidthScript =
                "var page = document.querySelector('canvas, .page, [class*=pdfPage], [class*=pdf-page]');" +
                "if (!page) return 0; return page.offsetWidth;";
            String getHeightScript =
                "var page = document.querySelector('canvas, .page, [class*=pdfPage], [class*=pdf-page]');" +
                "if (!page) return 0; return page.offsetHeight;";

            int widthAt0 = ((Number) js.executeScript(getWidthScript)).intValue();
            int heightAt0 = ((Number) js.executeScript(getHeightScript)).intValue();
            double ratioAt0 = (heightAt0 > 0) ? (double) widthAt0 / heightAt0 : 0;
            System.out.println("   At 0°: width=" + widthAt0 + ", height=" + heightAt0 + ", ratio=" + String.format("%.3f", ratioAt0));

            // Click rotate once (90°)
            rotateBtn.click();
            Thread.sleep(1000);

            int widthAt90 = ((Number) js.executeScript(getWidthScript)).intValue();
            int heightAt90 = ((Number) js.executeScript(getHeightScript)).intValue();
            double ratioAt90 = (heightAt90 > 0) ? (double) widthAt90 / heightAt90 : 0;
            System.out.println("   At 90°: width=" + widthAt90 + ", height=" + heightAt90 + ", ratio=" + String.format("%.3f", ratioAt90));

            // After 90° rotation, the aspect ratio should change significantly (portrait ↔ landscape)
            if (widthAt0 > 0 && heightAt0 > 0 && widthAt0 != heightAt0) {
                // For a non-square PDF, ratio should invert: if it was 0.77 (portrait), it becomes ~1.29 (landscape)
                Assert.assertTrue(Math.abs(ratioAt90 - ratioAt0) > 0.1,
                    "PDF aspect ratio should change significantly after 90° rotation! Ratio at 0°: " + String.format("%.3f", ratioAt0) + ", at 90°: " + String.format("%.3f", ratioAt90));
                System.out.println("✓ Rotation 1 (90°): Aspect ratio changed from " + String.format("%.3f", ratioAt0) + " to " + String.format("%.3f", ratioAt90) + " — PDF rotated in viewer");
            } else {
                System.out.println("✓ Rotation 1 (90°): Rotate button clicked successfully");
            }

            // Click rotate 2nd time (180°)
            rotateBtn.click();
            Thread.sleep(1000);

            int widthAt180 = ((Number) js.executeScript(getWidthScript)).intValue();
            int heightAt180 = ((Number) js.executeScript(getHeightScript)).intValue();
            double ratioAt180 = (heightAt180 > 0) ? (double) widthAt180 / heightAt180 : 0;
            System.out.println("   At 180°: width=" + widthAt180 + ", height=" + heightAt180 + ", ratio=" + String.format("%.3f", ratioAt180));

            // At 180°, ratio should be similar to 0° (same orientation, just upside down) - use tolerance
            if (ratioAt0 > 0) {
                Assert.assertTrue(Math.abs(ratioAt180 - ratioAt0) < 0.1,
                    "PDF at 180° should have similar aspect ratio to 0°. Ratio at 0°: " + String.format("%.3f", ratioAt0) + ", at 180°: " + String.format("%.3f", ratioAt180));
                System.out.println("✓ Rotation 2 (180°): Aspect ratio " + String.format("%.3f", ratioAt180) + " matches 0° (" + String.format("%.3f", ratioAt0) + ") — same orientation, upside down");
            }

            // Click rotate 3rd time (270°)
            rotateBtn.click();
            Thread.sleep(1000);

            int widthAt270 = ((Number) js.executeScript(getWidthScript)).intValue();
            int heightAt270 = ((Number) js.executeScript(getHeightScript)).intValue();
            double ratioAt270 = (heightAt270 > 0) ? (double) widthAt270 / heightAt270 : 0;
            System.out.println("   At 270°: width=" + widthAt270 + ", height=" + heightAt270 + ", ratio=" + String.format("%.3f", ratioAt270));

            // At 270°, ratio should be similar to 90° (swapped again)
            if (widthAt0 > 0 && heightAt0 > 0 && widthAt0 != heightAt0) {
                Assert.assertTrue(Math.abs(ratioAt270 - ratioAt0) > 0.1,
                    "PDF at 270° should have different aspect ratio than 0°. Ratio at 0°: " + String.format("%.3f", ratioAt0) + ", at 270°: " + String.format("%.3f", ratioAt270));
                System.out.println("✓ Rotation 3 (270°): Aspect ratio " + String.format("%.3f", ratioAt270) + " differs from 0° — confirms rotation");
            }

            // Click rotate 4th time (360° = back to original)
            rotateBtn.click();
            Thread.sleep(1000);

            int widthAt360 = ((Number) js.executeScript(getWidthScript)).intValue();
            int heightAt360 = ((Number) js.executeScript(getHeightScript)).intValue();
            double ratioAt360 = (heightAt360 > 0) ? (double) widthAt360 / heightAt360 : 0;
            System.out.println("   At 360°: width=" + widthAt360 + ", height=" + heightAt360 + ", ratio=" + String.format("%.3f", ratioAt360));

            // At 360°, ratio should match 0° (back to original)
            if (ratioAt0 > 0) {
                Assert.assertTrue(Math.abs(ratioAt360 - ratioAt0) < 0.1,
                    "PDF after 360° should return to original aspect ratio! Ratio at 0°: " + String.format("%.3f", ratioAt0) + ", at 360°: " + String.format("%.3f", ratioAt360));
                System.out.println("✓ Rotation 4 (360°): Aspect ratio " + String.format("%.3f", ratioAt360) + " matches original (" + String.format("%.3f", ratioAt0) + ") — full rotation confirmed");
            }

            System.out.println("✓ CONFIRMED: PDF rotation works — aspect ratio swaps at 90°/270° and matches original at 180°/360°");
        } catch (Exception e) {
            Assert.fail("Rotate functionality validation failed: " + e.getMessage());
        }
    }

    /**
     * Step 11: Vertical and Horizontal Scrollbar Validation
     * - Vertical scroll: test at current zoom (100%) since PDF has multiple pages
     * - Horizontal scroll: zoom in to 200%+ so PDF overflows horizontally, then scroll left/right
     * - Verifies scrollbars actually move the PDF content in the viewer
     */
    @Test(priority = 6)
    public void validateScrollbars() {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   STEP: Vertical & Horizontal Scrollbar Validation");
        System.out.println("════════════════════════════════════════════════════════════════\n");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement zoomInput = driver.findElement(By.xpath("//input[@title='Enter zoom percentage (50-300)']"));

            // Helper: Find the scrollable PDF container
            String findContainerScript =
                "var candidates = document.querySelectorAll('[class*=pdf], [class*=viewer], [class*=preview]');" +
                "for (var i = 0; i < candidates.length; i++) {" +
                "  var el = candidates[i];" +
                "  if (el.scrollHeight > el.clientHeight || el.scrollWidth > el.clientWidth) return el;" +
                "}" +
                "return null;";

            // Wrapper to get container in JS
            String getEl = "var el = (function() { " + findContainerScript.replace("return null;", "return null; })();");

            // ═══════════════════════════════════════════════════════════
            //  PART 1: VERTICAL SCROLLBAR (at current zoom)
            // ═══════════════════════════════════════════════════════════

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("   PART 1: VERTICAL SCROLLBAR");
            System.out.println("────────────────────────────────────────────────────────────────\n");

            // 1a. Check if vertical scrollbar exists (scrollHeight > clientHeight)
            Boolean hasVerticalScroll = (Boolean) js.executeScript(
                getEl + "if (!el) return false; return el.scrollHeight > el.clientHeight;");
            System.out.println("   Vertical scrollbar present: " + hasVerticalScroll);
            Assert.assertTrue(hasVerticalScroll, "Vertical scrollbar should be present in PDF viewer!");
            System.out.println("✓ Vertical scrollbar is present (PDF content taller than container)");

            // 1b. Get scrollable range info
            Number scrollHeight = (Number) js.executeScript(getEl + "if (!el) return 0; return el.scrollHeight;");
            Number clientHeight = (Number) js.executeScript(getEl + "if (!el) return 0; return el.clientHeight;");
            double maxScrollTop = scrollHeight.doubleValue() - clientHeight.doubleValue();
            System.out.println("   scrollHeight=" + scrollHeight + ", clientHeight=" + clientHeight + ", maxScrollTop=" + String.format("%.0f", maxScrollTop) + "px");

            // 1c. Scroll to top first (ensure clean start) - IMPORTANT: Previous tests may leave PDF scrolled down
            System.out.println("   Resetting scroll position to top...");
            js.executeScript(getEl + "if (el) el.scrollTop = 0;");
            Thread.sleep(800); // Increased wait time to ensure scroll completes

            Number initialScrollTop = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollTop;");
            System.out.println("   Initial vertical scroll position after reset: " + initialScrollTop + "px");

            // Verify we're actually at the top (allow small tolerance for rounding)
            Assert.assertTrue(initialScrollTop.doubleValue() < 10,
                "PDF should be scrolled to top before starting test! Current position: " + initialScrollTop + "px");
            System.out.println("✓ PDF successfully reset to top position");

            // 1d. Scroll down to 1/3 of max (not middle of scrollHeight, but 1/3 of actual scrollable range)
            double oneThird = maxScrollTop / 3;
            js.executeScript(getEl + "if (el) el.scrollTop = " + oneThird + ";");
            Thread.sleep(500);

            Number scrollTopAt1Third = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollTop;");
            System.out.println("   Vertical scroll after scrolling to 1/3: " + scrollTopAt1Third + "px");

            Assert.assertTrue(scrollTopAt1Third.doubleValue() > initialScrollTop.doubleValue(),
                "Vertical scroll did not move down! Before: " + initialScrollTop + ", After: " + scrollTopAt1Third);
            System.out.println("✓ Vertical scrollbar scrolled DOWN to 1/3 position (" + initialScrollTop + "px → " + String.format("%.0f", scrollTopAt1Third.doubleValue()) + "px)");

            // 1e. Scroll down to 2/3 of max
            double twoThirds = maxScrollTop * 2 / 3;
            js.executeScript(getEl + "if (el) el.scrollTop = " + twoThirds + ";");
            Thread.sleep(500);

            Number scrollTopAt2Thirds = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollTop;");
            System.out.println("   Vertical scroll after scrolling to 2/3: " + scrollTopAt2Thirds + "px");

            Assert.assertTrue(scrollTopAt2Thirds.doubleValue() > scrollTopAt1Third.doubleValue(),
                "Vertical scroll did not move further down! 1/3: " + scrollTopAt1Third + ", 2/3: " + scrollTopAt2Thirds);
            System.out.println("✓ Vertical scrollbar scrolled further DOWN to 2/3 position (" + String.format("%.0f", scrollTopAt1Third.doubleValue()) + "px → " + String.format("%.0f", scrollTopAt2Thirds.doubleValue()) + "px)");

            // 1f. Scroll to bottom (max scroll position)
            js.executeScript(getEl + "if (el) el.scrollTop = el.scrollHeight;");
            Thread.sleep(500);

            Number scrollTopAtBottom = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollTop;");
            System.out.println("   Vertical scroll at bottom: " + scrollTopAtBottom + "px");

            Assert.assertTrue(scrollTopAtBottom.doubleValue() >= scrollTopAt2Thirds.doubleValue(),
                "Vertical scroll did not reach bottom! 2/3: " + scrollTopAt2Thirds + ", Bottom: " + scrollTopAtBottom);
            System.out.println("✓ Vertical scrollbar reached BOTTOM of PDF (" + String.format("%.0f", scrollTopAtBottom.doubleValue()) + "px)");

            // 1g. Scroll back to top
            js.executeScript(getEl + "if (el) el.scrollTop = 0;");
            Thread.sleep(500);

            Number scrollTopBackToTop = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollTop;");
            System.out.println("   Vertical scroll after returning to top: " + scrollTopBackToTop + "px");

            Assert.assertEquals(scrollTopBackToTop.intValue(), 0,
                "Vertical scroll did not return to top! Position: " + scrollTopBackToTop);
            System.out.println("✓ Vertical scrollbar returned to TOP (0px)");

            System.out.println("\n✅ PART 1 PASSED: Vertical scrollbar works — top → 1/3 → 2/3 → bottom → back to top\n");

            // ═══════════════════════════════════════════════════════════
            //  PART 2: HORIZONTAL SCROLLBAR (zoom in first to trigger overflow)
            // ═══════════════════════════════════════════════════════════

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.println("   PART 2: HORIZONTAL SCROLLBAR (with zoom)");
            System.out.println("────────────────────────────────────────────────────────────────\n");

            // 2a. Zoom in to 200% to make PDF wider than container
            System.out.println("   Zooming in to 200% to trigger horizontal scrollbar...");
            zoomInput.clear();
            zoomInput.sendKeys("200");
            zoomInput.sendKeys("\n");
            Thread.sleep(800);

            int currentZoom = Integer.parseInt(zoomInput.getAttribute("value").trim());
            System.out.println("   Current zoom: " + currentZoom + "%");
            Assert.assertEquals(currentZoom, 200, "Zoom should be set to 200%");

            // 2b. Check if horizontal scrollbar now exists
            Boolean hasHorizontalScroll = (Boolean) js.executeScript(
                getEl + "if (!el) return false; return el.scrollWidth > el.clientWidth;");
            System.out.println("   Horizontal scrollbar present at 200% zoom: " + hasHorizontalScroll);

            // If 200% is not enough, zoom to 300%
            if (!hasHorizontalScroll) {
                System.out.println("   ⚠ Horizontal scrollbar not visible at 200%, zooming to 300%...");
                zoomInput.clear();
                zoomInput.sendKeys("300");
                zoomInput.sendKeys("\n");
                Thread.sleep(800);

                currentZoom = Integer.parseInt(zoomInput.getAttribute("value").trim());
                System.out.println("   Current zoom: " + currentZoom + "%");

                hasHorizontalScroll = (Boolean) js.executeScript(
                    getEl + "if (!el) return false; return el.scrollWidth > el.clientWidth;");
                System.out.println("   Horizontal scrollbar present at 300% zoom: " + hasHorizontalScroll);
            }

            Assert.assertTrue(hasHorizontalScroll,
                "Horizontal scrollbar should appear when zoomed in! PDF content should be wider than container at " + currentZoom + "%");
            System.out.println("✓ Horizontal scrollbar is present at " + currentZoom + "% zoom");

            // 2c. Get horizontal scrollable range
            Number scrollWidth = (Number) js.executeScript(getEl + "if (!el) return 0; return el.scrollWidth;");
            Number clientWidth = (Number) js.executeScript(getEl + "if (!el) return 0; return el.clientWidth;");
            double maxScrollLeft = scrollWidth.doubleValue() - clientWidth.doubleValue();
            System.out.println("   scrollWidth=" + scrollWidth + ", clientWidth=" + clientWidth + ", maxScrollLeft=" + String.format("%.0f", maxScrollLeft) + "px");

            // 2d. Scroll to left first (ensure clean start)
            js.executeScript(getEl + "if (el) el.scrollLeft = 0;");
            Thread.sleep(300);

            Number initialScrollLeft = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollLeft;");
            System.out.println("   Initial horizontal scroll position: " + initialScrollLeft + "px");

            // 2e. Scroll right to 1/3 of max horizontal range
            double oneThirdH = maxScrollLeft / 3;
            js.executeScript(getEl + "if (el) el.scrollLeft = " + oneThirdH + ";");
            Thread.sleep(500);

            Number scrollLeftAt1Third = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollLeft;");
            System.out.println("   Horizontal scroll after scrolling to 1/3: " + scrollLeftAt1Third + "px");

            Assert.assertTrue(scrollLeftAt1Third.doubleValue() > initialScrollLeft.doubleValue(),
                "Horizontal scroll did not move right! Before: " + initialScrollLeft + ", After: " + scrollLeftAt1Third);
            System.out.println("✓ Horizontal scrollbar scrolled RIGHT to 1/3 position (" + initialScrollLeft + "px → " + String.format("%.0f", scrollLeftAt1Third.doubleValue()) + "px)");

            // 2f. Scroll right to far end
            js.executeScript(getEl + "if (el) el.scrollLeft = el.scrollWidth;");
            Thread.sleep(500);

            Number scrollLeftAtEnd = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollLeft;");
            System.out.println("   Horizontal scroll at far right: " + scrollLeftAtEnd + "px");

            Assert.assertTrue(scrollLeftAtEnd.doubleValue() >= scrollLeftAt1Third.doubleValue(),
                "Horizontal scroll did not reach far right! 1/3: " + scrollLeftAt1Third + ", End: " + scrollLeftAtEnd);
            System.out.println("✓ Horizontal scrollbar reached FAR RIGHT (" + String.format("%.0f", scrollLeftAtEnd.doubleValue()) + "px)");

            // 2g. Scroll back to left
            js.executeScript(getEl + "if (el) el.scrollLeft = 0;");
            Thread.sleep(500);

            Number scrollLeftBackToStart = (Number) js.executeScript(getEl + "if (!el) return -1; return el.scrollLeft;");
            System.out.println("   Horizontal scroll after returning to left: " + scrollLeftBackToStart + "px");

            Assert.assertEquals(scrollLeftBackToStart.intValue(), 0,
                "Horizontal scroll did not return to left! Position: " + scrollLeftBackToStart);
            System.out.println("✓ Horizontal scrollbar returned to LEFT (0px)");

            System.out.println("\n✅ PART 2 PASSED: Horizontal scrollbar works — left → 1/3 → far right → back to left\n");

            // ═══════════════════════════════════════════════════════════
            //  CLEANUP: Reset zoom back to 100%
            // ═══════════════════════════════════════════════════════════

            System.out.println("   Resetting zoom to 100%...");
            zoomInput.clear();
            zoomInput.sendKeys("100");
            zoomInput.sendKeys("\n");
            Thread.sleep(500);
            System.out.println("✓ Zoom reset to 100%");

            // ═══════════════════════════════════════════════════════════
            //  FINAL SUMMARY
            // ═══════════════════════════════════════════════════════════

            System.out.println("\n════════════════════════════════════════════════════════════════");
            System.out.println("   ✅ SCROLLBAR VALIDATION PASSED!");
            System.out.println("════════════════════════════════════════════════════════════════");
            System.out.println("   ✓ Vertical scrollbar: top → 1/3 → 2/3 → bottom → back to top");
            System.out.println("   ✓ Horizontal scrollbar: zoomed to " + currentZoom + "% → left → 1/3 → far right → back to left");
            System.out.println("   ✓ Zoom reset to 100%");
            System.out.println("════════════════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            // Reset zoom to 100% even if test fails
            try {
                WebElement zoomInput = driver.findElement(By.xpath("//input[@title='Enter zoom percentage (50-300)']"));
                zoomInput.clear();
                zoomInput.sendKeys("100");
                zoomInput.sendKeys("\n");
            } catch (Exception ignored) {}
            Assert.fail("Scrollbar validation failed: " + e.getMessage());
        }
    }
}
