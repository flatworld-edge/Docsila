# TC_02: Table Sorting Test - Complete Guide

## ✅ What Was Created:

### 1. **DocumentModelPage.java** (New Page Object)
Location: `src/test/java/pageObject/DocumentModelPage.java`

**Features:**
- ✅ Verifies all 8 table headers are present
- ✅ Checks which headers have sorting capability (7 sortable, 1 non-sortable)
- ✅ Tests sorting on all sortable columns (ascending and descending)
- ✅ Verifies sort icons are present on sortable headers
- ✅ Confirms "Actions" header has NO sort icon

### 2. **TC_02_LoginPageSorting.java** (New Test Case)
Location: `src/test/java/testCase/TC_02_LoginPageSorting.java`

**Test Flow:**
1. Login to application (uses saved cookies after first run)
2. Wait for loader to disappear
3. Verify logo is present
4. Verify table is present
5. Verify all 8 headers are present
6. Verify 7 headers are sortable (all except "Actions")
7. Test sorting on each sortable header (click twice: ascending → descending)

### 3. **testng.xml** (Updated)
Added TC_02 to the test suite - both tests will run in sequence

---

## 🎯 Table Headers Tested:

### Sortable Headers (7):
1. ✅ **Document Model** - Has sort icon, sorting tested
2. ✅ **Document Files** - Has sort icon, sorting tested
3. ✅ **Attributes** - Has sort icon, sorting tested
4. ✅ **Truth Coverage** - Has sort icon, sorting tested
5. ✅ **Runs** - Has sort icon, sorting tested
6. ✅ **Last Run** - Has sort icon, sorting tested
7. ✅ **Accuracy** - Has sort icon, sorting tested

### Non-Sortable Header (1):
8. ✅ **Actions** - No sort icon, no sorting

---

## 🔍 What the Test Does:

### For Each Sortable Header:
```
1. Click header → Sort ascending (▲ active)
2. Verify sorting applied
3. Click header again → Sort descending (▼ active)
4. Verify sorting applied
5. Move to next header (arrows return to default state)
```

### Verification Points:
- ✅ Table is visible on the page
- ✅ All 8 headers are present and displayed
- ✅ 7 headers have sort icons (▲▼)
- ✅ "Actions" header has NO sort icon
- ✅ Clicking headers triggers sorting
- ✅ Data changes when sorting is applied

---

## 🚀 How to Run:

### Run Both Tests (TC_01 + TC_02):
```
In IntelliJ IDEA:
1. Build → Rebuild Project
2. Right-click testng.xml → Run 'testng.xml'
```

### Run Only TC_02 (Sorting Test):
```
In IntelliJ IDEA:
1. Right-click TC_02_LoginPageSorting.java
2. Select "Run TC_02_LoginPageSorting"
```

---

## 📊 Expected Console Output:

```
════════════════════════════════════════
   TC_02: TABLE SORTING TEST
════════════════════════════════════════

Step 1: Logging in...
✓ ALREADY AUTHENTICATED!
   Using saved cookies - No MFA needed

Step 2: Waiting for home page to load...
✓ Loader disappeared - Page loaded!

Step 3: Verifying logo...
✓ Logo is present on the page

Step 4: Verifying table is present...
✓ Table is present

Step 5: Verifying all table headers and sort icons...
✓ Header 'Document Model' is present
✓ Header 'Document Files' is present
✓ Header 'Attributes' is present
✓ Header 'Truth Coverage' is present
✓ Header 'Runs' is present
✓ Header 'Last Run' is present
✓ Header 'Accuracy' is present
✓ Header 'Actions' is present

✓ Header 'Document Model' has sort icon
✓ Header 'Document Files' has sort icon
✓ Header 'Attributes' has sort icon
✓ Header 'Truth Coverage' has sort icon
✓ Header 'Runs' has sort icon
✓ Header 'Last Run' has sort icon
✓ Header 'Accuracy' has sort icon
✓ Actions header correctly has NO sort icon

Step 6: Testing sorting functionality on all headers...

--- Testing: Document Model ---
✓ Clicked header 'Document Model'
✓ Sorting applied - Table has X rows
✓ Clicked header 'Document Model'
✓ Sorting applied - Table has X rows
✓ Completed sorting test for: Document Model

--- Testing: Document Files ---
✓ Clicked header 'Document Files'
✓ Sorting applied - Table has X rows
✓ Clicked header 'Document Files'
✓ Sorting applied - Table has X rows
✓ Completed sorting test for: Document Files

... (same for all other sortable headers)

════════════════════════════════════════
   ✓ TC_02 TEST PASSED SUCCESSFULLY!
   All headers verified and sorting works!
════════════════════════════════════════
```

---

## 📈 Test Assertions:

The test will **PASS** if:
- ✅ Logo is displayed (home page loaded properly)
- ✅ Table is present on the page
- ✅ Exactly 8 headers are found
- ✅ Exactly 7 sortable headers are found
- ✅ All headers can be clicked for sorting
- ✅ Sorting changes the table data

The test will **FAIL** if:
- ❌ Logo is not displayed
- ❌ Table is not present
- ❌ Wrong number of headers (not 8)
- ❌ Wrong number of sortable headers (not 7)
- ❌ Any header is missing
- ❌ Sorting doesn't work on any header

---

## 🎁 Benefits:

✅ **Reuses Login Flow** - No need to re-login (cookies saved)  
✅ **Fast Execution** - Only waits for loader, no fixed delays  
✅ **Comprehensive** - Tests all 7 sortable columns  
✅ **Automated Verification** - Checks headers, icons, and sorting  
✅ **HTML Reports** - Extent Reports with pass/fail status  
✅ **Screenshots on Failure** - Auto-captured for debugging  

---

## 🔄 Test Execution Order:

When running `testng.xml`:
1. **TC_01_LoginPage** runs first - Verifies login and logo
2. **TC_02_LoginPageSorting** runs second - Verifies table sorting
3. Both tests use the same browser session (cookies saved)
4. Combined HTML report generated with both test results

---

## 📁 Files Modified/Created:

### New Files:
- ✅ `pageObject/DocumentModelPage.java` - Page object for table
- ✅ `testCase/TC_02_LoginPageSorting.java` - Sorting test case

### Updated Files:
- ✅ `testng.xml` - Added TC_02 to test suite

### Unchanged Files (Still Working):
- ✅ `TC_01_LoginPage.java` - Logo verification test
- ✅ `LoginPage.java` - Login page object
- ✅ `HomePage.java` - Home page object
- ✅ `BaseClass.java` - Base test class
- ✅ `ExtentReportManager.java` - Reporting

---

## 🚀 You're Ready to Run!

Execute the tests now:
```
Build → Rebuild Project
Right-click testng.xml → Run 'testng.xml'
```

Both TC_01 and TC_02 will run automatically and generate a comprehensive HTML report! 🎉

