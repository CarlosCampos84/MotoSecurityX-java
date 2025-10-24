package br.com.motosecurityx.web;

import br.com.motosecurityx.domain.Moto;
import br.com.motosecurityx.service.MotoService;
import br.com.motosecurityx.service.PatioService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoController {

    private final MotoService service;
    private final PatioService patioService;

    public MotoController(MotoService service, PatioService patioService) {
        this.service = service;
        this.patioService = patioService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("motos", service.listar());
        return "motos/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("patios", patioService.listar());
        return "motos/form";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("moto", service.buscar(id));
        model.addAttribute("patios", patioService.listar());
        return "motos/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@Valid @ModelAttribute("moto") Moto moto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("patios", patioService.listar());
            return "motos/form";
        }
        service.salvar(moto);
        return "redirect:/motos?ok";
    }

    @PostMapping(value="/{id}", params = "_method=delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/motos?deleted";
    }
}
