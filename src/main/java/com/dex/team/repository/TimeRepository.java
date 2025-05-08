package com.dex.team.repository;

import com.dex.team.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {
    @Query("SELECT t FROM Time t WHERE t.data = :data")
    Time findByData(@Param("data") LocalDate data);

    @Query("SELECT t FROM Time t WHERE (:dataInicial IS NULL OR t.data >= :dataInicial) AND (:dataFinal IS NULL OR t.data <= :dataFinal)")
    List<Time> findByPeriodo(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);
}
