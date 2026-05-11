package UI.tests.EmployeeList;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class FilterByAngkatan extends BaseUITest {

    private static final Logger logger = Logger.getLogger(FilterByAngkatan.class.getName());

    @DataProvider(name = "dataAngkatan")
    public Object[][] getAngkatanData() {
        return new Object[][] {
                {"2024 Ganjil"},
                {"2024 Genap"},
                {"2025 Ganjil"},
                {"2025 Genap"}
        };
    }

    @Test(description = "Verify Filter by Angkatan is correctly applied in Detail CreateEmployeeAPITest", dataProvider = "dataAngkatan")
    public void testFilterByAngkatan(String targetAngkatan) {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: Filter Angkatan " + targetAngkatan + " ===");
        test.info("=== Eksekusi Skenario: Filter Angkatan " + targetAngkatan + " ===");

        // 1. LoginAPITest & Navigasi
        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        // 2. Eksekusi Filter
        test.info("Memilih angkatan: " + targetAngkatan + " dari dropdown...");
        employeePage.selectAngkatan(targetAngkatan);

        // Menahan bot sebentar memastikan filter selesai di-render (Smart Wait)
        test.info("Menunggu UI merespons filter...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> employeePage.getSelectedAngkatanText().contains(targetAngkatan));

        // 3. Masuk ke Detail & Validasi URL
        test.info("Mengklik tombol Detail pada baris pertama...");
        employeePage.clickFirstRowDetailButton();

        test.info("Menunggu halaman berpindah ke Detail CreateEmployeeAPITest...");
        boolean isUrlChangedToDetail = wait.until(ExpectedConditions.urlMatches(".*/admin/employee/[a-zA-Z0-9-]+"));
        Assert.assertTrue(isUrlChangedToDetail, "Bug: URL gagal bertransisi ke halaman detail!");

        // 4. Validasi Teks di Halaman Detail
        test.info("Memvalidasi nilai Angkatan di dalam halaman detail...");
        String actualAngkatanInDetail = employeePage.getAngkatanTextInDetail(targetAngkatan);

        Assert.assertEquals(actualAngkatanInDetail, targetAngkatan,
                "Bug: Angkatan yang muncul di Detail CreateEmployeeAPITest tidak sesuai dengan filter!");

        test.pass("Skenario Sukses: Berhasil masuk ke Detail CreateEmployeeAPITest dan memvalidasi angkatan " + targetAngkatan + ".");
        logger.info("Filter Angkatan " + targetAngkatan + " lulus validasi!");
    }
}