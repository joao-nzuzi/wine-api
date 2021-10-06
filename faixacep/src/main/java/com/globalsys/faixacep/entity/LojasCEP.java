package com.globalsys.faixacep.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity @Table(name = "cep")
@EntityListeners(AuditingEntityListener.class)
@Data
public class LojasCEP {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "codigo_loja", nullable = false)
    private String codigoLoja;

    @Column(name = "faixa_inicio", nullable = false)
    private Long faixaInicio;

    @Column(name = "faixa_fim", nullable = false)
    private Long faixaFim;

}
