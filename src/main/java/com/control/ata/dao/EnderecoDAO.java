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

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoDAO {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private BairroRepository bairroRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco save(Endereco endereco) {
        if (bairroRepository.getBairroByNome(endereco.getBairro().getNome()) != null) {
            Bairro bairro = bairroRepository.getBairroByNome(endereco.getBairro().getNome());
            bairro = bairroRepository.save(bairro);
            endereco = enderecoRepository.save(new Endereco(endereco.getRua(), bairro));
        } else {
            Bairro bairro = new Bairro(endereco.getBairro().getNome(), endereco.getBairro().getCidade());
            bairro = bairroRepository.save(bairro);
            endereco = new Endereco(endereco.getRua(), bairro);
            endereco = enderecoRepository.save(endereco);
        }
//        Bairro bairro = endereco.getBairro();
//        bairroRepository.save(endereco.getBairro());
//        Endereco aux = new Endereco(endereco.getRua(), bairro);
//        enderecoRepository.save(aux);
        return endereco;
    }

    public Endereco save(EnderecoDTO enderecoDTO) {
        Endereco endereco;
        if (bairroRepository.getBairroByNome(enderecoDTO.getBairro()) != null) {
            Bairro bairro = bairroRepository.getBairroByNome(enderecoDTO.getBairro());
            bairro = bairroRepository.save(bairro);
            endereco = new Endereco(enderecoDTO.getRua(), bairro);
            endereco = enderecoRepository.save(endereco);
        } else {
            Cidade cidade = cidadeRepository.getCidadeByNome(enderecoDTO.getCidade());
            Bairro bairro = new Bairro(enderecoDTO.getBairro(), cidade);
            bairro = bairroRepository.save(bairro);
            endereco = new Endereco(enderecoDTO.getRua(), bairro);
            endereco = enderecoRepository.save(endereco);
        }
        return endereco;
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
