package br.com.motosecurityx.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "moto", uniqueConstraints = @UniqueConstraint(name="uk_moto_placa", columnNames="placa"))
public class Moto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$",
            message = "Placa inválida (padrão Mercosul).")
    @Column(length = 8, nullable = false)
    private String placa;

    @NotBlank @Size(max = 80)
    private String modelo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patio_id")
    private Patio patioAtual;

    @NotNull
    private Boolean disponivel = true;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Patio getPatioAtual() { return patioAtual; }
    public void setPatioAtual(Patio patioAtual) { this.patioAtual = patioAtual; }
    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
