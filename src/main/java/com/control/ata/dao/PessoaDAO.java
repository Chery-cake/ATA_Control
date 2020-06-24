package com.control.ata.dao;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.pessoa.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PessoaDAO {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;

    public void save(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        if(pessoa.getTelefoneCollection() != null){
            ArrayList<Telefone> telefones = new ArrayList<>(pessoa.getTelefoneCollection());
            for (Telefone telefone : telefones) {
                telefoneRepository.save(telefone);
            }
        }
    }

    public void saveAll(Iterable<Pessoa> iterable) {
        for (Pessoa pessoa : iterable) {
            this.save(pessoa);
        }
    }

    public void delete(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }

    public void deleteAll(Iterable<Pessoa> iterable) {
        for (Pessoa pessoa : iterable) {
            this.delete(pessoa);
        }
    }

}



