package com.dex.team.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeDaDataDTO {
    private LocalDate data;
    private List<String> integrantes;
}
