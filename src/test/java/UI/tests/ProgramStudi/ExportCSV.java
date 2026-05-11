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

public class ExportCSV extends BaseUITest {

    private static final Logger logger = Logger.getLogger(ExportCSV.class.getName());

    @Test(description = "Verify Export CSV functionality in Detail Program Studi")
    public void testExportCSV() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        logger.info("=== Eksekusi Skenario: Export CSV Program Studi ===");
        test.info("=== Eksekusi Skenario: Export CSV Program Studi ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Berpindah ke tab Program Studi...");
        programStudiPage.clickProgramStudiTab();

        test.info("Mengklik tombol Detail pada baris pertama Program Studi...");
        programStudiPage.clickFirstRowDetail();

        test.info("Menunggu transisi URL ke halaman Detail Program Studi...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/division/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail Program Studi!");

        test.info("Mengeksekusi tombol Export CSV...");
        programStudiPage.clickExportCsvButton();

        test.pass("Skenario Sukses: Tombol Export CSV berhasil diklik.");
        logger.info("Skenario Export CSV tuntas dieksekusi!");
    }
}