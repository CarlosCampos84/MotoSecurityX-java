package br.com.motosecurityx.web;

import br.com.motosecurityx.domain.Patio;
import br.com.motosecurityx.service.PatioService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patios")
public class PatioController {

    private final PatioService service;

    public PatioController(PatioService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("patios", service.listar());
        return "patios/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("patio", new Patio());
        return "patios/form";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("patio", service.buscar(id));
        return "patios/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@Valid @ModelAttribute("patio") Patio patio, BindingResult br) {
        if (br.hasErrors()) return "patios/form";
        service.salvar(patio);
        return "redirect:/patios?ok";
    }

    @PostMapping(value="/{id}", params = "_method=delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/patios?deleted";
    }
}
