package com.control.ata.dao;

import com.control.ata.dto.EnderecoDTO;
import com.control.ata.model.endereco.Endereco;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoDAO {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco save(EnderecoDTO enderecoDTO) {
        return this.save(new Endereco(enderecoDTO.getRua(), cidadeRepository.getOne(enderecoDTO.getCidade())));
    }

    public List<Endereco> saveAll(Iterable<?> iterable) {
        List<Endereco> enderecos = new ArrayList<>();
        if (Endereco.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                enderecos.add(this.save((Endereco) o));
            }
        } else if (EnderecoDTO.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                enderecos.add(this.save((EnderecoDTO) o));
            }
        }
        return enderecos;
    }

}
