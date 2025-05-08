package com.dex.team.repository;

import com.dex.team.entity.Integrante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class IntegranteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IntegranteRepository integranteRepository;

    @Test
    public void whenFindById_thenReturnIntegrante() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador Teste");
        integrante.setFranquia("Franquia Teste");
        integrante.setFuncao("Função Teste");
        entityManager.persistAndFlush(integrante);

        Optional<Integrante> found = integranteRepository.findById(integrante.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getNome()).isEqualTo(integrante.getNome());
    }

    @Test
    public void whenInvalidId_thenReturnEmpty() {
        Optional<Integrante> found = integranteRepository.findById(-1L);
        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void givenListOfIntegrantes_whenFindAll_thenReturnAllIntegrantes() {
        Integrante integrante1 = new Integrante();
        integrante1.setNome("Jogador 1");
        integrante1.setFranquia("Franquia A");
        integrante1.setFuncao("Atacante");

        Integrante integrante2 = new Integrante();
        integrante2.setNome("Jogador 2");
        integrante2.setFranquia("Franquia B");
        integrante2.setFuncao("Zagueiro");

        entityManager.persist(integrante1);
        entityManager.persist(integrante2);
        entityManager.flush();

        List<Integrante> allIntegrantes = integranteRepository.findAll();

        assertThat(allIntegrantes).hasSize(2).extracting(Integrante::getNome)
                .containsOnly(integrante1.getNome(), integrante2.getNome());
    }

    @Test
    public void whenSaveIntegrante_thenReturnSavedIntegrante() {
        Integrante integrante = new Integrante();
        integrante.setNome("Novo Jogador");
        integrante.setFranquia("Nova Franquia");
        integrante.setFuncao("Nova Função");

        Integrante saved = integranteRepository.save(integrante);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNome()).isEqualTo(integrante.getNome());
    }

    @Test
    public void whenDeleteIntegrante_thenRemoveIntegrante() {
        Integrante integrante = new Integrante();
        integrante.setNome("Jogador para Deletar");
        integrante.setFranquia("Franquia X");
        integrante.setFuncao("Função Y");
        entityManager.persistAndFlush(integrante);

        integranteRepository.delete(integrante);
        Optional<Integrante> deleted = integranteRepository.findById(integrante.getId());

        assertThat(deleted.isPresent()).isFalse();
    }
}
