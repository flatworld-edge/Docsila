# Docsila_FWS - Test Execution Instructions

## Issues Fixed:
1. ✅ Added `captureScreen()` method to BaseClass for screenshot functionality
2. ✅ Fixed ExtentReportManager to properly capture screenshots on test failure
3. ✅ Configured project to use Java 17 (compatible with your setup)
4. ✅ Added ExtentReports for HTML test reporting
5. ✅ All credentials now loaded from config.properties file

## How to Run the Tests:

### Option 1: Using IntelliJ IDEA (Recommended)

1. **Reload Maven Project:**
   - Right-click on `pom.xml`
   - Select `Maven` -> `Reload Project`
   - Wait for dependencies to download

2. **Build the Project:**
   - Go to `Build` menu
   - Click `Build Project` (Ctrl+F9)
   - Wait for compilation to complete

3. **Run the Test:**
   - Right-click on `testng.xml` in the project explorer
   - Select `Run 'testng.xml'`
   
   OR
   
   - Right-click on `TC_01_LoginPage.java`
   - Select `Run 'TC_01_LoginPage'`

### Option 2: Using Command Line (if Maven is installed)

Open Command Prompt in project directory and run:
```
mvn clean test
```

## What the Test Does:

1. **Launches Chrome** with a persistent profile (no MFA required after first login)
2. **Reads credentials** from `config.properties`:
   - URL: https://msuitedocsiladev.z29.web.core.windows.net/
   - Email: vishal.a@flatworldsolutions.com
   - Password: Flatworld@123

3. **Performs login** (only first time - subsequent runs skip this)
4. **Verifies** the DocSila logo is displayed
5. **Generates HTML Report** with screenshots for failed tests

## Test Reports:

After test execution, you'll find:
- **HTML Report**: `reports/Test-Report-{timestamp}.html` (opens automatically)
- **Screenshots**: `screenshots/` folder (captured on test failures)

## Important Notes:

- **First Run**: Will require MFA approval on your mobile device
- **Subsequent Runs**: Will use saved Chrome profile (no MFA needed)
- **Chrome Profile**: Stored at `C:\Selenium\ChromeProfile`

## Troubleshooting:

If you see "Cannot resolve class" errors in IntelliJ:
1. File -> Invalidate Caches -> Invalidate and Restart
2. After restart, rebuild the project (Ctrl+F9)

If tests don't run:
1. Make sure Java 17 is installed
2. Check that ChromeDriver is compatible with your Chrome browser
3. Verify all Maven dependencies are downloaded

