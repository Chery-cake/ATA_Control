package com.control.ata.dao;

import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Telefone;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.pessoa.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaDAO {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;

    public Pessoa save(Pessoa pessoa) {
        pessoa = pessoaRepository.save(pessoa);
        ArrayList<Telefone> aux = new ArrayList<>();
        if (pessoa.getTelefoneCollection() != null) {
            ArrayList<Telefone> telefones = new ArrayList<>(pessoa.getTelefoneCollection());
            for (Telefone telefone : telefones) {
                aux.add(telefoneRepository.save(telefone));
            }
        }
        pessoa.setTelefoneCollection(aux);
        return pessoa;
    }

    public List<Pessoa> saveAll(Iterable<Pessoa> iterable) {
        List<Pessoa> pessoas = new ArrayList<>();
        for (Pessoa pessoa : iterable) {
            pessoas.add(this.save(pessoa));
        }
        return pessoas;
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



