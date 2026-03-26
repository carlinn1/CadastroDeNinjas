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
    public List<NinjaDTO> getNinjas() {
        return ninjaService.getNinjas();
    }

    @GetMapping("/listar/{id}")
    public NinjaDTO getNinjaById(@PathVariable Long id) {
        return ninjaService.getNinjaById(id);
    } 

    //Adicionar ninjas
    @PostMapping("/criar")
    public ResponseEntity<String> addNinja(@RequestBody NinjaDTO ninja) {
        NinjaDTO createdNinja = ninjaService.addNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body("Ninja criado com sucesso: " + createdNinja.getNome() + " com ID: " + createdNinja.getId());
    }

    @PutMapping("/atualizar/{id}")
        public ResponseEntity<String> updateNinja(@PathVariable Long id, @RequestBody NinjaDTO ninja) {
            NinjaDTO updatedNinja = ninjaService.updateNinja(id, ninja);
            if (updatedNinja != null) {
                return ResponseEntity.ok("Ninja atualizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja não encontrado para atualizar.");
            }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteNinja(@PathVariable Long id) {
        
        if (ninjaService.deleteNinja(id)) {
            return ResponseEntity.ok("Ninja deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ninja não encontrado para deletar.");
        }
    }
}
