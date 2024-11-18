package com.proyecto.integrador.backend.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyecto.integrador.backend.app.entity.Tarea;
import com.proyecto.integrador.backend.app.entity.Usuario;
import com.proyecto.integrador.backend.app.repository.TareaRepository;
import com.proyecto.integrador.backend.app.repository.UsuarioRepository;

@Service
public class TareaServiceImpl implements TareaService{

	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Tarea> findAll() {
		return tareaRepository.findAll();
	}

	@Override
	public List<Tarea> findAllById(int usuarioId) {
		return tareaRepository.findByUsuarioId(usuarioId);
	}

	@Override
	public Optional<Tarea> findById(int id) {
		return tareaRepository.findById(id);
	}

	@Override
	public Tarea create(Tarea tarea, int usuarioId) {
		Usuario usuario = getAuthenticatedUsuario();
		tarea.setUsuario(usuario);
		return tareaRepository.save(tarea);
	}

	@Override
	public Tarea update(int id, Tarea tarea) {
		Usuario usuario = getAuthenticatedUsuario();

		Optional<Tarea> tareaOptional = tareaRepository.findById(id);

		if (tareaOptional.isPresent()) {
			Tarea tareaDb = tareaOptional.orElse(null);

			// verificar si el usuario es propietario de la tarea
			if (tareaDb.getUsuario() == null || tareaDb.getUsuario().getId() != usuario.getId()) {
				throw new RuntimeException("No tienes permiso para actualizar esta tarea");
			}

			tareaDb.setTitulo(tarea.getTitulo());
			tareaDb.setDescripcion(tarea.getDescripcion());
			tareaDb.setFechaFin(tarea.getFechaFin());
			tareaDb.setCompletado(tarea.isCompletado());

			return tareaRepository.save(tareaDb);
		}
		
		return null;
	}

	@Override
	public Optional<Tarea> deleteById(int id) {
		Usuario usuario = getAuthenticatedUsuario();

		Optional<Tarea> tareaOptional = tareaRepository.findById(id);

		if (tareaOptional.isPresent()) {
			Tarea tarea = tareaOptional.orElse(null);

			if (tarea.getUsuario() == null || tarea.getUsuario().getId() != usuario.getId()) {
				throw new RuntimeException("No tienes permiso para eliminar esta tarea");
			}

			tareaRepository.deleteById(id);
		}
		return tareaOptional;
	}
	
	private Usuario getAuthenticatedUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

}
