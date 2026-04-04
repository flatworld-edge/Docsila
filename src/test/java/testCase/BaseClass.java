package testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass {

    protected WebDriver driver;
    protected Properties prop;

    @BeforeClass
    public void setup() {
        // Load config properties
        loadConfig();

        // Configure Chrome to use a separate profile directory (avoids conflicts)
        ChromeOptions options = new ChromeOptions();

        // Use a dedicated automation profile directory to avoid "Chrome is already running" error
        // Using a FIXED profile directory so Chrome remembers MFA login
        String automationProfile = "C:\\Selenium\\ChromeProfile";

        // Create the directory if it doesn't exist
        java.io.File profileDir = new java.io.File(automationProfile);
        if (!profileDir.exists()) {
            profileDir.mkdirs();
            System.out.println("Created new profile directory: " + automationProfile);
        }

        options.addArguments("user-data-dir=" + automationProfile);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        System.out.println("Launching Chrome (First time will require MFA, then it remembers)...");

        try {
            driver = new ChromeDriver(options);
            System.out.println("✓ Chrome launched successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error launching Chrome: " + e.getMessage());
            System.err.println("Troubleshooting steps:");
            System.err.println("1. Close all Chrome browser windows and try again");
            System.err.println("2. If problem persists, delete C:\\Selenium\\ChromeProfile and restart");
            System.err.println("3. Check if ChromeDriver is compatible with your Chrome version");
            throw e;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        String appURL = prop.getProperty("appURL");
        if (appURL == null || appURL.isEmpty()) {
            throw new RuntimeException("appURL property is missing in config.properties file!");
        }

        System.out.println("Navigating to: " + appURL);
        driver.get(appURL);

        // Add wait for page to fully load and any redirects to complete
        try {
            Thread.sleep(5000);
            System.out.println("✓ Initial page load completed, waiting for redirects and content to render...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "\\src\\test\\java\\utilities\\config.properties");
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getConfigProperty(String key) {
        return prop.getProperty(key);
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        // Create screenshots directory if it doesn't exist
        File screenshotDir = new File(System.getProperty("user.dir") + "\\screenshots\\");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}
