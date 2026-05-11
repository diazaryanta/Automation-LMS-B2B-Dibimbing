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

public class EditEmployeeAPITest extends BaseAPITest {

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

    @Test(description = "Verify successful update of an existing employee")
    public void testEditEmployeeSuccess() throws IOException {
        test.info("Menyiapkan data update untuk Employee ID: " + targetEmployeeId);

        String updatedName = "API Edit Automation " + System.currentTimeMillis();
        String uniqueEmail = "APIEdit_" + System.currentTimeMillis() + "@dibimbing.id";

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", updatedName);
        inputMap.put("employeeId", "1");
        inputMap.put("email", uniqueEmail);
        inputMap.put("phoneNumber", "1");
        inputMap.put("divisionId", "1b5b276e-7718-446a-a7c1-700edd1e31e0");
        inputMap.put("employeeRole", "API Role");
        inputMap.put("angkatanId", 1);
        inputMap.put("gender", "male");
        inputMap.put("dateOfBirth", null);
        inputMap.put("address", "Bekasi City");
        inputMap.put("nik", "1231231231231");
        inputMap.put("npwp", "2313123123123");

        test.info("Mengirim request Update Employee...");
        Response response = EmployeeService.updateEmployee(freshSessionCookie, targetEmployeeId, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNull(errors, "Seharusnya tidak ada errors saat update! Error aktual: " + errors);

        String returnedId = response.jsonPath().getString("data.updateEmployee.id");
        Assert.assertEquals(returnedId, targetEmployeeId, "ID yang direturn tidak sama dengan target ID yang diedit!");

        test.pass("Skenario Sukses: Berhasil melakukan edit Employee. Nama baru: " + updatedName);
    }
}