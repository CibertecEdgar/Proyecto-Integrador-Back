package com.proyecto.integrador.backend.app.service;

import java.util.List;
import java.util.Optional;
import com.proyecto.integrador.backend.app.entity.Proyecto;

public interface ProyectoService {

    public abstract List<Proyecto> findAll(); 
    public abstract Optional<Proyecto> findById(int id);
    public abstract List<Proyecto> findAllByIdUser(int usuarioId);
    public abstract Proyecto create(Proyecto proyecto, int usuarioId); 
    public abstract Proyecto update(int id, Proyecto proyecto); 
    public abstract Optional<Proyecto> deleteById(int id); 
}