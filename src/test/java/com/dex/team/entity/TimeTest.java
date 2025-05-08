package com.dex.team.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeTest {

    @Test
    void deveSetarEObterCampos() {
        Time time = new Time();
        time.setId(1L);
        time.setData(LocalDate.of(2024, 1, 1));

        ComposicaoTime comp = new ComposicaoTime();
        comp.setId(99L);
        comp.setTime(time);

        time.setComposicao(List.of(comp));

        assertThat(time.getId()).isEqualTo(1L);
        assertThat(time.getData()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(time.getComposicao()).hasSize(1);
        assertThat(time.getComposicao().get(0).getId()).isEqualTo(99L);
    }
}
