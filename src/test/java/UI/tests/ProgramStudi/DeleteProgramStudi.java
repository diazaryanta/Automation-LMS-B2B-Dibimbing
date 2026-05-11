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

public class DeleteProgramStudi extends BaseUITest {

    private static final Logger logger = Logger.getLogger(DeleteProgramStudi.class.getName());

    @Test(description = "Verify Delete Program Studi functionality")
    public void testDeleteProgramStudi() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        logger.info("=== Eksekusi Skenario: Delete Program Studi ===");
        test.info("=== Eksekusi Skenario: Delete Program Studi ===");

        // 1. LoginAPITest & Navigasi ke menu CreateEmployeeAPITest
        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        // 2. Pindah ke Tab Program Studi
        test.info("Berpindah ke tab Program Studi...");
        programStudiPage.clickProgramStudiTab();

        // 3. Klik Detail di baris pertama
        test.info("Mengklik tombol Detail pada baris pertama Program Studi...");
        programStudiPage.clickFirstRowDetail();

        // Mengamankan transisi URL menggunakan Smart Wait (Perhatikan URL division)
        test.info("Menunggu transisi URL ke halaman Detail Program Studi...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/division/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail Program Studi!");

        // 4. Buka Modal Edit
        test.info("Mengklik tombol Edit Program Studi...");
        programStudiPage.clickEditProgramStudi();

        // 5. Klik Delete di dalam modal Edit
        test.info("Mengklik tombol Delete berikon tempat sampah...");
        programStudiPage.clickDeleteProgramStudi();

        // 6. Konfirmasi Delete
        test.info("Menunggu pop-up peringatan dan mengklik konfirmasi Delete...");
        programStudiPage.clickConfirmDelete();

        test.pass("Skenario Sukses: Berhasil melakukan konfirmasi penghapusan Program Studi.");
        logger.info("Skenario Delete Program Studi tuntas dieksekusi!");
    }
}