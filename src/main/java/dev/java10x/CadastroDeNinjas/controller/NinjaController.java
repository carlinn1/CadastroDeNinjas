package dev.java10x.CadastroDeNinjas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.java10x.CadastroDeNinjas.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.service.NinjaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping
public class NinjaController {

    /*

        Essa classe é responsável por receber as requisições HTTP relacionadas aos ninjas,
        processá-las e retornar as respostas adequadas. Ela atua como um intermediário entre o
        cliente (frontend) e a camada de serviço (NinjaService), que contém a lógica de negócios para manipular os dados dos ninjas.

    */

    @Autowired
    private NinjaService ninjaService;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os ninjas", description = "Retorna uma lista de todos os ninjas cadastrados no sistema.")   
    @ApiResponse(responseCode = "200", description = "Lista de ninjas retornada com sucesso") 
    @ApiResponse(responseCode = "500", description = "Erro interno ao tentar listar os ninjas")
    @Parameter(name = "Nenhum parâmetro necessário para listar os ninjas", description = "Este endpoint não requer parâmetros de entrada", required = false) 
    
    public List<NinjaDTO> getNinjas() {
        return ninjaService.getNinjas();
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Obter ninja por ID", description = "Retorna os detalhes de um ninja específico com base no seu ID.")    
    @ApiResponse(responseCode = "200", description = "Ninja retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Ninja não encontrado com o ID fornecido")
    @Parameter(name = "id", description = "ID do ninja a ser obtido", required = true)

    public NinjaDTO getNinjaById(@PathVariable Long id) {

        return ninjaService.getNinjaById(id);
    } 

    //Adicionar ninjas
    @PostMapping("/criar")
    @Operation(summary = "Criar um novo ninja", description = "Adiciona um novo ninja ao sistema com os detalhes fornecidos.")  
    @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida para criar ninja")
    @Parameter(name = "ninja", description = "Objeto NinjaDTO contendo os detalhes do ninja a ser criado", required = true)

    public ResponseEntity<String> addNinja(@RequestBody NinjaDTO ninja) {
        NinjaDTO createdNinja = ninjaService.addNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body("Ninja criado com sucesso: " + createdNinja.getNome() + " com ID: " + createdNinja.getId());
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar um ninja", description = "Atualiza os detalhes de um ninja específico com base no seu ID.")  
    @ApiResponse(responseCode = "200", description = "Ninja atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Ninja não encontrado para atualizar")
    @Parameter(name = "id", description = "ID do ninja a ser atualizado", required = true)
    @Parameter(name = "ninja", description = "Objeto NinjaDTO contendo os detalhes do ninja a ser atualizado", required = true)

        public ResponseEntity<String> updateNinja(@PathVariable Long id, @RequestBody NinjaDTO ninja) {
            NinjaDTO updatedNinja = ninjaService.updateNinja(id, ninja);
            if (updatedNinja != null) {
                return ResponseEntity.ok("Ninja atualizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja não encontrado para atualizar.");
            }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um ninja", description = "Remove um ninja específico do sistema com base no seu ID.")  
    @ApiResponse(responseCode = "200", description = "Ninja deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Ninja não encontrado para deletar")
    @Parameter(name = "id", description = "ID do ninja a ser deletado", required = true)
    
    public ResponseEntity<String> deleteNinja(@PathVariable Long id) {
        
        if (ninjaService.deleteNinja(id)) {
            return ResponseEntity.ok("Ninja deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja não encontrado para deletar.");
        }
    }
}
