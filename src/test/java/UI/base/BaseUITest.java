package UI.base;

import UI.config.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentReportUI;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class BaseUITest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        extent = ExtentReportUI.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method, Object[] args) {
        String testName = this.getClass().getSimpleName();

        if (args != null && args.length > 0) {
            testName = testName + " : " + args[0].toString();
        } else {
            testName = testName + " : " + method.getName();
        }

        test = extent.createTest(testName);

        driver = DriverManager.getDriver();
        String loginUrl = ConfigReader.getProperty("ui.baseUrl");
        driver.get(loginUrl);
        test.info("Membuka browser dan menuju ke: " + loginUrl);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String screenshotName = result.getName() + "_" + System.currentTimeMillis() + ".png";

            String folderPath = System.getProperty("user.dir") + "/reports/screenshot/";
            File destFile = new File(folderPath + screenshotName);

            try {
                destFile.getParentFile().mkdirs();
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                test.fail("Test Gagal: " + result.getThrowable().getMessage(),
                        MediaEntityBuilder.createScreenCaptureFromPath("screenshot/" + screenshotName).build());

            } catch (IOException e) {
                System.out.println("Gagal menyimpan screenshot: " + e.getMessage());
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Berhasil!");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Dilewati.");
        }

        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}