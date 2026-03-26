package dev.java10x.CadastroDeNinjas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.java10x.CadastroDeNinjas.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.entity.NinjaModel;
import dev.java10x.CadastroDeNinjas.mapper.NinjaMapper;
import dev.java10x.CadastroDeNinjas.repository.NinjaRepository;

@Service
public class NinjaService {

    @Autowired
    private NinjaRepository ninjaRepository;

    @Autowired
    private NinjaMapper ninjaMapper = new NinjaMapper();

    // listar ninjas
    public List<NinjaDTO> getNinjas() {
        List<NinjaModel> ninjas = ninjaRepository.findAll();
        return ninjas.stream()
        .map(ninjaMapper::map)
        .collect(Collectors.toList());
    }

    // listar ninjas por id
    public NinjaDTO getNinjaById(Long id) {
        Optional<NinjaModel> ninja = ninjaRepository.findById(id);
        return ninja.map(ninjaMapper::map).orElse(null);
    }

    // adicionar ninja
    public NinjaDTO addNinja(NinjaDTO ninjaDto) {
        NinjaModel ninja = ninjaMapper.map(ninjaDto);
        return ninjaMapper.map(ninjaRepository.save(ninja));
    }

    // atualizar ninja
    public NinjaDTO updateNinja(Long id, NinjaDTO ninja) {
        NinjaModel existingNinja = ninjaRepository.findById(id).orElse(null);
        if (existingNinja != null) {
            existingNinja.setNome(ninja.getNome());
            existingNinja.setIdade(ninja.getIdade());
            existingNinja.setEmail(ninja.getEmail());
            existingNinja.setImg_url(ninja.getImg_url());
            existingNinja.setRank(ninja.getRank());
            return ninjaMapper.map(ninjaRepository.save(existingNinja));
        }
        return null;
    }

    // deletar ninja
    public boolean deleteNinja(Long id) {
        if (ninjaRepository.existsById(id)) {
            ninjaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
