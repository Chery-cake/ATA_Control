package com.control.ata.dao;

import com.control.ata.dto.PessoaDTO;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
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
    @Autowired
    private InstrutorRepository instrutorRepository;

    public Pessoa save(PessoaDTO pessoaDTO) {

        Pessoa pessoa;

        if (pessoaDTO.getIsInstrutor()) {
            pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getGenero(),
                                pessoaDTO.getDataNascimento(), pessoaDTO.getStatus(), pessoaDTO.getAtaNumberWorld(),
                                pessoaDTO.getAtaNumberBrasil(), pessoaDTO.getIsInstrutor(), pessoaDTO.getTelefone(),
                                faixaRepository.getOne(pessoaDTO.getFaixa()),
                                enderecoDAO.save(pessoaDTO.getEnderecoDTO()), pessoaDTO.getUsuario());
        } else {
            pessoa = new Pessoa(pessoaDTO.getNome(), pessoaDTO.getSobrenome(), pessoaDTO.getGenero(),
                                pessoaDTO.getDataNascimento(), pessoaDTO.getStatus(), pessoaDTO.getAtaNumberWorld(),
                                pessoaDTO.getAtaNumberBrasil(), pessoaDTO.getIsInstrutor(), pessoaDTO.getTelefone(),
                                faixaRepository.getOne(pessoaDTO.getFaixa()),
                                enderecoDAO.save(pessoaDTO.getEnderecoDTO()),
                                instrutorRepository.getOne(pessoaDTO.getInstrutor()), pessoaDTO.getUsuario());
        }
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



