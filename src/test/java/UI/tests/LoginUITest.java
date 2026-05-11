package UI.tests;

import UI.base.BaseUITest;
import UI.pages.LoginPage;
import utils.ConfigReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Logger;

public class LoginUITest extends BaseUITest {

    private static final Logger logger = Logger.getLogger(LoginUITest.class.getName());

    @Test(description = "Verify successful login with valid credentials")
    public void testLoginSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);

        String validEmail = ConfigReader.getProperty("email");
        String validPassword = ConfigReader.getProperty("password");

        logger.info("Mencoba login dengan email: " + validEmail);
        test.info("Mencoba login dengan email: " + validEmail);
        loginPage.loginToLms(validEmail, validPassword);

        logger.info("Menunggu halaman dashboard dimuat...");
        test.info("Menunggu halaman dashboard dimuat...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));

        logger.info("Memverifikasi apakah URL berubah ke dashboard...");
        test.info("Memverifikasi apakah URL berubah ke dashboard...");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), "Gagal login! URL tidak berpindah ke halaman dashboard.");

        logger.info("Assertion Lulus: Berhasil masuk ke halaman Dashboard!");
        test.pass("Assertion Lulus: Berhasil masuk ke halaman Dashboard!");
    }
}