package com.proyecto.integrador.backend.app.auth;

import jakarta.validation.constraints.NotEmpty;

public class RegisterRequest {

	@NotEmpty(message = "no debe estar vacío")
	String username;
	
	@NotEmpty(message = "no debe estar vacío")
	String password;
	
	@NotEmpty(message = "no debe estar vacío")
	String nombre;
	
	@NotEmpty(message = "no debe estar vacío")
	String apellido;
	
	@NotEmpty(message = "no debe estar vacío")
	String correo;
	
	@NotEmpty(message = "no debe estar vacío")
	String dni;
	
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
		
}
