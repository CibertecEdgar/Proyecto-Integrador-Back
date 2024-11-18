package com.proyecto.integrador.backend.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.integrador.backend.app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	// Para buscar el usuario
	public Optional<Usuario> findByUsername(String username);
	
	// Para buscar el correo
	public Optional<Usuario> findByCorreo(String correo);
	
	// Para buscar el dni
	public Optional<Usuario> findByDni(String dni);
}
