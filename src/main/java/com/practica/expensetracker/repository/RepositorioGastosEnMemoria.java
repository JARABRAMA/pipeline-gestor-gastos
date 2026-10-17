package com.practica.expensetracker.repository;

import com.practica.expensetracker.model.Gasto;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RepositorioGastosEnMemoria implements RepositorioGastos {

    private final Map<String, Gasto> gastos = new LinkedHashMap<>();

    @Override
    public void agregar(Gasto gasto) {
        gastos.put(gasto.getId(), gasto);
    }

    @Override
    public void eliminar(String id) {
        gastos.remove(id);
    }

    @Override
    public Optional<Gasto> buscarPorId(String id) {
        return Optional.ofNullable(gastos.get(id));
    }

    @Override
    public List<Gasto> listarTodos() {
        return List.copyOf(gastos.values());
    }

    public void limpiar() {
        gastos.clear();
    }
}
