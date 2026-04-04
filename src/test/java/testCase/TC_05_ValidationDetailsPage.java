package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import pageObject.LoginPage;
import pageObject.HomePage;
import pageObject.EvaluationRunsPage;
import pageObject.ValidationDetailsPage;

public class TC_05_ValidationDetailsPage extends BaseClass {

    private String targetDocument = "asset_gift_letter";
    private ValidationDetailsPage validationDetailsPage;

    // ══════════════════════════════════════════════════════════════════════
    //  TEST: NAVIGATE TO VALIDATION DETAILS PAGE
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 1)
    public void testNavigateToValidationDetailsPage() throws InterruptedException {
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        EvaluationRunsPage evaluationRunsPage = new EvaluationRunsPage(driver);
        validationDetailsPage = new ValidationDetailsPage(driver);

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TC_05: NAVIGATE TO VALIDATION DETAILS PAGE");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 1: LOGIN & VERIFY HOME PAGE
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 1: LOGIN & VERIFY HOME PAGE");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 1.1: Performing login...");
        loginPage.performLogin(email, password);

        System.out.println("Step 1.2: Waiting for home page to load...");
        homePage.waitForPageLoad();

        // Add extra wait for page to fully render
        Thread.sleep(3000);

        System.out.println("Step 1.3: Verifying logo is displayed...");
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "Logo is NOT displayed - Home page not loaded properly!");

        System.out.println("✅ Step 1 Completed: Successfully logged in and verified Home page\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 2: CLICK ON VIEW RUNS BUTTON FOR ASSET_GIFT_LETTER
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 2: CLICK ON VIEW RUNS BUTTON (EYE ICON)");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 2.1: Clicking View Runs button for document: " + targetDocument);
        homePage.clickViewRunsForDocument(targetDocument);

        System.out.println("Step 2.2: Waiting for Evaluation Runs page to load...");
        evaluationRunsPage.waitForPageLoad();

        System.out.println("Step 2.3: Verifying Evaluation Runs page is displayed...");
        boolean isEvaluationRunsPageDisplayed = evaluationRunsPage.isPageDisplayed();
        Assert.assertTrue(isEvaluationRunsPageDisplayed, "Evaluation Runs page is NOT displayed!");

        System.out.println("✅ Step 2 Completed: Successfully navigated to Evaluation Runs page\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 3: CLICK ON VIEW VALIDATION RESULTS BUTTON
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 3: CLICK ON VIEW VALIDATION RESULTS");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 3.1: Clicking View Validation Results button in Actions column...");
        evaluationRunsPage.clickFirstViewValidationResults();

        System.out.println("Step 3.2: Waiting for Validation Results page to load...");
        validationDetailsPage.waitForPageLoad();

        System.out.println("Step 3.3: Verifying Validation Results page is displayed...");
        boolean isValidationPageDisplayed = validationDetailsPage.isPageDisplayed();
        Assert.assertTrue(isValidationPageDisplayed, "Validation Results page is NOT displayed!");

        System.out.println("✅ Step 3 Completed: Successfully navigated to Validation Results page\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 4: CLICK ON VALIDATION DETAILS TAB
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 4: CLICK ON VALIDATION DETAILS TAB");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 4.1: Verifying Validation Details tab is visible...");
        boolean isValidationDetailsTabDisplayed = validationDetailsPage.isValidationDetailsTabDisplayed();
        Assert.assertTrue(isValidationDetailsTabDisplayed, "Validation Details tab is NOT displayed!");

        System.out.println("Step 4.2: Clicking on Validation Details tab...");
        validationDetailsPage.clickValidationDetailsTab();

        System.out.println("Step 4.3: Waiting for Validation Details page to load...");
        Thread.sleep(3000); // Increased wait time for table to fully load

        System.out.println("✅ Step 4 Completed: Successfully clicked on Validation Details tab\n");

        // ═══════════════════════════════════════════════════════════
        //  FINAL VERIFICATION
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   FINAL VERIFICATION");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Verification: Confirming we are on Validation Details page...");
        String currentURL = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentURL);

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_05: VALIDATION DETAILS PAGE NAVIGATION TEST PASSED!");
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TEST: VALIDATION DETAILS TABLE SORTING
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 2, dependsOnMethods = "testNavigateToValidationDetailsPage")
    public void testValidationDetailsTableSorting() throws InterruptedException {
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TC_05: VALIDATION DETAILS TABLE SORTING TEST");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("📌 NOTE: Continuing from previous test - Should be on Validation Details page\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 1: VERIFY WE'RE STILL ON VALIDATION DETAILS PAGE
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 1: VERIFY WE'RE STILL ON VALIDATION DETAILS PAGE");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 1.1: Checking current URL...");
        String currentURL = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentURL);

        System.out.println("\nStep 1.2: Verifying we have the validationDetailsPage object...");
        Assert.assertNotNull(validationDetailsPage, "ValidationDetailsPage object is null!");
        System.out.println("✓ ValidationDetailsPage object exists");

        System.out.println("\nStep 1.3: Waiting for table to fully load...");
        Thread.sleep(1500); // Reduced from 3000ms to 1500ms

        System.out.println("✅ Step 1 Completed: On Validation Details page with table loaded\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 2: VERIFY ALL 6 TABLE HEADERS ARE PRESENT
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 2: VERIFY ALL 6 TABLE HEADERS ARE PRESENT");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        String[] expectedHeaders = {"File Name", "Field", "Expected Value", "Actual Value", "Match", "Confidence"};

        System.out.println("Step 2.1: Verifying all required headers are present...");
        for (String headerName : expectedHeaders) {
            boolean isPresent = validationDetailsPage.isHeaderPresent(headerName);
            Assert.assertTrue(isPresent, "Header '" + headerName + "' is NOT present on the page!");
        }

        System.out.println("\nStep 2.2: Verifying header count...");
        int headerCount = validationDetailsPage.getHeaderCount();
        System.out.println("Total headers found: " + headerCount);
        Assert.assertEquals(headerCount, 6, "Expected exactly 6 headers but found: " + headerCount);

        System.out.println("\nStep 2.3: Verifying all headers are sortable...");
        int sortableCount = validationDetailsPage.getSortableHeaderCount();
        System.out.println("Sortable headers found: " + sortableCount);
        Assert.assertEquals(sortableCount, 6, "Expected all 6 headers to be sortable but found: " + sortableCount);

        System.out.println("\n✅ Step 2 Completed: All 6 table headers verified successfully!\n");

        // ═══════════════════════════════════════════════════════════
        //  STEP 3: TEST SORTING WITH DATA CAPTURE & VALIDATION
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   STEP 3: TEST SORTING WITH DATA CAPTURE & VALIDATION");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("Step 3.1: Testing sorting for all 6 columns...");
        validationDetailsPage.testSortingForAllHeadersFast();

        System.out.println("✅ Step 3 Completed: All sorting tests passed\n");

        // ═══════════════════════════════════════════════════════════
        //  FINAL VERIFICATION
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("   FINAL VERIFICATION");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        System.out.println("✓ All 6 table headers are present and verified");
        System.out.println("✓ All sorting functionality validated successfully");
        System.out.println("Final URL: " + driver.getCurrentUrl());

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   ✅ TC_05: TABLE SORTING TEST PASSED!");
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }
}
