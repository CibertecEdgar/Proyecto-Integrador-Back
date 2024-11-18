package com.proyecto.integrador.backend.app.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.integrador.backend.app.entity.Role;
import com.proyecto.integrador.backend.app.entity.Usuario;
import com.proyecto.integrador.backend.app.jwt.JwtService;
import com.proyecto.integrador.backend.app.repository.UsuarioRepository;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.getToken(usuario);
		return new AuthResponse(token);
	}

	public AuthResponse register(RegisterRequest request) {
		Optional<Usuario> correoOptional = usuarioRepository.findByCorreo(request.getCorreo());
		Optional<Usuario> dniOptional = usuarioRepository.findByDni(request.getDni());
		Optional<Usuario> username = usuarioRepository.findByUsername(request.getUsername()); 


		if (correoOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
		}

		if (dniOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El dni ya está registrado");
		}
		
		if (username.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El Nombre de usuario ya está registrado");
		}
		
		/* // Validar el tamaño del username
	    if (request.getUsername().length() < 6 || request.getUsername().length() > 30) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El nombre de usuario debe tener entre 6 y 30 caracteres.");
	    }*/

	    // Validar la fortaleza de la contraseña
	    if (!request.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_+=\\[\\]{}|;:'\",.<>?/])[^\\s]{6,30}$")) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La contraseña poco segura, debe tener al menos 6 caracteres, incluir números, letras y símbolos.");
	    }
		

		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.password));
		usuario.setNombre(request.getNombre());
		usuario.setApellido(request.getApellido());
		usuario.setCorreo(request.getCorreo());
		usuario.setDni(request.getDni());
		usuario.setRole(Role.USER);

		try {
			usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el usuario: " + e.getMessage());
		}

		return new AuthResponse(jwtService.getToken(usuario));

	}

	public boolean validateToken(String jwt) {
		try {
			jwtService.getUsernameFromToken(jwt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
