package com.practica.expensetracker.service;

import com.practica.expensetracker.model.Categoria;
import com.practica.expensetracker.model.Gasto;
import com.practica.expensetracker.repository.RepositorioGastos;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RastreadorGastos {

    private final RepositorioGastos repositorioGastos;

    public RastreadorGastos(RepositorioGastos repositorioGastos) {
        this.repositorioGastos = Objects.requireNonNull(repositorioGastos, "El repositorio de gastos no puede ser nulo");
    }

    public void agregarGasto(Gasto gasto) {
        repositorioGastos.agregar(gasto);
    }

    public void eliminarGasto(String id) {
        repositorioGastos.eliminar(id);
    }

    public List<Gasto> listarGastos() {
        return repositorioGastos.listarTodos();
    }

    public List<Gasto> listarPorCategoria(Categoria categoria) {
        return repositorioGastos.listarTodos().stream()
                .filter(gasto -> gasto.getCategoria() == categoria)
                .toList();
    }
}
