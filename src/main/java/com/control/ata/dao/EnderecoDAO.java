package com.control.ata.dao;

import com.control.ata.model.endereco.Endereco;
import com.control.ata.repository.endereco.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoDAO {

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public void save(Endereco endereco) {
        paisRepository.save(endereco.getBairro().getCidade().getEstado().getPais());
        estadoRepository.save(endereco.getBairro().getCidade().getEstado());
        cidadeRepository.save(endereco.getBairro().getCidade());
        bairroRepository.save(endereco.getBairro());
        enderecoRepository.save(endereco);
    }

    public void saveAll(Iterable<Endereco> iterable){
        for (Endereco endereco: iterable) {
            this.save(endereco);
        }
    }

}
