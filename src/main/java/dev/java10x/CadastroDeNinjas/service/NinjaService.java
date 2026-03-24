package dev.java10x.CadastroDeNinjas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.java10x.CadastroDeNinjas.entity.NinjaModel;
import dev.java10x.CadastroDeNinjas.repository.NinjaRepository;

@Service
public class NinjaService {

    @Autowired
    private NinjaRepository ninjaRepository;

    // listar ninjas
    public List<NinjaModel> getNinjas() {
        return ninjaRepository.findAll();

    }

    // listar ninjas por id
    public NinjaModel getNinjaById(Long id) {
        Optional<NinjaModel> ninja = ninjaRepository.findById(id);
        return ninja.orElse(null);
    }

    // adicionar ninja
    public NinjaModel addNinja(NinjaModel ninja) {
        return ninjaRepository.save(ninja);
    }

    // atualizar ninja
    public NinjaModel updateNinja(Long id, NinjaModel ninja) {
        NinjaModel existingNinja = ninjaRepository.findById(id).orElse(null);
        if (existingNinja != null) {
            existingNinja.setNome(ninja.getNome());
            existingNinja.setIdade(ninja.getIdade());
            existingNinja.setEmail(ninja.getEmail());
            existingNinja.setImg_url(ninja.getImg_url());
            existingNinja.setRank(ninja.getRank());
            return ninjaRepository.save(existingNinja);
        }
        return null;
    }

    // deletar ninja
    public void deleteNinja(Long id) {
        ninjaRepository.deleteById(id);
    }

}
