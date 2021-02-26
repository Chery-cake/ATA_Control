package com.control.ata.dao;

import com.control.ata.model.tipo_pessoa.Competidor;
import com.control.ata.model.tipo_pessoa.Instrutor;
import com.control.ata.model.tipo_pessoa.Juiz;
import com.control.ata.model.torneio.RodadaJuiz;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.repository.tipo_pessoa.CompetidorRepository;
import com.control.ata.repository.tipo_pessoa.InstrutorRepository;
import com.control.ata.repository.tipo_pessoa.JuizRepository;
import com.control.ata.repository.torneio.RodadaJuizRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoPessoaDAO {

    @Autowired
    private CompetidorRepository competidorRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;
    @Autowired
    private JuizRepository juizRepository;
    @Autowired
    private RodadaJuizRepository rodadaJuizRepository;
    @Autowired
    private TituloRepository tituloRepository;

    public Competidor save(Competidor competidor) {
        if (competidor.getId() == null) {
            Competidor aux = new Competidor(competidor.getPeso(), competidor.getAltura(), competidor.getNivel(),
                                            competidor.getPessoa(), competidor.getTorneio(),
                                            competidor.getCategoriaCompeticao());
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
        if (juizRepository.getJuizByPessoa(juiz.getPessoa()) != null) {
            Juiz juizOri = juizRepository.getJuizByPessoa(juiz.getPessoa());
            ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>(juizOri.getRodadaJuizList());
            rodadaJuizArrayList.addAll(juiz.getRodadaJuizList());

            int i = 0;
            while (i < rodadaJuizArrayList.size()) {
                for (int j = 0; j < rodadaJuizArrayList.size(); j++) {
                    if ((rodadaJuizArrayList.get(i).equals(rodadaJuizArrayList.get(j))) && (i != j)) {
                        rodadaJuizArrayList.remove(j);
                        i--;
                        break;
                    }
                }
                i++;
            }

            juizOri.setRodadaJuizList(rodadaJuizArrayList);
            return juizRepository.save(juizOri);
        } else {
            ArrayList<RodadaJuiz> rodadaJuizArrayList = new ArrayList<>();
            rodadaJuizArrayList.addAll(juiz.getRodadaJuizList());

            juiz.setRodadaJuizList(null);
            juiz = juizRepository.save(juiz);

            juiz.setRodadaJuizList(rodadaJuizArrayList);

            return juizRepository.save(juiz);
        }
    }

    private <T> T saveObj(Object obj) {
        Object o = null;
        if (Competidor.class.equals(obj.getClass())) {
            o = this.save((Competidor) obj);
        } else if (Instrutor.class.equals(obj.getClass())) {
            o = this.save((Instrutor) obj);
        } else if (Juiz.class.equals(obj.getClass())) {
            o = this.save((Juiz) obj);
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

    public void deleteAll(Iterable<?> iterable) {
        if (Competidor.class.equals(iterable.getClass())) {
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
        }
    }

}
