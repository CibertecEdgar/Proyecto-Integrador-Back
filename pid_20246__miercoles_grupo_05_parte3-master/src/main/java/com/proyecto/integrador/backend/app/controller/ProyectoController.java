package com.proyecto.integrador.backend.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.integrador.backend.app.entity.Proyecto;
import com.proyecto.integrador.backend.app.entity.Usuario;
import com.proyecto.integrador.backend.app.exception.dto.ProyectoAlreadyExistsException;
import com.proyecto.integrador.backend.app.repository.UsuarioRepository;
import com.proyecto.integrador.backend.app.service.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

	@Autowired
    private ProyectoService proyectoService;
	
    @Autowired
    private UsuarioRepository usuarioRepository;

	// @GetMapping
    // public ResponseEntity<List<Proyecto>> getAllProyectos() {
    //     List<Proyecto> proyectos = proyectoService.findAll();
    //     return ResponseEntity.ok(proyectos);
    // }
    
    @GetMapping
    public ResponseEntity<List<Proyecto>> getAllTasks() {
        Usuario usuario = getAuthenticatedUsuario();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Proyecto> proyectos = proyectoService.findAllByIdUser(usuario.getId());
        return ResponseEntity.ok(proyectos);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getProyectoById(@PathVariable int id) {
        Usuario usuario = getAuthenticatedUsuario();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        Optional<Proyecto> proyecto = proyectoService.findById(id);
        if (proyecto.isPresent() && proyecto.get().getUsuario().getId() == usuario.getId()) {
            return ResponseEntity.ok(proyecto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado o no autorizado");
        }
    }

	@PostMapping
    public ResponseEntity<?> createProyecto(@RequestBody Proyecto proyecto) {
        Usuario usuario = getAuthenticatedUsuario();

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }
    
        try {
            Proyecto nuevoProyecto = proyectoService.create(proyecto, usuario.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto);
        } catch (ProyectoAlreadyExistsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("detalles", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<?> updateProyecto(@PathVariable int id, @RequestBody Proyecto proyecto) {
        try {
            Proyecto proyectoActualizado = proyectoService.update(id, proyecto);
            return ResponseEntity.ok(proyectoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proyecto no encontrado");
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable int id) {
        Usuario usuario = getAuthenticatedUsuario();
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    
        Optional<Proyecto> proyectoOptional = proyectoService.findById(id);
        if (proyectoOptional.isPresent() && proyectoOptional.get().getUsuario().getId() == usuario.getId()) {
            proyectoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
	
    private Usuario getAuthenticatedUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}
