package com.dex.team.controller;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import com.dex.team.repository.IntegranteRepository;
import com.dex.team.repository.TimeRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class TimeControllerTest extends BaseControllerTest {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private IntegranteRepository integranteRepository;

    @AfterEach
    void tearDown() {
        timeRepository.deleteAll();
        integranteRepository.deleteAll();
    }

    @Test
    void whenGetAllTimes_thenReturnOk() {
        Time time = new Time();
        time.setData(LocalDate.of(2023, 1, 1));
        timeRepository.save(time);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/times")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1))
                .body("[0].data", contains(2023, 1, 1));
    }

    @Test
    void whenCreateTime_thenReturnCreated() {
        String requestBody = """
        {
            "data": "2023-01-15"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/times")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("data", hasItems(2023, 1, 15));
    }

    @Test
    void whenGetTimeById_thenReturnTime() {
        Time time = new Time();
        time.setData(LocalDate.of(2023, 2, 1));
        time = timeRepository.save(time);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/times/{id}", time.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(time.getId().intValue()))
                .body("data", hasItems(2023, 2, 1));
    }

    @Test
    void whenGetTimeWithComposicao_thenReturnTimeWithIntegrantes() {
    	Integrante integrante = new Integrante();
    	integrante.setNome("Jogador Composição");
    	integrante.setFranquia("Exemplo");
    	integrante.setFuncao("Atacante"); 
    	integrante = integranteRepository.save(integrante);

    	Time time = new Time();
    	time.setData(LocalDate.now());
    	time = timeRepository.save(time);

    	if (time.getComposicao() == null) {
    	    time.setComposicao(new ArrayList<>());
    	}
    	time.getComposicao().add(new ComposicaoTime(time, integrante));
    	timeRepository.save(time);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/times/{id}", time.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("composicao", hasSize(1))
                .body("composicao[0].integrante.nome", equalTo("Jogador Composição"));
    }


}
