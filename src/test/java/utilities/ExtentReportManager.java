package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports listener for generating test execution reports
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    @Override
    public void onStart(ITestContext context) {
        // Generate report file name with timestamp
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportFileName = "Test-Report-" + timestamp + ".html";

        // Create reports directory if it doesn't exist
        String reportDir = System.getProperty("user.dir") + File.separator + "reports";
        File reportsFolder = new File(reportDir);
        if (!reportsFolder.exists()) {
            reportsFolder.mkdirs();
        }

        reportPath = reportDir + File.separator + reportFileName;

        // Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter(reportPath);

        // Configure report settings
        sparkReporter.config().setDocumentTitle("Docsila Automation Report");
        sparkReporter.config().setReportName("Docsila Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information
        extent.setSystemInfo("Application", "Docsila");
        extent.setSystemInfo("Environment", "Dev");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   📊 EXTENT REPORT INITIALIZED");
        System.out.println("   Report: " + reportFileName);
        System.out.println("════════════════════════════════════════════════════════════════\n");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the report
        ExtentTest test = extent.createTest(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
        extentTest.set(test);

        System.out.println("\n▶ Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
        extentTest.get().pass("Test execution completed successfully");

        System.out.println("✅ Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        extentTest.get().fail(result.getThrowable());

        // Try to capture screenshot if possible
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof testCase.BaseClass) {
                testCase.BaseClass baseClass = (testCase.BaseClass) testInstance;
                String screenshotPath = baseClass.captureScreen(result.getMethod().getMethodName());
                extentTest.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                System.out.println("📸 Screenshot captured: " + screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("⚠ Could not capture screenshot: " + e.getMessage());
        }

        System.out.println("❌ Test Failed: " + result.getMethod().getMethodName());
        System.out.println("   Error: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        extentTest.get().skip(result.getThrowable());

        System.out.println("⏭ Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report
        if (extent != null) {
            extent.flush();
        }

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("   📊 EXTENT REPORT GENERATED");
        System.out.println("   Report Path: " + reportPath);
        System.out.println("════════════════════════════════════════════════════════════════\n");

        // Try to open the report automatically
        try {
            File reportFile = new File(reportPath);
            if (reportFile.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(reportFile.toURI());
                System.out.println("✓ Report opened in browser");
            }
        } catch (IOException e) {
            System.out.println("⚠ Could not open report automatically: " + e.getMessage());
            System.out.println("   Please open manually: " + reportPath);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used
    }
}
