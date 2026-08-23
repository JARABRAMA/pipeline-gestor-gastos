package com.practica.expensetracker.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GastoTest {


  @Test
  void when_construir_gasto_valido_then_no_lanzar_excepcion() {
    assertDoesNotThrow(() -> new Gasto(BigDecimal.valueOf(1000L), Categoria.COMIDA, LocalDate.now(), null));
  }

  @Test
  void when_construir_gasto_and_monto_nulo_then_lanzar_excepcion() {
    var ex = assertThrows(IllegalArgumentException.class, () -> new Gasto(null, Categoria.COMIDA, LocalDate.now(), null));
    assertEquals("El monto debe ser positivo", ex.getMessage());
  }

  @ParameterizedTest(name = "El monto debe ser un numero mayor a cero")
  @ValueSource(strings = {"0", "0.0", "-1", "-0.000001", "-1000000000"})
  void when_construir_gasto_and_monto_menor_cero_then_lanzar_excepcion(String monto) {
    var ex = assertThrows(IllegalArgumentException.class, () ->
            new Gasto(new BigDecimal(monto), Categoria.COMIDA, LocalDate.now(), null));
    assertEquals("El monto debe ser positivo", ex.getMessage());
  }

  @Test
  void when_construir_gasto_and_categoria_nula_then_lanzar_excepcion() {
    var ex = assertThrows(IllegalArgumentException.class, () -> new Gasto(BigDecimal.valueOf(1000L), null, LocalDate.now(), null));
    assertEquals("La categoria no puede ser nula", ex.getMessage());
  }

  @Test
  void when_construir_gasto_and_fecha_nula_then_lanzar_excepcion() {
    var ex = assertThrows(IllegalArgumentException.class, () -> new Gasto(BigDecimal.valueOf(1000L), Categoria.COMIDA, null, null));
    assertEquals("La fecha no puede ser nula", ex.getMessage());
  }
}