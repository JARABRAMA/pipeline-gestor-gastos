package com.practica.expensetracker.service;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import com.practica.expensetracker.support.GastoBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias simples Happy Path para {@link ServicioReporte}.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ServicioReporte - Happy Path")
public class ServicioReporteHappyPathTest {
    @Mock
    private RepositorioGastos repositorioGastos;

    @InjectMocks
    private ServicioReporte servicioReporte;

    private List<Gasto> gastosDelMes;

    @BeforeEach
    void prepararEscenarioComun() {
        // Arrange comun: 25.000 + 15.000 (COMIDA) + 20.000 (TRANSPORTE) = 60.000
        Gasto almuerzo = GastoBuilder.unGasto().conMonto("25000").conCategoria(Categoria.COMIDA).construir();
        Gasto cena = GastoBuilder.unGasto().conMonto("15000").conCategoria(Categoria.COMIDA).construir();
        Gasto bus = GastoBuilder.unGasto().conMonto("20000").conCategoria(Categoria.TRANSPORTE).construir();
        gastosDelMes = List.of(almuerzo, cena, bus);
    }

    @Test
    @DisplayName("Calcular el total suma los montos de todos los gastos")
    void dadoVariosGastos_cuandoSeCalculaElTotal_entoncesSeSumanTodosLosMontos() {
        // Arrange
        when(repositorioGastos.listarTodos()).thenReturn(gastosDelMes);
        BigDecimal totalEsperado = new BigDecimal("60000");

        // Act
        BigDecimal total = servicioReporte.calcularTotal();

        // Assert
        assertEquals(0, totalEsperado.compareTo(total),
                () -> "Total esperado " + totalEsperado + " pero fue " + total);
    }

    @Test
    @DisplayName("Calcular el total por categoria suma solo los gastos de esa categoria")
    void dadoGastosDeVariasCategorias_cuandoSeTotalizaUna_entoncesSoloSumaEsaCategoria() {
        // Arrange
        when(repositorioGastos.listarTodos()).thenReturn(gastosDelMes);
        BigDecimal totalComidaEsperado = new BigDecimal("40000");

        // Act
        BigDecimal totalComida = servicioReporte.calcularTotalPorCategoria(Categoria.COMIDA);

        // Assert
        assertEquals(0, totalComidaEsperado.compareTo(totalComida),
                () -> "Total de COMIDA esperado " + totalComidaEsperado + " pero fue " + totalComida);
    }

    @Test
    @DisplayName("Calcular el promedio divide el total entre la cantidad de gastos")
    void dadoVariosGastos_cuandoSeCalculaElPromedio_entoncesEsElTotalEntreLaCantidad() {
        // Arrange
        when(repositorioGastos.listarTodos()).thenReturn(gastosDelMes);
        BigDecimal promedioEsperado = new BigDecimal("20000");

        // Act
        BigDecimal promedio = servicioReporte.calcularPromedio();

        // Assert
        assertEquals(0, promedioEsperado.compareTo(promedio),
                () -> "Promedio esperado " + promedioEsperado + " pero fue " + promedio);
    }
}
