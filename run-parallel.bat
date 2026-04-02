@echo off
echo ========================================
echo Running Parallel Tests (testng-parallel.xml)
echo ========================================
echo.

mvn clean test -DsuiteXmlFile=testng-parallel.xml

echo.
echo ========================================
echo Parallel Tests Completed!
echo ========================================
pause
@echo off
echo ========================================
echo Running Sequential Tests (testng.xml)
echo ========================================
echo.

mvn clean test -DsuiteXmlFile=testng.xml

echo.
echo ========================================
echo Sequential Tests Completed!
echo ========================================
pause

