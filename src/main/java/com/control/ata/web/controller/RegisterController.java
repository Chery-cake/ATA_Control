package com.control.ata.web.controller;

import com.control.ata.dao.PessoaDAO;
import com.control.ata.dto.PessoaDTO;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pessoa")
public class RegisterController {

    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private FaixaRepository faixaRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    // ======================================REGISTER=============================================

    @PostMapping("/save")
    public ResponseEntity<?> registraPessoa(@Valid PessoaDTO pessoaDTO, BindingResult result) {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        log.info("PessoaDTO {}", pessoaDTO.toString());
        pessoaDAO.save(pessoaDTO);
        return ResponseEntity.ok().build();
    }

    @ModelAttribute("faixas")
    public List<Faixa> getFaixas(){
        return faixaRepository.findAll();
    }

    @ModelAttribute("instrutores")
    public List<Instrutor> getInstrutores(){
        return instrutorRepository.findAll();
    }

    @ModelAttribute("paises")
    public List<Pais> getPaises(){
        return paisRepository.findAll();
    }

    @PostMapping("/register/pais/{id}")
    @ModelAttribute("estados")
    public ResponseEntity<?> getEstados(@PathVariable("id") Integer id){
        return ResponseEntity.ok(estadoRepository.getAllByPais(paisRepository.getOne(id)));
    }

    @PostMapping("/register/estado/{id}")
    public ResponseEntity<?> getCidades(@PathVariable("id") Integer id){
        return ResponseEntity.ok(cidadeRepository.getAllByEstado(estadoRepository.getOne(id)));
    }

    @GetMapping("/register")
    public String abrirCadastro() {
        return "register";
    }

    // ======================================LOGIN=============================================

    @GetMapping("/login")
    public String abrirLogin() {
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

}
