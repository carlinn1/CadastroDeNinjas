package dev.java10x.CadastroDeNinjas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.java10x.CadastroDeNinjas.entity.MissoesModel;

public interface MissoesRepository extends JpaRepository<MissoesModel, Long> {
    
}
