package UI.tests.EmployeeList;

import UI.base.BaseUITest;
import UI.pages.LoginPage;
import UI.pages.EmployeePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.Logger;

public class AddEmployee extends BaseUITest {

    private static final Logger logger = Logger.getLogger(AddEmployee.class.getName());

    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        return new Object[][] {
                // 1. Test Valid (Positive Test)
                {
                        "Test Valid",
                        "QA Engineer Valid",
                        "123456789",
                        "qa.valid" + System.currentTimeMillis() + "@gmail.com",
                        "081323459876",
                        "QA Batch 3+",
                        "QA Engineer"
                },

                // 2. Name Null
                {
                        "Scenario Name Null",
                        " ",
                        "123456789",
                        "qa.namenull" + System.currentTimeMillis(),
                        "0101010101",
                        "QA Batch 3+",
                        "QA Engineer"
                },

                // 3. Employee ID Null
                {
                        "Scenario Employee ID Null",
                        "QA Tester ID Null",
                        " ",
                        "qa.idnull" + System.currentTimeMillis(),
                        "0101010101",
                        "QA Batch 3+",
                        "QA Engineer"
                },

                // 4. Email Duplicate
                {
                        "Scenario Email Duplicate",
                        "QA Tester Duplicate",
                        "123456789",
                        "emailss",
                        "0101010101",
                        "QA Batch 3+",
                        "QA Engineer"
                },

                // 5. Phone Number Invalid
                {
                        "Scenario Phone Invalid",
                        "QA Tester Phone",
                        "123456789",
                        "qa.phone" + System.currentTimeMillis(),
                        "0101010101",
                        "QA Batch 3+",
                        "QA Engineer"
                },

                // 6. Role Null
                {
                        "Scenario Role Null",
                        "QA Tester Role Null",
                        "123456789",
                        "qa.rolenull" + System.currentTimeMillis(),
                        "0101010101",
                        "QA Batch 3+",
                        " "
                },

                // 7. All Field Null
                {
                        "Scenario All Field Null",
                        "  ",
                        "  ",
                        "  " + System.currentTimeMillis(),
                        "01",
                        "QA Batch 3+",
                        "   ",
                },

                // 8. Special Character di setiap field
                {
                        "Scenario Special Character",
                        "!@#$%^&*",
                        "!@#$%",
                        "&%$#@!",
                        "0101010101",
                        "QA Batch 3+",
                        "*&^%$#"
                },

                // 9. Emoticon di setiap field
                {
                        "Scenario Emoticon",
                        "☺️😭🙈",
                        "😭📈🙏🏻",
                        "😌"+ System.currentTimeMillis() + "🗿",
                        "0101010101",
                        "QA Batch 3+",
                        "😎🔥"
                },

                // 10. Simbol-simbol unik
                {
                        "Scenario Unique Symbols",
                        "ˆ¡ºª£•™¶",
                        "£¶§¢∞¢",
                        "≤≥ºª£æ¬˚µπ«¡¶†",
                        "0101010101",
                        "QA Batch 3+",
                        "÷æ¬˚"
                }
        };
    }

    @Test(description = "Regression Test: Add Employee Field Validations", dataProvider = "employeeData")
    public void testAddEmployeeScenarios(String scenarioName, String name, String id, String email, String phone, String program, String role) {
        LoginPage loginPage = new LoginPage(driver);
        EmployeePage employeePage = new EmployeePage(driver);

        logger.info("=== Memulai: " + scenarioName + " ===");
        test.info("=== Memulai: " + scenarioName + " ===");

        loginPage.loginToLms(ConfigReader.getProperty("email"), ConfigReader.getProperty("password"));
        employeePage.clickEmployeeMenu();
        employeePage.clickAddEmployeeButton();

        employeePage.fillEmployeeForm(name, id, email, phone, program, role);
        employeePage.clickSubmitEmployeeButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        if (scenarioName.equals("Test Valid")) {
            try {
                boolean isFormClosed = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("name")));
                Assert.assertTrue(isFormClosed, "BUG: Data Valid tapi form tidak mau tertutup!");
                logger.info("Skenario Positif Berhasil: Data valid diterima dan form tertutup");
                test.pass("Skenario Positif Berhasil: Data valid diterima dan form tertutup.");
            } catch (TimeoutException e) {
                Assert.fail("BUG: Form tidak tertutup setelah klik submit pada data VALID.");
            }
        } else {
            String failMessage ="" + scenarioName + " terdapat bug.";
            logger.severe(failMessage);
            Assert.fail(failMessage);
        }
    }
}