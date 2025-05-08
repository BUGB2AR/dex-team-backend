package com.dex.team.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComposicaoTimeTest {

    @Test
    void deveCriarComposicaoTimeComConstrutorCompleto() {
        Time time = new Time();
        time.setId(1L);

        Integrante integrante = new Integrante();
        integrante.setId(1L);

        ComposicaoTime composicaoTime = new ComposicaoTime(time, integrante);

        assertThat(composicaoTime.getTime()).isEqualTo(time);
        assertThat(composicaoTime.getIntegrante()).isEqualTo(integrante);
    }

    @Test
    void deveSetarEObterCampos() {
        ComposicaoTime composicaoTime = new ComposicaoTime();

        Time time = new Time();
        time.setId(2L);
        composicaoTime.setTime(time);

        Integrante integrante = new Integrante();
        integrante.setId(3L);
        composicaoTime.setIntegrante(integrante);

        composicaoTime.setId(10L);

        assertThat(composicaoTime.getId()).isEqualTo(10L);
        assertThat(composicaoTime.getTime()).isEqualTo(time);
        assertThat(composicaoTime.getIntegrante()).isEqualTo(integrante);
    }
}
