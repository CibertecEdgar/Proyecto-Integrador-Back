package com.proyecto.integrador.backend.app.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.integrador.backend.app.entity.Tarea;

public interface TareaService {

	//
	public abstract List<Tarea> findAll();
	public abstract List<Tarea> findAllById(int usuarioId);
	public abstract Optional<Tarea> findById(int id);
	public abstract Tarea create(Tarea tarea, int usuarioId);
	public abstract Tarea update(int id, Tarea tarea);
	public abstract Optional<Tarea> deleteById(int id);
}
