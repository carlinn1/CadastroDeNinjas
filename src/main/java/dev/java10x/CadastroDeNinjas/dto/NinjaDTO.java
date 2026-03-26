package dev.java10x.CadastroDeNinjas.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NinjaDTO {

    // DTO serve para transferir os dados entre as camadas da aplicação, 
    // sem expor a entidade diretamente. Ele é usado para receber os dados 
    // do cliente e enviar os dados para o cliente, garantindo que a estrutura 
    // interna da entidade não seja exposta.
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    private String email;

    private String img_url;

    private String rank;
    
    private int idade;


}
