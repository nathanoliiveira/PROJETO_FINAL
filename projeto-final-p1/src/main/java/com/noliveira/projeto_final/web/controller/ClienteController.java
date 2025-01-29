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

import com.noliveira.projeto_final.proxy.ClienteProxy;
import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;
import com.noliveira.projeto_final.web.controller.dto.ClienteResponseDto;
import com.noliveira.projeto_final.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Banco JAVER API")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	private final ClienteProxy clienteProxy;
	
	public ClienteController(ClienteProxy clienteProxy) {
		this.clienteProxy = clienteProxy;
	}

	@Operation(summary = "Criar um novo cliente",
            description = "Recurso para cadastrar um novo cliente ao banco. ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Recurso não processado por falta de dados ou dados inválidos",
                    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Recurso não pode ser processado pois esse telefone já foi cadastrado para outro cliente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
	@PostMapping
	public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto) {
		return clienteProxy.createCliente(dto);
	}
	
	@Operation(summary = "Obter cliente por ID",
            description = "Recupera um cliente a partir do seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
	@GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        return clienteProxy.getClienteById(id);
    }
	
	@Operation(summary = "Excluir cliente por ID",
            description = "Exclui um cliente com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
	@DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> deleteById(@PathVariable Long id) {
        clienteProxy.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
	
	
	@Operation(summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Recurso não pode ser processado pois esse telefone já foi cadastrado para outro cliente",
                    		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
	@PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteCreateDto dto) {
        return clienteProxy.updateCliente(id, dto);
    }
	
	
	@Operation(summary = "Obter score de crédito do cliente",
            description = "Recupera o score de crédito de um cliente com base no seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Score de crédito recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
	@GetMapping("/score-credito/{id}")
	public ResponseEntity<Map<String, Float>> getScoreCredito(@PathVariable Long id) {
	    ResponseEntity<ClienteResponseDto> cliente = clienteProxy.getClienteById(id);

	    Float scoreCredito = cliente.getBody().getScoreCredito();
	    
	    Map<String, Float> response = new HashMap<>();
	    response.put("scoreCredito", scoreCredito);

	    return ResponseEntity.ok(response);
	}

	
}
