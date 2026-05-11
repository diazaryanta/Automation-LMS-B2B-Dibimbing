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

public class EditEmployee extends BaseUITest {

    private static final Logger logger = Logger.getLogger(EditEmployee.class.getName());

    @Test(description = "Verify Edit CreateEmployeeAPITest functionality in Detail CreateEmployeeAPITest")
    public void testEditEmployee() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Edit CreateEmployeeAPITest ===");
        test.info("=== Eksekusi Skenario: Edit CreateEmployeeAPITest ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Mengklik tombol Detail pada baris pertama...");
        employeePage.clickFirstRowDetailButton();

        test.info("Menunggu transisi URL ke halaman Detail CreateEmployeeAPITest...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail!");

        test.info("Mengklik tombol Edit CreateEmployeeAPITest...");
        employeePage.clickEditEmployeeButton();

        test.info("Mengisi form Edit CreateEmployeeAPITest dengan data yang diperbarui...");
        employeePage.fillEditEmployeeForm(
                "Fitur Edit Updated",
                "123",
                "fituredit@dibimbing.id",
                "1",
                "QA Batch 3+",
                "Senior QA"
        );

        test.info("Menyimpan perubahan (Save Changes)...");
        employeePage.clickSubmitEditEmployeeButton();

        test.pass("Skenario Sukses: Form Edit CreateEmployeeAPITest berhasil diisi dan disubmit tanpa hambatan.");
        logger.info("Skenario Edit CreateEmployeeAPITest tuntas dieksekusi!");
    }
}