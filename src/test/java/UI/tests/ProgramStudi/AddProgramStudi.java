package UI.tests.ProgramStudi;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import UI.pages.ProgramStudiPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class AddProgramStudi extends BaseUITest {

    private static final Logger logger = Logger.getLogger(AddProgramStudi.class.getName());

    @DataProvider(name = "programStudiData")
    public Object[][] getProgramStudiData() {
        return new Object[][] {
                // 1. Tes Valid (Data Lengkap)
                {
                        "Test Valid",
                        "Divisi QA Automation " + System.currentTimeMillis(),
                        "Data Lengkap Valid"
                },

                // 2. Program Studi Name Null
                {
                        "Scenario Name Null",
                        " ",
                        "Namenya kosong"
                },

                // 3. Program Studi Description Null
                {
                        "Scenario Description Null",
                        "Descriptionnya kosong",
                        " "
                },

                // 4. Semua field null
                {
                        "Scenario All Null",
                        " ",
                        " "
                },

                // 5. Special Character
                {
                        "Scenario Special Character",
                        "!@#$%^&*",
                        "(*&^%$#@!"
                },

                // 6. Emoticon di setiap field
                {
                        "Scenario Emoticon",
                        "☺️😭🙈",
                        "🗿😎🔥"
                },

                // 7. Simbol simbol unik
                {
                        "Scenario Unique Symbols",
                        "ˆ¡ºª£•™¶",
                        "÷æ¬˚£¶§¢∞¢"
                }
        };
    }

    @Test(description = "Verify Add Program Studi form dengan DataProvider", dataProvider = "programStudiData")
    public void testAddProgramStudi(String scenarioName, String psName, String psDescription) {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        logger.info("=== Eksekusi Skenario: " + scenarioName + " ===");
        test.info("=== Eksekusi Skenario: " + scenarioName + " ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();
        programStudiPage.clickProgramStudiTab();

        programStudiPage.clickAddProgramStudiButton();

        // Panggil form filler
        programStudiPage.fillProgramStudiForm(psName, psDescription);
        programStudiPage.clickSubmitConfirmButton();

        logger.info("Lagi ngecek hasil inputannya nih...");
        test.info("Lagi ngecek hasil inputannya nih...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // --- ASSERTION LOGIC (HANYA TEST VALID YANG PASS) ---

        if (scenarioName.equals("Test Valid")) {
            // JALUR POSITIF: Form HARUS tertutup (Test akan Pass jika sukses tertutup)
            try {
                boolean isFormClosed = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("name")));
                Assert.assertTrue(isFormClosed, "BUG: Data Valid tapi form tidak mau tertutup!");
                logger.info("Sukses banget: Data berhasil masuk dan form udah nutup otomatis.");
                test.pass("Skenario Positif Berhasil: Data valid diterima dan form tertutup.");
            } catch (TimeoutException e) {
                Assert.fail("BUG: Form tidak tertutup setelah klik submit pada data VALID.");
            }
        } else {
            // SEMUA SKENARIO SELAIN "Test Valid" AKAN DIPAKSA FAIL
            String failMessage = "Sengaja Digagalkan (FAILED): Skenario " + scenarioName + " dipaksa fail sesuai instruksi.";
            logger.severe(failMessage);
            Assert.fail(failMessage);
        }
    }
}