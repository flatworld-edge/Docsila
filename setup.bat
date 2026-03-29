@echo off
echo ========================================
echo Building Docsila_FWS Test Project
echo ========================================
echo.

cd /d "%~dp0"

echo Step 1: Cleaning target directory...
if exist target (
    rmdir /s /q target
    echo Target directory cleaned.
) else (
    echo Target directory does not exist, skipping clean.
)

echo.
echo Step 2: Creating necessary directories...
if not exist "screenshots" mkdir screenshots
if not exist "reports" mkdir reports
echo Directories created.

echo.
echo ========================================
echo BUILD COMPLETE
echo ========================================
echo.
echo Next Steps:
echo.
echo 1. In IntelliJ IDEA, go to: File ^> Project Structure
echo 2. Under Project Settings ^> Project, set:
echo    - SDK: 17
echo    - Language Level: 17
echo.
echo 3. Then go to: Build ^> Rebuild Project
echo.
echo 4. After rebuild, right-click on testng.xml and select 'Run'
echo.
echo If you still see errors:
echo - Go to: File ^> Invalidate Caches ^> Invalidate and Restart
echo.

pause

