package com.proyecto.integrador.backend.app.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
		
		
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		
		
		return ResponseEntity.ok(authService.register(request));
	}
	
	@GetMapping("/validate-token")
	public ResponseEntity<Boolean> validate(@RequestParam String jwt){
		boolean isTokenValid = authService.validateToken(jwt);
		return ResponseEntity.ok(isTokenValid);
	}
	
	
	
}
