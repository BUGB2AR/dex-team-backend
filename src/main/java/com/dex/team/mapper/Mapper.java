package com.dex.team.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.dex.team.dto.ContagemPorFranquiaDTO;
import com.dex.team.dto.FuncaoMaisComumDTO;
import com.dex.team.dto.TimeDTO;
import com.dex.team.dto.TimeDaDataDTO;
import com.dex.team.entity.Integrante;
import com.dex.team.entity.Time;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public TimeDTO toTimeDTO(Time time) {
        return modelMapper.map(time, TimeDTO.class);
    }

    public TimeDaDataDTO toTimeDaDataDTO(Time time) {
        TimeDaDataDTO dto = new TimeDaDataDTO();
        dto.setData(time.getData());
        dto.setIntegrantes(time.getComposicao().stream()
                               .map(c -> c.getIntegrante().getNome())
                               .toList());
        return dto;
    }

    public FuncaoMaisComumDTO toFuncaoMaisComumDTO(List<Integrante> integrantes) {
        Map<String, Long> funcaoCount = integrantes.stream()
            .collect(Collectors.groupingBy(Integrante::getFuncao, Collectors.counting()));

        String funcaoMaisComum = funcaoCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();

        FuncaoMaisComumDTO dto = new FuncaoMaisComumDTO();
        dto.setFuncao(funcaoMaisComum);
        return dto;
    }
    
    public ContagemPorFranquiaDTO toContagemPorFranquiaDTO(long quantidade) {
        ContagemPorFranquiaDTO dto = new ContagemPorFranquiaDTO();
        Map<String, Integer> franquias = Map.of("quantidade", (int) quantidade);
        
        dto.setFranquias(franquias);
        return dto;
    }


    public ContagemPorFranquiaDTO toContagemPorFranquiaDTO(List<Integrante> integrantes) {
        Map<String, Integer> franquiaCount = integrantes.stream()
            .collect(Collectors.groupingBy(Integrante::getFranquia, Collectors.summingInt(e -> 1)));

        ContagemPorFranquiaDTO dto = new ContagemPorFranquiaDTO();
        dto.setFranquias(franquiaCount);
        return dto;
    }
}

