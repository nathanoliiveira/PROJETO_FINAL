package com.noliveira.projeto_final;

import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;
import com.noliveira.projeto_final.web.controller.dto.ClienteResponseDto;
import com.noliveira.projeto_final.web.exception.ErrorMessage;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIT {
	
	@Autowired
	WebTestClient testClient;
	
	@Test
	public void createCliente_ComDadosValidos_RetornarClienteCriadoComStatus201() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    11987654322L,
			    true,
			    5000.0f
			);

			ClienteResponseDto responseBody = testClient
			    .post()
			    .uri("/api/v1/clientes")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isCreated()
			    .expectBody(ClienteResponseDto.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Jeferson");
			org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
			org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11987654322L);
			org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(500.0f);
			
			clienteCreateDto = new ClienteCreateDto(
				    "Nathan de Oliveira",
				    19988693947L,
				    true,
				    900.0f
				);

				responseBody = testClient
				    .post()
				    .uri("/api/v1/clientes")
				    .contentType(MediaType.APPLICATION_JSON)
				    .bodyValue(clienteCreateDto)
				    .exchange()
				    .expectStatus().isCreated()
				    .expectBody(ClienteResponseDto.class)
				    .returnResult().getResponseBody();

				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Nathan de Oliveira");
				org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
				org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19988693947L);
				org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(100.0f);
				
				clienteCreateDto = new ClienteCreateDto(
					    "Diego",
					    19988693944L,
					    false
					);

					responseBody = testClient
					    .post()
					    .uri("/api/v1/clientes")
					    .contentType(MediaType.APPLICATION_JSON)
					    .bodyValue(clienteCreateDto)
					    .exchange()
					    .expectStatus().isCreated()
					    .expectBody(ClienteResponseDto.class)
					    .returnResult().getResponseBody();

					org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Diego");
					org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isFalse();
					org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19988693944L);
					org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(100.0f);
	}
	
	@Test
	public void createCliente_ComDadosInvalidos_RetornarErrorComStatus400() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    19988177351L,
			    false,
			    5000.0f
			);

			ErrorMessage responseBody = testClient
			    .post()
			    .uri("/api/v1/clientes")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isBadRequest()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			
			clienteCreateDto = new ClienteCreateDto(
				    "Bob",
				    19988177351L,
				    true,
				    5000.0f
				);

				responseBody = testClient
				    .post()
				    .uri("/api/v1/clientes")
				    .contentType(MediaType.APPLICATION_JSON)
				    .bodyValue(clienteCreateDto)
				    .exchange()
				    .expectStatus().isBadRequest()
				    .expectBody(ErrorMessage.class)
				    .returnResult().getResponseBody();

				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void createCliente_ComTelefoneExistente_RetornarErrorComStatus409() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    11987654321L,
			    true,
			    5000.0f
			);

			ErrorMessage responseBody = testClient
			    .post()
			    .uri("/api/v1/clientes")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isEqualTo(409)
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void getCliente_ComIdExistente_RetornarClienteComStatus200() {
		ClienteResponseDto responseBody = testClient
			    .get()
			    .uri("/api/v1/clientes/500")
			    .exchange()
			    .expectStatus().isOk()
			    .expectBody(ClienteResponseDto.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(500);
			org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Pedro Souza");
			org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isFalse();
			org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11543210987L);
			org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(100.0f);
	}
	
	@Test
	public void getCliente_ComIdInexistente_RetornarErrorMessageComStatus404() {
		ErrorMessage responseBody = testClient
			    .get()
			    .uri("/api/v1/clientes/0")
			    .exchange()
			    .expectStatus().isNotFound()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void getScoreCreditoCliente_ComIdExistente_RetornarScoreCreditoComStatus200() {
		Map<String, Float> responseBody = testClient
			    .get()
			    .uri("/api/v1/clientes/score-credito/500")
			    .exchange()
			    .expectStatus().isOk()
			    .expectBody(new ParameterizedTypeReference<Map<String, Float>>() {})
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.get("scoreCredito")).isEqualTo(100f);
	}
	
	@Test
	public void getScoreCreditoCliente_ComIdInexistente_RetornarErrorMessageComStatus404() {
		ErrorMessage responseBody = testClient
			    .get()
			    .uri("/api/v1/clientes/score-credito/0")
			    .exchange()
			    .expectStatus().isNotFound()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void updateCliente_ComDadosValidos_RetornarClienteAtualizadoComStatus200() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Carlos Silva",
			    11987654321L,
			    true,
			    5000.0f
			);

			ClienteResponseDto responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/100")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isOk()
			    .expectBody(ClienteResponseDto.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Carlos Silva");
			org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
			org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11987654321L);
			org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(500.0f);
			
			clienteCreateDto = new ClienteCreateDto(
				    "Carlos da Silva",
				    11987654327L,
				    true,
				    900.0f
				);

				responseBody = testClient
				    .put()
				    .uri("/api/v1/clientes/100")
				    .contentType(MediaType.APPLICATION_JSON)
				    .bodyValue(clienteCreateDto)
				    .exchange()
				    .expectStatus().isOk()
				    .expectBody(ClienteResponseDto.class)
				    .returnResult().getResponseBody();

				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
				org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Carlos da Silva");
				org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isTrue();
				org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(11987654327L);
				org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(100.0f);
				
				clienteCreateDto = new ClienteCreateDto(
					    "Diego",
					    19988693954L,
					    false
					);

					responseBody = testClient
					    .put()
					    .uri("/api/v1/clientes/100")
					    .contentType(MediaType.APPLICATION_JSON)
					    .bodyValue(clienteCreateDto)
					    .exchange()
					    .expectStatus().isOk()
					    .expectBody(ClienteResponseDto.class)
					    .returnResult().getResponseBody();

					org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
					org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Diego");
					org.assertj.core.api.Assertions.assertThat(responseBody.getCorrentista()).isFalse();
					org.assertj.core.api.Assertions.assertThat(responseBody.getTelefone()).isEqualTo(19988693954L);
					org.assertj.core.api.Assertions.assertThat(responseBody.getScoreCredito()).isEqualTo(100.0f);
	}
	
	@Test
	public void updateCliente_ComDadosInvalidos_RetornarErrorComStatus400() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    19988178351L,
			    false,
			    5000.0f
			);

			ErrorMessage responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/100")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isBadRequest()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			
			clienteCreateDto = new ClienteCreateDto(
				    "Bob",
				    19988175351L,
				    true,
				    5000.0f
				);

				responseBody = testClient
				    .put()
				    .uri("/api/v1/clientes/100")
				    .contentType(MediaType.APPLICATION_JSON)
				    .bodyValue(clienteCreateDto)
				    .exchange()
				    .expectStatus().isBadRequest()
				    .expectBody(ErrorMessage.class)
				    .returnResult().getResponseBody();

				org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void updateCliente_ComTelefoneExistente_RetornarErrorComStatus409() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    11543210987L,
			    true,
			    5000.0f
			);

			ErrorMessage responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/100")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isEqualTo(409)
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void updateCliente_ComIdInexistente_RetornarErrorComStatus404() {
		ClienteCreateDto clienteCreateDto = new ClienteCreateDto(
			    "Jeferson",
			    11543210987L,
			    true,
			    5000.0f
			);

			ErrorMessage responseBody = testClient
			    .put()
			    .uri("/api/v1/clientes/0")
			    .contentType(MediaType.APPLICATION_JSON)
			    .bodyValue(clienteCreateDto)
			    .exchange()
			    .expectStatus().isNotFound()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	@Test
	public void deleteCliente_ComIdExistente_RetornarClienteComStatus200() {
		testClient
			    .delete()
			    .uri("/api/v1/clientes/400")
			    .exchange()
			    .expectStatus().isNoContent();
	}
	
	@Test
	public void deleteCliente_ComIdInexistente_RetornarErrorMessageComStatus404() {
		ErrorMessage responseBody = testClient
			    .delete()
			    .uri("/api/v1/clientes/0")
			    .exchange()
			    .expectStatus().isNotFound()
			    .expectBody(ErrorMessage.class)
			    .returnResult().getResponseBody();

			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
}