package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import testCase.BaseClass;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    @Override
    public void onStart(ITestContext testContext) {
        // Ensure the reports directory exists
        File reportsDir = new File(System.getProperty("user.dir") + "\\reports\\");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("Docsila Automation Report");
        sparkReporter.config().setReportName("Docsila Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Docsila");
        extent.setSystemInfo("Module", "Login");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        if (os != null) extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (includedGroups != null && !includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getMethod().getMethodName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getMethod().getMethodName() + " got failed");

        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.INFO, throwable.getMessage());
        }

        try {
            // Get the test instance and capture screenshot
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseClass) {
                BaseClass baseClass = (BaseClass) testInstance;
                String imgPath = baseClass.captureScreen(result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(imgPath);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getMethod().getMethodName() + " got skipped");
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.INFO, throwable.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Not used, but required by interface
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used, but required by interface
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        System.out.println("\n==============================================");
        System.out.println("Test Execution Completed!");
        System.out.println("Report Location: " + pathOfExtentReport);
        System.out.println("==============================================\n");

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
