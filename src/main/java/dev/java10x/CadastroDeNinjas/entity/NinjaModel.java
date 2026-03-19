package dev.java10x.CadastroDeNinjas.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_ninjas")
@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@AllArgsConstructor
@NoArgsConstructor
public class NinjaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private int idade;

    // Um ninja pode ter várias missões, e uma missão pode ser atribuída a vários ninjas
    @ManyToAny
    @JoinColumn(name = "missoes_id") // foreign key para a tabela de missões
    private MissoesModel missoes;

}


