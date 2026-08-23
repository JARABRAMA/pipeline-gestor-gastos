package com.practica.expensetracker.repository;

import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.support.GastoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
    Pruebas unitarias simples Happy Path para {@link RepositorioGastosEnMemoria}
 */
@DisplayName("RepositorioGastosEnMemoria - Happy Path")
class RepositorioGastosEnMemoriaHappyPathTest {

    private RepositorioGastos repositorio;

    @BeforeEach
    void inicializarRepositorio() {
        // Arrange comun: cada prueba arranca con un repositorio vacio e independiente
        repositorio = new RepositorioGastosEnMemoria();
    }

    @Test
    @DisplayName("Agregar un gasto permite recuperarlo despues por su id")
    void dadoUnGastoValido_cuandoSeAgrega_entoncesSePuedeBuscarPorSuId() {
        // Arrange
        Gasto gasto = GastoBuilder.unGasto().conId("gasto-001").construir();

        // Act
        repositorio.agregar(gasto);
        Optional<Gasto> encontrado = repositorio.buscarPorId("gasto-001");

        // Assert
        assertAll("gasto persistido",
                () -> assertTrue(encontrado.isPresent(), "El gasto agregado debe existir"),
                () -> assertEquals(gasto, encontrado.orElseThrow()));
    }

    @Test
    @DisplayName("Listar todos devuelve los gastos en el orden en que se agregaron")
    void dadoVariosGastosAgregados_cuandoSeListanTodos_entoncesConservanElOrdenDeInsercion() {
        // Arrange
        Gasto primero = GastoBuilder.unGasto().conId("gasto-001").construir();
        Gasto segundo = GastoBuilder.unGasto().conId("gasto-002").construir();
        repositorio.agregar(primero);
        repositorio.agregar(segundo);

        // Act
        List<Gasto> gastos = repositorio.listarTodos();

        // Assert
        assertEquals(List.of(primero, segundo), gastos);
    }

    @Test
    @DisplayName("Eliminar un gasto existente lo retira del repositorio")
    void dadoUnGastoAlmacenado_cuandoSeElimina_entoncesYaNoEstaDisponible() {
        // Arrange
        Gasto gasto = GastoBuilder.unGasto().conId("gasto-001").construir();
        repositorio.agregar(gasto);

        // Act
        repositorio.eliminar("gasto-001");

        // Assert
        assertAll("gasto eliminado",
                () -> assertTrue(repositorio.buscarPorId("gasto-001").isEmpty()),
                () -> assertTrue(repositorio.listarTodos().isEmpty()));
    }

}
