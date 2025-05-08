package com.dex.team.repository;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ComposicaoTimeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;

    @Test
    public void whenSaveComposicaoTime_thenReturnSavedComposicao() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador Teste");
        integrante.setFranquia("Franquia Teste");
        integrante.setFuncao("Função Teste");
        entityManager.persist(integrante);

        Time time = new Time();
        time.setData(LocalDate.now());
        entityManager.persist(time);

        ComposicaoTime composicao = new ComposicaoTime();
        composicao.setIntegrante(integrante);
        composicao.setTime(time);

        ComposicaoTime saved = composicaoTimeRepository.save(composicao);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getIntegrante()).isEqualTo(integrante);
        assertThat(saved.getTime()).isEqualTo(time);
    }

    @Test
    public void whenFindAll_thenReturnAllComposicoes() {
    	Integrante integrante1 = new Integrante();
    	integrante1.setNome("Jogador 1");
    	integrante1.setFranquia("Franquia A");
    	integrante1.setFuncao("Atacante");
    	entityManager.persist(integrante1);

    	Integrante integrante2 = new Integrante();
    	integrante2.setNome("Jogador 2");
    	integrante2.setFranquia("Franquia B");
    	integrante2.setFuncao("Defensor");
    	entityManager.persist(integrante2);

        Time time = new Time();
        time.setData(LocalDate.now());
        entityManager.persist(time);

        ComposicaoTime composicao1 = new ComposicaoTime();
        composicao1.setIntegrante(integrante1);
        composicao1.setTime(time);
        entityManager.persist(composicao1);

        ComposicaoTime composicao2 = new ComposicaoTime();
        composicao2.setIntegrante(integrante2);
        composicao2.setTime(time);
        entityManager.persist(composicao2);

        List<ComposicaoTime> allComposicoes = composicaoTimeRepository.findAll();

        assertThat(allComposicoes).hasSize(2)
                .extracting(ComposicaoTime::getIntegrante)
                .extracting(Integrante::getNome)
                .containsExactlyInAnyOrder("Jogador 1", "Jogador 2");
    }


    @Test
    public void whenDeleteComposicaoTime_thenRemoveFromDatabase() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador para Deletar");
        integrante.setFranquia("Franquia Teste");
        integrante.setFuncao("Função Teste");
        entityManager.persist(integrante);

        Time time = new Time();
        time.setData(LocalDate.now());
        entityManager.persist(time);

        ComposicaoTime composicao = new ComposicaoTime();
        composicao.setIntegrante(integrante);
        composicao.setTime(time);
        entityManager.persistAndFlush(composicao);

        composicaoTimeRepository.delete(composicao);
        ComposicaoTime deleted = entityManager.find(ComposicaoTime.class, composicao.getId());

        assertThat(deleted).isNull();
    }

}
