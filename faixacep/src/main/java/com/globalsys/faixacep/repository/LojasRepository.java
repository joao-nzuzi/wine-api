package com.globalsys.faixacep.repository;

import com.globalsys.faixacep.entity.LojasCEP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LojasRepository extends JpaRepository<LojasCEP, Integer> {
    @Query(value = "SELECT * FROM cep where faixa_inicio >= :faixaInicio AND faixa_fim <= :faixaFim", nativeQuery = true)
    List<LojasCEP> getLojasByFaixaInicioAndFaixaFim(Long faixaInicio, Long faixaFim);

    @Query(value = "SELECT * FROM cep where faixa_inicio >= :faixaInicio AND faixa_fim <= :faixaFim", nativeQuery = true)
    Optional<LojasCEP> findLojaFaixaInicioAndFaixaFim(Long faixaInicio, Long faixaFim);
}
