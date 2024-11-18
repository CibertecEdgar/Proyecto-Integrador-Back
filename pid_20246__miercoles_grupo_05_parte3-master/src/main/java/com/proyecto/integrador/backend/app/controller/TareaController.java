package com.proyecto.integrador.backend.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.integrador.backend.app.entity.Tarea;
import com.proyecto.integrador.backend.app.entity.Usuario;
import com.proyecto.integrador.backend.app.repository.UsuarioRepository;
import com.proyecto.integrador.backend.app.service.TareaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
	
	@Autowired
	private TareaService tareaService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*CRUD*/
	@GetMapping
    public ResponseEntity<List<Tarea>> getAllTasks() {
        Usuario usuario = getAuthenticatedUsuario();

        List<Tarea> tasks = tareaService.findAllById(usuario.getId());
        return ResponseEntity.ok(tasks);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {
        Usuario usuario = getAuthenticatedUsuario();
        Optional<Tarea> tasks = tareaService.findById(id);
        
        if (tasks.isPresent() && tasks.get().getUsuario().getId() == (usuario.getId())) {
        	return ResponseEntity.ok(tasks.orElseThrow(() -> new RuntimeException("Tarea no encontrada")));
        } else {
            return ResponseEntity.badRequest().body("Tarea no encontrada");
        }
        
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody Tarea tarea, BindingResult result) {
    	if(result.hasFieldErrors()) {
			return validation(result);
		}
    	
    	Usuario usuario = getAuthenticatedUsuario();

        Tarea nuevaTarea = tareaService.create(tarea, usuario.getId());
        return ResponseEntity.ok(nuevaTarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody Tarea tarea, BindingResult result, @PathVariable int id) {
    	if(result.hasFieldErrors()) {
			return validation(result);
		}
    	
    	Usuario usuario = getAuthenticatedUsuario();

        Tarea tareaActualizada = tareaService.update(id, tarea);

        if (tareaActualizada != null) {
            return ResponseEntity.ok(tareaActualizada);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        Usuario usuario = getAuthenticatedUsuario();

        Optional<Tarea> tareaOptional = tareaService.deleteById(id);

        if (tareaOptional.isPresent() && tareaOptional.get().getUsuario().getId() == (usuario.getId())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
    
    /*OBTENER EL USUARIO AUTENTICADO DEL SECURITY CONTEXT*/
    private Usuario getAuthenticatedUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    /*VALIDACION */
    private ResponseEntity<?> validation(BindingResult result) {
		Map<String, String> errors = new HashMap<>();
		
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errors);
	}
	
}
