package com.control.ata.web.controller;

import com.control.ata.dao.EnderecoDAO;
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
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import com.control.ata.security.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private EnderecoDAO enderecoDAO;
    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;
    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;
    @Autowired
    private AcademiaRepository academiaRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UsuarioService usuarioService;

    // ======================================REGISTER ACADEMIA=============================================

    @GetMapping("/academia")
    public String cadastroAcademia() {
        return "academia";
    }

    @PostMapping("/academia/save")
    public ResponseEntity<?> registraAcademia(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        AcademiaRegister academiaRegister = jacksonObjectMapper.readValue(decodedJson, AcademiaRegister.class);

        academiaRepository.save(new Academia(academiaRegister.academia.getNome(), enderecoDAO.save(
                academiaRegister.enderecoDTO)));
        return ResponseEntity.ok().build();
    }

    // ======================================REGISTER PESSOA=============================================

    @PostMapping("/save")
    public ResponseEntity<?> registraPessoa(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        PessoaRegister pessoaRegister = jacksonObjectMapper.readValue(decodedJson, PessoaRegister.class);

        PessoaDTO pessoaDTO = pessoaRegister.pessoaDTO;
        pessoaDTO.setEnderecoDTO(pessoaRegister.enderecoDTO);

        Pessoa pessoa = pessoaDAO.save(pessoaDTO);

        if (pessoaDTO.getIsInstrutor()) {
            pessoa.setInstrutor(
                    tipoPessoaDAO.save(new Instrutor(academiaRepository.getOne(pessoaDTO.getAcademia()), pessoa)));
            pessoa = pessoaDAO.save(pessoa);
        }

        usuarioService.signUpUser(
                new Usuario(pessoa, pessoaRegister.usuario.getEmail(), pessoaRegister.usuario.getPassword()));

        return ResponseEntity.ok().build();
    }

    @ModelAttribute("paises")
    public List<Pais> getPaises() {
        return paisRepository.findAll();
    }

    @PostMapping("/pais/{id}")
    public ResponseEntity<?> getEstados(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(estadoRepository.getAllByPais(paisRepository.getOne(id)));
    }

    @PostMapping("/estado/{id}")
    public ResponseEntity<?> getCidades(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(cidadeRepository.getAllByEstado(estadoRepository.getOne(id)));
    }

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

    @GetMapping("/register")
    public String abrirCadastro() {
        return "register";
    }

    @GetMapping("/register/confirm") //todo arrumar
    public String confirmMail(@RequestParam("toke") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(
                token);

//        optionalConfirmationToken.ifPresent(pessoaService::confirmarPessoa); //todo arrumar
        return "redirect:/";
    }

    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String login() {
        return "login";
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

    private static class PessoaRegister {
        public PessoaDTO pessoaDTO;
        public EnderecoDTO enderecoDTO;
        public Usuario usuario;

        public PessoaRegister(PessoaDTO pessoaDTO, EnderecoDTO enderecoDTO, Usuario usuario) {
            this.pessoaDTO = pessoaDTO;
            this.enderecoDTO = enderecoDTO;
            this.usuario = usuario;
        }

        public PessoaRegister() {
        }
    }

    private static class AcademiaRegister {
        public Academia academia;
        public EnderecoDTO enderecoDTO;

        public AcademiaRegister() {
        }

        public AcademiaRegister(Academia academia, EnderecoDTO enderecoDTO, Usuario user) {
            this.academia = academia;
            this.enderecoDTO = enderecoDTO;
        }
    }

}
