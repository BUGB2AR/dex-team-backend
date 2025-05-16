package com.dex.team.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EstatisticasControllerTest extends BaseControllerTest {

	@Test
	public void testTimeDaData() {
	    LocalDate data = LocalDate.of(2023, 1, 1);

	    given()
	        .param("data", data.toString())
	    .when()
	        .get("/api/estatisticas/time-da-data")
	    .then()
	        .statusCode(HttpStatus.OK.value());
	}

    @Test
    public void testIntegranteMaisUsado() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 1);

        given()
            .param("dataInicial", dataInicial.toString())
            .param("dataFinal", dataFinal.toString())
        .when()
            .get("/api/estatisticas/integrante-mais-usado")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testIntegrantesDoTimeMaisComum() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 1);

        given()
            .param("dataInicial", dataInicial.toString())
            .param("dataFinal", dataFinal.toString())
        .when()
            .get("/api/estatisticas/time-mais-comum")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testFuncaoMaisComum() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 1);

        given()
            .param("dataInicial", dataInicial.toString())
            .param("dataFinal", dataFinal.toString())
        .when()
            .get("/api/estatisticas/funcao-mais-comum")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testFranquiaMaisFamosa() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 1);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataInicialStr = dataInicial.format(formatter);
        String dataFinalStr = dataFinal.format(formatter);

        given()
            .param("dataInicial", dataInicialStr)
            .param("dataFinal", dataFinalStr)
        .when()
            .get("/api/estatisticas/franquia-mais-famosa")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
            
    }



    @Test
    public void testContagemPorFranquia() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 1);

        given()
            .param("dataInicial", dataInicial.toString())
            .param("dataFinal", dataFinal.toString())
        .when()
            .get("/api/estatisticas/contagem-por-franquia")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testContagemPorFuncao() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 12, 31);

        given()
            .param("dataInicial", dataInicial.toString())
            .param("dataFinal", dataFinal.toString())
        .when()
            .get("/api/estatisticas/contagem-por-funcao")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("quantidade", equalTo(0));
    }
}
