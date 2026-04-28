package br.com.petflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_PFW_PET")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Espécie é obrigatória")
    @Column(nullable = false, length = 50)
    private String especie;

    @Column(length = 100)
    private String raca;

    @Min(value = 0, message = "Idade não pode ser negativa")
    private Integer idade;

    @DecimalMin(value = "0.1", message = "Peso deve ser maior que zero")
    @Column(precision = 5, scale = 2)
    private Double peso;

    @NotBlank(message = "Nome do tutor é obrigatório")
    @Column(name = "tutor_nome", nullable = false, length = 150)
    private String tutorNome;

    @Email(message = "E-mail inválido")
    @Column(name = "tutor_email", length = 150)
    private String tutorEmail;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDate.now();
    }
}
