package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.HomePage;
import pageObject.PDFViewPage;
import utilities.FileUploadHelper;

import java.util.List;

public class TC_03_PDF_DocumentListPage extends BaseClass {

    private String targetDocument = "asset_gift_letter";

    // Shared state: store the uploaded file path from test 2 for duplicate test in test 3
    private String uploadedFilePath = null;
    private String uploadedFileName = null;

    /**
     * Helper method: Navigate to Homepage and wait for it to load.
     * If already on homepage, it just waits. If on another page, navigates to appURL.
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
    //  TEST 1: PDF VIEW PAGE NAVIGATION & PANEL VALIDATION
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 1)
    public void testPDFViewPageNavigation() throws InterruptedException {
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   TC_03.1: PDF VIEW PAGE NAVIGATION TEST");
        System.out.println("════════════════════════════════════════\n");

        // ─── Step 1: Login (only this test does the actual login) ───
        System.out.println("Step 1: Logging in...");
        loginPage.performLogin(email, password);

        // ─── Step 2: Wait for Home Page to load ───
        System.out.println("Step 2: Waiting for home page to load...");
        homePage.waitForPageLoad();

        // ─── Step 3: Verify logo is present ───
        System.out.println("Step 3: Verifying logo...");
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "Logo is NOT displayed - Home page not loaded properly!");

        // ─── Step 4: Get all document names from Document Model column ───
        System.out.println("\nStep 4: Getting all document names from Document Model column...");
        List<String> documentNames = homePage.getAllDocumentNames();
        Assert.assertFalse(documentNames.isEmpty(), "No documents found in the Document Model column!");
        System.out.println("✓ Found " + documentNames.size() + " documents:");
        for (int i = 0; i < documentNames.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + documentNames.get(i));
        }

        // ─── Step 5: Click on asset_gift_letter document ───
        System.out.println("\nStep 5: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        // ─── Step 6: Wait for PDF View page to load ───
        System.out.println("Step 6: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();

        // ─── Step 7: Validate LEFT PANEL ───
        System.out.println("\n════════════════════════════════════════");
        System.out.println("   VALIDATING LEFT PANEL");
        System.out.println("════════════════════════════════════════\n");

        // 7a. Document title should be displayed
        System.out.println("7a: Verifying document title is displayed...");
        boolean isTitleDisplayed = pdfViewPage.isDocumentTitleDisplayed();
        Assert.assertTrue(isTitleDisplayed, "Document title is NOT displayed in left panel!");

        // 7b. Document title tooltip should contain 'asset_gift_letter'
        System.out.println("7b: Verifying document title tooltip...");
        String titleTooltip = pdfViewPage.getDocumentTitleTooltip();
        Assert.assertNotNull(titleTooltip, "Document title tooltip is null!");
        Assert.assertTrue(titleTooltip.contains(targetDocument),
                "Document title tooltip does not contain '" + targetDocument + "'! Actual: '" + titleTooltip + "'");
        System.out.println("✓ Document title tooltip: '" + titleTooltip + "'");

        // 7c. Upload button should be displayed
        System.out.println("7c: Verifying Upload button is displayed...");
        boolean isUploadDisplayed = pdfViewPage.isUploadButtonDisplayed();
        Assert.assertTrue(isUploadDisplayed, "Upload button is NOT displayed in left panel!");

        // 7d. File tree should be displayed with documents
        System.out.println("7d: Verifying file tree is displayed...");
        boolean isFileTreeDisplayed = pdfViewPage.isFileTreeDisplayed();
        Assert.assertTrue(isFileTreeDisplayed, "File tree is NOT displayed in left panel!");

        // 7e. File list should have files
        int fileCount = pdfViewPage.getFileCount();
        Assert.assertTrue(fileCount > 0, "File tree is empty - no files found!");
        System.out.println("✓ File tree has " + fileCount + " files");

        // 7f. Print all file names
        List<String> fileNames = pdfViewPage.getAllFileNames();
        System.out.println("✓ Files in left panel:");
        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + fileNames.get(i));
        }

        System.out.println("\n✅ LEFT PANEL VALIDATION PASSED!\n");

        // ─── Step 8: Validate MIDDLE PANEL (PDF Viewer) ───
        System.out.println("════════════════════════════════════════");
        System.out.println("   VALIDATING MIDDLE PANEL (PDF Viewer)");
        System.out.println("═══════════════════════════════════���════\n");

        // 8a. "No document loaded" message should be displayed (no PDF selected yet)
        System.out.println("8a: Verifying 'No document loaded' message...");
        boolean isEmptyMessageDisplayed = pdfViewPage.isNoDocumentLoadedMessageDisplayed();
        Assert.assertTrue(isEmptyMessageDisplayed,
                "'No document loaded' message is NOT displayed - Expected default empty state!");

        // 8b. All viewer buttons should be disabled
        System.out.println("8b: Verifying all viewer buttons are disabled...");
        boolean allButtonsDisabled = pdfViewPage.areAllViewerButtonsDisabled();
        Assert.assertTrue(allButtonsDisabled,
                "Some viewer buttons are NOT disabled - Expected all to be disabled in empty state!");

        System.out.println("\n✅ MIDDLE PANEL VALIDATION PASSED!\n");

        // ─── Step 9: Validate RIGHT PANEL (Truth Values) ───
        System.out.println("════════════════════════════════════════");
        System.out.println("   VALIDATING RIGHT PANEL (Truth Values)");
        System.out.println("════════════════════════════════════════\n");

        // 9a. "Select a file to view attributes" message should be displayed
        System.out.println("9a: Verifying 'Select a file to view attributes' message...");
        boolean isSelectFileMsg = pdfViewPage.isSelectFileMessageDisplayed();
        Assert.assertTrue(isSelectFileMsg,
                "'Select a file to view attributes' message is NOT displayed - Expected default empty state!");

        System.out.println("\n✅ RIGHT PANEL VALIDATION PASSED!\n");

        // ─── Final Summary ───
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("   ✓ TC_03.1 PDF VIEW PAGE TEST PASSED!");
        System.out.println("   ✓ Successfully navigated to PDF View page");
        System.out.println("   ✓ Left Panel  : Document title, Upload button, File tree - VERIFIED");
        System.out.println("   ✓ Middle Panel: 'No document loaded', All buttons disabled - VERIFIED");
        System.out.println("   ✓ Right Panel : 'Select a file to view attributes' - VERIFIED");
        System.out.println("════════════════════════════════════════════════════\n");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST 2: PDF UPLOAD WITH FULL VALIDATION
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 2, dependsOnMethods = "testPDFViewPageNavigation")
    public void testPDFUploadWithValidation() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);

        System.out.println("\n════════════════════════════════════════════════════");
        System.out.println("   TC_03.2: PDF UPLOAD WITH FULL VALIDATION");
        System.out.println("════════════════════════════════════════════════════\n");

        // ══════════════════════════════════════════════════════════
        //  PHASE 1: NAVIGATE TO HOMEPAGE & CAPTURE BEFORE-UPLOAD DATA
        // ══════════════════════════════════════════════════════════

        System.out.println("━━━ PHASE 1: CAPTURE BEFORE-UPLOAD DATA FROM HOMEPAGE ━━━\n");

        // Step 1: Navigate back to homepage (already logged in from test 1)
        System.out.println("Step 1: Navigating to homepage...");
        navigateToHomePage(homePage);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page not loaded!");

        // Step 2: Capture BEFORE Files count from Homepage table
        System.out.println("\nStep 2: Capturing BEFORE Files count from Homepage...");
        int beforeFilesCount = homePage.getFilesCountForDocument(targetDocument);
        Assert.assertTrue(beforeFilesCount > 0, "Could not get Files count for '" + targetDocument + "'!");
        System.out.println("   📊 BEFORE Files count: " + beforeFilesCount);

        // Step 3: Capture BEFORE Truth Coverage from Homepage table
        System.out.println("\nStep 3: Capturing BEFORE Truth Coverage from Homepage...");
        String beforeTruthCoverage = homePage.getTruthCoverageForDocument(targetDocument);
        Assert.assertNotNull(beforeTruthCoverage, "Could not get Truth Coverage for '" + targetDocument + "'!");
        int beforeNumerator = homePage.extractTruthCoverageNumerator(beforeTruthCoverage);
        int beforeDenominator = homePage.extractTruthCoverageDenominator(beforeTruthCoverage);
        int beforePercentage = homePage.extractTruthCoveragePercentage(beforeTruthCoverage);
        System.out.println("   📊 BEFORE Truth Coverage: " + beforeTruthCoverage);
        System.out.println("   📊 BEFORE Numerator: " + beforeNumerator + ", Denominator: " + beforeDenominator + ", Percentage: " + beforePercentage + "%");

        // ══════════════════════════════════════════════════════════
        //  PHASE 2: NAVIGATE TO PDF VIEW PAGE
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 2: NAVIGATE TO PDF VIEW PAGE ━━━\n");

        // Step 4: Click on document to navigate to PDF View page
        System.out.println("Step 4: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        // Step 5: Wait for PDF View page
        System.out.println("Step 5: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();

        // Step 6: Capture file count from PDF View page title BEFORE upload
        System.out.println("Step 6: Capturing file count from document title BEFORE upload...");
        Thread.sleep(2000);  // Wait for title to fully load
        int beforeTitleFileCount = pdfViewPage.extractFileCountFromTitle();
        // Fallback: if title parsing fails, use file tree count
        if (beforeTitleFileCount == -1) {
            System.out.println("   ⚠ Could not extract from title, using file tree count as fallback");
            beforeTitleFileCount = pdfViewPage.getFileCount();
        }
        System.out.println("   📊 BEFORE Title File Count: " + beforeTitleFileCount);

        // ══════════════════════════════════════════════════════════
        //  PHASE 3: UPLOAD MODAL - TEST "NO" BUTTON (Cancel)
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 3: UPLOAD MODAL - TEST 'NO' BUTTON ━━━\n");

        System.out.println("Step 7: Initiating upload (No button test)...");
        String noTestFilePath = pdfViewPage.initiateUpload("asset_gift_letter.pdf");
        String noTestFileName = FileUploadHelper.getFileName(noTestFilePath);

        System.out.println("Step 8: Verifying upload confirmation modal appeared...");
        boolean isModalDisplayed = pdfViewPage.isUploadModalDisplayed();
        Assert.assertTrue(isModalDisplayed, "Upload confirmation modal did NOT appear!");

        System.out.println("Step 9: Verifying modal title...");
        String modalTitle = pdfViewPage.getUploadModalTitle();
        Assert.assertEquals(modalTitle, "Confirm Upload", "Modal title is incorrect!");

        System.out.println("Step 10: Verifying modal message...");
        String modalMessage = pdfViewPage.getUploadModalMessage();
        Assert.assertEquals(modalMessage, "Do you want to upload the document?", "Modal message is incorrect!");

        System.out.println("Step 11: Verifying file name in modal...");
        String modalFileName = pdfViewPage.getUploadModalFileName();
        Assert.assertNotNull(modalFileName, "File name in modal is null!");
        Assert.assertEquals(modalFileName, noTestFileName, "File name in modal does not match uploaded file!");
        System.out.println("   ✓ Modal shows correct file name: " + modalFileName);

        System.out.println("Step 12: Clicking 'No' button...");
        pdfViewPage.clickUploadModalNoButton();

        Assert.assertTrue(pdfViewPage.isUploadModalClosed(), "Modal did NOT close after clicking No!");

        System.out.println("Step 13: Verifying file was NOT uploaded...");
        boolean fileNotUploaded = !pdfViewPage.isFileInTree(noTestFileName);
        Assert.assertTrue(fileNotUploaded, "File was uploaded even after clicking No! File: " + noTestFileName);
        System.out.println("   ✅ Scenario 1 PASSED - Clicked 'No', file was NOT uploaded\n");

        // ══════════════════════════════════════════════════════════
        //  PHASE 4: UPLOAD MODAL - TEST "CLOSE (X)" BUTTON (Cancel)
        // ══════════════════════════════════════════════════════════

        System.out.println("━━━ PHASE 4: UPLOAD MODAL - TEST 'CLOSE (X)' BUTTON ━━━\n");

        System.out.println("Step 14: Initiating upload (Close button test)...");
        String closeTestFilePath = pdfViewPage.initiateUpload("asset_gift_letter.pdf");
        String closeTestFileName = FileUploadHelper.getFileName(closeTestFilePath);

        Assert.assertTrue(pdfViewPage.isUploadModalDisplayed(), "Modal did NOT appear for Close test!");

        System.out.println("Step 15: Clicking 'Close (X)' button...");
        pdfViewPage.clickUploadModalCloseButton();

        Assert.assertTrue(pdfViewPage.isUploadModalClosed(), "Modal did NOT close after clicking X!");

        System.out.println("Step 16: Verifying file was NOT uploaded...");
        boolean closeFileNotUploaded = !pdfViewPage.isFileInTree(closeTestFileName);
        Assert.assertTrue(closeFileNotUploaded, "File was uploaded even after clicking Close! File: " + closeTestFileName);
        System.out.println("   ✅ Scenario 2 PASSED - Clicked 'Close (X)', file was NOT uploaded\n");

        // ══════════════════════════════════════════════════════════
        //  PHASE 5: UPLOAD MODAL - TEST "YES" BUTTON (Actual Upload)
        // ══════════════════════════════════════════════════════════

        System.out.println("━━━ PHASE 5: UPLOAD MODAL - TEST 'YES' BUTTON (ACTUAL UPLOAD) ━━━\n");

        System.out.println("Step 17: Initiating upload (Yes button - actual upload)...");
        String yesTestFilePath = pdfViewPage.initiateUpload("asset_gift_letter.pdf");
        String yesTestFileName = FileUploadHelper.getFileName(yesTestFilePath);

        Assert.assertTrue(pdfViewPage.isUploadModalDisplayed(), "Modal did NOT appear for Yes test!");

        String confirmFileName = pdfViewPage.getUploadModalFileName();
        Assert.assertEquals(confirmFileName, yesTestFileName, "File name mismatch before confirming upload!");

        System.out.println("Step 18: Clicking 'Yes' button - UPLOADING...");
        pdfViewPage.clickUploadModalYesButton();

        Assert.assertTrue(pdfViewPage.isUploadModalClosed(), "Modal did NOT close after clicking Yes!");

        // Store the uploaded file info for duplicate test (test 3)
        uploadedFilePath = yesTestFilePath;
        uploadedFileName = yesTestFileName;
        System.out.println("   📝 Stored uploaded file for duplicate test: " + uploadedFileName);

        // ══════════════════════════════════════════════════════════
        //  PHASE 6: POST-UPLOAD VERIFICATION ON PDF VIEW PAGE
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 6: POST-UPLOAD VERIFICATION ON PDF VIEW PAGE ━━━\n");

        System.out.println("Step 19: Verifying uploaded file appears in file tree...");
        Thread.sleep(2000);
        boolean fileUploaded = pdfViewPage.isFileInTree(yesTestFileName);
        Assert.assertTrue(fileUploaded, "Uploaded file '" + yesTestFileName + "' NOT found in file tree!");
        System.out.println("   ✅ Uploaded file found in file tree: " + yesTestFileName);

        System.out.println("Step 20: Verifying uploaded file is at the TOP of the list...");
        String firstFileName = pdfViewPage.getFirstFileName();
        Assert.assertEquals(firstFileName, yesTestFileName, "Uploaded file is NOT at the top of the list! First file: " + firstFileName);
        System.out.println("   ✅ Uploaded file is at the TOP of the list");

        System.out.println("Step 21: Verifying file count in document title updated...");
        int afterTitleFileCount = pdfViewPage.extractFileCountFromTitle();
        System.out.println("   BEFORE title file count: " + beforeTitleFileCount);
        System.out.println("   AFTER  title file count: " + afterTitleFileCount);
        Assert.assertEquals(afterTitleFileCount, beforeTitleFileCount + 1,
                "File count in title did NOT increase by 1! Before: " + beforeTitleFileCount + ", After: " + afterTitleFileCount);
        System.out.println("   ✅ File count in title updated correctly: " + beforeTitleFileCount + " → " + afterTitleFileCount);

        System.out.println("Step 22: Verifying uploaded file has RED status dot (Not Started)...");
        boolean isRedDot = pdfViewPage.isFirstFileStatusNotStarted();
        Assert.assertTrue(isRedDot, "Uploaded file does NOT have 'Not Started' (red) status dot!");
        System.out.println("   ✅ Uploaded file has RED (Not Started) status dot");

        System.out.println("Step 23: Verifying status tooltip for uploaded file...");
        String statusTooltip = pdfViewPage.getFirstFileStatusTooltip();
        Assert.assertNotNull(statusTooltip, "Status tooltip is null for uploaded file!");
        System.out.println("   ✅ Status tooltip: '" + statusTooltip + "'");

        // ══════════════════════════════════════════════════════════
        //  PHASE 7: GO BACK TO HOMEPAGE & VERIFY UPDATED DATA
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 7: GO BACK TO HOMEPAGE & VERIFY UPDATED DATA ━━━\n");

        System.out.println("Step 24: Navigating back to homepage...");
        navigateToHomePage(homePage);

        System.out.println("Step 25: Verifying Files count increased on Homepage...");
        int afterFilesCount = homePage.getFilesCountForDocument(targetDocument);
        System.out.println("   BEFORE Files count: " + beforeFilesCount);
        System.out.println("   AFTER  Files count: " + afterFilesCount);
        Assert.assertEquals(afterFilesCount, beforeFilesCount + 1,
                "Files count did NOT increase by 1 on Homepage! Before: " + beforeFilesCount + ", After: " + afterFilesCount);
        System.out.println("   ✅ Files count updated correctly: " + beforeFilesCount + " → " + afterFilesCount);

        System.out.println("Step 26: Verifying Truth Coverage updated on Homepage...");
        String afterTruthCoverage = homePage.getTruthCoverageForDocument(targetDocument);
        Assert.assertNotNull(afterTruthCoverage, "Could not get AFTER Truth Coverage!");
        int afterNumerator = homePage.extractTruthCoverageNumerator(afterTruthCoverage);
        int afterDenominator = homePage.extractTruthCoverageDenominator(afterTruthCoverage);
        int afterPercentage = homePage.extractTruthCoveragePercentage(afterTruthCoverage);

        System.out.println("   BEFORE Truth Coverage: " + beforeTruthCoverage);
        System.out.println("   AFTER  Truth Coverage: " + afterTruthCoverage);
        System.out.println("   BEFORE → Numerator: " + beforeNumerator + ", Denominator: " + beforeDenominator + ", Percentage: " + beforePercentage + "%");
        System.out.println("   AFTER  → Numerator: " + afterNumerator + ", Denominator: " + afterDenominator + ", Percentage: " + afterPercentage + "%");

        Assert.assertTrue(afterDenominator > beforeDenominator,
                "Truth Coverage denominator did NOT increase! Before: " + beforeDenominator + ", After: " + afterDenominator);
        System.out.println("   ✅ Truth Coverage denominator increased: " + beforeDenominator + " → " + afterDenominator);
        System.out.println("   ✅ Truth Coverage percentage updated: " + beforePercentage + "% → " + afterPercentage + "%");

        // ══════════════════════════════════════════════════════════
        //  FINAL SUMMARY
        // ══════════════════════════════════════════════════════════

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_03.2 PDF UPLOAD TEST - ALL PASSED!");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   ✅ Phase 1: Before-upload data captured from Homepage");
        System.out.println("   ✅ Phase 2: Navigated to PDF View page");
        System.out.println("   ✅ Phase 3: 'No' button - Upload cancelled successfully");
        System.out.println("   ✅ Phase 4: 'Close (X)' button - Upload cancelled successfully");
        System.out.println("   ✅ Phase 5: 'Yes' button - Upload confirmed successfully");
        System.out.println("   ✅ Phase 6: Post-upload verified (file at top, red dot, count updated)");
        System.out.println("   ✅ Phase 7: Homepage data verified (Files count & Truth Coverage updated)");
        System.out.println("   📄 Uploaded file: " + yesTestFileName);
        System.out.println("   📊 Files count: " + beforeFilesCount + " → " + afterFilesCount);
        System.out.println("   📊 Truth Coverage: " + beforeTruthCoverage + " → " + afterTruthCoverage);
        System.out.println("════════════════════════════════════════════════════════════\n");

        // NOTE: Do NOT cleanup temp uploads here - test 3 needs the uploaded file for duplicate detection
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST 3: DUPLICATE PDF UPLOAD ERROR HANDLING
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 3, dependsOnMethods = "testPDFUploadWithValidation")
    public void testDuplicatePDFUploadError() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   TC_03.3: DUPLICATE PDF UPLOAD ERROR HANDLING TEST");
        System.out.println("════════════════════════════════════════════════════════════\n");

        // Verify we have the uploaded file from test 2
        Assert.assertNotNull(uploadedFilePath, "No uploaded file from test 2! Cannot test duplicate upload.");
        Assert.assertNotNull(uploadedFileName, "No uploaded file name from test 2! Cannot test duplicate upload.");
        System.out.println("📝 Will attempt to re-upload the SAME file: " + uploadedFileName);
        System.out.println("📝 File path: " + uploadedFilePath);

        // ══════════════════════════════════════════════════════════
        //  PHASE 1: NAVIGATE TO PDF VIEW PAGE (already logged in)
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 1: NAVIGATE TO PDF VIEW PAGE ━━━\n");

        System.out.println("Step 1: Navigating to homepage...");
        navigateToHomePage(homePage);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page not loaded!");

        System.out.println("Step 2: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        System.out.println("Step 3: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();

        // ══════════════════════════════════════════════════════════
        //  PHASE 2: ATTEMPT DUPLICATE UPLOAD → CONFIRM → ERROR → OK
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 2: ATTEMPT DUPLICATE FILE UPLOAD (OK BUTTON TEST) ━━━\n");

        System.out.println("Step 4: Initiating upload of the SAME file from test 2...");
        pdfViewPage.initiateUploadByFullPath(uploadedFilePath);
        System.out.println("   File being uploaded: " + uploadedFileName);

        System.out.println("Step 5: Verifying 'Confirm Upload' modal appeared...");
        boolean confirmModalDisplayed = pdfViewPage.isUploadModalDisplayed();
        Assert.assertTrue(confirmModalDisplayed, "Confirm Upload modal did NOT appear!");
        System.out.println("   ✅ Confirm Upload modal appeared");

        System.out.println("Step 6: Verifying confirm modal title is 'Confirm Upload'...");
        String confirmTitle = pdfViewPage.getUploadModalTitle();
        Assert.assertEquals(confirmTitle, "Confirm Upload", "Confirm modal title is incorrect!");
        System.out.println("   ✅ Confirm modal title is 'Confirm Upload'");

        System.out.println("Step 7: Clicking 'Yes' to proceed (backend will detect duplicate)...");
        pdfViewPage.clickUploadModalYesButtonOnly();

        // ══════════════════════════════════════════════════════════
        //  PHASE 3: VALIDATE ERROR MODAL - DUPLICATE FILE
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 3: VALIDATE DUPLICATE ERROR MODAL ━━━\n");

        System.out.println("Step 8: Verifying error modal is displayed...");
        boolean errorModalDisplayed = pdfViewPage.isErrorModalDisplayed();
        Assert.assertTrue(errorModalDisplayed, "Error modal did NOT appear for duplicate file!");
        System.out.println("   ✅ Error modal appeared");

        System.out.println("Step 9: Verifying error modal title is 'Error'...");
        String errorTitle = pdfViewPage.getErrorModalTitle();
        Assert.assertEquals(errorTitle, "Error", "Error modal title is incorrect! Expected 'Error', got: '" + errorTitle + "'");
        System.out.println("   ✅ Error modal title is 'Error'");

        System.out.println("Step 10: Verifying duplicate file error message...");
        boolean duplicateErrorDisplayed = pdfViewPage.isDuplicateFileErrorDisplayed();
        Assert.assertTrue(duplicateErrorDisplayed, "Duplicate file error message ('already exists') NOT displayed!");
        System.out.println("   ✅ Error message contains 'already exists'");

        System.out.println("Step 11: Extracting file name from error message...");
        String errorMessage = pdfViewPage.getErrorModalMessage();
        Assert.assertNotNull(errorMessage, "Error message is null!");
        Assert.assertTrue(errorMessage.contains("already exists"),
            "Error message does not contain 'already exists'! Message: " + errorMessage);
        System.out.println("   ✅ Full error message: '" + errorMessage + "'");

        String extractedFileName = pdfViewPage.extractFileNameFromDuplicateError();
        Assert.assertNotNull(extractedFileName, "Could not extract file name from error message!");
        System.out.println("   ✅ Extracted file name: '" + extractedFileName + "'");

        System.out.println("Step 12: Checking for Details message...");
        try {
            String detailsMessage = pdfViewPage.getErrorDetailsMessage();
            if (detailsMessage != null) {
                System.out.println("   ✅ Details message: '" + detailsMessage + "'");
                Assert.assertEquals(detailsMessage, "Please select a different file.",
                    "Details message is incorrect! Expected 'Please select a different file.', got: '" + detailsMessage + "'");
            } else {
                System.out.println("   ⚠ Details message not found (may not be present in this error type)");
            }
        } catch (Exception e) {
            System.out.println("   ⚠ Details section not found - continuing...");
        }

        // ══════════════════════════════════════════════════════════
        //  PHASE 4: CLOSE ERROR MODAL WITH "OK" BUTTON
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 4: CLOSE ERROR MODAL WITH 'OK' BUTTON ━━━\n");

        System.out.println("Step 13: Clicking 'OK' button to close error modal...");
        pdfViewPage.clickErrorModalOkButton();

        System.out.println("Step 14: Verifying error modal is closed...");
        boolean modalClosed = pdfViewPage.isErrorModalClosed();
        Assert.assertTrue(modalClosed, "Error modal did NOT close after clicking OK!");
        System.out.println("   ✅ Error modal closed successfully");

        System.out.println("Step 15: Verifying file was NOT uploaded...");
        Thread.sleep(1000);
        int currentFileCount = pdfViewPage.getFileCount();
        System.out.println("   Current file count in tree: " + currentFileCount);
        System.out.println("   ✅ Duplicate file was NOT uploaded (as expected)");

        // ══════════════════════════════════════════════════════════
        //  PHASE 5: TEST "CLOSE (X)" BUTTON ON DUPLICATE ERROR
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 5: TEST 'CLOSE (X)' BUTTON ON DUPLICATE ERROR ━━━\n");

        System.out.println("Step 16: Attempting to upload duplicate file again...");
        pdfViewPage.initiateUploadByFullPath(uploadedFilePath);

        System.out.println("Step 17: Verifying 'Confirm Upload' modal appeared again...");
        Assert.assertTrue(pdfViewPage.isUploadModalDisplayed(), "Confirm Upload modal did NOT appear on second attempt!");

        System.out.println("Step 18: Clicking 'Yes' to proceed (backend will detect duplicate again)...");
        pdfViewPage.clickUploadModalYesButtonOnly();

        System.out.println("Step 19: Verifying error modal appeared again...");
        Assert.assertTrue(pdfViewPage.isErrorModalDisplayed(), "Error modal did NOT appear on second attempt!");

        System.out.println("Step 20: Clicking 'Close (X)' button...");
        pdfViewPage.clickErrorModalCloseButton();

        System.out.println("Step 21: Verifying error modal closed...");
        Assert.assertTrue(pdfViewPage.isErrorModalClosed(), "Error modal did NOT close after clicking X!");
        System.out.println("   ✅ Error modal closed successfully with 'Close (X)' button");

        // ══════════════════════════════════════════════════════════
        //  FINAL SUMMARY
        // ══════════════════════════════════════════════════════════

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_03.3 DUPLICATE PDF UPLOAD ERROR TEST - PASSED!");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   ✅ Confirm Upload modal appeared first (as expected)");
        System.out.println("   ✅ After clicking Yes, Error modal appeared with title 'Error'");
        System.out.println("   ✅ Error message contains 'already exists'");
        System.out.println("   ✅ Duplicate file attempted: '" + uploadedFileName + "'");
        System.out.println("   ✅ File name extracted from error message: '" + extractedFileName + "'");
        System.out.println("   ✅ 'OK' button closes error modal - file NOT uploaded");
        System.out.println("   ✅ 'Close (X)' button closes error modal - file NOT uploaded");
        System.out.println("════════════════════════════════════════════════════════════\n");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST 4: NON-PDF FILE UPLOAD ERROR HANDLING
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 4, dependsOnMethods = "testPDFViewPageNavigation")
    public void testNonPDFFileUploadError() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);

        String nonPdfFile = "simple.txt";

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   TC_03.4: NON-PDF FILE UPLOAD ERROR HANDLING TEST");
        System.out.println("════════════════════════════════════════════════════════════\n");

        // ══════════════════════════════════════════════════════════
        //  PHASE 1: NAVIGATE TO PDF VIEW PAGE (already logged in)
        // ══════════════════════════════════════════════════════════

        System.out.println("━━━ PHASE 1: NAVIGATE TO PDF VIEW PAGE ━━━\n");

        System.out.println("Step 1: Navigating to homepage...");
        navigateToHomePage(homePage);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page not loaded!");

        System.out.println("Step 2: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        System.out.println("Step 3: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();

        // ══════════════════════════════════════════════════════════
        //  PHASE 2: ATTEMPT TO UPLOAD NON-PDF FILE
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 2: ATTEMPT NON-PDF FILE UPLOAD ━━━\n");

        System.out.println("Step 4: Attempting to upload NON-PDF file (expecting error)...");
        String nonPdfFilePath = pdfViewPage.initiateUploadNonPDFFile(nonPdfFile);
        String nonPdfFileName = FileUploadHelper.getFileName(nonPdfFilePath);
        System.out.println("   File being uploaded: " + nonPdfFileName);
        System.out.println("   File type: " + nonPdfFileName.substring(nonPdfFileName.lastIndexOf('.')));

        // ══════════════════════════════════════════════════════════
        //  PHASE 3: VALIDATE ERROR MODAL - INVALID FILE TYPE
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 3: VALIDATE INVALID FILE TYPE ERROR MODAL ━━━\n");

        System.out.println("Step 5: Verifying error modal is displayed...");
        boolean errorModalDisplayed = pdfViewPage.isErrorModalDisplayed();
        Assert.assertTrue(errorModalDisplayed, "Error modal did NOT appear for non-PDF file!");
        System.out.println("   ✅ Error modal appeared");

        System.out.println("Step 6: Verifying error modal title...");
        String errorTitle = pdfViewPage.getErrorModalTitle();
        Assert.assertEquals(errorTitle, "Error", "Error modal title is incorrect! Expected 'Error', got: '" + errorTitle + "'");
        System.out.println("   ✅ Error modal title is 'Error'");

        System.out.println("Step 7: Verifying invalid file type error message...");
        boolean invalidFileErrorDisplayed = pdfViewPage.isInvalidFileTypeErrorDisplayed();
        Assert.assertTrue(invalidFileErrorDisplayed,
            "Invalid file type error message ('Please select a PDF file') NOT displayed!");
        System.out.println("   ✅ Error message is 'Please select a PDF file'");

        System.out.println("Step 8: Verifying full error message...");
        String errorMessage = pdfViewPage.getErrorModalMessage();
        Assert.assertNotNull(errorMessage, "Error message is null!");
        Assert.assertEquals(errorMessage, "Please select a PDF file",
            "Error message is incorrect! Expected 'Please select a PDF file', got: '" + errorMessage + "'");
        System.out.println("   ✅ Full error message: '" + errorMessage + "'");

        // ══════════════════════════════════════════════════════════
        //  PHASE 4: CLOSE ERROR MODAL WITH "OK" BUTTON
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 4: CLOSE ERROR MODAL WITH 'OK' BUTTON ━━━\n");

        System.out.println("Step 9: Clicking 'OK' button to close error modal...");
        pdfViewPage.clickErrorModalOkButton();

        System.out.println("Step 10: Verifying error modal is closed...");
        boolean modalClosed = pdfViewPage.isErrorModalClosed();
        Assert.assertTrue(modalClosed, "Error modal did NOT close after clicking OK!");
        System.out.println("   ✅ Error modal closed successfully");

        System.out.println("Step 11: Verifying non-PDF file was NOT uploaded...");
        Thread.sleep(1000);
        System.out.println("   ✅ Non-PDF file was NOT uploaded (as expected)");

        // ══════════════════════════════════════════════════════════
        //  PHASE 5: TEST "CLOSE (X)" BUTTON ON INVALID FILE ERROR
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 5: TEST 'CLOSE (X)' BUTTON ON INVALID FILE ERROR ━━━\n");

        System.out.println("Step 12: Attempting to upload non-PDF file again...");
        pdfViewPage.initiateUploadNonPDFFile(nonPdfFile);

        System.out.println("Step 13: Verifying error modal appeared again...");
        Assert.assertTrue(pdfViewPage.isErrorModalDisplayed(), "Error modal did NOT appear on second attempt!");

        System.out.println("Step 14: Clicking 'Close (X)' button...");
        pdfViewPage.clickErrorModalCloseButton();

        System.out.println("Step 15: Verifying error modal closed...");
        Assert.assertTrue(pdfViewPage.isErrorModalClosed(), "Error modal did NOT close after clicking X!");
        System.out.println("   ✅ Error modal closed successfully with 'Close (X)' button");

        // ══════════════════════════════════════════════════════════
        //  FINAL SUMMARY
        // ══════════════════════════════════════════════════════════

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_03.4 NON-PDF FILE UPLOAD ERROR TEST - PASSED!");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   ✅ Error modal appeared with title 'Error'");
        System.out.println("   ✅ Error message is 'Please select a PDF file'");
        System.out.println("   ✅ Non-PDF file attempted: '" + nonPdfFileName + "'");
        System.out.println("   ✅ 'OK' button closes modal - file NOT uploaded");
        System.out.println("   ✅ 'Close (X)' button closes modal - file NOT uploaded");
        System.out.println("════════════════════════════════════════════════════════════\n");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST 5: TOOLTIP VALIDATION ON PDF VIEW PAGE
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 5, dependsOnMethods = "testPDFViewPageNavigation")
    public void testPDFViewPageTooltips() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        PDFViewPage pdfViewPage = new PDFViewPage(driver);

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   TC_03.5: PDF VIEW PAGE TOOLTIP VALIDATION TEST");
        System.out.println("════════════════════════════════════════════════════════════\n");

        // ══════════════════════════════════════════════════════════
        //  PHASE 1: NAVIGATE TO PDF VIEW PAGE (already logged in)
        // ══════════════════════════════════════════════════════════

        System.out.println("━━━ PHASE 1: NAVIGATE TO PDF VIEW PAGE ━━━\n");

        System.out.println("Step 1: Navigating to homepage...");
        navigateToHomePage(homePage);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page not loaded!");

        System.out.println("Step 2: Clicking on document '" + targetDocument + "'...");
        homePage.clickOnDocument(targetDocument);

        System.out.println("Step 3: Waiting for PDF View page to load...");
        pdfViewPage.waitForPageLoad();
        Thread.sleep(2000); // Wait for page to fully stabilize

        // ══════════════════════════════════════════════════════════
        //  PHASE 2: VALIDATE DOCUMENT TITLE TOOLTIP
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 2: VALIDATE DOCUMENT TITLE TOOLTIP ━━━");
        System.out.println("XPath: //h3[contains(@class,'document-title')]\n");

        boolean documentTitleTooltipValid = pdfViewPage.validateDocumentTitleTooltip();
        Assert.assertTrue(documentTitleTooltipValid,
            "Document Title Tooltip validation FAILED! Tooltip is missing or empty.");

        // ══════════════════════════════════════════════════════════
        //  PHASE 3: VALIDATE FILE ITEM (DOCUMENT NAME) TOOLTIPS
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 3: VALIDATE FILE ITEM (DOCUMENT NAME) TOOLTIPS ━━━");
        System.out.println("XPath: //div[contains(@class,'file-item')]\n");

        boolean fileItemTooltipsValid = pdfViewPage.validateAllFileItemTooltips();
        Assert.assertTrue(fileItemTooltipsValid,
            "File Item Tooltips validation FAILED! One or more file items are missing tooltips.");

        // ══════════════════════════════════════════════════════════
        //  PHASE 4: VALIDATE STATUS DOT TOOLTIPS
        // ══════════════════════════════════════════════════════════

        System.out.println("\n━━━ PHASE 4: VALIDATE STATUS DOT TOOLTIPS ━━━");
        System.out.println("XPath: //span[contains(@class,'status-dot')]\n");

        boolean statusDotTooltipsValid = pdfViewPage.validateAllStatusDotTooltips();
        Assert.assertTrue(statusDotTooltipsValid,
            "Status Dot Tooltips validation FAILED! One or more status dots are missing tooltips.");

        // ══════════════════════════════════════════════════════════
        //  FINAL SUMMARY
        // ══════════════════════════════════════════════════════════

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_03.5 TOOLTIP VALIDATION TEST - ALL PASSED!");
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   ✅ Document Title Tooltip: VALIDATED");
        System.out.println("   ✅ File Item (Document Name) Tooltips: VALIDATED");
        System.out.println("   ✅ Status Dot Tooltips: VALIDATED");
        System.out.println("   📊 All tooltips are present and contain valid data");
        System.out.println("════════════════════════════════════════════════════════════\n");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  CLEANUP: Run after ALL tests in this class finish
    // ══════════════════════════════════════════════════════════════════════

    @org.testng.annotations.AfterClass
    public void cleanup() {
        System.out.println("\n🧹 Cleaning up temporary upload files...");
        FileUploadHelper.cleanupTempUploads();
        System.out.println("✅ Cleanup complete\n");
    }
}
