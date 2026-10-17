package com.practica.expensetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.expensetracker.dto.GastoRequest;
import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastosEnMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GastoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositorioGastosEnMemoria repositorio;

    @BeforeEach
    void setUp() {
        repositorio.limpiar();
    }

    @Test
    void shouldCreateGasto() throws Exception {
        GastoRequest request = new GastoRequest(
                new BigDecimal("25000"),
                Categoria.COMIDA,
                LocalDate.of(2026, 1, 15),
                "Almuerzo"
        );

        mockMvc.perform(post("/api/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monto", is(25000)))
                .andExpect(jsonPath("$.categoria", is("COMIDA")))
                .andExpect(jsonPath("$.descripcion", is("Almuerzo")));
    }

    @Test
    void shouldListGastos() throws Exception {
        repositorio.agregar(new Gasto(new BigDecimal("10000"), Categoria.COMIDA, LocalDate.of(2026, 1, 1), "A"));
        repositorio.agregar(new Gasto(new BigDecimal("20000"), Categoria.TRANSPORTE, LocalDate.of(2026, 1, 2), "B"));

        mockMvc.perform(get("/api/gastos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldListGastosByCategoria() throws Exception {
        repositorio.agregar(new Gasto(new BigDecimal("10000"), Categoria.COMIDA, LocalDate.of(2026, 1, 1), "A"));
        repositorio.agregar(new Gasto(new BigDecimal("20000"), Categoria.TRANSPORTE, LocalDate.of(2026, 1, 2), "B"));

        mockMvc.perform(get("/api/gastos").param("categoria", "COMIDA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoria", is("COMIDA")));
    }

    @Test
    void shouldGetGastoById() throws Exception {
        Gasto gasto = new Gasto(new BigDecimal("15000"), Categoria.SALUD, LocalDate.of(2026, 1, 5), "Consulta");
        repositorio.agregar(gasto);

        mockMvc.perform(get("/api/gastos/{id}", gasto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(gasto.getId())))
                .andExpect(jsonPath("$.descripcion", is("Consulta")));
    }

    @Test
    void shouldReturn404WhenGastoNotFound() throws Exception {
        mockMvc.perform(get("/api/gastos/{id}", "inexistente"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteGasto() throws Exception {
        Gasto gasto = new Gasto(new BigDecimal("5000"), Categoria.OTROS, LocalDate.of(2026, 1, 10), "Otros");
        repositorio.agregar(gasto);

        mockMvc.perform(delete("/api/gastos/{id}", gasto.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/gastos/{id}", gasto.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        mockMvc.perform(delete("/api/gastos/{id}", "no-existe"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenMontoInvalid() throws Exception {
        GastoRequest request = new GastoRequest(
                new BigDecimal("-100"),
                Categoria.COMIDA,
                LocalDate.of(2026, 1, 15),
                "Negativo"
        );

        mockMvc.perform(post("/api/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenBodyInvalid() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/api/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
