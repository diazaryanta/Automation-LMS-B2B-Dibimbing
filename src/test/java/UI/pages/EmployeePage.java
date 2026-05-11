package UI.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import java.time.Duration;

public class EmployeePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Script khusus untuk menembus pertahanan Virtual DOM React JS
    private final String reactBypassScript =
            "var el = arguments[0]; " +
                    "var val = arguments[1]; " +
                    "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set; " +
                    "nativeInputValueSetter.call(el, val); " +
                    "el.dispatchEvent(new Event('input', { bubbles: true }));";

    //==================================
    // --- ADD NEW EMPLOYEE FEATURES ---
    //==================================

    private By employeeMenu = By.id("layout-desktop-menu-item-box-employee");
    private By addEmployeeButton = By.id("button-add-employee");

    private By nameInput = By.id("name");
    private By employeeIdInput = By.id("employeeId");
    private By emailInput = By.id("email");
    private By phoneInput = By.id("phoneNumber");
    private By programStudiSelect = By.id("division");
    private By roleInput = By.id("employeeRole");
    private By submitEmployeeButton = By.id("button-add-employee-submit");

    public EmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickEmployeeMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeMenu)).click();
    }

    public boolean isUrlMatch() {
        return wait.until(ExpectedConditions.urlContains("/admin/employee"));
    }

    public void clickAddEmployeeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton)).click();
    }

    public void fillEmployeeForm(String name, String empId, String email, String phone, String programStudi, String role) {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        WebElement empIdEl = driver.findElement(employeeIdInput);
        WebElement emailEl = driver.findElement(emailInput);
        WebElement phoneEl = driver.findElement(phoneInput);
        WebElement roleEl = driver.findElement(roleInput);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(reactBypassScript, nameEl, name);
        js.executeScript(reactBypassScript, empIdEl, empId);
        js.executeScript(reactBypassScript, emailEl, email);
        js.executeScript(reactBypassScript, roleEl, role);

        phoneEl.sendKeys(phone);

        if (programStudi != null && !programStudi.trim().isEmpty()) {
            try {
                Select selectProgram = new Select(driver.findElement(programStudiSelect));
                selectProgram.selectByVisibleText(programStudi);
            } catch (Exception e) {
                System.out.println("Dropdown native tersembunyi, mencoba via JavaScript...");
                js.executeScript(
                        "var select = arguments[0]; for(var i=0; i<select.options.length; i++){ if(select.options[i].text === arguments[1]){ select.selectedIndex = i; select.dispatchEvent(new Event('change', {bubbles: true})); break; } }",
                        driver.findElement(programStudiSelect), programStudi
                );
            }
        }
    }

    public void clickSubmitEmployeeButton() {
        WebElement submitBtn = driver.findElement(submitEmployeeButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", submitBtn);
        wait.until(ExpectedConditions.elementToBeClickable(submitEmployeeButton));

        try {
            submitBtn.click();
        } catch (Exception e) {
            System.out.println("Klik standar terhalang, mengeksekusi JS Click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        }
    }

    //====================
    // --- ACTION MENU ---
    //====================

    private By actionMenuButton = By.id("menu-button-admin-employee-action");
    private By downloadOption = By.cssSelector("button[data-action='download']");
    public void clickActionMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(actionMenuButton)).click();
    }

    public void clickDownloadOption() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadOption)).click();
    }

    //============================
    // --- SEARCH BAR FEATURES ---
    //============================

    private By searchInput = By.cssSelector("div#input-admin-employee-search input");
    private By firstRowTable = By.cssSelector("table tbody tr:nth-child(1)");

    //===================================
    // --- DROPDOWN ANGKATAN FEATURES ---
    //===================================
    private By angkatanDropdownBtn = By.xpath("//button[contains(@class, 'chakra-menu__menu-button') and (contains(., 'Filter') or contains(., '202'))]");

    public void selectAngkatan(String namaAngkatan) {
        wait.until(ExpectedConditions.elementToBeClickable(angkatanDropdownBtn)).click();
        By angkatanOption = By.xpath("//button[@role='menuitem' and text()='" + namaAngkatan + "']");
        wait.until(ExpectedConditions.elementToBeClickable(angkatanOption)).click();
    }

    public String getSelectedAngkatanText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(angkatanDropdownBtn)).getText();
    }

    //========================
    // --- DETAIL EMPLOYEE ---
    //========================

    private By detailButtonFirstRow = By.id("button-detail-employee-0");
    public void clickFirstRowDetailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(detailButtonFirstRow)).click();
    }

    public String getAngkatanTextInDetail(String expectedAngkatan) {
        By angkatanValueLocator = By.xpath("//p[text()='" + expectedAngkatan + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(angkatanValueLocator)).getText();
    }

    //==================================
    // --- ASSIGNED PROGRAM FEATURES ---
    //==================================

    private By assignedProgramTab = By.id("tabs-employee-detail--tab-1");
    public void clickAssignedProgramTab() {
        wait.until(ExpectedConditions.elementToBeClickable(assignedProgramTab)).click();
    }

    //=========================
    // --- ACTIONS FEATURES ---
    //=========================

    public void searchEmployee(String keyword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchInput).sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public String getFirstRowText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowTable)).getText();
    }

    //======================================
    // --- RESEND EMAIL ACCOUNT FEATURES ---
    //======================================

    private By resendEmailBtn = By.id("resend-email-button");
    private By resendEmailConfirmBtn = By.id("resend-email-confirm-button");

    public void clickResendEmailButton() {
        wait.until(ExpectedConditions.elementToBeClickable(resendEmailBtn)).click();
    }

    public void clickResendEmailConfirmButton() {
        wait.until(ExpectedConditions.elementToBeClickable(resendEmailConfirmBtn)).click();
    }

    //=================================
    // --- DELETE EMPLOYEE FEATURES ---
    //=================================

    private By deleteEmployeeBtn = By.id("delete-employee-button");
    private By confirmDeleteBtn = By.id("confirm-delete-button");

    public void clickDeleteEmployeeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteEmployeeBtn)).click();
    }

    public void clickConfirmDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();
    }

    //================================================
    // --- ACTIVATE / INACTIVATE EMPLOYEE FEATURES ---
    //================================================

    private By activationBtn = By.id("activation-employee-button");
    private By confirmActivationBtn = By.id("activation-employee-confirm-button");

    public void clickActivationButton() {
        wait.until(ExpectedConditions.elementToBeClickable(activationBtn)).click();
    }

    public void clickConfirmActivationButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmActivationBtn)).click();
    }

    //===============================
    // --- EDIT EMPLOYEE FEATURES ---
    //===============================

    private By editEmployeeBtn = By.id("edit-employee-button");
    private By editNameInput = By.id("edit-employee-name-input");
    private By editEmpIdInput = By.id("edit-employee-employee-id-input");
    private By editEmailInput = By.id("edit-employee-email-input");
    private By editPhoneInput = By.id("edit-employee-phone-number-input");
    private By editDivisionSelect = By.id("edit-employee-division-select");
    private By editRoleInput = By.id("edit-employee-employee-role-input");
    private By editSubmitBtn = By.id("edit-employee-save-changes-button");

    public void clickEditEmployeeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editEmployeeBtn)).click();
    }

    public void fillEditEmployeeForm(String name, String empId, String email, String phone, String programStudi, String role) {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(editNameInput));
        WebElement empIdEl = driver.findElement(editEmpIdInput);
        WebElement emailEl = driver.findElement(editEmailInput);
        WebElement phoneEl = driver.findElement(editPhoneInput);
        WebElement roleEl = driver.findElement(editRoleInput);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(reactBypassScript, nameEl, name);
        js.executeScript(reactBypassScript, empIdEl, empId);
        js.executeScript(reactBypassScript, emailEl, email);
        js.executeScript(reactBypassScript, roleEl, role);

        phoneEl.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.BACK_SPACE);
        phoneEl.sendKeys(phone);

        if (programStudi != null && !programStudi.trim().isEmpty()) {
            try {
                Select selectProgram = new Select(driver.findElement(editDivisionSelect));
                selectProgram.selectByVisibleText(programStudi);
            } catch (Exception e) {
                System.out.println("Dropdown native tersembunyi, mencoba via JavaScript...");
                js.executeScript(
                        "var select = arguments[0]; for(var i=0; i<select.options.length; i++){ if(select.options[i].text === arguments[1]){ select.selectedIndex = i; select.dispatchEvent(new Event('change', {bubbles: true})); break; } }",
                        driver.findElement(editDivisionSelect), programStudi
                );
            }
        }
    }

    public void clickSubmitEditEmployeeButton() {
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(editSubmitBtn));
        try {
            submitBtn.click();
        } catch (Exception e) {
            System.out.println("Klik standar terhalang, mengeksekusi JS Click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        }
    }

}