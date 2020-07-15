package com.control.ata.dao;

import com.control.ata.model.time.Time;
import com.control.ata.model.tipo_pessoa.Treinador;
import com.control.ata.model.torneio.Titulo;
import com.control.ata.repository.time.TimeRepository;
import com.control.ata.repository.torneio.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeDAO {

    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private TituloRepository tituloRepository;
    @Autowired
    private TipoPessoaDAO tipoPessoaDAO;

    public Time save(Time time) {
        Time aux = new Time(time.getRepresenta(), time.getJunior(), null, null);
        aux = timeRepository.save(aux);
        aux.setCompetidores(time.getCompetidores());
        aux = timeRepository.save(aux);
        if (time.getTituloList() != null) {
            if (!time.getTituloList().isEmpty()) {
                ArrayList<Titulo> titulos = new ArrayList<>();//todo melhorar a incersao dos titulos
                for (Titulo titulo : time.getTituloList()) {
                    titulo.setTime(aux);
                    titulos.add(tituloRepository.save(titulo));
                }
                aux.setTituloList(titulos);
            }
        }
        if(time.getTreinadorList() != null){
            if(!time.getTreinadorList().isEmpty()){
                ArrayList<Treinador> treinadorArrayList = new ArrayList<>();
                for (Treinador treinador:time.getTreinadorList()){
                    treinadorArrayList.add(tipoPessoaDAO.save(new Treinador(treinador.getPessoa(), aux)));
                }
                aux.setTreinadorList(treinadorArrayList);
            }
        }
        return timeRepository.save(aux);
    }

    public List<Time> saveAll(Iterable<Time> iterable) {
        List<Time> times = new ArrayList<>();
        for (Time time : iterable) {
            times.add(this.save(time));
        }
        return times;
    }

    public void delete(Time time) {
        timeRepository.delete(time);
    }

    public void deleteAll(Iterable<Time> iterable) {
        for (Time time : iterable) {
            this.delete(time);
        }
    }

}
