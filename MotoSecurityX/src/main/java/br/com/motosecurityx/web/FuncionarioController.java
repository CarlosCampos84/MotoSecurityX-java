package br.com.motosecurityx.web;

import br.com.motosecurityx.domain.Funcionario;
import br.com.motosecurityx.service.FuncionarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("funcionarios", service.listar());
        return "funcionarios/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionarios/form";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("funcionario", service.buscar(id));
        return "funcionarios/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute Funcionario f) {
        service.salvar(f);
        return "redirect:/funcionarios?ok";
    }

    @PostMapping(value="/{id}", params = "_method=delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/funcionarios?deleted";
    }
}
