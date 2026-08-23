package com.practica.expensetracker.service;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ServicioReporteTest {

  @Mock
  private RepositorioGastos repositorioGastos;

  @InjectMocks
  private ServicioReporte servicioReporte;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private List<Gasto> gastos;

  void construirListaGastos() {
    this.gastos = List.of(
            new Gasto(
                    BigDecimal.valueOf(10000L),
                    Categoria.TRANSPORTE,
                    LocalDate.now(),
                    "Pasajes para la universidad"
            ),
            new Gasto(
                    BigDecimal.valueOf(40000L),
                    Categoria.COMIDA,
                    LocalDate.now(),
                    "Almuerzo"
            ),
            new Gasto(
                    BigDecimal.valueOf(3000L),
                    Categoria.SALUD,
                    LocalDate.now(),
                    "Pastillas para el dolor de cabeza"
            ),
            new Gasto(
                    BigDecimal.valueOf(40000L),
                    Categoria.SALUD,
                    LocalDate.now(),
                    "Radiografía de rodilla"
            ),
            new Gasto(
                    BigDecimal.valueOf(10000L),
                    Categoria.EDUCACION,
                    LocalDate.now(),
                    "Fotocopias de calidad de software"
            )
    );
  }


  // calcularTotal ---
  @Test
  void when_calcularTotal_then_sumar_montos() {
    construirListaGastos();
    when(repositorioGastos.listarTodos()).thenReturn(gastos);

    var suma = gastos.stream().map(Gasto::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add);
    assertEquals(suma, servicioReporte.calcularTotal());
  }

  @Test
  void when_calcularTotal_and_repositorio_vacio_then_cero() {
    construirListaGastos();
    when(repositorioGastos.listarTodos()).thenReturn(Collections.emptyList());

    var actual =  servicioReporte.calcularTotal();

    assertEquals(BigDecimal.ZERO, actual);
  }

  @Test
  void when_calcularTotal_and_repository_lanza_exception_then_lanzar_exception() {
    when(repositorioGastos.listarTodos()).thenThrow(new RuntimeException("message"));

    var ex = assertThrows(RuntimeException.class, () -> servicioReporte.calcularTotal());
    assertEquals("message",  ex.getMessage());
  }


  // calcularPorCategoria  ---
  @Test
  void when_calcularPorCategoria_then_sumar_montos_por_categoria() {
    construirListaGastos();
    when(repositorioGastos.listarTodos()).thenReturn(gastos);
    var categoria = Categoria.SALUD;
    var expected = gastos.stream().filter(g -> g.getCategoria().equals(categoria))
            .map(Gasto::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add);

    var actual = servicioReporte.calcularTotalPorCategoria(categoria);

    assertEquals(expected, actual);
  }

  @Test
  void when_calcularPorCategoria_and_argumento_nulo_then_cero() {
    construirListaGastos();
    when(repositorioGastos.listarTodos()).thenReturn(gastos);

    var actual =  servicioReporte.calcularTotalPorCategoria(null);
    assertEquals(BigDecimal.ZERO, actual);
  }

  @Test
  void when_calcularPorCategoria_and_no_coincidencias_then_cero() {
    construirListaGastos();
    var categoria = Categoria.ENTRETENIMIENTO; // no hay gastos de categoria entretenimiento
    // la variable de clase gastos

    when(repositorioGastos.listarTodos()).thenReturn(gastos);
    var actual =  servicioReporte.calcularTotalPorCategoria(categoria);
    assertEquals(BigDecimal.ZERO, actual);
  }

  @Test
  void when_calcularPorCategoria_and_repositorio_vacio_then_cero() {
    when(repositorioGastos.listarTodos()).thenReturn(Collections.emptyList());

    var actual =  servicioReporte.calcularTotalPorCategoria(null);

    assertEquals(BigDecimal.ZERO, actual);
  }

  @Test
  void when_calcularPorCategoria_and_repository_lanza_exception_then_lanzar_exception() {
    when(repositorioGastos.listarTodos()).thenThrow(new RuntimeException("message"));

    var ex = assertThrows(RuntimeException.class, () -> servicioReporte.calcularTotalPorCategoria(Categoria.ENTRETENIMIENTO));
    assertEquals("message", ex.getMessage());
  }


  // calcularPromedio ---
  @Test
  void when_calcularPromedio_then_calcular_promedio() {
    construirListaGastos();
    when(repositorioGastos.listarTodos()).thenReturn(gastos);
    var sum = gastos.stream().map(Gasto::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add);
    var prom = sum.divide(BigDecimal.valueOf(gastos.size()), MathContext.DECIMAL64);

    var actual =  servicioReporte.calcularPromedio();
    assertEquals(prom, actual);
  }

  @Test
  void when_calcularPromedio_and_repositorio_vacio_then_cero() {
    when(repositorioGastos.listarTodos()).thenReturn(Collections.emptyList());

    var actual =  servicioReporte.calcularPromedio();

    assertEquals(BigDecimal.ZERO, actual);
  }

  @Test
  void when_calcularPromedio_and_repositorio_lanza_exception_then_lanzar_exception() {
    when(repositorioGastos.listarTodos()).thenThrow(new RuntimeException("message"));

    var ex = assertThrows(RuntimeException.class, () -> servicioReporte.calcularPromedio());
    assertEquals("message", ex.getMessage());
  }

}