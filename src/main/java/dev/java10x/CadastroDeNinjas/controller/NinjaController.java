package dev.java10x.CadastroDeNinjas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.java10x.CadastroDeNinjas.entity.NinjaModel;
import dev.java10x.CadastroDeNinjas.service.NinjaService;

@RestController
@RequestMapping
public class NinjaController {

    @Autowired
    private NinjaService ninjaService;

    @GetMapping("/listar")
    public List<NinjaModel> getNinjas() {
        return ninjaService.getNinjas();
    }

    @GetMapping("/listar/{id}")
    public NinjaModel getNinjaById(@PathVariable Long id) {
        return ninjaService.getNinjaById(id);
    } 

    //Adicionar ninjas
    @PostMapping("/criar")
    public NinjaModel addNinja(@RequestBody NinjaModel ninja) {
        return ninjaService.addNinja(ninja);
    }

    @PutMapping("/atualizar/{id}")
        public NinjaModel updateNinja(@PathVariable Long id, @RequestBody NinjaModel ninja) {
            return ninjaService.updateNinja(id, ninja);
    }

    @DeleteMapping("/deletar/{id}")
    public void deleteNinja(@PathVariable Long id) {
        ninjaService.deleteNinja(id);
    }
}
