package com.dex.team.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeMaisComumDTO {
	private Long timeId;
	private LocalDate dataTime;
	private String franquia;
	private List<String> integrantes;
}
