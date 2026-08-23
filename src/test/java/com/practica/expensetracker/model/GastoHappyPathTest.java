package com.practica.expensetracker.model;

import com.practica.expensetracker.support.GastoBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias simples Happy Path para la entidad {@link Gasto}.
 *
 */
@DisplayName("Gasto - Happy Path")
class GastoHappyPathTest {

    @Test
    @DisplayName("Crear un gasto con datos validos conserva el estado recibido")
    void dadoDatosValidos_cuandoSeCreaElGasto_entoncesConservarSuEstado() {
        // Arrange
        BigDecimal montoEsperado = new BigDecimal("25000");
        Categoria categoriaEsperada = Categoria.COMIDA;
        LocalDate fechaEsperada = LocalDate.of(2026, 3, 10);
        String descripcionEsperada = "Almuerzo de trabajo";

        // Act
        Gasto gasto = new Gasto(montoEsperado, categoriaEsperada, fechaEsperada, descripcionEsperada);

        // Assert
        assertAll("estado del gasto recien creado",
                () -> assertEquals(montoEsperado, gasto.getMonto()),
                () -> assertEquals(categoriaEsperada, gasto.getCategoria()),
                () -> assertEquals(fechaEsperada, gasto.getFecha()),
                () -> assertEquals(descripcionEsperada, gasto.getDescripcion()));
    }

    @Test
    @DisplayName("Crear un gasto sin id explicito genera un identificador unico")
    void dadoUnGastoSinId_cuandoSeCrea_entoncesRecibeUnIdentificadorUnico() {
        // Arrange
        GastoBuilder builder = GastoBuilder.unGasto();

        // Act
        Gasto primerGasto = builder.construir();
        Gasto segundoGasto = builder.construir();

        // Assert
        assertAll("identificadores generados",
                () -> assertNotNull(primerGasto.getId()),
                () -> assertFalse(primerGasto.getId().isBlank(), "El id generado no debe estar vacio"),
                () -> assertNotEquals(primerGasto.getId(), segundoGasto.getId()));
    }

    @Test
    @DisplayName("Actualizar el monto con un valor valido reemplaza el monto anterior")
    void dadoUnGastoExistente_cuandoSeActualizaElMonto_entoncesQuedaElNuevoValor() {
        // Arrange
        Gasto gasto = GastoBuilder.unGasto().conMonto("10000").construir();
        BigDecimal nuevoMonto = new BigDecimal("18500");

        // Act
        gasto.setMonto(nuevoMonto);

        // Assert
        assertEquals(0, nuevoMonto.compareTo(gasto.getMonto()),
                "El gasto debe reflejar el monto actualizado");
    }

    @Test
    @DisplayName("Dos gastos con el mismo id son iguales y comparten hashCode")
    void dadoDosGastosConElMismoId_cuandoSeComparan_entoncesSonEquivalentes() {
        // Arrange
        String idCompartido = "gasto-001";
        Gasto gasto = GastoBuilder.unGasto().conId(idCompartido).conMonto("10000").construir();
        Gasto mismoGasto = GastoBuilder.unGasto().conId(idCompartido).conMonto("99000").construir();

        // Act
        boolean sonIguales = gasto.equals(mismoGasto);

        // Assert
        assertAll("contrato equals/hashCode basado en el id",
                () -> assertTrue(sonIguales, "La identidad del gasto la define su id"),
                () -> assertEquals(gasto.hashCode(), mismoGasto.hashCode()));
    }
}
