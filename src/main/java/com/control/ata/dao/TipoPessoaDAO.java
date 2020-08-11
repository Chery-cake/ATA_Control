package com.control.ata.dao;

import com.control.ata.model.tipo_pessoa.*;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.repository.tipo_pessoa.*;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoPessoaDAO {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private CompetidorRepository competidorRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;
    @Autowired
    private JuizRepository juizRepository;
    @Autowired
    private TreinadorRepository treinadorRepository;
    @Autowired
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Competidor save(Competidor competidor) {
        if (competidor.getId() == null) {
            Competidor aux = new Competidor(competidor.getPeso(), competidor.getAltura(), competidor.getNivel(),
                                            competidor.getPessoa(), competidor.getCategoriaCompeticao(),
                                            competidor.getTime());
            aux.setCategoriaCompeticao(null);
            aux = competidorRepository.save(aux);
            aux.setCategoriaCompeticao(competidor.getCategoriaCompeticao());
            if (competidor.getTituloList() != null) {
                if (!competidor.getTituloList().isEmpty()) {
                    ArrayList<Titulo> titulos = new ArrayList<>();//todo melhorar a incersao dos titulos
                    for (Titulo titulo : competidor.getTituloList()) {
                        titulo.setCompetidor(aux);
                        titulos.add(tituloRepository.save(titulo));
                    }
                    aux.setTituloList(titulos);
                }
            }
            return competidorRepository.save(aux);
        }
        return competidorRepository.save(competidor);
    }

    public Instrutor save(Instrutor instrutor) {
        return instrutorRepository.save(instrutor);
    }

    public Juiz save(Juiz juiz) {
        return juizRepository.save(juiz);
    }

    public Treinador save(Treinador treinador) {
        return treinadorRepository.save(treinador);
    }

    private <T> T saveObj(Object obj) {
        Object o = null;
        if (Administrador.class.equals(obj.getClass())) {
            o = this.save((Administrador) obj);
        } else if (Competidor.class.equals(obj.getClass())) {
            o = this.save((Competidor) obj);
        } else if (Instrutor.class.equals(obj.getClass())) {
            o = this.save((Instrutor) obj);
        } else if (Juiz.class.equals(obj.getClass())) {
            o = this.save((Juiz) obj);
        } else if (Treinador.class.equals(obj.getClass())) {
            o = this.save((Treinador) obj);
        }
        return (T) o;
    }

    public <T> List<T> saveAll(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (Object o : iterable) {
            list.add(this.saveObj(o));
        }
        return list;
    }

    public void delete(Administrador administrador) {
        administradorRepository.delete(administrador);
    }

    public void delete(Competidor competidor) {
        competidorRepository.delete(competidor);
    }

    public void delete(Instrutor instrutor) {
        instrutorRepository.delete(instrutor);
    }

    public void delete(Juiz juiz) {
        if (rodadaJuizRepository.getRodadaJuizByJuiz(juiz) != null) {
            RodadaJuiz rodadaJuiz = rodadaJuizRepository.getRodadaJuizByJuiz(juiz);
            rodadaJuizRepository.delete(rodadaJuiz);
        }
        juizRepository.delete(juiz);
    }

    public void delete(Treinador treinador) {
        treinadorRepository.delete(treinador);
    }

    public void deleteAll(Iterable<?> iterable) {
        if (Administrador.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.delete((Administrador) o);
            }
        } else if (Competidor.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.delete((Competidor) o);
            }
        } else if (Instrutor.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.delete((Instrutor) o);
            }
        } else if (Juiz.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.delete((Juiz) o);
            }
        } else if (Treinador.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.delete((Treinador) o);
            }
        }
    }

}
