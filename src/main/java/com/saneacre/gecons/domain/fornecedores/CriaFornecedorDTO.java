package com.saneacre.gecons.domain.fornecedores;

import com.saneacre.gecons.domain.enums.PersonalidadeJuridica;
import jakarta.validation.constraints.*;

public record CriaFornecedorDTO(
        @NotNull
        PersonalidadeJuridica tipo,

        @NotNull @NotBlank
        String nome,

        @NotNull @NotBlank
        @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
                message = "CPF/CNPJ inválido")
        String documento,

        @Pattern(regexp = "^(\\(\\d{2}\\)\\s9\\s\\d{4}-\\d{4}$|\\(\\d{2}\\)\\s\\s\\d{4}-\\d{4}$)",
                message = "Número de contato inválido!")
        String contato,

        @Email(message = "Endereço de e-mail inválido!")
        String email) {}
