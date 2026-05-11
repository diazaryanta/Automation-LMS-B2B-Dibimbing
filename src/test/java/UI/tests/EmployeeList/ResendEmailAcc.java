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

public class ResendEmailAcc extends BaseUITest {

    private static final Logger logger = Logger.getLogger(ResendEmailAcc.class.getName());

    @Test(description = "Verify Resend Email Account functionality in Detail CreateEmployeeAPITest")
    public void testResendEmailAccount() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Resend Email Account ===");
        test.info("=== Eksekusi Skenario: Resend Email Account ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Mengklik tombol Detail pada baris pertama...");
        employeePage.clickFirstRowDetailButton();

        test.info("Menunggu transisi URL ke halaman Detail CreateEmployeeAPITest...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail!");

        test.info("Mengeksekusi tombol Resend Email Account...");
        employeePage.clickResendEmailButton();

        test.info("Menunggu modal muncul dan mengklik tombol konfirmasi Resend...");
        employeePage.clickResendEmailConfirmButton();

        test.pass("Skenario Sukses: Berhasil masuk ke detail dan melakukan konfirmasi Resend Email.");
        logger.info("Skenario Resend Email Account tuntas dieksekusi!");
    }
}