package br.com.motosecurityx.web;

import br.com.motosecurityx.domain.Funcionario;
import br.com.motosecurityx.repository.AlocacaoRepository;
import br.com.motosecurityx.repository.FuncionarioRepository;
import br.com.motosecurityx.repository.MotoRepository;
import br.com.motosecurityx.service.AlocacaoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alocacoes")
@PreAuthorize("hasRole('ADMIN')")
public class AlocacaoController {

    private final AlocacaoService alocacaoService;
    private final FuncionarioRepository funcRepo;
    private final MotoRepository motoRepo;
    private final AlocacaoRepository alocRepo;

    public AlocacaoController(AlocacaoService alocacaoService, FuncionarioRepository funcRepo,
                              MotoRepository motoRepo, AlocacaoRepository alocRepo) {
        this.alocacaoService = alocacaoService;
        this.funcRepo = funcRepo;
        this.motoRepo = motoRepo;
        this.alocRepo = alocRepo;
    }

    // Tela: escolher moto para o funcionário
    @GetMapping("/novo/{funcionarioId}")
    public String formAlocar(@PathVariable Long funcionarioId, Model model) {
        Funcionario f = funcRepo.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        model.addAttribute("funcionario", f);
        model.addAttribute("motosDisponiveis", motoRepo.findByDisponivelTrue());
        return "alocacoes/form";
    }

    @PostMapping
    public String alocar(@RequestParam Long funcionarioId, @RequestParam Long motoId) {
        alocacaoService.alocar(funcionarioId, motoId);
        return "redirect:/funcionarios?alocado";
    }

    @PostMapping("/devolver/{alocacaoId}")
    public String devolver(@PathVariable Long alocacaoId) {
        alocacaoService.devolver(alocacaoId);
        return "redirect:/funcionarios?devolvido";
    }
}
