package API.tests.Employee;

import API.base.BaseAPITest;
import API.services.AuthService;
import API.services.EmployeeService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class EmployeeStatusAPITest extends BaseAPITest {

    private String freshSessionCookie;
    private String targetEmployeeId = "0746dcda-48dd-403e-88df-4ce347b68a5f";

    @BeforeClass
    public void setupAuth() {
        String email = "arwendymelyn@dibimbing.id";
        String password = "s2et9bh6l";
        String companyId = "811637b1-9989-4d45-a9f5-220c5f2354f7";

        Response loginResp = AuthService.login(email, password, companyId);
        freshSessionCookie = loginResp.getCookie("sid_b2b");
        Assert.assertNotNull(freshSessionCookie, "Gagal mendapatkan fresh cookie saat setup!");
    }

    // =================================================================================
    // BAGIAN 1: PENGUJIAN INACTIVATE
    // =================================================================================

    @Test(description = "1. Inactivate Employee (Pertama Kali)", priority = 1)
    public void testInactivateFirstTime() throws IOException {
        test.info("Tembak API Inactivate untuk mengubah status menjadi nonaktif...");
        Response response = EmployeeService.inactivateEmployee(freshSessionCookie, targetEmployeeId);

        String responseBody = response.getBody().asString();
        test.info("Raw Response API: " + responseBody);

        if (responseBody.contains("\"errors\"")) {
            Assert.fail("FAIL: Gagal Inactivate untuk pertama kalinya. Response: " + responseBody);
        } else {
            test.pass("PASS: Berhasil mengubah status Employee menjadi Inactive.");
        }
    }

    @Test(description = "2. Inactivate Employee (Kedua Kali / Duplikat)", priority = 2)
    public void testInactivateDuplicate() throws IOException {
        test.info("Tembak API Inactivate LAGI pada akun yang sudah nonaktif...");
        Response response = EmployeeService.inactivateEmployee(freshSessionCookie, targetEmployeeId);

        String responseBody = response.getBody().asString();
        test.info("Raw Response API: " + responseBody);

        if (responseBody.contains("\"errors\"")) {
            if (responseBody.toLowerCase().contains("already inactive")) {
                test.pass("PASS: API berhasil menolak dengan pesan yang tepat -> " + responseBody);
            } else {
                Assert.fail("FAIL: Muncul error yang tidak terduga -> " + responseBody);
            }
        } else {
            test.pass("PASS: API bersifat Idempotent (sukses tanpa memberikan pesan error).");
        }
    }


    // =================================================================================
    // BAGIAN 2: PENGUJIAN ACTIVATE
    // =================================================================================

    @Test(description = "3. Activate Employee (Pertama Kali)", priority = 3)
    public void testActivateFirstTime() throws IOException {
        test.info("Tembak API Activate untuk mengubah status kembali menjadi aktif...");
        Response response = EmployeeService.activateEmployee(freshSessionCookie, targetEmployeeId);

        String responseBody = response.getBody().asString();
        test.info("Raw Response API: " + responseBody);

        if (responseBody.contains("\"errors\"")) {
            Assert.fail("FAIL: Gagal Activate untuk pertama kalinya. Response: " + responseBody);
        } else {
            test.pass("PASS: Berhasil mengubah status Employee kembali menjadi Active.");
        }
    }

    @Test(description = "4. Activate Employee (Kedua Kali / Duplikat)", priority = 4)
    public void testActivateDuplicate() throws IOException {
        test.info("Tembak API Activate LAGI pada akun yang sudah aktif...");
        Response response = EmployeeService.activateEmployee(freshSessionCookie, targetEmployeeId);

        String responseBody = response.getBody().asString();
        test.info("Raw Response API: " + responseBody);

        if (responseBody.contains("\"errors\"")) {
            if (responseBody.toLowerCase().contains("already active") || responseBody.toLowerCase().contains("active")) {
                test.pass("PASS: API berhasil menolak dengan pesan yang tepat -> " + responseBody);
            } else {
                Assert.fail("FAIL: Muncul error yang tidak terduga -> " + responseBody);
            }
        } else {
            test.pass("PASS: API bersifat Idempotent (sukses tanpa memberikan pesan error).");
        }
    }
}