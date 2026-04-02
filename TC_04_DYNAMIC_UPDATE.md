# TC_04: Dynamic PDF File Selection - Update Summary

## 🎯 Problem Solved

**BEFORE:** TC_04 was trying to open a hardcoded file `asset_gift_letter.pdf` which may not exist in the list.

**AFTER:** TC_04 now **dynamically selects the first/top file** from the list (the file uploaded in TC_03).

---

## 🔧 Changes Made

### 1. **PDFContentView.java** - Added Dynamic Methods

#### ✅ New Method: `getFirstFileName()`
```java
/**
 * Get the first (top) file name from the file list.
 * This is typically the most recently uploaded file.
 */
public String getFirstFileName() {
    // Uses XPath: (//span[contains(@class,'tree-label')])[1]
    // Returns the top file name in the list
}
```

#### ✅ New Method: `clickFirstPDFFile()`
```java
/**
 * Click on the first (top) file in the file list.
 * This is typically the most recently uploaded file.
 * @return The name of the clicked file
 */
public String clickFirstPDFFile() {
    // Gets the first file name
    // Clicks on it
    // Returns the filename for validation
}
```

---

### 2. **TC_04_PDF_PDFViewPage.java** - Updated Test Logic

#### ❌ **REMOVED:**
```java
private String targetPDFFile = "asset_gift_letter.pdf"; // Hardcoded - BAD!
pdfContentView.clickPDFFileInList(targetPDFFile);
```

#### ✅ **ADDED (Step 2):**
```java
// Step 2.1: Get the first file name dynamically
String targetPDFFile = pdfContentView.getFirstFileName();
Assert.assertNotNull(targetPDFFile, "No files found in the file list!");
Assert.assertFalse(targetPDFFile.isEmpty(), "First file name is empty!");

// Step 2.2: Click the first file
String clickedFileName = pdfContentView.clickFirstPDFFile();
Assert.assertEquals(clickedFileName, targetPDFFile, "Clicked file name mismatch!");
```

---

## 📊 Test Flow (Updated)

### **STEP 1:** Login & Navigate to PDF View Page ✅
- Same as before

### **STEP 2:** Click on First PDF File (TOP OF LIST) 🔥 **NEW!**
- **2.1** Get first file name from the list dynamically
- **2.2** Click on that file
- **2.3** Validate it was clicked correctly

### **STEP 3-7:** Collapse/Expand Validation ✅
- All validations now use the **dynamic filename** from Step 2
- Tooltips, file names, and bar validations all work with ANY filename

---

## 🎯 XPath Used for Dynamic Selection

```xpath
# Get first file name
(//span[contains(@class,'tree-label')])[1]

# Click first file (by position, not by name)
(//span[contains(@class,'tree-label')])[1]
```

---

## ✨ Benefits

| Feature | Before | After |
|---------|--------|-------|
| File Selection | ❌ Hardcoded `asset_gift_letter.pdf` | ✅ Dynamic first file |
| Works with TC_03 upload | ❌ Only if file named exactly | ✅ Always uses top file |
| Handles unique names | ❌ Fails with timestamp files | ✅ Works with any filename |
| Reusability | ❌ Limited | ✅ Fully reusable |
| Maintenance | ❌ Needs manual update | ✅ Zero maintenance |

---

## 📝 Console Output Example

```
════════════════════════════════════════════════════════════════
   STEP 2: CLICK ON FIRST PDF FILE (TOP OF LIST)
════════════════════════════════════════════════════════════════

Step 2.1: Getting first file name from the file list...
📋 Getting first file name from the file list...
✓ First file name: 'TestPDF_20260401_204116.pdf'
✓ First file name: 'TestPDF_20260401_204116.pdf' (uploaded in TC_03)

Step 2.2: Clicking on first PDF file 'TestPDF_20260401_204116.pdf'...
📄 Clicking on first PDF file: 'TestPDF_20260401_204116.pdf' in the file list...
✓ Successfully clicked on first PDF file: 'TestPDF_20260401_204116.pdf'

✅ Step 2 Completed: PDF file clicked successfully
```

---

## 🎯 How It Integrates with TC_03

**TC_03 (Test 2):** Uploads a new PDF file → File appears at **TOP of list**

**TC_04 (Test):** Clicks the **TOP file** (uploaded in TC_03) → Validates collapse/expand

✅ **Perfect integration** - TC_04 always uses the file from TC_03!

---

## 🔍 Validation Points

1. ✅ File name is retrieved successfully
2. ✅ File name is not null or empty
3. ✅ File is clickable
4. ✅ Clicked filename matches retrieved filename
5. ✅ Collapsed bar shows correct filename
6. ✅ Tooltip matches the filename
7. ✅ All expand/collapse operations work with the dynamic filename

---

## 🚀 Ready to Run!

```bash
# Run TC_03 and TC_04 together (as configured in testng.xml)
mvn test

# Or run only TC_04
mvn test -Dtest=TC_04_PDF_PDFViewPage
```

---

**Updated:** April 1, 2026  
**Status:** ✅ Fully Dynamic & Ready to Use  
**Compilation:** ✅ No Errors (only minor warnings)

