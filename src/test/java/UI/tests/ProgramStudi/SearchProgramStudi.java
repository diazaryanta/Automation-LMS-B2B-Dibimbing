package UI.tests.ProgramStudi;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import UI.pages.ProgramStudiPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class SearchProgramStudi extends BaseUITest {

    private static final Logger logger = Logger.getLogger(SearchProgramStudi.class.getName());

    @Test(description = "Verify Search functionality in Program Studi Tab")
    public void testSearchProgramStudi() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        String keyword = "QA Batch 3+";

        logger.info("=== Eksekusi Skenario: Search Program Studi ===");
        test.info("=== Eksekusi Skenario: Search Program Studi ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Berpindah ke tab Program Studi...");
        programStudiPage.clickProgramStudiTab();

        test.info("Melakukan pencarian dengan keyword: '" + keyword + "'...");
        programStudiPage.searchProgramStudi(keyword);

        test.info("Menunggu hasil pencarian dimuat di tabel...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector("table tbody tr:nth-child(1)"), keyword));
        } catch (Exception e) {
            logger.warning("Loading tabel lama atau data tidak ditemukan dalam 10 detik.");
        }

        test.info("Memvalidasi data pada baris pertama...");
        String firstRowData = programStudiPage.getFirstRowText();

        Assert.assertTrue(firstRowData.contains(keyword),
                "Bug: Hasil pencarian di baris pertama tidak mengandung kata '" + keyword + "'! \nData aktual: " + firstRowData);

        test.pass("Skenario Sukses: Pencarian Program Studi berhasil menampilkan data '" + keyword + "'.");
        logger.info("Skenario Search Program Studi tuntas dieksekusi!");
    }
}