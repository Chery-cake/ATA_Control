package com.control.ata.web.controller;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.dto.CompetidorDTO;
import com.control.ata.dto.EnderecoDTO;
import com.control.ata.dto.PessoaDTO;
import com.control.ata.dto.TorneioDTO;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.repository.torneio.TorneioRepository;
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
import java.util.*;

@Controller
public class FormController {

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
    private CategoriaTorneioRepository categoriaTorneioRepository;
    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private CategoriaCompeticaoRepository categoriaCompeticaoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UsuarioService usuarioService;

    // ======================================REGISTER ACADEMIA=============================================

    @GetMapping("/academia")
    public String cadastroAcademia() {
        return "academia";
    }

    @PostMapping("/save/academia")
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

    @PostMapping("/save/pessoa")
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

    @GetMapping("/register/pessoa")
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

    // ======================================CADASTRAR COMPETIDOR=============================================

    @GetMapping("/register/competidor")
    public String abrirCadastroCompetidor() {
        return "competidor_form";
    }

    @PostMapping("/save/competidor")//todo adicionar time e titulos
    public ResponseEntity<?> salvarCompetidor(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        CompetidorDTO competidorDTO = jacksonObjectMapper.readValue(decodedJson, CompetidorDTO.class);

        Pessoa pessoa = pessoaRepository.getOne(competidorDTO.getPessoa());
        Faixa faixa = pessoa.getFaixa();
        Integer nivel;
        switch (faixa.getId()) {
            case 1:
                nivel = 0;
                break;
            case 2:
                nivel = 0;
                break;
            case 3:
                nivel = 0;
                break;
            case 4:
                nivel = 1;
                break;
            case 5:
                nivel = 1;
                break;
            case 6:
                nivel = 1;
                break;
            case 7:
                nivel = 2;
                break;
            case 8:
                nivel = 2;
                break;
            case 9:
                nivel = 2;
                break;
            case 10:
                nivel = 3;
                break;
            case 11:
                nivel = 4;
                break;
            case 12:
                nivel = 5;
                break;
            case 13:
                nivel = 5;
                break;
            case 14:
                nivel = 6;
                break;
            case 15:
                nivel = 6;
                break;
            case 16:
                nivel = 7;
                break;
            case 17:
                nivel = 7;
                break;
            case 18:
                nivel = 8;
                break;
            case 19:
                nivel = 9;
                break;
            default:
                nivel = 0;
        }

        ArrayList<Integer> integerArrayList = competidorDTO.getCategoriaCompeticao();
        ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
        for (int i = 0; i < integerArrayList.size(); i++) {
            categoriaCompeticaoArrayList.add(categoriaCompeticaoRepository.getOne(integerArrayList.get(i)));
        }

        Competidor competidor = new Competidor(competidorDTO.getPeso(), competidorDTO.getAltura(), nivel, pessoa,
                                               torneioRepository.getOne(competidorDTO.getTorneio()),
                                               categoriaCompeticaoArrayList);

        tipoPessoaDAO.save(competidor);

        return ResponseEntity.ok().build();
    }

    // ======================================AJUSTAR PERFIL=============================================

    @GetMapping("/ajustar")
    public String ajustarPerfil() {
        return "ajustar_perfil";
    }

    @PostMapping("/save/ajuste")
    public ResponseEntity<?> ajustarPessoa(@Valid @RequestBody String json,
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
            if (instrutorRepository.findByPessoa(pessoa) == null) {
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

    // ======================================CRIAR TORNEIO=============================================

    @GetMapping("/criar/torneio")
    public String criarTorneio() {
        return "criar_torneio";
    }

    @PostMapping("/save/torneio")
    public ResponseEntity<?> registrarTorneio(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        TorneioRegister torneioRegister = jacksonObjectMapper.readValue(decodedJson, TorneioRegister.class);

        TorneioDTO torneioDTO = torneioRegister.torneioDTO;
        torneioDTO.setEnderecoDTO(torneioRegister.enderecoDTO);

        torneioRepository.save(
                new Torneio(torneioDTO.getDataInicio(), torneioDTO.getDataTermino(), torneioDTO.getMaxNumeroRingues(),
                            torneioDTO.getPontuar(),
                            enderecoDAO.save(torneioDTO.getEnderecoDTO()),
                            categoriaTorneioRepository.getOne(torneioDTO.getCategoriaTorneio())));

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

    @ModelAttribute("torneios")
    public List<Torneio> getTorneios() {
        return torneioRepository.findAll();
    }

    @ModelAttribute("categoriasTorneio")
    public List<CategoriaTorneio> getCategoriaTorneio() {
        return categoriaTorneioRepository.findAll();
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

    @PostMapping("/categorias/competicao")
    public ResponseEntity<?> getCidades() {
        return ResponseEntity.ok(categoriaCompeticaoRepository.findAll());
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

        @Override
        public String toString() {
            return "PessoaRegister{" +
                    "pessoaDTO=" + pessoaDTO +
                    ", enderecoDTO=" + enderecoDTO +
                    ", usuario=" + usuario +
                    '}';
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

    private static class TorneioRegister {
        public TorneioDTO torneioDTO;
        public EnderecoDTO enderecoDTO;

        public TorneioRegister() {
        }

        public TorneioRegister(TorneioDTO torneioDTO, EnderecoDTO enderecoDTO) {
            this.torneioDTO = torneioDTO;
            this.enderecoDTO = enderecoDTO;
        }
    }

}
