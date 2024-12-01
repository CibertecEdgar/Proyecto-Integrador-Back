package com.proyecto.integrador.backend.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyecto.integrador.backend.app.entity.Proyecto;
import com.proyecto.integrador.backend.app.entity.Usuario;
import com.proyecto.integrador.backend.app.repository.ProyectoRepository;
import com.proyecto.integrador.backend.app.repository.UsuarioRepository;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	 @Autowired
	 private ProyectoRepository proyectoRepository;

	 @Autowired
	 private UsuarioRepository usuarioRepository;
	
	 @Override
	    public List<Proyecto> findAll() {
	        return proyectoRepository.findAll();
	    }

	    @Override
	    public Optional<Proyecto> findById(int id) {
	        return proyectoRepository.findById(id);
	    }

		@Override
		public List<Proyecto> findAllByIdUser(int usuarioId) {
			return proyectoRepository.findByUsuarioId(usuarioId);
		}

	    @Override
	    public Proyecto create(Proyecto proyecto, int usuarioId) {
			Usuario usuario = getAuthenticatedUsuario();
			
	        // Validar que no exista un proyecto con el mismo nombre
	        Optional<Proyecto> existingProyecto = proyectoRepository.findByTitulo(proyecto.getTitulo());
	        if (existingProyecto.isPresent()) {
	            throw new RuntimeException("Ya existe un proyecto con el titulo: " + proyecto.getTitulo());
	        }

			proyecto.setUsuario(usuario);
	        return proyectoRepository.save(proyecto);
	    }

	    @Override
	    public Proyecto update(int id, Proyecto proyecto) {
	        Optional<Proyecto> existingProyecto = proyectoRepository.findById(id);
	        if (existingProyecto.isPresent()) {
	            Proyecto proyectoDb = existingProyecto.get();

	            // Actualizar datos del proyecto
	            proyectoDb.setTitulo(proyecto.getTitulo());

	            return proyectoRepository.save(proyectoDb);
	        }
	        throw new RuntimeException("Proyecto con ID " + id + " no encontrado");
	    }

	    @Override
	    public Optional<Proyecto> deleteById(int id) {
	        Optional<Proyecto> existingProyecto = proyectoRepository.findById(id);
	        if (existingProyecto.isPresent()) {
	            proyectoRepository.deleteById(id);
	        }
	        return existingProyecto;
	    }

		private Usuario getAuthenticatedUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		}

}
