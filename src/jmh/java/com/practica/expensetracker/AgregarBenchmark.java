package com.practica.expensetracker;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class AgregarBenchmark {

    @Param({"100", "10000", "1000000"})
    private int cantidadGastosPrevios;

    private RepositorioGastos repositorio;

    @Setup(Level.Iteration)
    public void prepararRepositorio() {
        repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastosPrevios; i++) {
            repositorio.agregar(new Gasto(BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "gasto " + i));
        }
    }

    @Benchmark
    public void agregar() {
        repositorio.agregar(new Gasto(BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "nuevo"));
    }
}
