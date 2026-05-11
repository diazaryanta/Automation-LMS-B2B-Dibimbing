package API.tests;

import API.base.BaseAPITest;
import API.services.AuthService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginAPITest extends BaseAPITest {

    public static String sessionCookie = "";
    private final String defaultCompanyId = "811637b1-9989-4d45-a9f5-220c5f2354f7";

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                // Format: { "Nama Skenario", "Email", "Password", "Kategori (VALID/INVALID)" }

                // 1. Login Valid
                {"1. Login Valid", "arwendymelyn@dibimbing.id", "s2et9bh6l", "VALID"},

                // 2. Login dengan email invalid
                {"2. Login dengan email invalid", "ngawur@dibimbing.id", "s2et9bh6l", "INVALID"},

                // 3. Login dengan email null/kosong
                {"3. Login dengan email null", "", "s2et9bh6l", "INVALID"},

                // 4. Login dengan password invalid
                {"4. Login dengan password invalid", "arwendymelyn@dibimbing.id", "salah123", "INVALID"},

                // 5. Login dengan password null/kosong
                {"5. Login dengan password null", "arwendymelyn@dibimbing.id", "", "INVALID"},

                // 6. Login dengan email & password null/kosong
                {"6. Login dengan email & password null", "", "", "INVALID"},

                // 7. Login dengan leading/trailing spaces pada email (Seharusnya tetap valid karena di-trim backend)
                {"7. Login leading/trailing spaces email", "   arwendymelyn@dibimbing.id   ", "s2et9bh6l", "VALID"},

                // 8a. Login case insensitivity email (Seharusnya tetap valid)
                {"8a. Login case insensitive email", "ArwendyMelyn@DIBIMBING.id", "s2et9bh6l", "VALID"},

                // 8b. Login case sensitive password (Seharusnya DITOLAK karena password KAPITAL)
                {"8b. Login case sensitive password", "arwendymelyn@dibimbing.id", "S2ET9BH6L", "INVALID"}
        };
    }

    @Test(description = "Verify various GraphQL login scenarios", dataProvider = "loginData")
    public void testLoginScenarios(String scenarioName, String email, String password, String expectedOutcome) {
        test.info("=== Eksekusi Skenario: " + scenarioName + " ===");
        test.info("Payload -> Email: [" + email + "], Password: [" + password + "]");

        Response response = AuthService.login(email, password, defaultCompanyId);

        Assert.assertEquals(response.statusCode(), 200, "API-nya nolak request kita (HTTP Status bukan 200)!");

        if (expectedOutcome.equals("VALID")) {
            String role = response.jsonPath().getString("data.login.user.role");

            if (role == null) {
                Assert.fail("BUG (" + scenarioName + "): Gagal login! Kemungkinan backend API tidak melakukan TRIM() atau tidak mengabaikan CASE pada email.");
            }

            Assert.assertEquals(role, "admin", "Role-nya bukan admin!");

            Object errors = response.jsonPath().get("data.login.errors");
            Assert.assertNull(errors, "BUG (" + scenarioName + "): Ada error message dari GraphQL padahal data seharusnya valid!");

            if (scenarioName.equals("1. Login Valid")) {
                sessionCookie = response.getCookie("sid_b2b");
                test.info("Berhasil menangkap session cookie (sid_b2b): " + sessionCookie);
                Assert.assertNotNull(sessionCookie, "Gagal mendapatkan Cookie sid_b2b dari response header!");
            }

            test.pass("Skenario Sukses: API menerima kredensial dengan benar.");

        } else {
            Object user = response.jsonPath().get("data.login.user");
            Assert.assertNull(user, "BUG CRITICAL (" + scenarioName + "): API mengembalikan data user padahal kredensial salah/kosong!");

            String errorMessageStr = response.jsonPath().getString("data.login.errors[0].message");

            if (errorMessageStr == null) {
                errorMessageStr = response.jsonPath().getString("errors[0].message");
            }

            Assert.assertNotNull(errorMessageStr, "BUG (" + scenarioName + "): API tidak memberikan pesan error sama sekali!");
            test.info("Error message yang ditangkap: " + errorMessageStr);

            if (errorMessageStr.toLowerCase().contains("wrong username or password")) {
                String failMessage = "FAILED (" + scenarioName + "): Pesan error adalah 'wrong username or password'.";
                test.fail(failMessage);
                Assert.fail(failMessage);
            } else {
                test.pass("Aman: Pesan error yang didapat BUKAN 'wrong username or password'.");
            }
        }
    }
}