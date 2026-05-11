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

public class DeleteEmployee extends BaseUITest {

    private static final Logger logger = Logger.getLogger(DeleteEmployee.class.getName());

    @Test(description = "Verify Delete CreateEmployeeAPITest functionality in Detail CreateEmployeeAPITest")
    public void testDeleteEmployee() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Delete CreateEmployeeAPITest ===");
        test.info("=== Eksekusi Skenario: Delete CreateEmployeeAPITest ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Mengklik tombol Detail pada baris pertama...");
        employeePage.clickFirstRowDetailButton();

        test.info("Menunggu transisi URL ke halaman Detail CreateEmployeeAPITest...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail!");

        test.info("Mengeksekusi tombol Delete CreateEmployeeAPITest...");
        employeePage.clickDeleteEmployeeButton();

        test.info("Menunggu modal muncul dan mengklik tombol konfirmasi Delete...");
        employeePage.clickConfirmDeleteButton();

        test.pass("Skenario Sukses: Berhasil masuk ke detail dan melakukan konfirmasi Delete CreateEmployeeAPITest.");
        logger.info("Skenario Delete CreateEmployeeAPITest tuntas dieksekusi!");
    }
}