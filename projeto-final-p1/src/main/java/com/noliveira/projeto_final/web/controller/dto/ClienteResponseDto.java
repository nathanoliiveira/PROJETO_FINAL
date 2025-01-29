package com.noliveira.projeto_final.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ClienteResponseDto {

	private Long id;
	private String nome;
	private Long telefone;
	private Boolean correntista;
	private Float scoreCredito;
	private Float saldoCc;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getTelefone() {
		return telefone;
	}
	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
	public Boolean getCorrentista() {
		return correntista;
	}
	public void setCorrentista(Boolean correntista) {
		this.correntista = correntista;
	}
	public Float getScoreCredito() {
		return scoreCredito;
	}
	public void setScoreCredito(Float scoreCredito) {
		this.scoreCredito = scoreCredito;
	}
	public Float getSaldoCc() {
		return saldoCc;
	}
	public void setSaldoCc(Float saldoCc) {
		this.saldoCc = saldoCc;
	}
	public ClienteResponseDto(Long id, String nome, Long telefone, Boolean correntista, Float scoreCredito,
			Float saldoCc) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.correntista = correntista;
		this.scoreCredito = scoreCredito;
		this.saldoCc = saldoCc;
	}
	public ClienteResponseDto() {
		super();
	}	
	
}
