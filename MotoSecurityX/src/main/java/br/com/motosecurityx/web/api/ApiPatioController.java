package br.com.motosecurityx.web.api;

import br.com.motosecurityx.domain.Patio;
import br.com.motosecurityx.service.PatioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class ApiPatioController {

    private final PatioService service;

    public ApiPatioController(PatioService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public List<Patio> findAll() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public Patio findById(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patio> create(@Valid @RequestBody Patio patio) {
        Patio saved = service.salvar(patio);
        return ResponseEntity.created(URI.create("/api/patios/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Patio update(@PathVariable Long id, @Valid @RequestBody Patio patio) {
        patio.setId(id);
        return service.salvar(patio);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
