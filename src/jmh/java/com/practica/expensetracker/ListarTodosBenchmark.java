package com.practica.expensetracker;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ListarTodosBenchmark {

    @Param({"100", "10000", "1000000"})
    private int cantidadGastos;

    private RepositorioGastos repositorio;

    @Setup(Level.Trial)
    public void prepararRepositorio() {
        repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastos; i++) {
            repositorio.agregar(new Gasto(BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "gasto " + i));
        }
    }

    @Benchmark
    public List<Gasto> listarTodos() {
        return repositorio.listarTodos();
    }
}
