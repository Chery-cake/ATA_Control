package com.control.ata.dao;

import com.control.ata.model.tipo_pessoa.*;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.repository.tipo_pessoa.*;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(Administrador administrador) {
        administradorRepository.save(administrador);
    }

    public void save(Competidor competidor) {
        competidorRepository.save(competidor);
    }

    public void save(Instrutor instrutor) {
        instrutorRepository.save(instrutor);
    }

    public void save(Juiz juiz) {
        juizRepository.save(juiz);
    }

    public void save(Treinador treinador) {
        treinadorRepository.save(treinador);
    }

    public void saveAll(Iterable<?> iterable) {
        if (Administrador.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.save((Administrador) o);
            }
        } else if (Competidor.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.save((Competidor) o);
            }
        } else if (Instrutor.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.save((Instrutor) o);
            }
        } else if (Juiz.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.save((Juiz) o);
            }
        } else if (Treinador.class.equals(iterable.getClass())) {
            for (Object o : iterable) {
                this.save((Treinador) o);
            }
        }
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
        if(rodadaJuizRepository.getRodadaJuizByJuiz(juiz) != null){
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
