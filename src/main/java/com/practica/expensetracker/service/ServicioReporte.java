package com.practica.expensetracker.service;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;

@Service
public class ServicioReporte {

    private final RepositorioGastos repositorioGastos;

    public ServicioReporte(RepositorioGastos repositorioGastos) {
        this.repositorioGastos = Objects.requireNonNull(repositorioGastos, "El repositorio de gastos no puede ser nulo");
    }

    public BigDecimal calcularTotal() {
        return repositorioGastos.listarTodos().stream()
                .map(Gasto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalPorCategoria(Categoria categoria) {
        return repositorioGastos.listarTodos().stream()
                .filter(gasto -> gasto.getCategoria() == categoria)
                .map(Gasto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularPromedio() {
        List<Gasto> gastos = repositorioGastos.listarTodos();
        if (gastos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = calcularTotal();
        return total.divide(BigDecimal.valueOf(gastos.size()), MathContext.DECIMAL64);
    }
}
