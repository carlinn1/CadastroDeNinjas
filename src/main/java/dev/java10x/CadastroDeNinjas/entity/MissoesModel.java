package dev.java10x.CadastroDeNinjas.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_missoes")
@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@AllArgsConstructor
@NoArgsConstructor

public class MissoesModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeMissao;
    private String dificuldade;

    // Uma missão pode ser atribuída a vários ninjas, 
    // e um ninja pode ter várias missões

    @OneToMany(mappedBy = "missoes")
    private List<NinjaModel> ninja;
    
}
