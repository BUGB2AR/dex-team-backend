package com.dex.team.entity;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegranteTest {

    @Test
    void deveSetarEObterCampos() {
        Integrante integrante = new Integrante();
        integrante.setId(1L);
        integrante.setNome("Mario");
        integrante.setFuncao("Líder");
        integrante.setFranquia("Nintendo");

        assertThat(integrante.getId()).isEqualTo(1L);
        assertThat(integrante.getNome()).isEqualTo("Mario");
        assertThat(integrante.getFuncao()).isEqualTo("Líder");
        assertThat(integrante.getFranquia()).isEqualTo("Nintendo");
    }
}
