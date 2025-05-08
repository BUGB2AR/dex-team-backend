package com.dex.team.service;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import com.dex.team.repository.TimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiServiceTest {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private ApiService apiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTimeDaData() {
        LocalDate data = LocalDate.of(2023, 1, 1);
        Time time = new Time();
        time.setData(data);

        when(timeRepository.findByData(data)).thenReturn(time);

        Time result = apiService.timeDaData(data);
        assertEquals(time, result);
        verify(timeRepository, times(1)).findByData(data);
    }

    @Test
    void testIntegranteMaisUsado() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 31);

        Integrante integrante1 = new Integrante();
        integrante1.setId(1L);
        integrante1.setNome("Jogador 1");

        Integrante integrante2 = new Integrante();
        integrante2.setId(2L);
        integrante2.setNome("Jogador 2");

        Time time1 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 10), integrante1, integrante2);
        Time time2 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 20), integrante1, integrante1, integrante2);

        when(timeRepository.findByPeriodo(dataInicial, dataFinal)).thenReturn(Arrays.asList(time1, time2));

        Integrante result = apiService.integranteMaisUsado(dataInicial, dataFinal);
        assertEquals(integrante1, result);
    }

    @Test
    void testIntegrantesDoTimeMaisComum() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 31);

        Integrante integrante1 = new Integrante();
        integrante1.setId(1L);
        integrante1.setNome("Jogador 1");

        Integrante integrante2 = new Integrante();
        integrante2.setId(2L);
        integrante2.setNome("Jogador 2");

        Time time1 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 10), integrante1, integrante2);
        Time time2 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 20), integrante1, integrante2);
        Time time3 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 30), integrante1);

        when(timeRepository.findByPeriodo(dataInicial, dataFinal)).thenReturn(Arrays.asList(time1, time2, time3));

        List<String> result = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal);
        assertEquals(2, result.size());
        assertTrue(result.contains("Jogador 1"));
        assertTrue(result.contains("Jogador 2"));
    }

    @Test
    void testFuncaoMaisComum() {
        LocalDate dataInicial = LocalDate.of(2023, 1, 1);
        LocalDate dataFinal = LocalDate.of(2023, 1, 31);

        Integrante integrante1 = new Integrante();
        integrante1.setFuncao("Atacante");

        Integrante integrante2 = new Integrante();
        integrante2.setFuncao("Zagueiro");

        Time time1 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 10), integrante1, integrante2);
        Time time2 = criarTimeComIntegrantes(LocalDate.of(2023, 1, 20), integrante1, integrante1);

        when(timeRepository.findByPeriodo(dataInicial, dataFinal)).thenReturn(Arrays.asList(time1, time2));

        String result = apiService.funcaoMaisComum(dataInicial, dataFinal);
        assertEquals("Atacante", result);
    }

    private Time criarTimeComIntegrantes(LocalDate data, Integrante... integrantes) {
        Time time = new Time();
        time.setData(data);

        if (time.getComposicao() == null) {
            time.setComposicao(new ArrayList<>());
        }

        for (Integrante integrante : integrantes) {
            ComposicaoTime ct = new ComposicaoTime();
            ct.setIntegrante(integrante);
            ct.setTime(time);
            time.getComposicao().add(ct);
        }

        return time;
    }

}
