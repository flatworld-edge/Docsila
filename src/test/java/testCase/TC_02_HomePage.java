package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.HomePage;

public class TC_02_HomePage extends BaseClass {

    @Test(priority = 1)
    public void testTableHeadersAndSorting() throws InterruptedException {
        // Get credentials from config file
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   TC_02: TABLE SORTING TEST");
        System.out.println("════════════════════════════════════════\n");

        // Step 1: Perform login
        System.out.println("Step 1: Logging in...");
        loginPage.performLogin(email, password);

        // Step 2: Wait for home page to load (loader disappears)
        System.out.println("Step 2: Waiting for home page to load...");
        homePage.waitForPageLoad();

        // Step 3: Verify logo is present
        System.out.println("Step 3: Verifying logo...");
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "Logo is NOT displayed - Home page not loaded properly!");

        // Step 4: Verify table is present
        System.out.println("\nStep 4: Verifying table is present...");
        boolean isTablePresent = homePage.isTablePresent();
        Assert.assertTrue(isTablePresent, "Table is NOT present on the page!");

        // Step 5: Verify all headers are present and check sort icons
        System.out.println("\nStep 5: Verifying all table headers and sort icons...");
        homePage.verifyAllHeaders();

        // Verify expected header count (8 total headers)
        int headerCount = homePage.getHeaderCount();
        System.out.println("DEBUG: Actual header count = " + headerCount);
        Assert.assertTrue(headerCount >= 8, "Expected at least 8 headers but found: " + headerCount);

        // Verify sortable header count (7 sortable, 1 non-sortable "Actions")
        int sortableCount = homePage.getSortableHeaderCount();
        System.out.println("DEBUG: Actual sortable count = " + sortableCount);
        Assert.assertTrue(sortableCount >= 7, "Expected at least 7 sortable headers but found: " + sortableCount);

        // Step 6: Test sorting on all sortable headers
        System.out.println("\nStep 6: Testing sorting functionality on all headers...");
        homePage.testSortingForAllHeaders();

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ✓ TC_02 SORTING TEST PASSED!");
        System.out.println("════════════════════════════════════════\n");
    }

    @Test(priority = 2)
    public void testTooltipValidation() throws InterruptedException {
        // Get credentials from config file
        String email = getConfigProperty("email");
        String password = getConfigProperty("password");

        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   TC_02: TOOLTIP VALIDATION TEST");
        System.out.println("════════════════════════════════════════\n");

        // Step 1: Perform login
        System.out.println("Step 1: Logging in...");
        loginPage.performLogin(email, password);

        // Step 2: Wait for home page to load (loader disappears)
        System.out.println("Step 2: Waiting for home page to load...");
        homePage.waitForPageLoad();

        // Step 3: Verify logo is present
        System.out.println("Step 3: Verifying logo...");
        boolean isLogoPresent = homePage.isLogoDisplayed();
        Assert.assertTrue(isLogoPresent, "Logo is NOT displayed - Home page not loaded properly!");

        // Step 4: Validate all tooltips
        System.out.println("\nStep 4: Validating all tooltips...");
        homePage.verifyAllTooltips();

        // Step 5: Assert specific tooltip validations
        System.out.println("\nStep 5: Asserting specific tooltips...");

        int validTooltips = homePage.getValidTooltipCount();
        Assert.assertTrue(validTooltips > 0, "No valid tooltips found on the page!");

        boolean viewRunsValid = homePage.verifyViewRunsButtonTooltip();
        Assert.assertTrue(viewRunsValid, "'View Runs' button tooltip is missing or incorrect!");

        boolean runNewValidationValid = homePage.verifyRunNewValidationButtonTooltip();
        Assert.assertTrue(runNewValidationValid, "'Run New Validation' button tooltip is missing or incorrect!");

        boolean truthCoverageValid = homePage.verifyTruthCoverageBadgeTooltips();
        Assert.assertTrue(truthCoverageValid, "Truth Coverage badge tooltip is missing or incorrect!");

        System.out.println("\n════════════════════════════════════════");
        System.out.println("   ✓ TC_02 TOOLTIP TEST PASSED!");
        System.out.println("════════════════════════════════════════\n");
    }
}
