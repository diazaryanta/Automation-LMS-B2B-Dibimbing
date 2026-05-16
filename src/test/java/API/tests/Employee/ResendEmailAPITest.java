package API.tests.Employee;

import API.base.BaseAPITest;
import API.services.AuthService;
import API.services.EmployeeService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ResendEmailAPITest extends BaseAPITest {

    private String freshSessionCookie;
    private String validEmployeeId = "0746dcda-48dd-403e-88df-4ce347b68a5f";

    @BeforeClass
    public void setupAuth() {
        String email = "arwendymelyn@dibimbing.id";
        String password = "s2et9bh6l";
        String companyId = "811637b1-9989-4d45-a9f5-220c5f2354f7";

        Response loginResp = AuthService.login(email, password, companyId);
        freshSessionCookie = loginResp.getCookie("sid_b2b");
        Assert.assertNotNull(freshSessionCookie, "Gagal mendapatkan fresh cookie saat setup!");
    }

    @Test(description = "Verify successful email resend for a valid employee ID", priority = 1)
    public void testResendEmailSuccess() throws IOException {
        test.info("Mengirim request Resend Email untuk Employee ID: " + validEmployeeId);

        Response response = EmployeeService.resendEmail(freshSessionCookie, validEmployeeId);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNull(errors, "Seharusnya tidak ada errors! Error aktual: " + errors);

        String returnedId = response.jsonPath().getString("data.resendEmployeePasswordEmail.id");
        Assert.assertEquals(returnedId, validEmployeeId, "ID yang direturn tidak sama dengan target ID!");

        test.pass("Skenario Sukses: Berhasil mengirim request Resend Email.");
    }

    @Test(description = "Verify failed email resend when using invalid employee ID", priority = 2)
    public void testResendEmailInvalidId() throws IOException {
        String invalidId = "049767a8-e15f-42fc-8f4b-50b2947c52b7a7";
        test.info("Mengirim request Resend Email dengan Employee ID yang TIDAK VALID: " + invalidId);

        Response response = EmployeeService.resendEmail(freshSessionCookie, invalidId);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNotNull(errors, "Bug: API tidak memberikan error saat dikasih ID ngawur!");

        String errorMessage = response.jsonPath().getString("errors[0].message");
        test.info("Pesan error yang ditangkap: " + errorMessage);

        test.pass("Skenario Negatif Sukses: API berhasil menolak ID yang tidak valid.");
    }
}