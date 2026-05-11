package UI.tests.EmployeeList;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class Activate extends BaseUITest {

    private static final Logger logger = Logger.getLogger(Activate.class.getName());

    @Test(description = "Verify Activate/Inactivate CreateEmployeeAPITest functionality in Detail CreateEmployeeAPITest")
    public void testActivateEmployee() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Activate / Inactivate CreateEmployeeAPITest ===");
        test.info("=== Eksekusi Skenario: Activate / Inactivate CreateEmployeeAPITest ===");

        // 1. LoginAPITest & Navigasi ke menu CreateEmployeeAPITest
        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        // 2. Masuk ke halaman Detail CreateEmployeeAPITest baris pertama
        test.info("Mengklik tombol Detail pada baris pertama...");
        employeePage.clickFirstRowDetailButton();

        // Mengamankan transisi URL menggunakan Smart Wait
        test.info("Menunggu transisi URL ke halaman Detail CreateEmployeeAPITest...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail!");

        // 3. Eksekusi tombol Activate/Inactivate di halaman detail
        test.info("Mengeksekusi tombol Activate/Inactivate...");
        employeePage.clickActivationButton();

        // 4. Konfirmasi pada modal pop-up
        test.info("Menunggu modal muncul dan mengklik tombol konfirmasi...");
        employeePage.clickConfirmActivationButton();

        test.pass("Skenario Sukses: Berhasil masuk ke detail dan melakukan konfirmasi Activate/Inactivate.");
        logger.info("Skenario Activate/Inactivate tuntas dieksekusi!");
    }
}