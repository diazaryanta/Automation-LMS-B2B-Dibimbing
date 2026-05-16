package UI.tests.EmployeeList;

import UI.base.BaseUITest;
import UI.pages.EmployeePage;
import UI.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class SearchEmployeeList extends BaseUITest {

    private static final Logger logger = Logger.getLogger(SearchEmployeeList.class.getName());

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][] {
                {"Search by Email", "fitursearch@dibimbing.id"}
        };
    }

    @Test(description = "Verify Search functionality in CreateEmployeeAPITest List", dataProvider = "searchData")
    public void testSearchEmployee(String scenarioName, String keyword) {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Eksekusi Skenario: " + scenarioName + " ===");
        test.info("=== Eksekusi Skenario: " + scenarioName + " ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();

        test.info("Ketik keyword: " + keyword);
        employeePage.searchEmployee(keyword);

        test.info("Menunggu data di tabel ter-update sesuai keyword...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By firstRowLocator = By.cssSelector("table tbody tr:nth-child(1)");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(firstRowLocator, keyword));

        test.info("Validasi data yang muncul di tabel...");
        String firstRowData = employeePage.getFirstRowText();

        Assert.assertTrue(firstRowData.contains(keyword),
                "Wah nge-bug nih! Udah dicari tapi '" + keyword + "' ga muncul di tabel.");

        test.pass("Data " + keyword + " berhasil ditemukan di tabel hasil pencarian.");
        logger.info("Skenario " + scenarioName + " lulus!");
    }
}