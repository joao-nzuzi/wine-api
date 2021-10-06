package com.globalsys.faixacep.service.impl;

import com.globalsys.faixacep.entity.LojasCEP;
import com.globalsys.faixacep.repository.LojasRepository;
import com.globalsys.faixacep.service.LojasEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @Transactional
public class LojasEntityServiceImpl implements LojasEntityService {

    @Autowired
    private LojasRepository lojasRepository;

    @Override
    public LojasCEP getLojaByFaixaInicioAndFaixaFim(Long faixaInicio, Long faixaFim) {
        if(verificarFaixa(faixaInicio, faixaFim)){
            return lojasRepository.findLojaFaixaInicioAndFaixaFim(faixaInicio, faixaFim).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "A Faixa de CEP informada não pertence a nenhuma Loja!"));
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE," A faixa de CEP de inicio não deve ser maior que a faixa de fim");
        }
    }

    @Override
    public LojasCEP createFaixaCEP(LojasCEP lojas) {
        try{
            if(verificarFaixa(lojas.getFaixaInicio(), lojas.getFaixaFim())){
                List<LojasCEP> faixa_existente = lojasRepository.getLojasByFaixaInicioAndFaixaFim(lojas.getFaixaInicio(), lojas.getFaixaFim());
                if(!faixa_existente.isEmpty()){
                    for(int i = 0; i < faixa_existente.size(); i++){
                        throw new DataIntegrityViolationException("Erro! A faixa de CEP conflita com a faixa de CEP da loja " + faixa_existente.get(i).getCodigoLoja());
                    }
                }else{
                    return this.lojasRepository.save(lojas);
                }
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "A faixa de inicio não deve ser superior que a Faixa de fim ");
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public ResponseEntity<LojasCEP> updateFaixaCEP(Integer id, LojasCEP lojas) {
        try{
            Optional<LojasCEP> antigosDadosLoja = lojasRepository.findById(id);
            List<LojasCEP> faixa_existente = lojasRepository.getLojasByFaixaInicioAndFaixaFim(lojas.getFaixaInicio(), lojas.getFaixaFim());
            if(verificarFaixa(lojas.getFaixaInicio(), lojas.getFaixaFim())){
                if(!faixa_existente.isEmpty()){
                    for(int i = 0; i < faixa_existente.size(); i++){
                        throw new DataIntegrityViolationException("Erro! A faixa de CEP conflita com a faixa de CEP da loja " + faixa_existente.get(i).getCodigoLoja());
                    }
                }else{
                    if(antigosDadosLoja.isPresent()){
                        LojasCEP novosDadosLoja = antigosDadosLoja.get();
                        novosDadosLoja.setCodigoLoja(lojas.getCodigoLoja());
                        novosDadosLoja.setFaixaInicio(lojas.getFaixaInicio());
                        novosDadosLoja.setFaixaFim(lojas.getFaixaFim());
                        lojasRepository.save(novosDadosLoja);
                        return new ResponseEntity<>(novosDadosLoja, HttpStatus.OK);
                    }else{
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja inexistente");
                    }
                }
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "A faixa de inicio não deve ser superior que a Faixa de fim ");
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public void deleteFaixaCEP(Integer id) {
        try{
            lojasRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O id infomado não existe"));
            lojasRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<LojasCEP> findAll() {
        return lojasRepository.findAll();
    }

    public boolean verificarFaixa(Long faixaInicio, Long faixaFim) {

        if (faixaInicio > faixaFim) {
            return false;
        }
        return true;
    }
}
