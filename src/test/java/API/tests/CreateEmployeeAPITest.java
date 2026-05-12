package API.tests;

import API.base.BaseAPITest;
import API.services.AuthService;
import API.services.EmployeeService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateEmployeeAPITest extends BaseAPITest {

    private String freshSessionCookie;

    @BeforeClass
    public void setupAuth() {
        String email = "arwendymelyn@dibimbing.id";
        String password = "s2et9bh6l";
        String companyId = "811637b1-9989-4d45-a9f5-220c5f2354f7";

        Response loginResp = AuthService.login(email, password, companyId);

        freshSessionCookie = loginResp.getCookie("sid_b2b");
        Assert.assertNotNull(freshSessionCookie, "Gagal mendapatkan fresh cookie saat setup!");
    }

    @Test(description = "Verify successful creation of a new employee", priority = 2)
    public void testCreateEmployeeSuccess() throws IOException {
        test.info("Menyiapkan payload variabel dengan email unik...");

        String uniqueEmail = "apitesting" + System.currentTimeMillis();

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", "API Testing Sukses");
        inputMap.put("employeeId", "");
        inputMap.put("email", uniqueEmail);
        inputMap.put("phoneNumber", "123456789");
        inputMap.put("divisionId", "c59fd0d9-d8d2-41c6-af29-544193da15a9");
        inputMap.put("employeeRole", "");
        inputMap.put("gender", "");
        inputMap.put("address", "");
        inputMap.put("nik", "");
        inputMap.put("npwp", "");

        test.info("Mengirim request Create Employee dengan fresh cookie...");
        Response response = EmployeeService.createEmployee(freshSessionCookie, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNull(errors, "Seharusnya tidak ada errors! Error aktual: " + errors);

        String employeeId = response.jsonPath().getString("data.createEmployee.id");
        Assert.assertNotNull(employeeId, "ID Employee tidak boleh kosong!");

        test.pass("Skenario Sukses: Employee baru berhasil dibuat dengan ID: " + employeeId);
    }

    @Test(description = "Verify failed creation of employee due to registered email", priority = 1)
    public void testCreateEmployeeDuplicateEmail() throws IOException {
        test.info("Menyiapkan payload variabel untuk duplicate email...");

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", "API Testing");
        inputMap.put("employeeId", "");
        inputMap.put("email", "apitesting");
        inputMap.put("phoneNumber", "1");
        inputMap.put("divisionId", "c59fd0d9-d8d2-41c6-af29-544193da15a9");
        inputMap.put("employeeRole", "");
        inputMap.put("gender", "");
        inputMap.put("address", "");
        inputMap.put("nik", "");
        inputMap.put("npwp", "");

        test.info("Mengirim request Create Employee dengan fresh cookie...");
        Response response = EmployeeService.createEmployee(freshSessionCookie, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        String errorMessage = response.jsonPath().getString("errors[0].message");
        Assert.assertNotNull(errorMessage, "BUG: Seharusnya GraphQL mengembalikan error message!");
        test.info("Error message yang ditangkap: " + errorMessage);

        String expectedErrorMsg = "The user with this email is already registered, please use another email";

        boolean isExpectedError = errorMessage.contains(expectedErrorMsg);

        Assert.assertTrue(isExpectedError,
                "FAILED: Ekspektasi error adalah '" + expectedErrorMsg + "', tetapi yang didapat: '" + errorMessage + "'");

        test.pass("Skenario Sukses: API berhasil menolak email duplikat dan menampilkan pesan error yang tepat.");
    }

}