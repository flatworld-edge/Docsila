package testCase;

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
}
