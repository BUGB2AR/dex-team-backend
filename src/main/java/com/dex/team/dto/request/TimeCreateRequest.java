package com.dex.team.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeCreateRequest {
    private String nome;
    private LocalDate data;
    private List<Long> integrantesIds;
}
