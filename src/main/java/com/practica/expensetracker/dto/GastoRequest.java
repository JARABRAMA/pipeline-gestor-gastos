package com.practica.expensetracker.dto;

import com.practica.expensetracker.model.Categoria;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRequest(
        @NotNull(message = "El monto no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser positivo")
        BigDecimal monto,

        @NotNull(message = "La categoria no puede ser nula")
        Categoria categoria,

        @NotNull(message = "La fecha no puede ser nula")
        LocalDate fecha,

        @Size(max = 255, message = "La descripcion no puede exceder 255 caracteres")
        String descripcion
) {
}
