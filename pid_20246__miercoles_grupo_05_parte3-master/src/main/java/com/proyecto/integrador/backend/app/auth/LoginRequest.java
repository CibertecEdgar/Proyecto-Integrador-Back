package com.proyecto.integrador.backend.app.auth;

import jakarta.validation.constraints.NotEmpty;

public class LoginRequest {
	
	@NotEmpty(message = "no debe estar vacío")
	String username;
	
	@NotEmpty(message = "no debe estar vacío")
	String password;
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public LoginRequest() {
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
		
}
