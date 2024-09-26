package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static baseUrl.TrelloBaseUrl.*;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ApiTrelloStepDefinitions {

    String boardId;
    List<String> boardListId;
    List<String> cardIdList = new ArrayList<>();
    String updateCardId;

    @Given("kullanici {string} isminde {string} olusturur")
    public void kullanici_isminde_olusturur(String name, String path) {

        setSpec();
        spec.pathParam("path", path)
                .queryParams("name", name);

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .post("/{path}");
        response.then().statusCode(200);

        JsonPath responseJsonPath = response.jsonPath();
        boardId = responseJsonPath.get("id");
    }


    @Then("kullanici {string} isminde olusturdugu {string} dogrular")
    public void kullanici_isminde_olusturdugu_dogrular(String name, String path) {

        setSpec();
        spec.pathParam("path", path)
                .pathParam("idPath", boardId);

        Response response = given()
                .spec(spec)
                .when()
                .get("/{path}/{idPath}");

        JsonPath responseJsonPath = response.jsonPath();
        assertEquals(boardId, responseJsonPath.get("id"));
        assertEquals(name, responseJsonPath.get("name"));
    }


    @Then("kullanici olusturdugu boarddan list idlerini alir")
    public void kullanici_olusturdugu_boarddan_list_idlerini_alir() {
        setSpec();
        spec.pathParam("boardsPath", "boards")
                .pathParam("idPath", boardId)
                .pathParam("listsPath", "lists");

        Response response = given()
                .spec(spec)
                .when()
                .get("/{boardsPath}/{idPath}/{listsPath}");

        JsonPath responseJsonPath = response.jsonPath();
        boardListId = responseJsonPath.getList("id");
    }


    @When("kullanici olusturulan borda {string} ekler")
    public void kullanici_olusturulan_borda_ekler(String path, DataTable dataTable) {

        List<String> cartNameList = dataTable.asList(String.class);

        for (String name : cartNameList) {
            setSpec();
            int index = new Random().nextInt(boardListId.size());
            spec.pathParam("path", path)
                    .queryParams("name", name)
                    .queryParams("idList", boardListId.get(index));

            Response response = given()
                    .spec(spec)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/{path}");
            response.then().statusCode(200);

            JsonPath responseJsonPath = response.jsonPath();
            cardIdList.add(responseJsonPath.get("id"));
        }
    }


    @Then("kullanici olusturdugu kartlari dogrular")
    public void kullanici_olusturdugu_kartlari_dogrular(DataTable dataTable) {

        List<String> cartName = dataTable.asList(String.class);

        setSpec();
        for (int i = 0; i < cardIdList.size(); i++) {

            spec.pathParam("path", "cards")
                    .pathParam("idPath", cardIdList.get(i));

            Response response = given()
                    .spec(spec)
                    .when()
                    .get("/{path}/{idPath}");

            JsonPath responseJsonPath = response.jsonPath();
            assertEquals(cardIdList.get(i), responseJsonPath.get("id"));
            assertEquals(cartName.get(i), responseJsonPath.get("name"));
        }
    }


    @Then("kullanici kartlardan birini {string} olarak gunceller")
    public void kullanici_kartlardan_birini_olarak_gunceller(String newName) {

        int number = new Random().nextInt(cardIdList.size());

        updateCardId = cardIdList.get(number);

        setSpec();
        spec.pathParam("path", "cards")
                .pathParams("idPath", updateCardId)
                .queryParams("name", newName);

        Response response = given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .when()
                .put("/{path}/{idPath}");
        response.then().statusCode(200);
    }


    @Then("kullanici guncelledigi {string} karti dogrular")
    public void kullanici_guncelledigi_karti_dogrular(String updateName) {

        setSpec();
        spec.pathParam("path", "cards")
                .pathParam("idPath", updateCardId);

        Response response = given()
                .spec(spec)
                .when()
                .get("/{path}/{idPath}");

        JsonPath responseJsonPath = response.jsonPath();
        assertEquals(updateCardId, responseJsonPath.get("id"));
        assertEquals(updateName, responseJsonPath.get("name"));
    }


    @Then("kullanici olusturulan kartlari siler")
    public void kullanici_olusturulan_kartlari_siler() {

        for (int i = 0; i < cardIdList.size(); i++) {

            setSpec();
            spec.pathParam("path", "cards")
                    .pathParam("idPath", cardIdList.get(i));

            Response response = given()
                    .spec(spec)
                    .when()
                    .delete("/{path}/{idPath}");
            response.then().statusCode(200);
        }
    }

    @Then("kullanici kartlari sildigini dogrular")
    public void kullanici_kartlari_sildigini_dogrular() {

        try {
            for (int i = 0; i < cardIdList.size(); i++) {

                setSpec();
                spec.pathParam("path", "cards")
                        .pathParam("idPath", cardIdList.get(i));

                Response response = given()
                        .spec(spec)
                        .when()
                        .get("/{path}/{idPath}");
                response.then().statusCode(404);
            }
        } catch (Exception e) {
            System.out.println("Cards Body Not Found");
        }
    }


    @Then("kullanici olusturulan boardi siler")
    public void kullanici_olusturulan_boardi_siler() {

        setSpec();
        spec.pathParam("path", "boards")
                .pathParam("idPath", boardId);

        Response response = given()
                .spec(spec)
                .when()
                .delete("/{path}/{idPath}");
        response.then().statusCode(200);
    }


    @Then("kullanici boardi sildigini dogrular")
    public void kullanici_boardi_sildigini_dogrular() {

        try {
            setSpec();
            spec.pathParam("path", "boards")
                    .pathParam("idPath", boardId);

            Response response = given()
                    .spec(spec)
                    .when()
                    .get("/{path}/{idPath}");
            response.then().statusCode(404);
        } catch (Exception e) {
            System.out.println("Boards Body Not Found");
        }
    }
}
