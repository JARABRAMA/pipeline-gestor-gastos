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
import java.util.concurrent.TimeUnit;

/**
 * eliminar() borra una entrada real cada vez, asi que no se puede repetir la
 * misma llamada tal cual (la segunda vez ya no encontraria nada que borrar).
 * Por eso se reinserta el objetivo antes de CADA invocacion (Level.Invocation);
 * JMH no cuenta ese setup dentro del tiempo medido, pero anade algo de ruido
 * porque el reset ocurre con mas frecuencia que en los demas benchmarks.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class EliminarBenchmark {

    private static final String ID_OBJETIVO = "id-a-eliminar";

    @Param({"100", "10000", "1000000"})
    private int cantidadGastosPrevios;

    private RepositorioGastos repositorio;

    @Setup(Level.Trial)
    public void prepararRepositorioBase() {
        repositorio = new RepositorioGastosEnMemoria();
        for (int i = 0; i < cantidadGastosPrevios; i++) {
            repositorio.agregar(new Gasto(String.valueOf(i), BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "gasto " + i));
        }
    }

    @Setup(Level.Invocation)
    public void insertarObjetivo() {
        repositorio.agregar(new Gasto(ID_OBJETIVO, BigDecimal.TEN, Categoria.COMIDA, LocalDate.now(), "a eliminar"));
    }

    @Benchmark
    public void eliminar() {
        repositorio.eliminar(ID_OBJETIVO);
    }
}
