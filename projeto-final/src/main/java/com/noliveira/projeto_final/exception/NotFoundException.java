package com.noliveira.projeto_final.exception;

public class NotFoundException extends RuntimeException{
	
	private String recurso;
	private String codigo;
	
	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String recurso, String codigo) {
		this.recurso = recurso;
		this.codigo = codigo;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
}
