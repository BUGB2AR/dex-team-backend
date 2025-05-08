package com.dex.team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "integrante")
@Getter
@Setter
public class Integrante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String franquia;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String funcao;
}