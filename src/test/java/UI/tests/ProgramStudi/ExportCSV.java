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

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;

public class ExportCSV extends BaseUITest {

    private static final Logger logger = Logger.getLogger(ExportCSV.class.getName());
    // Pastikan jalur folder ini sama dengan yang diatur di DriverManager
    private final String downloadPath = System.getProperty("user.home") + File.separator + "Downloads";

    @Test(description = "Verify Export CSV functionality in Detail Program Studi")
    public void testExportCSV() {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);
        ProgramStudiPage programStudiPage = new ProgramStudiPage(driver);

        logger.info("=== Eksekusi Skenario: Export CSV Program Studi ===");

        // Step 1: Pastikan folder 'downloads' ada di project
        File directory = new File(downloadPath);
        if (!directory.exists()) {
            directory.mkdirs();
            logger.info("Folder downloads baru saja dibuat.");
        }

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();
        programStudiPage.clickProgramStudiTab();
        programStudiPage.clickFirstRowDetail();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/division/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail Program Studi!");

        int initialCsvCount = countFiles(downloadPath);

        test.info("Mengeksekusi tombol Export CSV...");
        programStudiPage.clickExportCsvButton();

        test.info("Menunggu file CSV selesai diunduh secara dinamis...");

        boolean isDownloaded = waitForNewFile(downloadPath, initialCsvCount, 10);

        Assert.assertTrue(isDownloaded, "FAILED: File CSV gagal dideteksi di folder project setelah 10 detik!");

        test.pass("Skenario Sukses: File CSV berhasil terdeteksi di folder downloads project.");
        logger.info("Skenario Export CSV tuntas!");
    }

    private int countFiles(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        return (files == null) ? 0 : files.length;
    }

    private boolean waitForNewFile(String dirPath, int initialCount, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        File dir = new File(dirPath);

        while (System.currentTimeMillis() < endTime) {
            File[] currentFiles = dir.listFiles();
            int currentCount = (currentFiles == null) ? 0 : currentFiles.length;

            if (currentCount > initialCount) {
                boolean hasCrDownload = false;
                for (File f : currentFiles) {
                    if (f.getName().endsWith(".crdownload")) {
                        hasCrDownload = true;
                        break;
                    }
                }

                if (!hasCrDownload) {
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                    return true;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
}