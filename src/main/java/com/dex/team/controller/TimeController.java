package com.dex.team.controller;

import com.dex.team.entity.ComposicaoTime;
import com.dex.team.entity.Time;
import com.dex.team.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;

    @GetMapping
    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Time criar(@RequestBody Time time) {
        if (time.getComposicao() != null) {
            for (ComposicaoTime composicao : time.getComposicao()) {
                composicao.setTime(time);
                composicao.setIntegrante(composicao.getIntegrante());
            }
        }
        return timeRepository.save(time);
    }


    @GetMapping("/{id}")
    public Time buscarPorId(@PathVariable Long id) {
        return timeRepository.findById(id).orElse(null);
    }
}
