@echo off
echo ========================================
echo Compiling Docsila_FWS Test Project
echo ========================================
echo.

cd /d "%~dp0"

REM Find Maven in common locations
set MAVEN_HOME=
if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" (
    set MAVEN_CMD="C:\Program Files\Apache\maven\bin\mvn.cmd"
) else if exist "C:\apache-maven\bin\mvn.cmd" (
    set MAVEN_CMD="C:\apache-maven\bin\mvn.cmd"
) else if exist "%USERPROFILE%\apache-maven\bin\mvn.cmd" (
    set MAVEN_CMD="%USERPROFILE%\apache-maven\bin\mvn.cmd"
) else (
    set MAVEN_CMD=mvn
)

echo Using Maven command: %MAVEN_CMD%
echo.

echo Step 1: Cleaning previous builds...
call %MAVEN_CMD% clean

echo.
echo Step 2: Compiling test classes...
call %MAVEN_CMD% test-compile

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo Now you can run the tests in IntelliJ IDEA:
    echo 1. Right-click on testng.xml
    echo 2. Select "Run testng.xml"
    echo.
    echo OR
    echo.
    echo 1. Right-click on TC_01_LoginPage.java
    echo 2. Select "Run TC_01_LoginPage"
    echo.
) else (
    echo.
    echo ========================================
    echo BUILD FAILED!
    echo ========================================
    echo Please check the error messages above.
    echo.
)

pause

