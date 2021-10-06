package com.globalsys.faixacep.controller;

import com.globalsys.faixacep.entity.LojasCEP;
import com.globalsys.faixacep.service.LojasEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faixa/cep/")
public class FaixaCEPController {

    @Autowired
    private LojasEntityService service;

    @GetMapping("/lojas")
    public List<LojasCEP> getAllUsers(){
        return service.findAll();
    }

    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<LojasCEP> createLojaFaixaCEP(@RequestBody LojasCEP lojas){
        LojasCEP loja = service.createFaixaCEP(lojas);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(loja, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LojasCEP> updateLojaFaixaCEP(@PathVariable("id") Integer id, @RequestBody LojasCEP lojas){
        return service.updateFaixaCEP(id, lojas);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public void deleteLojaFaixaCEP(@PathVariable("id") Integer id){
        service.deleteFaixaCEP(id);
    }

    @GetMapping("/getLojasByFaixaInicioAndFaixaFim/{faixaInicio}/{faixaFim}")
    public LojasCEP getLojaByFaixaInicioAndFaixaFim(@PathVariable(value = "faixaInicio") Long faixaInicio, @PathVariable(value = "faixaFim") Long faixaFim) {
        return service.getLojaByFaixaInicioAndFaixaFim(faixaInicio, faixaFim);
    }
}
