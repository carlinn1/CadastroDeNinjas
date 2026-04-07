package dev.java10x.CadastroDeNinjas.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.java10x.CadastroDeNinjas.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.service.NinjaService;

@Controller
@RequestMapping("/ninja/ui")

public class NinjaCrontrollerUI {

    // Classe responsável por lidar com a interface do usuário, 
    // como exibir menus, receber entradas e mostrar resultados.

    private final NinjaService ninjaService;

    public NinjaCrontrollerUI(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    } 

    @GetMapping({"", "/menu"})
    public String listarNinjas(Model model) {
        List<NinjaDTO> ninjas = ninjaService.getNinjas();
        model.addAttribute("ninjas", ninjas);
        model.addAttribute("totalNinjas", ninjas.size());
        return "ninjas-menu";
    }
}