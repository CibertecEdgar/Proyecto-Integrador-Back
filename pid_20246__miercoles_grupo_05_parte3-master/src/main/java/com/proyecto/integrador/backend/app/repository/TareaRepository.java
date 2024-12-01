package com.proyecto.integrador.backend.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador.backend.app.entity.Proyecto;
import com.proyecto.integrador.backend.app.entity.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {
	
	public abstract List<Tarea> findByUsuarioId(int usuarioId);
	Page<Tarea> findByUsuarioId(int usuarioId, Pageable pageable);
	
	//buscar por el titulo y idproyecto
	public Optional<Tarea> findByTituloAndProyecto(String titulo, Proyecto idProyecto);
}
