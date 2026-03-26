package dev.java10x.CadastroDeNinjas.mapper;

import org.springframework.stereotype.Component;

import dev.java10x.CadastroDeNinjas.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.entity.NinjaModel;

@Component
public class NinjaMapper {

    // O NinjaMapper é responsável por converter entre a 
    // entidade NinjaModel e o DTO NinjaDTO.
    // O mapper é usado para garantir que a estrutura interna da 
    // entidade não seja exposta diretamente para o cliente, e para 
    // facilitar a transferência de dados entre as camadas da aplicação.

    public NinjaModel map (NinjaDTO ninjaDTO) {
        NinjaModel ninjaModel = new NinjaModel();
        ninjaModel.setId(ninjaDTO.getId());
        ninjaModel.setNome(ninjaDTO.getNome());
        ninjaModel.setEmail(ninjaDTO.getEmail());
        ninjaModel.setImg_url(ninjaDTO.getImg_url());
        ninjaModel.setRank(ninjaDTO.getRank());
        ninjaModel.setIdade(ninjaDTO.getIdade());
        return ninjaModel;
    }

    public NinjaDTO map (NinjaModel ninjaModel) {
        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setId(ninjaModel.getId());
        ninjaDTO.setNome(ninjaModel.getNome());
        ninjaDTO.setEmail(ninjaModel.getEmail());
        ninjaDTO.setImg_url(ninjaModel.getImg_url());
        ninjaDTO.setRank(ninjaModel.getRank());
        ninjaDTO.setIdade(ninjaModel.getIdade());
        return ninjaDTO;
    }

}
