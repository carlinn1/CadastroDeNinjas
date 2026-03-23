package dev.java10x.CadastroDeNinjas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(unique = true)
    private String email;

    private String img_url;
    
    private int idade;

    // Varios ninjas podem estar ligados a uma mesma missao
    @ManyToOne
    @JoinColumn(name = "missoes_id") // foreign key para a tabela de missões
    private MissoesModel missoes;

}


