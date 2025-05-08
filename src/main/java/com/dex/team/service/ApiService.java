package com.dex.team.service;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import com.dex.team.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Autowired
    private TimeRepository timeRepository;

    /**
     * Retorna o time com os integrantes da data especificada
     * @param data Data do time a ser buscado
     * @return Time da data especificada ou null se não existir
     */
    public Time timeDaData(LocalDate data) {
        return timeRepository.findByData(data);
    }

    /**
     * Retorna o integrante que aparece na maior quantidade de times no período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Integrante mais usado no período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        Map<Integrante, Long> contagemIntegrantes = times.stream()
                .flatMap(time -> time.getComposicao().stream())
                .map(ComposicaoTime::getIntegrante)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return contagemIntegrantes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Retorna os integrantes do time mais comum no período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Lista de nomes dos integrantes do time mais comum
     */
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        // Agrupa times por seus integrantes (considerando a combinação de integrantes como chave)
        Map<String, List<Time>> timesPorCombinacao = times.stream()
                .collect(Collectors.groupingBy(time -> {
                    List<String> integrantes = time.getComposicao().stream()
                            .map(ct -> ct.getIntegrante().getId().toString())
                            .sorted()
                            .collect(Collectors.toList());
                    return String.join(",", integrantes);
                }));

        // Encontra a combinação mais comum
        Optional<Map.Entry<String, List<Time>>> combinacaoMaisComum = timesPorCombinacao.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()));

        if (combinacaoMaisComum.isPresent()) {
            Time primeiroTimeComEssaCombinacao = combinacaoMaisComum.get().getValue().get(0);
            return primeiroTimeComEssaCombinacao.getComposicao().stream()
                    .map(ct -> ct.getIntegrante().getNome())
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /**
     * Retorna a função mais comum nos times do período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Nome da função mais comum
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        Map<String, Long> contagemFuncoes = times.stream()
                .flatMap(time -> time.getComposicao().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return contagemFuncoes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Retorna a franquia mais comum nos times do período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Nome da franquia mais comum
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        Map<String, Long> contagemFranquias = times.stream()
                .flatMap(time -> time.getComposicao().stream())
                .map(ct -> ct.getIntegrante().getFranquia())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return contagemFranquias.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Retorna a contagem de franquias distintas no período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Número de franquias distintas
     */
    public long contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        return times.stream()
                .flatMap(time -> time.getComposicao().stream())
                .map(ct -> ct.getIntegrante().getFranquia())
                .distinct()
                .count();
    }


    /**
     * Retorna a contagem de funções distintas no período
     * @param dataInicial Data inicial do período (pode ser null)
     * @param dataFinal Data final do período (pode ser null)
     * @return Número de funções distintas
     */
    public long contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal) {
        List<Time> times = timeRepository.findByPeriodo(dataInicial, dataFinal);

        return times.stream()
                .flatMap(time -> time.getComposicao().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .distinct()
                .count();
    }
}
