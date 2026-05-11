package UI.tests.EmployeeList;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;

public class ActionDownload extends BaseUITest {

    private static final Logger logger = Logger.getLogger(ActionDownload.class.getName());

    @Test(description = "Verify Download action in CreateEmployeeAPITest List")
    public void testDownloadEmployeeData() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Download CreateEmployeeAPITest Data ===");
        test.info("=== Eksekusi Skenario: Download CreateEmployeeAPITest Data ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Membuka menu dropdown Action...");
        employeePage.clickActionMenu();

        test.info("Mengklik tombol Download...");
        employeePage.clickDownloadOption();

        test.info("Memantau folder Downloads di Mac untuk memastikan file beres diunduh...");

        String downloadPath = System.getProperty("user.home") + "/Downloads";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        boolean isDownloaded = wait.until(webDriver -> {
            File dir = new File(downloadPath);
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.getName().toLowerCase().contains("employee") && !file.getName().endsWith(".download")) {
                        return true;
                    }
                }
            }
            return false;
        });

        if (isDownloaded) {
            test.pass("Skenario Sukses: File berhasil diunduh!");
            logger.info("File udah masuk ke folder Downloads.");
        } else {
            test.fail("Skenario Gagal: Udah ditungguin 15 detik, filenya ga muncul-muncul.");
        }
    }
}