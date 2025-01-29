package com.noliveira.projeto_final.web.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClienteCreateDto {
	
	@NotBlank
	@Size(min = 5, max = 150)
	private String nome;
	
	@NotNull
	//@Min(10)
	//@Max(11)
	private Long telefone;
	
	@NotNull
	private Boolean correntista;
	
	private Float saldoCc;
	
	
	
	public ClienteCreateDto(@NotBlank @Size(min = 5, max = 150) String nome, @NotNull @Min(10) @Max(11) Long telefone,
			@NotNull Boolean correntista, Float saldoCc) {
		this.nome = nome;
		this.telefone = telefone;
		this.correntista = correntista;
		this.saldoCc = saldoCc;
	}
	



	public ClienteCreateDto() {}




	public ClienteCreateDto(@NotBlank @Size(min = 5, max = 150) String nome, @NotNull Long telefone, @NotNull boolean correntista) {
		this.nome = nome;
		this.telefone = telefone;
		this.correntista = correntista;
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



	public Float getSaldoCc() {
		return saldoCc;
	}



	public void setSaldoCc(Float saldoCc) {
		this.saldoCc = saldoCc;
	}

}
