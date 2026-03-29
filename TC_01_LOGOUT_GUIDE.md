# TC_01 Logout Functionality - Complete Guide

## ✅ What Was Added:

### **New Page Object: LogoutPage.java**
A complete page object for handling logout functionality with all modal interactions.

### **Updated Test: TC_01_LoginPage.java**
Now includes comprehensive logout testing with 3 scenarios.

---

## 🎯 Logout Functionality - What Gets Tested:

### **3 Complete Scenarios:**

#### **Scenario 1: Click "No" Button**
1. Click Logout button (with tooltip "Logout")
2. Verify modal appears with title "Confirm Logout"
3. Verify modal message "Do you want to Log Out?"
4. Click "No" button
5. ✅ Verify modal closes
6. ✅ Verify user stays on same page (logo still visible)

#### **Scenario 2: Click "Close (X)" Button**
1. Click Logout button again
2. Verify modal appears
3. Click Close (X) button in modal header
4. ✅ Verify modal closes
5. ✅ Verify user stays on same page (logo still visible)

#### **Scenario 3: Click "Yes" Button (Actual Logout)**
1. Click Logout button
2. Verify modal appears
3. Click "Yes" button
4. ✅ Verify user is logged out
5. ✅ Verify redirected to login page (Flatworld button visible)

---

## 📋 XPaths Used (All Validated):

### **Logout Button:**
```xpath
//button[@title='Logout']
```
- Tooltip validation: `getAttribute("title")` → "Logout"

### **Modal Elements:**

**Modal Container:**
```xpath
//div[@role='dialog']
```

**Modal Title:**
```xpath
//h3[@id='modalTitle']
```
- Expected text: "Confirm Logout"

**Modal Message:**
```xpath
//p[@id='modalMessage']
```
- Expected text: "Do you want to Log Out?"

**Close (X) Button:**
```xpath
//button[@aria-label='Close modal']
```

**No Button:**
```xpath
//button[normalize-space()='No']
```
OR
```xpath
//button[contains(@class,'modal-btn-cancel')]
```

**Yes Button:**
```xpath
//button[normalize-space()='Yes']
```
OR
```xpath
//button[contains(@class,'modal-btn-confirm')]
```

---

## 🚀 How to Run:

### **Run TC_01 Only:**
```
In IntelliJ IDEA:
1. Build → Rebuild Project
2. Right-click TC_01_LoginPage.java → Run 'TC_01_LoginPage'
```

### **Run All Tests (TC_01 + TC_02):**
```
1. Build → Rebuild Project
2. Right-click testng.xml → Run 'testng.xml'
```

---

## 📊 Expected Console Output:

```
════════════════════════════════════════
   TC_01: LOGIN AND LOGOUT TEST
════════════════════════════════════════

✓ ALREADY AUTHENTICATED!
   Using saved cookies - No MFA needed

⏳ Waiting for loader to disappear...
✓ Loader disappeared - Page loaded!
🔍 Checking logo immediately...
✓ Logo is present on the page
✓ Test PASSED - Logo verified successfully!

════════════════════════════════════════
   TESTING LOGOUT SCENARIOS
════════════════════════════════════════

--- Scenario 1: Testing 'No' Button ---
Logout button tooltip: Logout
🔍 Looking for Logout button...
✓ Found Logout button with tooltip: 'Logout'
✓ Clicked Logout button
🔍 Checking if logout confirmation modal is displayed...
✓ Logout confirmation modal is displayed
Modal title: Confirm Logout
Modal message: Do you want to Log Out?
Clicking 'No' button on modal...
✓ Clicked 'No' button
🔍 Verifying modal is closed...
✓ Modal is closed - Still on the same page
✓ Logo is present on the page
✓ Scenario 1 PASSED - Clicked No, stayed on same page

--- Scenario 2: Testing 'Close (X)' Button ---
🔍 Looking for Logout button...
✓ Found Logout button with tooltip: 'Logout'
✓ Clicked Logout button
🔍 Checking if logout confirmation modal is displayed...
✓ Logout confirmation modal is displayed
Clicking Close (X) button on modal...
✓ Clicked Close (X) button
🔍 Verifying modal is closed...
✓ Modal is closed - Still on the same page
✓ Logo is present on the page
✓ Scenario 2 PASSED - Clicked Close (X), stayed on same page

--- Scenario 3: Testing 'Yes' Button (Actual Logout) ---
🔍 Looking for Logout button...
✓ Found Logout button with tooltip: 'Logout'
✓ Clicked Logout button
🔍 Checking if logout confirmation modal is displayed...
✓ Logout confirmation modal is displayed
Clicking 'Yes' button on modal...
✓ Clicked 'Yes' button - Logging out...
🔍 Verifying user is logged out...
✓ User is logged out - Redirected to login page
✓ Scenario 3 PASSED - Clicked Yes, successfully logged out

════════════════════════════════════════
   ✓ ALL LOGOUT SCENARIOS PASSED!
════════════════════════════════════════

Browser closed successfully.
```

---

## 🎯 Test Validations:

### **What Gets Verified:**

✅ **Logout Button:**
- Has tooltip "Logout"
- Is clickable

✅ **Modal Appearance:**
- Modal appears after clicking logout
- Modal has correct title: "Confirm Logout"
- Modal has correct message: "Do you want to Log Out?"

✅ **"No" Button:**
- Closes the modal
- User stays on same page
- Logo still visible (not logged out)

✅ **"Close (X)" Button:**
- Closes the modal
- User stays on same page
- Logo still visible (not logged out)

✅ **"Yes" Button:**
- Logs out the user
- Redirects to login page
- Flatworld button visible on login page

---

## 📈 Test Flow Diagram:

```
TC_01_LoginPage Test
        ↓
    Login (or skip if cookies saved)
        ↓
    Wait for Loader to disappear
        ↓
    Verify Logo Present
        ↓
    ═══════════════════════════════════
    Test Logout Scenarios
    ═══════════════════════════════════
        ↓
    Scenario 1: "No" Button
    ├─ Click Logout
    ├─ Verify Modal (title, message)
    ├─ Click "No"
    ├─ Verify Modal Closes
    └─ Verify Still on Page ✅
        ↓
    Scenario 2: "Close (X)" Button
    ├─ Click Logout
    ├─ Verify Modal
    ├─ Click Close (X)
    ├─ Verify Modal Closes
    └─ Verify Still on Page ✅
        ↓
    Scenario 3: "Yes" Button
    ├─ Click Logout
    ├─ Verify Modal
    ├─ Click "Yes"
    ├─ Verify Logout
    └─ Verify on Login Page ✅
        ↓
    Close Browser
```

---

## 🎁 Benefits:

✅ **Complete Coverage** - Tests all 3 modal options (Yes, No, Close)  
✅ **Tooltip Validation** - Verifies logout button tooltip  
✅ **Modal Content Validation** - Checks title and message text  
✅ **Navigation Verification** - Ensures correct page after each action  
✅ **Reusable Code** - LogoutPage can be used in other tests  
✅ **HTML Reports** - All scenarios in Extent Reports with pass/fail  
✅ **Screenshots on Failure** - Auto-captured for debugging  

---

## 📝 LogoutPage Methods Available:

```java
// Get logout button tooltip
String tooltip = logoutPage.getLogoutTooltip();

// Click logout button
logoutPage.clickLogoutButton();

// Check if modal is displayed
boolean isModalVisible = logoutPage.isLogoutModalDisplayed();

// Get modal content
String title = logoutPage.getModalTitle();
String message = logoutPage.getModalMessage();

// Click modal buttons
logoutPage.clickNoButton();
logoutPage.clickCloseButton();
logoutPage.clickYesButton();

// Verify results
boolean modalClosed = logoutPage.isModalClosed();
boolean loggedOut = logoutPage.isLoggedOut();

// Complete logout (convenience method)
logoutPage.performLogout(); // Clicks Yes and verifies logout
```

---

## 🔄 To Add Logout to Other Tests:

```java
// In any test class extending BaseClass:
LogoutPage logoutPage = new LogoutPage(driver);

// Option 1: Complete logout
logoutPage.performLogout();

// Option 2: Custom flow
logoutPage.clickLogoutButton();
if (logoutPage.isLogoutModalDisplayed()) {
    logoutPage.clickYesButton();
    Assert.assertTrue(logoutPage.isLoggedOut());
}
```

---

## 🎉 Your Test is Complete!

**TC_01_LoginPage now includes:**
1. ✅ Login verification (with cookie support)
2. ✅ Logo verification
3. ✅ Logout tooltip verification
4. ✅ Modal content verification
5. ✅ "No" button testing
6. ✅ "Close (X)" button testing
7. ✅ "Yes" button testing (actual logout)
8. ✅ Navigation verification for all scenarios

Run your test now and watch all logout scenarios pass! 🚀

