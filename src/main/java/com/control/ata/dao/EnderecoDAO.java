package com.control.ata.dao;

import com.control.ata.dto.EnderecoDTO;
import com.control.ata.model.endereco.Bairro;
import com.control.ata.model.endereco.Cidade;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.repository.endereco.BairroRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoDAO {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public void save(Endereco endereco) {
        bairroRepository.save(endereco.getBairro());
        enderecoRepository.save(endereco);
    }

    public void save(EnderecoDTO enderecoDTO) {
        if(bairroRepository.getBairroByNome(enderecoDTO.getBairro()) != null){
            Bairro bairro = bairroRepository.getBairroByNome(enderecoDTO.getBairro());
            bairroRepository.save(bairro);
            enderecoRepository.save(new Endereco(enderecoDTO.getRua(), bairro));
        }else {
            Cidade cidade = cidadeRepository.getCidadeByNome(enderecoDTO.getCidade());
            Bairro bairro = new Bairro(enderecoDTO.getBairro(), cidade);
            bairroRepository.save(bairro);
            enderecoRepository.save(new Endereco(enderecoDTO.getRua(), bairro));
        }
    }

    public void saveAll(Iterable<Endereco> iterable){
        for (Endereco endereco: iterable) {
            this.save(endereco);
        }
    }

}
