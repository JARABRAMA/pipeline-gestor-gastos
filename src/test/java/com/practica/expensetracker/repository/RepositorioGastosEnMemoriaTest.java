package com.practica.expensetracker.repository;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioGastosEnMemoriaTest {

  private RepositorioGastosEnMemoria repositorio;
  private Gasto gasto1;
  private Gasto gasto2;
  private Gasto gasto3;

  @BeforeEach
  void setUp() {
    repositorio = new RepositorioGastosEnMemoria();
    gasto1 = new Gasto("1", BigDecimal.valueOf(200L), Categoria.COMIDA, LocalDate.now(), null);
    gasto2 = new Gasto("2", BigDecimal.valueOf(5000L), Categoria.SALUD, LocalDate.now(), null);
    gasto3 = new Gasto("3", BigDecimal.valueOf(300L), Categoria.TRANSPORTE, LocalDate.now(), null);
  }

  @Test
  void agregar_gasto_y_recuperarlo_por_id() {
    repositorio.agregar(gasto1);

    var actual = repositorio.buscarPorId(gasto1.getId());
    assertTrue(actual.isPresent());
    assertEquals(gasto1, actual.get());
  }

  @Test
  void recuperar_por_id_que_no_existe() {
    var actual = repositorio.buscarPorId("id-no-existe");
    assertEquals(Optional.empty(), actual);
  }

  @Test
  void agregar_gasto_y_eliminar_por_id() {
    repositorio.agregar(gasto1);

    repositorio.eliminar(gasto1.getId());

    var actual = repositorio.buscarPorId(gasto1.getId());
    assertTrue(actual.isEmpty());
    assertEquals(Optional.empty(), actual);
  }

  @Test
  void eliminar_por_id_que_no_existe() {
    assertDoesNotThrow(() -> repositorio.eliminar("id-no-existe"));

  }

  @Test
  void eliminar_gasto_con_id_nulo() {
    assertDoesNotThrow(() -> repositorio.eliminar(null));
  }

  @Test
  void buscar_por_id_nulo() {
    var actual = assertDoesNotThrow(() -> repositorio.buscarPorId(null));
    assertTrue(actual.isEmpty());
  }

  @Test
  void listar_todos_los_gastos() {
    repositorio.agregar(gasto1);
    repositorio.agregar(gasto2);
    repositorio.agregar(gasto3);

    var expected = List.of(gasto1, gasto2, gasto3);

    assertEquals(expected, repositorio.listarTodos());
  }

  @Test
  void listar_todos_cuando_no_existe() {
    var actual = repositorio.listarTodos();
    assertEquals(Collections.emptyList(), actual);
  }

}