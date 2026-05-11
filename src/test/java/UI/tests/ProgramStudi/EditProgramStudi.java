package UI.tests.ProgramStudi;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import UI.pages.ProgramStudiPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class EditProgramStudi extends BaseUITest {

    private static final Logger logger = Logger.getLogger(EditProgramStudi.class.getName());

    @Test(description = "Verify Edit Program Studi functionality")
    public void testEditProgramStudi() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        logger.info("=== Eksekusi Skenario: Edit Program Studi ===");
        test.info("=== Eksekusi Skenario: Edit Program Studi ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Berpindah ke tab Program Studi...");
        programStudiPage.clickProgramStudiTab();

        test.info("Mengklik tombol Detail pada baris pertama Program Studi...");
        programStudiPage.clickFirstRowDetail();

        test.info("Menunggu transisi URL ke halaman Detail Program Studi...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/division/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail Program Studi!");

        test.info("Mengklik tombol Edit Program Studi...");
        programStudiPage.clickEditProgramStudi();

        String updatedName = "QA Updated Program Studi";
        String updatedDesc = "Nyoba update dulu yak";

        test.info("Mengisi form Edit Program Studi...");
        programStudiPage.fillEditProgramStudiForm(updatedName, updatedDesc);

        test.info("Menyimpan perubahan (Save Changes)...");
        programStudiPage.clickSubmitEditProgramStudi();

        test.pass("Skenario Sukses: Form Edit Program Studi berhasil disubmit dengan lancar.");
        logger.info("Skenario Edit Program Studi tuntas dieksekusi!");
    }
}