package com.practica.expensetracker;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
import com.practica.expensetracker.service.ServicioReporte;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Los gastos se reparten en round-robin entre las 6 categorias, asi que
 * calcularTotalPorCategoria(COMIDA) solo suma ~1/6 de los registros aunque
 * internamente listarTodos() copie el 100% de la coleccion.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class CalcularTotalPorCategoriaBenchmark {

    private static final Categoria[] CATEGORIAS = Categoria.values();

    @Param({"100", "10000", "1000000"})
    private int cantidadGastos;

    private ServicioReporte servicioReporte;

    @Setup(Level.Trial)
    public void preparar() {
        RepositorioGastos repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastos; i++) {
            Categoria categoria = CATEGORIAS[i % CATEGORIAS.length];
            repositorio.agregar(new Gasto(BigDecimal.TEN, categoria, LocalDate.now(), "gasto " + i));
        }
        servicioReporte = new ServicioReporte(repositorio);
    }

    @Benchmark
    public BigDecimal calcularTotalPorCategoria() {
        return servicioReporte.calcularTotalPorCategoria(Categoria.COMIDA);
    }
}
