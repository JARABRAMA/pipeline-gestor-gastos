package com.practica.expensetracker.controller;

import com.practica.expensetracker.dto.GastoRequest;
import com.practica.expensetracker.dto.GastoResponse;
import com.practica.expensetracker.exception.RecursoNoEncontradoException;
import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.service.RastreadorGastos;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final RastreadorGastos rastreadorGastos;

    public GastoController(RastreadorGastos rastreadorGastos) {
        this.rastreadorGastos = rastreadorGastos;
    }

    @GetMapping
    public List<GastoResponse> listar(@RequestParam(required = false) Categoria categoria) {
        List<Gasto> gastos = categoria == null
                ? rastreadorGastos.listarGastos()
                : rastreadorGastos.listarPorCategoria(categoria);
        return gastos.stream()
                .map(GastoResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public GastoResponse obtenerPorId(@PathVariable String id) {
        return rastreadorGastos.listarGastos().stream()
                .filter(gasto -> id.equals(gasto.getId()))
                .findFirst()
                .map(GastoResponse::from)
                .orElseThrow(() -> new RecursoNoEncontradoException("Gasto no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<GastoResponse> crear(@RequestBody @Valid GastoRequest request) {
        Gasto gasto = new Gasto(
                request.monto(),
                request.categoria(),
                request.fecha(),
                request.descripcion()
        );
        rastreadorGastos.agregarGasto(gasto);
        GastoResponse response = GastoResponse.from(gasto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gasto.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        boolean existe = rastreadorGastos.listarGastos().stream()
                .anyMatch(gasto -> id.equals(gasto.getId()));
        if (!existe) {
            throw new RecursoNoEncontradoException("Gasto no encontrado con id: " + id);
        }
        rastreadorGastos.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }
}
