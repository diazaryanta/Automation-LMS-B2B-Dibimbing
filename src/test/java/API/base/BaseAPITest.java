package API.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ExtentReportAPI;

import java.lang.reflect.Method;

public class BaseAPITest {
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportAPI.getInstance();
    }

    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Gagal: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Lulus Sempurna!");
        } else {
            test.skip("Test Di-skip");
        }
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}