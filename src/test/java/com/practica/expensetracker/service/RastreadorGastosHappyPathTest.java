package com.practica.expensetracker.service;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.support.GastoBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias simples Happy Path para {@link RastreadorGastos}.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RastreadorGastos - Happy Path")
public class RastreadorGastosHappyPathTest {

    @Mock
    private RepositorioGastos repositorioGastos;

    @InjectMocks
    private RastreadorGastos rastreadorGastos;

    @Test
    @DisplayName("Agregar un gasto delega la persistencia en el repositorio")
    void dadoUnGastoValido_cuandoSeAgrega_entoncesSeDelegaEnElRepositorio() {
        // Arrange
        Gasto gasto = GastoBuilder.unGasto().conId("gasto-001").construir();

        // Act
        rastreadorGastos.agregarGasto(gasto);

        // Assert
        verify(repositorioGastos).agregar(gasto);
    }

    @Test
    @DisplayName("Eliminar un gasto delega el borrado en el repositorio")
    void dadoElIdDeUnGasto_cuandoSeElimina_entoncesSeDelegaEnElRepositorio() {
        // Arrange
        String idGasto = "gasto-001";

        // Act
        rastreadorGastos.eliminarGasto(idGasto);

        // Assert
        verify(repositorioGastos).eliminar(idGasto);
    }

    @Test
    @DisplayName("Listar gastos devuelve exactamente lo que entrega el repositorio")
    void dadoUnRepositorioConGastos_cuandoSeListan_entoncesSeDevuelvenTodos() {
        // Arrange
        Gasto comida = GastoBuilder.unGasto().conCategoria(Categoria.COMIDA).construir();
        Gasto transporte = GastoBuilder.unGasto().conCategoria(Categoria.TRANSPORTE).construir();
        when(repositorioGastos.listarTodos()).thenReturn(List.of(comida, transporte));

        // Act
        List<Gasto> gastos = rastreadorGastos.listarGastos();

        // Assert
        assertEquals(List.of(comida, transporte), gastos);
    }

    @Test
    @DisplayName("Listar por categoria devuelve solo los gastos de esa categoria")
    void dadoGastosDeVariasCategorias_cuandoSeFiltraPorUna_entoncesSoloRetornaEsaCategoria() {
        // Arrange
        Gasto almuerzo = GastoBuilder.unGasto().conCategoria(Categoria.COMIDA).construir();
        Gasto cena = GastoBuilder.unGasto().conCategoria(Categoria.COMIDA).construir();
        Gasto bus = GastoBuilder.unGasto().conCategoria(Categoria.TRANSPORTE).construir();
        when(repositorioGastos.listarTodos()).thenReturn(List.of(almuerzo, cena, bus));

        // Act
        List<Gasto> gastosDeComida = rastreadorGastos.listarPorCategoria(Categoria.COMIDA);

        // Assert
        assertEquals(List.of(almuerzo, cena), gastosDeComida);
    }

}
