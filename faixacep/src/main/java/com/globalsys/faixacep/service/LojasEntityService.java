package com.globalsys.faixacep.service;

import com.globalsys.faixacep.entity.LojasCEP;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LojasEntityService {
    LojasCEP getLojaByFaixaInicioAndFaixaFim(Long faixaInicio, Long faixaFim);
    LojasCEP createFaixaCEP(LojasCEP lojas);
    ResponseEntity<LojasCEP> updateFaixaCEP(Integer id, LojasCEP lojas);
    void deleteFaixaCEP(Integer id);
    List<LojasCEP> findAll();
}
