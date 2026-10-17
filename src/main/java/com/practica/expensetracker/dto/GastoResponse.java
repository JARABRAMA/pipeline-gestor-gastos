package com.practica.expensetracker.dto;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoResponse(
        String id,
        BigDecimal monto,
        Categoria categoria,
        LocalDate fecha,
        String descripcion
) {
    public static GastoResponse from(Gasto gasto) {
        return new GastoResponse(
                gasto.getId(),
                gasto.getMonto(),
                gasto.getCategoria(),
                gasto.getFecha(),
                gasto.getDescripcion()
        );
    }
}
