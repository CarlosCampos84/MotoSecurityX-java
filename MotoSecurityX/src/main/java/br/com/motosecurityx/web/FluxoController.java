package br.com.motosecurityx.web;

import br.com.motosecurityx.service.MotoService;
import br.com.motosecurityx.service.PatioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fluxos")
public class FluxoController {

    private final MotoService motoService;
    private final PatioService patioService;

    public FluxoController(MotoService motoService, PatioService patioService) {
        this.motoService = motoService;
        this.patioService = patioService;
    }

    @GetMapping("/mover/{motoId}")
    public String formMover(@PathVariable Long motoId, Model model) {
        model.addAttribute("moto", motoService.buscar(motoId));
        model.addAttribute("patios", patioService.listar());
        return "motos/mover";
    }

    @PostMapping("/mover")
    @PreAuthorize("hasRole('ADMIN')")
    public String mover(@RequestParam Long motoId, @RequestParam Long patioId) {
        motoService.moverMoto(motoId, patioId);
        return "redirect:/motos?movida";
    }
}
