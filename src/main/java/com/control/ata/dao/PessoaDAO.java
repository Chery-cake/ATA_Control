package com.control.ata.dao;

import com.control.ata.dto.PessoaDTO;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaDAO {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private EnderecoDAO enderecoDAO;

    public Pessoa save(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getGenero(),
                                   pessoaDTO.getDataNascimento(), pessoaDTO.getEmail(), pessoaDTO.getSenha(),
                                   pessoaDTO.getStatus(), pessoaDTO.getFoto(), pessoaDTO.getAtaNumberWorld(),
                                   pessoaDTO.getAtaNumberBrasil(), pessoaDTO.getIsInstrutor(), pessoaDTO.getTelefone(),
                                   faixaRepository.getOne(pessoaDTO.getFaixa()),
                                   enderecoDAO.save(pessoaDTO.getEnderecoDTO()), pessoaDTO.getInstrutor());
        return this.save(pessoa);
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> saveAll(Iterable<?> iterable) {
        List<Pessoa> pessoas = new ArrayList<>();
        if (Pessoa.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                pessoas.add(this.save((Pessoa) o));
            }
        } else if (PessoaDTO.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                pessoas.add(this.save((PessoaDTO) o));
            }
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



