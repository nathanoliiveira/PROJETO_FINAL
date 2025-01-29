package com.noliveira.projeto_final.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noliveira.projeto_final.entity.Cliente;
import com.noliveira.projeto_final.service.ClienteService;
import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;
import com.noliveira.projeto_final.web.controller.dto.ClienteResponseDto;
import com.noliveira.projeto_final.web.controller.dto.mapper.ClienteMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping
	public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto) {
		Cliente cliente = ClienteMapper.toCliente(dto);
		clienteService.salvar(cliente);
		return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> deleteById(@PathVariable Long id) {
        clienteService.apagarPorId(id);
        return ResponseEntity.noContent().build();
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteCreateDto dto) {
        Cliente cliente = clienteService.editarCliente(id, dto);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }
	
	@GetMapping("/score-credito/{id}")
    public ResponseEntity<Map<String, Float>> getScoreCredito(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        Map<String, Float> response = new HashMap<>();
        response.put("scoreCredito", cliente.getScoreCredito());
        return ResponseEntity.ok(response);
    }
	
}
