package com.practica.expensetracker;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Compara el constructor que genera un UUID automaticamente contra el que
 * recibe un id explicito, para aislar cuanto cuesta UUID.randomUUID() dentro
 * del costo total de crear un Gasto.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class GastoConstructorBenchmark {

    private static final BigDecimal MONTO = BigDecimal.TEN;
    private static final LocalDate FECHA = LocalDate.now();
    private static final String ID_FIJO = "id-fijo-de-prueba";

    @Benchmark
    public Gasto crearGastoConUuidAutogenerado() {
        return new Gasto(MONTO, Categoria.COMIDA, FECHA, "gasto de prueba");
    }

    @Benchmark
    public Gasto crearGastoConIdExplicito() {
        return new Gasto(ID_FIJO, MONTO, Categoria.COMIDA, FECHA, "gasto de prueba");
    }
}
