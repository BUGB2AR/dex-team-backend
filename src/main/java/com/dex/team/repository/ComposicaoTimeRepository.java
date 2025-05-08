package com.dex.team.repository;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComposicaoTimeRepository extends JpaRepository<ComposicaoTime, Long> {
    List<ComposicaoTime> findAllByIntegrante(Integrante integrante);
}
