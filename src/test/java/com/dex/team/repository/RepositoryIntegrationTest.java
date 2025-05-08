package com.dex.team.repository;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryIntegrationTest {

    @Autowired
    private IntegranteRepository integranteRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;

    @Test
    public void whenSaveTimeWithComposicao_thenCorrectlySaved() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador Integração");
        integrante.setFranquia("Franquia Integração");
        integrante.setFuncao("Função Integração");
        integrante = integranteRepository.save(integrante);

        Time time = new Time();
        time.setData(LocalDate.now());

        ComposicaoTime composicao = new ComposicaoTime();
        composicao.setIntegrante(integrante);
        composicao.setTime(time);

        time.setComposicao(List.of(composicao));

        time = timeRepository.save(time);

        Time savedTime = timeRepository.findById(time.getId()).orElse(null);
        assertThat(savedTime).isNotNull();
        assertThat(savedTime.getComposicao()).hasSize(1);
        assertThat(savedTime.getComposicao().get(0).getIntegrante().getNome())
                .isEqualTo("Jogador Integração");

        List<ComposicaoTime> composicoesDoIntegrante = composicaoTimeRepository.findAllByIntegrante(integrante);
        assertThat(composicoesDoIntegrante).hasSize(1);
        assertThat(composicoesDoIntegrante.get(0).getTime().getData())
                .isEqualTo(time.getData());
    }

}
