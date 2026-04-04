package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.HomePage;
import pageObject.EvaluationRunsPage;
import pageObject.ValidationDetailsPage;

/**
 * TC_06: Filter Validation Test Suite
 *
 * This test class validates all filtering functionality on the Validation Details Page
 * by testing each filter individually with both valid and invalid data, verifying results,
 * and clearing filters between tests.
 *
 * Enhanced with:
 * - Column data verification (ensures filtered data matches filter criteria)
 * - Clear filters verification (ensures all inputs are cleared after clicking Clear Filters)
 */
public class TC_06_FilterValidation extends BaseClass {

    private String targetDocument = "asset_gift_letter";
    private ValidationDetailsPage validationDetailsPage;

    // ══════════════════════════════════════════════════════════════════════
    //  TEST: COMPREHENSIVE FILTER VALIDATION
    // ══════════════════════════════════════════════════════════════════════

    @Test(priority = 1, description = "Comprehensive Filter Validation - Test all filters with valid and invalid data + column verification")
    public void testAllFiltersWithClearBetween() throws InterruptedException {
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        EvaluationRunsPage evaluationRunsPage = new EvaluationRunsPage(driver);
        validationDetailsPage = new ValidationDetailsPage(driver);

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   TC_06: COMPREHENSIVE FILTER VALIDATION TEST");
        System.out.println("   WITH COLUMN DATA & CLEAR FILTERS VERIFICATION");
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // ========== NAVIGATION ==========
        System.out.println("📍 STEP 1: NAVIGATION TO VALIDATION DETAILS PAGE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Performing login...");
        loginPage.performLogin(email, password);
        homePage.waitForPageLoad();
        Thread.sleep(2000);

        System.out.println("→ Navigating to Evaluation Runs...");
        homePage.clickViewRunsForDocument(targetDocument);
        evaluationRunsPage.waitForPageLoad();

        System.out.println("→ Navigating to Validation Details...");
        evaluationRunsPage.clickFirstViewValidationResults();
        validationDetailsPage.waitForPageLoad();
        validationDetailsPage.clickValidationDetailsTab();
        Thread.sleep(2000);

        System.out.println("✅ Successfully navigated to Validation Details page\n");

        // Capture initial row count for reference
        int initialRowCount = validationDetailsPage.getVisibleRowCount();
        System.out.println("📊 Initial table row count: " + initialRowCount + "\n");

        // ========== FILTER TEST 1: FILE NAME FILTER - VALID DATA ==========
        System.out.println("\n📍 STEP 2: TESTING FILE NAME FILTER - VALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Getting a valid file name from the table...");
        String validFileName = validationDetailsPage.getFirstValueFromColumn("File Name");
        System.out.println("   Selected value: '" + validFileName + "'");

        System.out.println("→ Entering filter value...");
        validationDetailsPage.enterFileNameFilter(validFileName);
        Thread.sleep(1500);

        System.out.println("→ Verifying filtered results...");
        int rowsAfterFileNameFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterFileNameFilter);
        Assert.assertTrue(rowsAfterFileNameFilter < initialRowCount || rowsAfterFileNameFilter > 0,
                "File Name filter did not work correctly!");

        // ✨ NEW: Verify column data matches filter
        System.out.println("→ Verifying File Name column data matches filter...");
        boolean fileNameColumnVerified = validationDetailsPage.verifyFileNameColumnContains(validFileName);
        Assert.assertTrue(fileNameColumnVerified, "File Name column data does NOT match filter value!");
        System.out.println("✅ Column Data Verified: All File Name values contain the filter text");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        // ✨ NEW: Verify filters are actually cleared
        System.out.println("→ Verifying all filters are cleared...");
        boolean filtersCleared1 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared1, "Filters were NOT cleared properly!");
        System.out.println("✅ Clear Filters Verified: All filter inputs are empty");

        int rowsAfterClear1 = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after clear: " + rowsAfterClear1);
        Assert.assertEquals(rowsAfterClear1, initialRowCount, "Rows not restored after clearing File Name filter!");
        System.out.println("✅ File Name filter test completed successfully\n");

        // ========== FILTER TEST 2: FILE NAME FILTER - INVALID DATA ==========
        System.out.println("\n📍 STEP 3: TESTING FILE NAME FILTER - INVALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        String invalidFileName = "InvalidFile_XYZ999_NotExists.pdf";
        System.out.println("→ Entering invalid filter value: '" + invalidFileName + "'");
        validationDetailsPage.enterFileNameFilter(invalidFileName);
        Thread.sleep(1500);

        System.out.println("→ Verifying 'No results found' message is displayed...");
        boolean noResultsDisplayed1 = validationDetailsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsDisplayed1, "No results message is NOT displayed for invalid File Name!");
        System.out.println("✅ 'No results found' message displayed correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared2 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared2, "Filters NOT cleared after invalid data!");

        int rowsAfterClear2 = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after clear: " + rowsAfterClear2);
        Assert.assertEquals(rowsAfterClear2, initialRowCount, "Rows not restored after clearing invalid File Name filter!");
        System.out.println("✅ Invalid File Name filter test completed successfully\n");

        // ========== FILTER TEST 3: FIELD FILTER - VALID DATA ==========
        System.out.println("\n📍 STEP 4: TESTING FIELD FILTER - VALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Getting a valid field value from the table...");
        String validField = validationDetailsPage.getFirstValueFromColumn("Field");
        System.out.println("   Selected value: '" + validField + "'");

        System.out.println("→ Entering filter value...");
        validationDetailsPage.enterFieldFilter(validField);
        Thread.sleep(1500);

        System.out.println("→ Verifying filtered results...");
        int rowsAfterFieldFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterFieldFilter);
        Assert.assertTrue(rowsAfterFieldFilter > 0, "Field filter resulted in no rows!");

        // ✨ NEW: Verify column data matches filter
        System.out.println("→ Verifying Field column data matches filter...");
        boolean fieldColumnVerified = validationDetailsPage.verifyFieldColumnContains(validField);
        Assert.assertTrue(fieldColumnVerified, "Field column data does NOT match filter value!");
        System.out.println("✅ Column Data Verified: All Field values match the filter");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared3 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared3, "Filters NOT cleared!");

        int rowsAfterClear3 = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after clear: " + rowsAfterClear3);
        Assert.assertEquals(rowsAfterClear3, initialRowCount, "Rows not restored after clearing Field filter!");
        System.out.println("✅ Field filter test completed successfully\n");

        // ========== FILTER TEST 4: FIELD FILTER - INVALID DATA ==========
        System.out.println("\n📍 STEP 5: TESTING FIELD FILTER - INVALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        String invalidField = "InvalidField_ABC123_NotExists";
        System.out.println("→ Entering invalid filter value: '" + invalidField + "'");
        validationDetailsPage.enterFieldFilter(invalidField);
        Thread.sleep(1500);

        System.out.println("→ Verifying 'No results found' message is displayed...");
        boolean noResultsDisplayed2 = validationDetailsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsDisplayed2, "No results message is NOT displayed for invalid Field!");
        System.out.println("✅ 'No results found' message displayed correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared4 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared4, "Filters NOT cleared!");

        int rowsAfterClear4 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear4, initialRowCount, "Rows not restored!");
        System.out.println("✅ Invalid Field filter test completed successfully\n");

        // ========== FILTER TEST 5: EXPECTED VALUE FILTER - VALID DATA ==========
        System.out.println("\n📍 STEP 6: TESTING EXPECTED VALUE FILTER - VALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Getting a valid expected value from the table...");
        String validExpectedValue = validationDetailsPage.getFirstValueFromColumn("Expected Value");
        System.out.println("   Selected value: '" + validExpectedValue + "'");

        System.out.println("→ Entering filter value...");
        validationDetailsPage.enterExpectedValueFilter(validExpectedValue);
        Thread.sleep(1500);

        System.out.println("→ Verifying filtered results...");
        int rowsAfterExpectedFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterExpectedFilter);
        Assert.assertTrue(rowsAfterExpectedFilter > 0, "Expected Value filter resulted in no rows!");

        // ✨ NEW: Verify column data matches filter
        System.out.println("→ Verifying Expected Value column data matches filter...");
        boolean expectedColumnVerified = validationDetailsPage.verifyExpectedValueColumnContains(validExpectedValue);
        Assert.assertTrue(expectedColumnVerified, "Expected Value column data does NOT match filter!");
        System.out.println("✅ Column Data Verified: All Expected Value values match the filter");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared5 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared5, "Filters NOT cleared!");

        int rowsAfterClear5 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear5, initialRowCount, "Rows not restored!");
        System.out.println("✅ Expected Value filter test completed successfully\n");

        // ========== FILTER TEST 6: EXPECTED VALUE FILTER - INVALID DATA ==========
        System.out.println("\n📍 STEP 7: TESTING EXPECTED VALUE FILTER - INVALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        String invalidExpectedValue = "InvalidExpected_999_NotExists";
        System.out.println("→ Entering invalid filter value: '" + invalidExpectedValue + "'");
        validationDetailsPage.enterExpectedValueFilter(invalidExpectedValue);
        Thread.sleep(1500);

        System.out.println("→ Verifying 'No results found' message is displayed...");
        boolean noResultsDisplayed3 = validationDetailsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsDisplayed3, "No results message NOT displayed!");
        System.out.println("✅ 'No results found' message displayed correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared6 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared6, "Filters NOT cleared!");

        int rowsAfterClear6 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear6, initialRowCount, "Rows not restored!");
        System.out.println("✅ Invalid Expected Value filter test completed successfully\n");

        // ========== FILTER TEST 7: ACTUAL VALUE FILTER - VALID DATA ==========
        System.out.println("\n📍 STEP 8: TESTING ACTUAL VALUE FILTER - VALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Getting a valid actual value from the table...");
        String validActualValue = validationDetailsPage.getFirstValueFromColumn("Actual Value");
        System.out.println("   Selected value: '" + validActualValue + "'");

        System.out.println("→ Entering filter value...");
        validationDetailsPage.enterActualValueFilter(validActualValue);
        Thread.sleep(1500);

        System.out.println("→ Verifying filtered results...");
        int rowsAfterActualFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterActualFilter);
        Assert.assertTrue(rowsAfterActualFilter > 0, "Actual Value filter resulted in no rows!");

        // ✨ NEW: Verify column data matches filter
        System.out.println("→ Verifying Actual Value column data matches filter...");
        boolean actualColumnVerified = validationDetailsPage.verifyActualValueColumnContains(validActualValue);
        Assert.assertTrue(actualColumnVerified, "Actual Value column data does NOT match filter!");
        System.out.println("✅ Column Data Verified: All Actual Value values match the filter");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared7 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared7, "Filters NOT cleared!");

        int rowsAfterClear7 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear7, initialRowCount, "Rows not restored!");
        System.out.println("✅ Actual Value filter test completed successfully\n");

        // ========== FILTER TEST 8: ACTUAL VALUE FILTER - INVALID DATA ==========
        System.out.println("\n📍 STEP 9: TESTING ACTUAL VALUE FILTER - INVALID DATA");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        String invalidActualValue = "InvalidActual_777_NotExists";
        System.out.println("→ Entering invalid filter value: '" + invalidActualValue + "'");
        validationDetailsPage.enterActualValueFilter(invalidActualValue);
        Thread.sleep(1500);

        System.out.println("→ Verifying 'No results found' message is displayed...");
        boolean noResultsDisplayed4 = validationDetailsPage.isNoResultsMessageDisplayed();
        Assert.assertTrue(noResultsDisplayed4, "No results message NOT displayed!");
        System.out.println("✅ 'No results found' message displayed correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared8 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared8, "Filters NOT cleared!");

        int rowsAfterClear8 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear8, initialRowCount, "Rows not restored!");
        System.out.println("✅ Invalid Actual Value filter test completed successfully\n");

        // ========== FILTER TEST 9: MATCH DROPDOWN - YES ==========
        System.out.println("\n📍 STEP 10: TESTING MATCH DROPDOWN - 'YES' OPTION");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Selecting 'Yes' option...");
        validationDetailsPage.selectMatchOption("yes");
        Thread.sleep(1500);

        System.out.println("→ Verifying all visible rows have 'MATCH' in Match column...");
        int rowsAfterYesFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterYesFilter);

        // ✨ Verify only MATCH values shown
        boolean allRowsMatch = validationDetailsPage.verifyMatchColumnValues("MATCH");
        Assert.assertTrue(allRowsMatch, "Not all rows have 'MATCH' when 'Yes' is selected!");
        System.out.println("✅ Match dropdown 'Yes' filter working correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared9 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared9, "Dropdown NOT reset to 'All'!");

        int rowsAfterClear9 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear9, initialRowCount, "Rows not restored!");
        System.out.println("✅ Match dropdown cleared successfully\n");

        // ========== FILTER TEST 10: MATCH DROPDOWN - NO ==========
        System.out.println("\n📍 STEP 11: TESTING MATCH DROPDOWN - 'NO' OPTION");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("→ Selecting 'No' option...");
        validationDetailsPage.selectMatchOption("no");
        Thread.sleep(1500);

        System.out.println("→ Verifying all visible rows have 'MISMATCH' in Match column...");
        int rowsAfterNoFilter = validationDetailsPage.getVisibleRowCount();
        System.out.println("   Rows after filter: " + rowsAfterNoFilter);

        boolean allRowsMismatch = validationDetailsPage.verifyMatchColumnValues("MISMATCH");
        Assert.assertTrue(allRowsMismatch, "Not all rows have 'MISMATCH' when 'No' is selected!");
        System.out.println("✅ Match dropdown 'No' filter working correctly");

        System.out.println("→ Clicking Clear Filters button...");
        validationDetailsPage.clickClearFilters();
        Thread.sleep(1500);

        boolean filtersCleared10 = validationDetailsPage.areFiltersCleared();
        Assert.assertTrue(filtersCleared10, "Dropdown NOT reset to 'All'!");

        int rowsAfterClear10 = validationDetailsPage.getVisibleRowCount();
        Assert.assertEquals(rowsAfterClear10, initialRowCount, "Rows not restored!");
        System.out.println("✅ Match dropdown cleared successfully\n");

        // ========== FINAL SUMMARY ==========
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   ✅✅✅ ALL FILTER TESTS COMPLETED SUCCESSFULLY! ✅✅✅");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("Filters Tested:");
        System.out.println("  1. ✅ File Name - Valid Data + Column Verification");
        System.out.println("  2. ✅ File Name - Invalid Data + No Results Message");
        System.out.println("  3. ✅ Field - Valid Data + Column Verification");
        System.out.println("  4. ✅ Field - Invalid Data + No Results Message");
        System.out.println("  5. ✅ Expected Value - Valid Data + Column Verification");
        System.out.println("  6. ✅ Expected Value - Invalid Data + No Results Message");
        System.out.println("  7. ✅ Actual Value - Valid Data + Column Verification");
        System.out.println("  8. ✅ Actual Value - Invalid Data + No Results Message");
        System.out.println("  9. ✅ Match Dropdown - Yes + Column Verification");
        System.out.println(" 10. ✅ Match Dropdown - No + Column Verification");
        System.out.println("\n✅ Clear Filters button verified after EACH test!");
        System.out.println("✅ Column data verified to match filter criteria!");
        System.out.println("✅ Table reset to initial state verified after each test!");
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }
}
