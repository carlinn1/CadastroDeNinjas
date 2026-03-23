package dev.java10x.CadastroDeNinjas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.java10x.CadastroDeNinjas.entity.NinjaModel;

public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
    
}
