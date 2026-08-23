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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Control O(1) esperado: buscar por id en el mapa no deberia depender del
 * tamano de la coleccion, a diferencia de listarTodos().
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class BuscarPorIdBenchmark {

    @Param({"100", "10000", "1000000"})
    private int cantidadGastos;

    private RepositorioGastos repositorio;
    private String idBuscado;

    @Setup(Level.Trial)
    public void preparar() {
        repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastos; i++) {
            String id = String.valueOf(i);
            repositorio.agregar(new Gasto(id, BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "gasto " + i));
            if (i == cantidadGastos / 2) {
                idBuscado = id;
            }
        }
    }

    @Benchmark
    public Optional<Gasto> buscarPorId() {
        return repositorio.buscarPorId(idBuscado);
    }
}
