package com.dex.team.controller;

import com.dex.team.dto.ContagemPorFranquiaDTO;
import com.dex.team.dto.FuncaoMaisComumDTO;
import com.dex.team.dto.TimeDaDataDTO;
import com.dex.team.dto.TimeMaisComumDTO;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import com.dex.team.mapper.Mapper;
import com.dex.team.service.ApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private Mapper mapper; 

    @GetMapping("/time-da-data")
    public TimeDaDataDTO timeDaData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Time> time = apiService.timeDaData(data);
        if (time == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado para a data " + data);
        }
        return mapper.toTimeDaDataDTO(time);
    }


    @GetMapping("/integrante-mais-usado")
    public Integrante integranteMaisUsado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        return apiService.integranteMaisUsado(dataInicial, dataFinal);
    }

    @GetMapping("/time-mais-comum")
    public List<String> integrantesDoTimeMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<String> integrantes = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal);
        return integrantes;
    }
    
    @GetMapping("/time-mais-comum-detalhado")
    public TimeMaisComumDTO timeMaisComumDetalhado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        TimeMaisComumDTO dto = apiService.timeMaisComumDetalhado(dataInicial, dataFinal);
        
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum time mais comum encontrado no período.");
        }
        
        return dto;
    }

    @GetMapping("/funcao-mais-comum")
    public FuncaoMaisComumDTO funcaoMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        String funcao = apiService.funcaoMaisComum(dataInicial, dataFinal);
        FuncaoMaisComumDTO dto = new FuncaoMaisComumDTO();
        dto.setFuncao(funcao);
        return dto; 
    }

    @GetMapping("/franquia-mais-famosa")
    public Map<String, String> franquiaMaisFamosa(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        if (dataInicial == null || dataFinal == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }

        String franquia = apiService.franquiaMaisFamosa(dataInicial, dataFinal);

        if (franquia == null || franquia.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Franquia não encontrada para o intervalo de datas fornecido");
        }

        return Map.of("franquia", franquia);
    }



    @GetMapping("/contagem-por-franquia")
    public ContagemPorFranquiaDTO contagemPorFranquia(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        long quantidade = apiService.contagemPorFranquia(dataInicial, dataFinal);
        
        return mapper.toContagemPorFranquiaDTO(quantidade);
    }


    @GetMapping("/contagem-por-funcao")
    public Map<String, Long> contagemPorFuncao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        return Map.of("quantidade", apiService.contagemPorFuncao(dataInicial, dataFinal));
    }
}
