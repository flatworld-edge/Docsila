| 4 | Check expand button | ✅ Tooltip shows "Expand file list" |
| 5 | Click expand button | ✅ File list reappears, bar disappears |
| 6 | Check collapse button | ✅ Tooltip shows "Collapse file list" |
| 7 | Click collapse button | ✅ File list collapses, bar reappears |

---

## ⚙️ Configuration

**Target Document:** `asset_gift_letter`  
**Target PDF File:** `asset_gift_letter.pdf`

You can change these values in the test class:
```java
private String targetDocument = "asset_gift_letter";
private String targetPDFFile = "asset_gift_letter.pdf";
```

---

## 🎨 Design Pattern

This test follows the **Page Object Model (POM)** design pattern:

1. **BasePage** - Base class for all page objects
2. **PDFCotentView** - Page object containing locators and methods
3. **BaseClass** - Base test class with WebDriver setup
4. **TC_04_PDF_PDFViewPage** - Test class with test methods

**Benefits:**
- ✅ Separation of concerns
- ✅ Reusable methods
- ✅ Easy maintenance
- ✅ Readable test code
- ✅ Consistent logging and error handling

---

## 🔧 Running the Test

### Option 1: Run via TestNG XML
```xml
<!-- Add to testng.xml -->
<test name="TC_04: PDF Content View Test">
    <classes>
        <class name="testCase.TC_04_PDF_PDFViewPage"/>
    </classes>
</test>
```

### Option 2: Run via Maven
```bash
mvn test -Dtest=TC_04_PDF_PDFViewPage
```

### Option 3: Run via IDE
Right-click on `TC_04_PDF_PDFViewPage.java` → Run as TestNG Test

---

## 📝 Console Output Example

```
════════════════════════════════════════════════════════════════
   TC_04: PDF CONTENT VIEW - COLLAPSE & EXPAND FUNCTIONALITY
════════════════════════════════════════════════════════════════

════════════════════════════════════════════════════════════════
   STEP 1: LOGIN & NAVIGATE TO PDF VIEW PAGE
════════════════════════════════════════════════════════════════

Step 1.1: Logging in...
✓ Login successful

Step 1.2: Waiting for home page to load...
✓ Home page loaded

Step 1.3: Verifying logo is displayed...
✓ Logo is displayed

Step 1.4: Clicking on document 'asset_gift_letter'...
✓ Document clicked

Step 1.5: Waiting for PDF View page to load...
✓ PDF View page loaded

✅ Step 1 Completed: Successfully navigated to PDF View page

════════════════════════════════════════════════════════════════
   STEP 2: CLICK ON UPLOADED PDF FILE
════════════════════════════════════════════════════════════════

Step 2.1: Clicking on PDF file 'asset_gift_letter.pdf'...
📄 Clicking on PDF file: 'asset_gift_letter.pdf' in the file list...
✓ Successfully clicked on PDF file: 'asset_gift_letter.pdf'

✅ Step 2 Completed: PDF file clicked successfully

[... continues for all 7 steps ...]

════════════════════════════════════════════════════════════════
   ✅ TC_04: PDF CONTENT VIEW TEST PASSED!
════════════════════════════════════════════════════════════════
   ✓ Step 1: Successfully logged in and navigated to PDF View page
   ✓ Step 2: Successfully clicked on PDF file 'asset_gift_letter.pdf'
   ✓ Step 3: Collapsed file indicator bar validated
   ✓ Step 4: Expand button tooltip validated: 'Expand file list'
   ✓ Step 5: File list expanded successfully
   ✓ Step 6: Collapse button tooltip validated: 'Collapse file list'
   ✓ Step 7: File list collapsed successfully
════════════════════════════════════════════════════════════════
```

---

## 🐛 Troubleshooting

### Issue 1: PDF file not found
**Error:** `Failed to click PDF file: asset_gift_letter.pdf`  
**Solution:** Ensure the PDF file exists in the file list. Check the file name matches exactly.

### Issue 2: Collapsed bar not appearing
**Error:** `Collapsed file indicator bar is NOT displayed`  
**Solution:** Verify that clicking the PDF file triggers the collapse animation. Check the XPath for the collapsed bar.

### Issue 3: Expand/Collapse buttons not clickable
**Error:** `Failed to click expand button`  
**Solution:** Increase wait time or ensure buttons are not obscured by other elements.

---

## 📚 Related Test Cases

- **TC_01:** Login and Logout
- **TC_02:** Homepage validation
- **TC_03:** PDF Document List Page (upload, duplicate validation)
- **TC_04:** PDF Content View (this test)

---

## ✅ Validation Summary

This test validates:
1. ✓ PDF file click functionality
2. ✓ File list collapse on PDF selection
3. ✓ Collapsed bar visibility and content
4. ✓ Tooltip accuracy (file name, expand, collapse)
5. ✓ Expand button functionality
6. ✓ File list re-expansion
7. ✓ Collapse button functionality
8. ✓ File list re-collapse
9. ✓ UI state transitions (collapsed ↔ expanded)

---

**Created:** April 1, 2026  
**Author:** Test Automation Framework  
**Version:** 1.0
# TC_04: PDF Content View - Collapse & Expand Functionality Guide

## 📋 Overview

This test case validates the **PDF file list collapse/expand functionality** in the PDF View page. It ensures that when a user clicks on a PDF file, the file list collapses into a compact vertical bar, and can be expanded/collapsed using dedicated buttons.

---

## �� Test Objectives

1. ✅ **Click on a PDF file** and verify the file list collapses
2. ✅ **Validate the collapsed bar** displays the file name and tooltip
3. ✅ **Verify expand button** tooltip and functionality
4. ✅ **Validate file list re-expansion** when expand button is clicked
5. ✅ **Verify collapse button** tooltip and functionality
6. ✅ **Validate file list re-collapse** when collapse button is clicked

---

## 📂 Files Created

### 1. **PDFContentView.java** (Page Object)
**Location:** `src/test/java/pageObject/PDFCotentView.java`

**Purpose:** Contains all locators and methods for interacting with the PDF content view, collapse/expand functionality.

**Key Methods:**
- `clickPDFFileInList(String fileName)` - Clicks a specific PDF file in the list
- `isCollapsedFileIndicatorDisplayed()` - Verifies collapsed bar is visible
- `getCollapsedFileIndicatorTooltip()` - Gets tooltip from collapsed bar
- `getCollapsedFileNameText()` - Gets file name displayed on collapsed bar
- `isExpandButtonDisplayed()` - Checks if expand button is visible
- `getExpandButtonTooltip()` - Gets expand button tooltip
- `clickExpandButton()` - Clicks expand button to show file list
- `isFileListPanelDisplayed()` - Verifies file list panel is visible
- `isCollapseButtonDisplayed()` - Checks if collapse button is visible
- `getCollapseButtonTooltip()` - Gets collapse button tooltip
- `clickCollapseButton()` - Clicks collapse button to hide file list
- `isCollapsedFileIndicatorHidden()` - Verifies collapsed bar is hidden

---

### 2. **TC_04_PDF_PDFViewPage.java** (Test Class)
**Location:** `src/test/java/testCase/TC_04_PDF_PDFViewPage.java`

**Purpose:** Test class that validates the complete collapse/expand workflow.

**Test Method:** `testPDFContentViewCollapseExpand()`

---

## 🔍 XPath Locators Used

### 1. **Collapsed File Indicator (Bar)**
```xpath
// Main container
//div[contains(@class,'collapsed-file-indicator')]

// With tooltip
//div[@title='asset_gift_letter.pdf']

// File name text
//div[contains(@class,'collapsed-file-indicator')]//span
```

### 2. **Expand Button (on collapsed bar)**
```xpath
// Direct with tooltip
//button[@title='Expand file list']

// SVG icon
//button[@title='Expand file list']//svg

// Polyline (arrow pointing right)
//polyline[@points='9 18 15 12 9 6']
```

### 3. **Collapse Button (on file list panel)**
```xpath
// Using polyline (arrow pointing left)
//button[.//polyline[@points='15 18 9 12 15 6']]

// Polyline alone
//polyline[@points='15 18 9 12 15 6']
```

### 4. **File List Panel**
```xpath
// File tree container
//div[contains(@class,'file-tree')]

// Specific file
//span[contains(@class,'tree-label') and text()='asset_gift_letter.pdf']
```

---

## 🚀 Test Flow (7 Steps)

### **STEP 1: Login & Navigate to PDF View Page**
1. Login with credentials from config.properties
2. Wait for homepage to load
3. Verify logo is displayed
4. Click on document "asset_gift_letter"
5. Wait for PDF View page to load

### **STEP 2: Click on Uploaded PDF File**
1. Click on "asset_gift_letter.pdf" in the file list
2. Wait for PDF to load and UI to transition (2 seconds)

### **STEP 3: Validate Collapsed File Indicator Bar**
1. Verify collapsed bar is displayed
2. Verify PDF file name is displayed on the bar
3. Validate tooltip matches the PDF file name

### **STEP 4: Validate Expand Button Tooltip**
1. Verify expand button is displayed on collapsed bar
2. Validate expand button tooltip is "Expand file list"

### **STEP 5: Click Expand Button & Validate File List Reappears**
1. Click expand button
2. Verify collapsed bar is now hidden
3. Verify file list panel is now displayed

### **STEP 6: Validate Collapse Button Tooltip**
1. Verify collapse button is displayed on file list panel
2. Validate collapse button tooltip is "Collapse file list"

### **STEP 7: Click Collapse Button & Validate Bar Reappears**
1. Click collapse button
2. Verify collapsed file indicator bar is displayed again
3. Verify file name is still displayed on the bar

---

## 📊 Expected Results

| Step | Action | Expected Result |
|------|--------|----------------|
| 1 | Login & Navigate | ✅ PDF View page loads successfully |
| 2 | Click PDF file | ✅ File list collapses, PDF opens on right side |
| 3 | Validate collapsed bar | ✅ Bar displays with file name & tooltip |

