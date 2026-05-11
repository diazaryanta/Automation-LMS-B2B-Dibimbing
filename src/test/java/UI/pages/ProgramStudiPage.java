package UI.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgramStudiPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Script khusus yang sudah di-upgrade agar support <input> DAN <textarea>
    private final String reactBypassScript =
            "var el = arguments[0]; " +
                    "var val = arguments[1]; " +
                    "var proto = el.tagName.toLowerCase() === 'textarea' ? window.HTMLTextAreaElement.prototype : window.HTMLInputElement.prototype; " +
                    "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(proto, 'value').set; " +
                    "nativeInputValueSetter.call(el, val); " +
                    "el.dispatchEvent(new Event('input', { bubbles: true }));";

    // ==================================
    // --- ADD PROGRAM STUDI FEATURES ---
    // ==================================

    private By programStudiTab = By.id("tabs-admin-employee--tab-1");
    private By addProgramStudiButton = By.id("add-division-button");
    private By nameInput = By.id("name");
    private By descriptionInput = By.id("description");
    private By submitConfirmButton = By.id("add-division-confirm-button");

    private By firstRowDetailBtn = By.xpath("(//button[@id='detail-division-button'])[1]");
    private By editProgramStudiBtn = By.id("edit-division-button");
    private By deleteProgramStudiBtn = By.id("delete-division-button");
    private By confirmDeleteBtn = By.id("delete-division-confirm-button");

    public ProgramStudiPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickProgramStudiTab() {
        wait.until(ExpectedConditions.elementToBeClickable(programStudiTab)).click();
    }

    public void clickAddProgramStudiButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addProgramStudiButton)).click();
    }

    public void fillProgramStudiForm(String name, String description) {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        WebElement descEl = driver.findElement(descriptionInput);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Gunakan script Bypass React pintar
        js.executeScript(reactBypassScript, nameEl, name);
        js.executeScript(reactBypassScript, descEl, description);
    }

    public void clickSubmitConfirmButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitConfirmButton)).click();
    }

    // ========================================
    // --- ACTIONS DETAIL & DELETE FEATURES ---
    // ========================================

    public void clickFirstRowDetail() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowDetailBtn)).click();
    }

    public void clickEditProgramStudi() {
        wait.until(ExpectedConditions.elementToBeClickable(editProgramStudiBtn)).click();
    }

    public void clickDeleteProgramStudi() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteProgramStudiBtn)).click();
    }

    public void clickConfirmDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();
    }

    //============================
    // --- EXPORT CSV FEATURES ---
    //============================

    private By exportCsvBtn = By.id("export-employee-button");
    public void clickExportCsvButton() {
        wait.until(ExpectedConditions.elementToBeClickable(exportCsvBtn)).click();
    }

    //=================================
    // --- SEARCH BAR PROGRAM STUDI ---
    //=================================

    private By searchInput = By.cssSelector("div#search-division-input input");
    private By firstRowTable = By.cssSelector("table tbody tr:nth-child(1)");

    public void searchProgramStudi(String keyword) {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchField.clear();
        searchField.sendKeys(keyword);
        searchField.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public String getFirstRowText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowTable)).getText();
    }

    //====================================
    // --- EDIT PROGRAM STUDI FEATURES ---
    //====================================

    private By submitEditBtn = By.id("edit-division-confirm-button");

    public void fillEditProgramStudiForm(String newName, String newDescription) {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        WebElement descEl = driver.findElement(descriptionInput);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(reactBypassScript, nameEl, newName);
        js.executeScript(reactBypassScript, descEl, newDescription);
    }

    public void clickSubmitEditProgramStudi() {
        wait.until(ExpectedConditions.elementToBeClickable(submitEditBtn)).click();
    }

}