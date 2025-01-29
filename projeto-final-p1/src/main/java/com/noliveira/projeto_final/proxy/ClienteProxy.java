package com.noliveira.projeto_final.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;
import com.noliveira.projeto_final.web.controller.dto.ClienteResponseDto;

import jakarta.validation.Valid;

@FeignClient(name = "clienteService", url= "http://localhost:8080/api/v1/clientes")
public interface ClienteProxy {

	@PostMapping
	ResponseEntity<ClienteResponseDto> createCliente(@RequestBody @Valid ClienteCreateDto dto);

	@GetMapping("/{id}")
	ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable Long id);

	@DeleteMapping("/{id}")
	void deleteCliente(@PathVariable Long id);

	@PutMapping("/{id}")
	ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteCreateDto dto);

}
