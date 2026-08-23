package com.practica.expensetracker;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
import com.practica.expensetracker.service.RastreadorGastos;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Mismo patron que CalcularTotalPorCategoriaBenchmark pero desde la capa de
 * negocio (RastreadorGastos), para confirmar que el costo de copiar en
 * listarTodos() se propaga igual sin importar por donde se llegue a el.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ListarPorCategoriaBenchmark {

    private static final Categoria[] CATEGORIAS = Categoria.values();

    @Param({"100", "10000", "1000000"})
    private int cantidadGastos;

    private RastreadorGastos rastreador;

    @Setup(Level.Trial)
    public void preparar() {
        RepositorioGastos repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastos; i++) {
            Categoria categoria = CATEGORIAS[i % CATEGORIAS.length];
            repositorio.agregar(new Gasto(String.valueOf(i), BigDecimal.TEN, categoria, LocalDate.now(), "gasto " + i));
        }
        rastreador = new RastreadorGastos(repositorio);
    }

    @Benchmark
    public List<Gasto> listarPorCategoria() {
        return rastreador.listarPorCategoria(Categoria.COMIDA);
    }
}
