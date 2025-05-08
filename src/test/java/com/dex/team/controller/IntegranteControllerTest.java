package com.dex.team.controller;

import com.dex.team.entity.Integrante;
import com.dex.team.repository.IntegranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class IntegranteControllerTest extends BaseControllerTest {

    @Autowired
    private IntegranteRepository integranteRepository;

    @AfterEach
    void tearDown() {
        integranteRepository.deleteAll();
    }

    @Test
    void whenGetAllIntegrantes_thenReturnOk() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador Teste");
        integrante.setFranquia("Franquia Teste");
        integrante.setFuncao("Função Teste");
        integranteRepository.save(integrante);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/integrantes")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1)) 
                .body("[0].nome", equalTo("Jogador Teste"))
                .body("[0].franquia", equalTo("Franquia Teste"))
                .body("[0].funcao", equalTo("Função Teste"));
    }

    @Test
    void whenGetIntegranteById_thenReturnIntegranteDTO() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador por ID");
        integrante.setFuncao("Atacante");
        integrante.setFranquia("Franquia A");
        integrante = integranteRepository.save(integrante);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/integrantes/{id}", integrante.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(integrante.getId().intValue()))
                .body("nome", equalTo("Jogador por ID"))
                .body("franquia", equalTo("Franquia A"))
                .body("funcao", equalTo("Atacante"));
    }

    @Test
    void whenGetIntegranteByInvalidId_thenReturnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/integrantes/{id}", 999)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void whenCreateIntegrante_thenReturnCreated() {
        String requestBody = """
        {
            "nome": "Novo Jogador",
            "franquia": "Nova Franquia",
            "funcao": "Nova Função"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/integrantes")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", notNullValue()) 
                .body("nome", equalTo("Novo Jogador"))
                .body("franquia", equalTo("Nova Franquia"))
                .body("funcao", equalTo("Nova Função"));
    }

    @Test
    void whenCreateIntegranteWithInvalidData_thenReturnBadRequest() {
        String requestBody = """
        	    {
        	        "nome": "Nome do Integrante",
        	        "franquia": "Franquia sem Nome",
        	        "funcao": "Função sem Nome"
        	    
        	    """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/integrantes")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value()); 
    }
}
