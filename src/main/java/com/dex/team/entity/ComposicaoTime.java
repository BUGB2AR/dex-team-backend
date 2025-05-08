package com.dex.team.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "composicao_time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComposicaoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_time", nullable = false)
    @JsonBackReference
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_integrante", nullable = false)
    private Integrante integrante;

    public ComposicaoTime(Time time, Integrante integrante) {
        this.time = time;
        this.integrante = integrante;
    }
}
