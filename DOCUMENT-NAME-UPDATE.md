# Document Name Update Summary

## Date: April 1, 2026

## Overview
Updated the document name references throughout the project:
1. **Target document** (Document Model column): Changed from `asset_gift_letter_temp` to `asset_gift_letter`
2. **Upload PDF file**: Changed from `sample.pdf` to `asset_gift_letter.pdf`

---

## Changes Made

### 1. Physical File Renamed ✅
**Location:** `src/test/resources/testData/`
- **Before:** `sample.pdf`
- **After:** `asset_gift_letter.pdf`

### 2. Target Document Updated ✅
**File:** `src/test/java/testCase/TC_03_PDF_DocumentListPage.java`
- **Before:** `private String targetDocument = "asset_gift_letter_temp";`
- **After:** `private String targetDocument = "asset_gift_letter";`
- Updated all related comments referencing `asset_gift_letter_temp` → `asset_gift_letter`

### 3. Upload File References Updated ✅
**File:** `src/test/java/testCase/TC_03_PDF_DocumentListPage.java`
- All 3 upload calls updated: `initiateUpload("asset_gift_letter.pdf")`

### 4. Documentation Comments Updated ✅
- **PDFViewPage.java:** Updated example in `extractFileCountFromTitle()` JavaDoc
- **HomePage.java:** Updated example in `getFilesCountForDocument()` JavaDoc
- **FileUploadHelper.java:** Updated examples in `createUniqueFile()` and `getTestDataFilePath()` JavaDoc

---

## Impact Assessment

### ✅ No Breaking Changes
- The `targetDocument` variable is used dynamically everywhere via XPath
- All method signatures remain the same
- The `FileUploadHelper` utility handles any PDF file name dynamically
- Test flow remains identical

### ✅ Test Flow Preserved
1. Script searches for `asset_gift_letter` in the Document Model column
2. Clicks on it to navigate to PDF View page
3. Uploads `asset_gift_letter.pdf` (creates unique timestamped copies)
4. Validates upload, duplicate, and non-PDF error flows

---

## Testing Recommendations

After this update, run the test suite to verify:
```bash
mvn clean test -Dtest=TC_03_PDF_DocumentListPage
```

Or run all tests:
```bash
mvn clean test
```

---

## Notes
- The target document in tests remains: `asset_gift_letter_temp` (this is the document model name in the application)
- The upload file is now: `asset_gift_letter.pdf` (this is the actual PDF being uploaded)
- All temporary test files are still created with unique names and cleaned up after tests
