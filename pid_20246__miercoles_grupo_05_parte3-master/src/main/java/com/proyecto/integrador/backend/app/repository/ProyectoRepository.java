package com.proyecto.integrador.backend.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador.backend.app.entity.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
	public abstract Optional<Proyecto> findByTitulo(String titulo);
	public abstract List<Proyecto> findByUsuarioId(int usuarioId);

}
