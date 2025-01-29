package com.noliveira.projeto_final;

import com.noliveira.projeto_final.proxy.ClienteProxy;
import com.noliveira.projeto_final.web.controller.ClienteController;
import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;
import com.noliveira.projeto_final.web.controller.dto.ClienteResponseDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(ClienteController.class)
public class ClienteIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteProxy clienteProxy;

    @Test
    public void criarCliente_comDadosValidos_RetornarStatusCode201() throws Exception {
        ClienteCreateDto dto = new ClienteCreateDto("João Pereira", 9876543210L, false, 3500.0f);

        ClienteResponseDto response = new ClienteResponseDto(1L, "João Pereira", 9876543210L, false, 350.0f, 3500.0f);

        when(clienteProxy.createCliente(any(ClienteCreateDto.class)))
            .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));

        mockMvc.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("{\"nome\": \"João Pereira\", \"telefone\": \"9876543210\", \"correntista\": false, \"saldoCc\": 3500.0}"))
                .andExpect(status().isCreated())  
                .andExpect(jsonPath("$.nome").value("João Pereira"))
                .andExpect(jsonPath("$.telefone").value("9876543210"))
                .andExpect(jsonPath("$.correntista").value(false))
                .andExpect(jsonPath("$.saldoCc").value(3500.0));
    }

    @Test
    public void criarCliente_comDadosInvalidos_RetornarStatusCode400() throws Exception {
        
        mockMvc.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("{\"nome\": \"\", \"telefone\": \"9876543210\", \"correntista\": false, \"saldoCc\": 3500.0}"))
                .andExpect(status().isBadRequest());  
    }

    @Test
    public void criarCliente_comDadosConflitantes_RetornarStatusCode409() throws Exception {
       
        ClienteCreateDto dto = new ClienteCreateDto("João Pereira", 9876543210L, false, 3500.0f);

        ClienteResponseDto response = new ClienteResponseDto(1L, "João Pereira", 9876543210L, false, 350.0f, 3500.0f);
        
        when(clienteProxy.createCliente(any(ClienteCreateDto.class)))
            .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT).body(response));

        mockMvc.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("{\"nome\": \"João Pereira\", \"telefone\": \"9876543210\", \"correntista\": false, \"saldoCc\": 3500.0}"))
                .andExpect(status().isConflict());  
    }
    
    @Test
    public void obterClientePorId_clienteExistente_RetornarStatusCode200() throws Exception {
        ClienteResponseDto response = new ClienteResponseDto(1L, "João Pereira", 9876543210L, false, 350.0f, 3500.0f);
        
        when(clienteProxy.getClienteById(1L))
            .thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Pereira"))
                .andExpect(jsonPath("$.telefone").value("9876543210"))
                .andExpect(jsonPath("$.correntista").value(false))
                .andExpect(jsonPath("$.saldoCc").value(3500.0));
    }
    
    @Test
    public void obterClientePorId_clienteNaoExistente_RetornarStatusCode404() throws Exception {
        when(clienteProxy.getClienteById(2L))
            .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/api/v1/clientes/2"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void excluirCliente_porIdExistente_RetornarStatusCode204() throws Exception {
        doNothing().when(clienteProxy).deleteCliente(1L);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void obterScoreCredito_clienteExistente_RetornarStatusCode200() throws Exception {
        Map<String, Float> response = new HashMap<>();
        response.put("scoreCredito", 350.0f);

        when(clienteProxy.getClienteById(1L))
            .thenReturn(ResponseEntity.ok(new ClienteResponseDto(1L, "João Pereira", 9876543210L, false, 350.0f, 3500.0f)));

        mockMvc.perform(get("/api/v1/clientes/score-credito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scoreCredito").value(350.0f));
    }

    @Test
    public void atualizarCliente_clienteExistente_RetornarStatusCode200() throws Exception {
        ClienteCreateDto dto = new ClienteCreateDto("João Pereira", 9876543210L, false, 4000.0f);
        ClienteResponseDto response = new ClienteResponseDto(1L, "João Pereira", 9876543210L, false, 400.0f, 4000.0f);

        when(clienteProxy.updateCliente(1L, dto))
            .thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(put("/api/v1/clientes/1")
                .contentType("application/json")
                .content("{\"nome\": \"João Pereira\", \"telefone\": \"9876543210\", \"correntista\": true, \"saldoCc\": 4000.0}"))
                .andExpect(status().isOk());
    }

}