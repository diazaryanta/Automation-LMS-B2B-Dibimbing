package API.tests.ProgramStudi;

import API.base.BaseAPITest;
import API.services.AuthService;
import API.services.ProgramStudiService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateProgramStudiAPITest extends BaseAPITest {

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

    @Test(description = "Verify successful creation of a new Program Studi (Division)", priority = 1)
    public void testCreateProgramStudiSuccess() throws IOException {
        test.info("Menyiapkan payload variabel untuk Program Studi baru...");

        String uniqueName = "API Test " + System.currentTimeMillis();

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", uniqueName);
        inputMap.put("description", "Test API Valid Add New Program Studi");

        test.info("Mengirim request Create Division dengan fresh cookie...");
        Response response = ProgramStudiService.createProgramStudi(freshSessionCookie, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNull(errors, "Seharusnya tidak ada errors! Error aktual: " + errors);

        String divisionId = response.jsonPath().getString("data.createDivision.id");
        Assert.assertNotNull(divisionId, "ID Program Studi tidak boleh kosong!");

        String actualName = response.jsonPath().getString("data.createDivision.name");
        Assert.assertEquals(actualName, uniqueName, "Nama Program Studi yang tersimpan di server tidak sesuai!");

        test.pass("Skenario Sukses: Program Studi baru berhasil dibuat dengan ID: " + divisionId);
    }

    @Test(description = "Verify failed creation when Program Studi Name is empty", priority = 2)
    public void testCreateProgramStudiEmptyName() throws IOException {
        test.info("Menyiapkan payload dengan Field Name KOSONG...");

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", "");
        inputMap.put("description", "Deskripsi ini valid tapi namanya kosong");

        test.info("Mengirim request Create Division...");
        Response response = ProgramStudiService.createProgramStudi(freshSessionCookie, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus tetap 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNotNull(errors, "Bug: API mengizinkan pembuatan Program Studi tanpa nama! (errors null)");

        String errorMessage = response.jsonPath().getString("errors[0].message");
        Assert.assertNotNull(errorMessage, "Seharusnya ada pesan error detail dari GraphQL!");
        test.info("Pesan error yang berhasil ditangkap: " + errorMessage);

        test.pass("Skenario Negatif Sukses: API berhasil menolak Program Studi tanpa nama.");
    }

    @Test(description = "Verify failed creation when Program Studi Description is empty", priority = 3)
    public void testCreateProgramStudiEmptyDescription() throws IOException {
        test.info("Menyiapkan payload dengan Field Description KOSONG...");

        String uniqueName = "API Test No Desc " + System.currentTimeMillis();

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("name", uniqueName);
        inputMap.put("description", "");

        test.info("Mengirim request Create Division...");
        Response response = ProgramStudiService.createProgramStudi(freshSessionCookie, inputMap);

        Assert.assertEquals(response.statusCode(), 200, "Status code GraphQL harus tetap 200!");

        Object errors = response.jsonPath().get("errors");
        Assert.assertNotNull(errors, "Bug: API mengizinkan pembuatan Program Studi tanpa deskripsi! (errors null)");

        String errorMessage = response.jsonPath().getString("errors[0].message");
        Assert.assertNotNull(errorMessage, "Seharusnya ada pesan error detail dari GraphQL!");
        test.info("Pesan error yang berhasil ditangkap: " + errorMessage);

        test.pass("Skenario Negatif Sukses: API berhasil menolak Program Studi tanpa deskripsi.");
    }
}