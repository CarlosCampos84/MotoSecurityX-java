package br.com.motosecurityx.web.api;

import br.com.motosecurityx.domain.Moto;
import br.com.motosecurityx.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class ApiMotoController {

    private final MotoService service;

    public ApiMotoController(MotoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public List<Moto> findAll() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public Moto findById(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Moto> create(@Valid @RequestBody Moto moto) {
        Moto saved = service.salvar(moto);
        return ResponseEntity.created(URI.create("/api/motos/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Moto update(@PathVariable Long id, @Valid @RequestBody Moto moto) {
        moto.setId(id);
        return service.salvar(moto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
