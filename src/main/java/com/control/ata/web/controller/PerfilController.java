package com.control.ata.web.controller;

import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.dto.EnderecoDTO;
import com.control.ata.dto.PessoaDTO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PerfilController {

    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;
    @Autowired
    private AcademiaRepository academiaRepository;
    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PaisRepository paisRepository;

    // ======================================PERFIL=============================================

    @GetMapping("/perfil")
    public String perfil(){
        return "perfil";
    }

    // ======================================AJUSTAR PERFIL=============================================

    @GetMapping("/ajustar")
    public String ajustarPerfil(){
        return "ajustar_perfil";
    }

    @PostMapping("/ajustar/save")
    public ResponseEntity<?> registraPessoa(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        PessoaAjuste pessoaAjuste = jacksonObjectMapper.readValue(decodedJson, PessoaAjuste.class);

        PessoaDTO pessoaDTO = pessoaAjuste.pessoaDTO;
        pessoaDTO.setEnderecoDTO(pessoaAjuste.enderecoDTO);

        Pessoa pessoa = pessoaDAO.save(pessoaDTO);

        if (pessoaDTO.getIsInstrutor()) {
            if(instrutorRepository.findByPessoa(pessoa) == null){
                pessoa.setInstrutor(
                        tipoPessoaDAO.save(new Instrutor(academiaRepository.getOne(pessoaDTO.getAcademia()), pessoa)));
                pessoa = pessoaDAO.save(pessoa);
            }
        }

        Usuario usuario = pessoaAjuste.usuario;
        usuario.setPessoa(pessoa);
        usuarioService.updateUser(usuario);

        return ResponseEntity.ok().build();
    }

    // ======================================MODEL ATTRIBUTES=============================================

    @ModelAttribute("faixas")
    public List<Faixa> getFaixas() {
        return faixaRepository.findAll();
    }

    @ModelAttribute("academias")
    public List<Academia> getAcademias() {
        return academiaRepository.findAll();
    }

    @ModelAttribute("instrutores")
    public List<Instrutor> getInstrutores() {
        return instrutorRepository.findAll();
    }

    @ModelAttribute("paises")
    public List<Pais> getPaises() {
        return paisRepository.findAll();
    }

    // ======================================FUNCTIONS=============================================

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        return null;
    }

    // ======================================CLASSES=============================================

    private static class PessoaAjuste {
        public PessoaDTO pessoaDTO;
        public EnderecoDTO enderecoDTO;
        public Usuario usuario;

        public PessoaAjuste(PessoaDTO pessoaDTO, EnderecoDTO enderecoDTO, Usuario usuario) {
            this.pessoaDTO = pessoaDTO;
            this.enderecoDTO = enderecoDTO;
            this.usuario = usuario;
        }

        public PessoaAjuste() {
        }
    }

}
