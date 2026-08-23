package com.practica.expensetracker.support;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Constructor fluido de objetos {@link Gasto} para pruebas (patron Test Data Builder).
 */

public final class GastoBuilder {

    private static final BigDecimal MONTO_POR_DEFECTO = new BigDecimal("10000");
    private static final Categoria CATEGORIA_POR_DEFECTO = Categoria.COMIDA;
    private static final LocalDate FECHA_POR_DEFECTO = LocalDate.of(2026, 1, 15);
    private static final String DESCRIPCION_POR_DEFECTO = "Almuerzo corriente";

    private String id;
    private BigDecimal monto = MONTO_POR_DEFECTO;
    private Categoria categoria = CATEGORIA_POR_DEFECTO;
    private LocalDate fecha = FECHA_POR_DEFECTO;
    private String descripcion = DESCRIPCION_POR_DEFECTO;

    private GastoBuilder() {
    }

    public static GastoBuilder unGasto() {
        return new GastoBuilder();
    }

    public GastoBuilder conId(String id) {
        this.id = id;
        return this;
    }

    public GastoBuilder conMonto(String monto) {
        this.monto = new BigDecimal(monto);
        return this;
    }

    public GastoBuilder conCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public GastoBuilder conFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public GastoBuilder conDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Gasto construir() {
        return id == null
                ? new Gasto(monto, categoria, fecha, descripcion)
                : new Gasto(id, monto, categoria, fecha, descripcion);
    }
}
