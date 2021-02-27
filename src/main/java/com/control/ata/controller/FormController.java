package com.control.ata.controller;

import com.control.ata.dao.EnderecoDAO;
import com.control.ata.dao.PessoaDAO;
import com.control.ata.dao.RingueDAO;
import com.control.ata.dao.TipoPessoaDAO;
import com.control.ata.dto.*;
import com.control.ata.model.endereco.Academia;
import com.control.ata.model.endereco.Pais;
import com.control.ata.model.individual.*;
import com.control.ata.model.pessoa.Faixa;
import com.control.ata.model.pessoa.Pessoa;
import com.control.ata.model.pessoa.Planilheiro;
import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.CategoriaCompeticao;
import com.control.ata.model.torneio.CategoriaTorneio;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Torneio;
import com.control.ata.repository.endereco.AcademiaRepository;
import com.control.ata.repository.endereco.CidadeRepository;
import com.control.ata.repository.endereco.EstadoRepository;
import com.control.ata.repository.endereco.PaisRepository;
import com.control.ata.repository.individual.*;
import com.control.ata.repository.pessoa.FaixaRepository;
import com.control.ata.repository.pessoa.PessoaRepository;
import com.control.ata.repository.pessoa.PlanilheiroRepository;
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import com.control.ata.repository.tipo_pessoa.JuizRepository;
import com.control.ata.repository.torneio.CategoriaCompeticaoRepository;
import com.control.ata.repository.torneio.CategoriaTorneioRepository;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TorneioRepository;
import com.control.ata.security.entity.ConfirmationToken;
import com.control.ata.security.entity.Usuario;
import com.control.ata.security.enuns.UserRole;
import com.control.ata.security.repository.ConfirmationTokenRepository;
import com.control.ata.security.repository.UsuarioRepository;
import com.control.ata.security.service.UsuarioService;
import com.control.ata.service.RingueService;
import com.control.ata.service.planilhaIndividual.ChaveIndividual;
import com.control.ata.service.planilhaIndividual.ListaIndividual;
import com.control.ata.service.planilhaIndividual.RankIndividual;
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
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private JuizRepository juizRepository;
    @Autowired
    private CompetidorRepository competidorRepository;
    @Autowired
    private RingueDAO ringueDAO;
    @Autowired
    private RingueIndividualRepository ringueIndividualRepository;
    @Autowired
    private PlanilheiroRepository planilheiroRepository;
    @Autowired
    private RankingIndividualRepository rankingIndividualRepository;
    @Autowired
    private ListaCategoriaCompetidorFechadaRepository listaCategoriaCompetidorFechadaRepository;
    @Autowired
    private RingueService ringueService;
    @Autowired
    private ChaveIndividual chaveIndividual;
    @Autowired
    private ListaIndividual listaIndividual;
    @Autowired
    private RankIndividual rankIndividual;
    @Autowired
    private PlanilhaListaIndividualRepository planilhaListaIndividualRepository;
    @Autowired
    private PlanilhaChaveamentoIndividualRepository planilhaChaveamentoIndividualRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @PostMapping("/verifica/usuario/pessoa/{email}")
    public ResponseEntity<Boolean> verificaUsuario(@PathVariable("email") String email){

        if(usuarioRepository.getUsuarioByEmail(email) == null){
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);

    }

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

    @PostMapping("/save/competidor")//todo adicionar titulos
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
                nivel = 8;
                break;
            default:
                nivel = 0;
        }

        ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
        for (int i = 0; i < competidorDTO.getCategoriaCompeticao().size(); i++) {
            categoriaCompeticaoArrayList.add(
                    categoriaCompeticaoRepository.getOne(competidorDTO.getCategoriaCompeticao().get(i)));
        }

        Competidor competidor = tipoPessoaDAO.save(
                new Competidor(competidorDTO.getPeso(), competidorDTO.getAltura(), nivel, pessoa,
                               torneioRepository.getOne(competidorDTO.getTorneio()),
                               categoriaCompeticaoArrayList));

        if (!competidorDTO.getCategoriaCompeticaoFechada().isEmpty()) {
            categoriaCompeticaoArrayList = new ArrayList<>();
            for (int i = 0; i < competidorDTO.getCategoriaCompeticaoFechada().size(); i++) {
                categoriaCompeticaoArrayList.add(
                        categoriaCompeticaoRepository.getOne(competidorDTO.getCategoriaCompeticaoFechada().get(i)));
            }
            ListaCategoriaCompetidorFechada listaCategoriaCompetidorFechada = listaCategoriaCompetidorFechadaRepository.save(
                    new ListaCategoriaCompetidorFechada(competidor));
            listaCategoriaCompetidorFechada.setCategoriaCompeticao(categoriaCompeticaoArrayList);
            listaCategoriaCompetidorFechadaRepository.save(listaCategoriaCompetidorFechada);
        }

        return ResponseEntity.ok().build();
    }

    // ======================================AJUSTAR PERFIL=============================================

    @GetMapping("/ajustar/pessoa")
    public String ajustarPerfil() {
        return "ajustar_perfil";
    }


    @PostMapping("/save/ajuste/pessoa")
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

    // ======================================AJUSTAR ALUNOS=============================================

    @GetMapping("/ajustar/alunos")
    public String ajustarAluno() {
        return "ajustar_alunos";
    }


    @PostMapping("/save/ajuste/aluno")
    public ResponseEntity<?> ajustarAluno(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        AlunoAjuste alunoAjuste = jacksonObjectMapper.readValue(decodedJson, AlunoAjuste.class);

        Pessoa pessoa = pessoaRepository.getOne(alunoAjuste.aluno);

        pessoa.setAtaNumberWorld(alunoAjuste.ataNumberWorld);
        pessoa.setAtaNumberBrasil(alunoAjuste.ataNumberBrasil);
        pessoa.setStatus(alunoAjuste.status);

        pessoaRepository.save(pessoa);

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

        Torneio torneio = torneioRepository.save(
                new Torneio(torneioDTO.getDataInicio(), torneioDTO.getDataTermino(), torneioDTO.getMaxNumeroRingues(),
                            torneioDTO.getPontuar(),
                            enderecoDAO.save(torneioDTO.getEnderecoDTO()),
                            categoriaTorneioRepository.getOne(torneioDTO.getCategoriaTorneio())));

        ArrayList<String> stringArrayList_ini = torneioRegister.inicio;
        ArrayList<String> stringArrayList_ter = torneioRegister.termino;
        ArrayList<Date> dateArrayList = torneioRegister.dia;
        for (int i = 0; i < stringArrayList_ini.size(); i++) {
            rodadaJuizRepository.save(
                    new RodadaJuiz(stringArrayList_ini.get(i), stringArrayList_ter.get(i), dateArrayList.get(i),
                                   torneio));
        }

        return ResponseEntity.ok().build();
    }

    // ======================================CADASTRAR JUIZ=============================================

    @GetMapping("/register/juiz")
    public String abrirCadastroJuiz() {
        return "cadastro_juiz";
    }

    @PostMapping("/save/juiz")
    public ResponseEntity<?> cadastrarJuiz(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        RegistroJuiz registroJuiz = jacksonObjectMapper.readValue(decodedJson, RegistroJuiz.class);

        Pessoa pessoa = pessoaRepository.getOne(registroJuiz.pessoa);

        ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>();
        ArrayList<Integer> integerArrayList = registroJuiz.rodadas;

        for (int i = 0; i < integerArrayList.size(); i++) {
            rodadaJuizArrayList.add(rodadaJuizRepository.getOne(integerArrayList.get(i)));
        }

        tipoPessoaDAO.save(new Juiz(pessoa, rodadaJuizArrayList));

        return ResponseEntity.ok().build();
    }

    // ======================================RELATORIOS=============================================

    @GetMapping("/relatorio/cadastros")
    public String getRelatorioCadastro() {
        return "relatorio_cadastro";
    }

    @GetMapping("/relatorio/torneio")
    public String getRelatorioTorneio() {
        return "relatorio_torneio";
    }

    @GetMapping("/relatorio/ranking/individual")
    public String getRelatorioRankingIndividual() {
        return "relatorio_rank_individual";
    }

    // ======================================CRIAR CATEGORIA=============================================

    @GetMapping("/criar/categoria")
    public String cadastroCategoria() {
        return "registrar_categoria";
    }

    @PostMapping("/save/categoria")
    public ResponseEntity<?> cadastrarCategoria(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        CategoriaCompeticao categoriaCompeticao = jacksonObjectMapper.readValue(decodedJson, CategoriaCompeticao.class);

        categoriaCompeticaoRepository.save(categoriaCompeticao);

        return ResponseEntity.ok().build();
    }

    // ======================================CRIAR RINGUES=============================================

    @GetMapping("/cadastrar/ringues")
    public String cadastrarRingues() {
        return "cadastro_ringues";
    }

    @PostMapping("/save/ringues")
    public ResponseEntity<?> cadastrarRingues(@Valid @RequestBody String json,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        RegistroRingueIndividual registroRingueIndividual = jacksonObjectMapper.readValue(decodedJson,
                                                                                          RegistroRingueIndividual.class);

        for (RingueIndividualDTO ringueIndividualDTO : registroRingueIndividual.arrayRingue) {
            ArrayList<CategoriaCompeticao> categoriaCompeticaoArrayList = new ArrayList<>();
            for (Integer i : ringueIndividualDTO.getCategorias()) {
                categoriaCompeticaoArrayList.add(categoriaCompeticaoRepository.getOne(i));
            }
            ArrayList<Juiz> juizArrayList = new ArrayList<>();
            for (Integer i : ringueIndividualDTO.getJuizes()) {
                juizArrayList.add(juizRepository.getOne(i));
            }
            RingueIndividual ringueIndividual = new RingueIndividual(ringueIndividualDTO.getGenero(),
                                                                     ringueIndividualDTO.getFechado(),
                                                                     ringueIndividualDTO.getNumeroRingue(),
                                                                     ringueIndividualDTO.getNumeroRodada(),
                                                                     ringueIndividualDTO.getIdade(),
                                                                     ringueIndividualDTO.getNivel(), juizArrayList,
                                                                     torneioRepository.getOne(
                                                                             ringueIndividualDTO.getTorneio()),
                                                                     categoriaCompeticaoArrayList,
                                                                     rodadaJuizRepository.getOne(
                                                                             ringueIndividualDTO.getRodada()));
            ringueDAO.save(ringueIndividual);
        }

        return ResponseEntity.ok().build();
    }

    // ======================================INICIAR TORNEIO=============================================

    @GetMapping("/iniciar/torneio")
    public String iniTorneio() {
        return "iniciarTorneio";
    }

    @PostMapping("/inicia/torneio/{id}")
    public ResponseEntity<?> iniciarTorneio(@Valid @RequestBody String json, @PathVariable("id") Integer id,
            BindingResult result) throws UnsupportedEncodingException, JsonProcessingException {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
        ObjectMapper jacksonObjectMapper = new ObjectMapper();
        usuarioDTO usuarioDTO = jacksonObjectMapper.readValue(decodedJson, FormController.usuarioDTO.class);

        Planilheiro planilheiro = planilheiroRepository.save(new Planilheiro(torneioRepository.getOne(id)));

        Usuario usuario = new Usuario(planilheiro, usuarioDTO.email,
                                      usuarioDTO.password);
        usuario.setUserRole(UserRole.ROLE_PLANILHA);
        usuarioService.signUpUser(usuario);

        Torneio torneio = torneioRepository.getOne(id);
        torneio.setIniciado(true);
        torneio = torneioRepository.save(torneio);

        ArrayList<RingueIndividual> ringueIndividualArrayList = (ArrayList<RingueIndividual>) ringueService.createRingueIndividual(
                torneio);

        for (RingueIndividual ringueIndividual : ringueIndividualArrayList) {
            listaIndividual.createPlanilhasLista(ringueIndividual);
            chaveIndividual.createPlanilhasChave(ringueIndividual);
        }

        return ResponseEntity.ok().build();
    }

    // ======================================TERMINA TORNEIO=============================================

    @GetMapping("/terminar/torneio")
    public String terTorneio() {
        return "terminarTorneio";
    }

    @PostMapping("/terminar/torneio/{id}")
    public ResponseEntity<?> terminaTorneio(@Valid @RequestBody String json, @PathVariable("id") Integer id,
            BindingResult result) {
        ResponseEntity<?> errors = getErrors(result);
        if (errors != null) return errors;

        Torneio torneio = torneioRepository.getOne(id);
        torneio.setTerminado(true);
        torneioRepository.save(torneio);

        planilheiroRepository.delete(planilheiroRepository.getByTorneio(torneio));

        if (torneio.getPontuar()) {
            for (RingueIndividual ringueIndividual : ringueIndividualRepository.getAllByTorneio(torneio)) {
                for (PlanilhaListaIndividual planilhaListaIndividual : planilhaListaIndividualRepository.getAllByRingueIndividual(
                        ringueIndividual)) {
                    rankIndividual.setRankingLista(planilhaListaIndividual);
                }
                for (PlanilhaChaveamentoIndividual planilhaChaveamentoIndividual : planilhaChaveamentoIndividualRepository.getAllByRingueIndividual(
                        ringueIndividual)) {
                    rankIndividual.setRankingChave(planilhaChaveamentoIndividual);
                }
            }
        }

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

    @ModelAttribute("torneiosNIni")
    public List<Torneio> getTorneiosNIni() {
        return torneioRepository.getAllByIniciado(false);
    }

    @ModelAttribute("torneiosIni")
    public List<Torneio> getTorneiosIni() {
        return torneioRepository.getAllByIniciado(true);
    }

    @ModelAttribute("torneiosNTer")
    public List<Torneio> getTorneiosNTer() {
        return torneioRepository.getAllByTerminado(false);
    }

    @ModelAttribute("torneiosTer")
    public List<Torneio> getTorneiosTer() {
        return torneioRepository.getAllByTerminado(true);
    }

    @ModelAttribute("torneiosNTerIni")
    public List<Torneio> getTorneiosNTerIni() {
        return torneioRepository.getAllByIniciadoAndTerminado(true, false);
    }

    @ModelAttribute("torneiosNTerNIni")
    public List<Torneio> getTorneiosNTerNIni() {
        return torneioRepository.getAllByIniciadoAndTerminado(false, false);
    }

    @ModelAttribute("categoriasTorneio")
    public List<CategoriaTorneio> getCategoriaTorneio() {
        return categoriaTorneioRepository.findAll();
    }

    @ModelAttribute("categoriasCompeticao")
    public List<CategoriaCompeticao> getCategoriaCompeticao() {
        return categoriaCompeticaoRepository.findAll();
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

    @PostMapping("/alunos/instrutor/{id}")
    public ResponseEntity<?> getAlunos(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pessoaRepository.getAllByInstrutorAndIsInstrutor(
                instrutorRepository.findByPessoa(pessoaRepository.getOne(id)), false));
    }

    @PostMapping("/categorias/competicao")
    public ResponseEntity<?> getCidades() {
        return ResponseEntity.ok(categoriaCompeticaoRepository.findAll());
    }

    @PostMapping("/rodadas/torneio/{id}")
    public ResponseEntity<?> getRodadaJuiz(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(rodadaJuizRepository.getAllByTorneio(torneioRepository.getOne(id)));
    }

    @PostMapping("/competidor/torneio/{id}")
    public ResponseEntity<?> getCompetidor(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(competidorRepository.getAllByTorneio(torneioRepository.getOne(id)));
    }

    @PostMapping("/juiz/torneio/{id}")
    public ResponseEntity<?> getJuiz(@PathVariable("id") Integer id) {

        ArrayList<RodadaJuiz> rodadaJuizArrayList = (ArrayList<RodadaJuiz>) rodadaJuizRepository.getAllByTorneio(
                torneioRepository.getOne(id));

        ArrayList<Juiz> juizArrayList = new ArrayList<>();
        for (RodadaJuiz rodadaJuiz : rodadaJuizArrayList) {
            juizArrayList.addAll(juizRepository.getAllByRodadaJuizList(rodadaJuiz.getId()));
        }

        return ResponseEntity.ok(juizArrayList);
    }

    @PostMapping("/qunatRingues/torneio/{id}")
    public ResponseEntity<?> getQuantRingues(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(torneioRepository.getOne(id).getMaxNumeroRingues());
    }

    @PostMapping("/juiz/rodada/{id}")
    public ResponseEntity<?> getJuizRodada(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(juizRepository.getAllByRodadaJuizList(id));
    }

    @PostMapping("/ringueInd/rodada/{id}")
    public ResponseEntity<?> getRingueInd(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ringueIndividualRepository.getAllByRodadaJuiz(rodadaJuizRepository.getOne(id)));
    }

    @PostMapping("/rank/individual/categoria/{id}")
    public ResponseEntity<?> getRankIndividual(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(
                rankingIndividualRepository.getAllByCategoriaCompeticao(categoriaCompeticaoRepository.getOne(id)));
    }

    @PostMapping("/categorias/rank/pessoa/{id}")
    public ResponseEntity<?> getRanksCompetidor(@PathVariable("id") Integer id) {
        ArrayList<RankingIndividual> rankingIndividualArrayList = (ArrayList<RankingIndividual>) rankingIndividualRepository.getAllByPessoa(
                pessoaRepository.getOne(id));
        List<CategoriaCompeticao> categoriaCompeticaoList = new ArrayList<>();
        if (!rankingIndividualArrayList.isEmpty()) {
            for (RankingIndividual rankingIndividual : rankingIndividualArrayList) {
                categoriaCompeticaoList.addAll(
                        categoriaCompeticaoRepository.getAllByRankingIndividual(rankingIndividual.getId()));
            }
        }
        return ResponseEntity.ok(categoriaCompeticaoList);
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

    private static class AlunoAjuste {
        public Integer aluno;
        public String ataNumberWorld;
        public String ataNumberBrasil;
        public Integer status;

        public AlunoAjuste() {
        }

        public AlunoAjuste(Integer aluno, String ataNumberWorld, String ataNumberBrasil, Integer status) {
            this.aluno = aluno;
            this.ataNumberWorld = ataNumberWorld;
            this.ataNumberBrasil = ataNumberBrasil;
            this.status = status;
        }
    }

    private static class TorneioRegister {
        public TorneioDTO torneioDTO;
        public EnderecoDTO enderecoDTO;
        public ArrayList<String> inicio;
        public ArrayList<String> termino;
        public ArrayList<Date> dia;

        public TorneioRegister() {
        }

        public TorneioRegister(TorneioDTO torneioDTO, EnderecoDTO enderecoDTO, ArrayList<String> inicio,
                ArrayList<String> termino, ArrayList<Date> dia) {
            this.torneioDTO = torneioDTO;
            this.enderecoDTO = enderecoDTO;
            this.inicio = inicio;
            this.termino = termino;
            this.dia = dia;
        }
    }

    private static class RegistroJuiz {
        public Integer pessoa;
        public ArrayList<Integer> rodadas;

        public RegistroJuiz() {
        }

        public RegistroJuiz(Integer pessoa, ArrayList<Integer> rodadas) {
            this.pessoa = pessoa;
            this.rodadas = rodadas;
        }
    }

    private static class RegistroRingueIndividual {
        public ArrayList<RingueIndividualDTO> arrayRingue;

        public RegistroRingueIndividual() {
        }

        public RegistroRingueIndividual(ArrayList<RingueIndividualDTO> arrayRingue) {
            this.arrayRingue = arrayRingue;
        }
    }

    private static class usuarioDTO {
        public String email;
        public String password;

        public usuarioDTO() {
        }

        public usuarioDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

}
