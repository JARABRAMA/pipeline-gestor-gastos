package com.practica.expensetracker.controller;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.service.ServicioReporte;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ServicioReporte servicioReporte;

    public ReporteController(ServicioReporte servicioReporte) {
        this.servicioReporte = servicioReporte;
    }

    @GetMapping("/total")
    public BigDecimal calcularTotal() {
        return servicioReporte.calcularTotal();
    }

    @GetMapping("/total/{categoria}")
    public BigDecimal calcularTotalPorCategoria(@PathVariable Categoria categoria) {
        return servicioReporte.calcularTotalPorCategoria(categoria);
    }

    @GetMapping("/promedio")
    public BigDecimal calcularPromedio() {
        return servicioReporte.calcularPromedio();
    }
}
