package com.dex.team.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dex.team.dto.IntegranteDTO;
import com.dex.team.dto.ContagemPorFranquiaDTO;
import com.dex.team.dto.FuncaoMaisComumDTO;
import com.dex.team.dto.TimeDaDataDTO;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;
import com.dex.team.mapper.Mapper;
import com.dex.team.repository.IntegranteRepository;
import com.dex.team.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class IntegranteController {

    private final Mapper mapper;
    private final IntegranteRepository integranteRepository;
    private final TimeRepository timeRepository;

    public IntegranteController(Mapper mapper, IntegranteRepository integranteRepository, TimeRepository timeRepository) {
        this.mapper = mapper;
        this.integranteRepository = integranteRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/api/time-da-data")
    public TimeDaDataDTO getTimeDaData(@RequestParam String data) {
        List<Time> time = timeRepository.findByData(LocalDate.parse(data));
        return mapper.toTimeDaDataDTO(time);
    }

    @GetMapping("/api/funcao-mais-comum")
    public FuncaoMaisComumDTO getFuncaoMaisComum() {
        List<Integrante> integrantes = integranteRepository.findAll();
        return mapper.toFuncaoMaisComumDTO(integrantes);
    }

    @GetMapping("/api/contagem-por-franquia")
    public ContagemPorFranquiaDTO getContagemPorFranquia() {
        List<Integrante> integrantes = integranteRepository.findAll();
        return mapper.toContagemPorFranquiaDTO(integrantes);
    }

    @GetMapping("/api/integrantes")
    public List<IntegranteDTO> getAllIntegrantes() {
        return integranteRepository.findAll().stream()
            .map(this::toIntegranteDTO)
            .collect(Collectors.toList());
    }

    @PostMapping("/api/integrantes")
    public IntegranteDTO createIntegrante(@RequestBody IntegranteDTO integranteDTO) {
        Integrante integrante = toIntegrante(integranteDTO);
        Integrante savedIntegrante = integranteRepository.save(integrante);
        return toIntegranteDTO(savedIntegrante);
    }

    @GetMapping("/api/integrantes/{id}")
    public ResponseEntity<IntegranteDTO> getIntegranteById(@PathVariable Long id) {
        return integranteRepository.findById(id)
            .map(integrante -> ResponseEntity.ok(toIntegranteDTO(integrante)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND) 
                    .body(null));
    }


    private IntegranteDTO toIntegranteDTO(Integrante integrante) {
        IntegranteDTO dto = new IntegranteDTO();
        dto.setId(integrante.getId());
        dto.setNome(integrante.getNome());
        dto.setFranquia(integrante.getFranquia());
        dto.setFuncao(integrante.getFuncao());
        return dto;
    }

    private Integrante toIntegrante(IntegranteDTO dto) {
        Integrante integrante = new Integrante();
        integrante.setId(dto.getId());
        integrante.setNome(dto.getNome());
        integrante.setFranquia(dto.getFranquia());
        integrante.setFuncao(dto.getFuncao());
        return integrante;
    }
}
