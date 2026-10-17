package com.practica.expensetracker.controller;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioGastosEnMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio.limpiar();
    }

    @Test
    void shouldGetTotal() throws Exception {
        repositorio.agregar(new Gasto(new BigDecimal("10000"), Categoria.COMIDA, LocalDate.now(), "A"));
        repositorio.agregar(new Gasto(new BigDecimal("20000"), Categoria.TRANSPORTE, LocalDate.now(), "B"));

        mockMvc.perform(get("/api/reportes/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(30000)));
    }

    @Test
    void shouldGetPromedio() throws Exception {
        repositorio.agregar(new Gasto(new BigDecimal("10000"), Categoria.COMIDA, LocalDate.now(), "A"));
        repositorio.agregar(new Gasto(new BigDecimal("20000"), Categoria.TRANSPORTE, LocalDate.now(), "B"));

        mockMvc.perform(get("/api/reportes/promedio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(15000)));
    }

    @Test
    void shouldGetTotalByCategoria() throws Exception {
        repositorio.agregar(new Gasto(new BigDecimal("10000"), Categoria.COMIDA, LocalDate.now(), "A"));
        repositorio.agregar(new Gasto(new BigDecimal("20000"), Categoria.COMIDA, LocalDate.now(), "B"));
        repositorio.agregar(new Gasto(new BigDecimal("5000"), Categoria.TRANSPORTE, LocalDate.now(), "C"));

        mockMvc.perform(get("/api/reportes/total/{categoria}", Categoria.COMIDA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(30000)));
    }
}
