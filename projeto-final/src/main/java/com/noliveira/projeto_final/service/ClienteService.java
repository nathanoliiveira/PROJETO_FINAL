package com.noliveira.projeto_final.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noliveira.projeto_final.entity.Cliente;
import com.noliveira.projeto_final.exception.NotFoundException;
import com.noliveira.projeto_final.exception.TelefoneUniqueViolationException;
import com.noliveira.projeto_final.repository.ClienteRepository;
import com.noliveira.projeto_final.util.ClienteUtil;
import com.noliveira.projeto_final.web.controller.dto.ClienteCreateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Cliente salvar(Cliente cliente) {
		cliente = ClienteUtil.verificaCliente(cliente);		
		try {
			return clienteRepository.save(cliente);
		} catch (DataIntegrityViolationException ex) {
			throw new TelefoneUniqueViolationException(String.format("Cliente com número de telefone '%s' não pode ser cadastrado, pois já existe no sistema", cliente.getTelefone()));
		}
	}

	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
		);
	}

	@Transactional
	public void apagarPorId(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(
				() -> new NotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
		);
		clienteRepository.deleteById(id);
	}
	
	@Transactional
    public Cliente editarCliente(Long id, ClienteCreateDto dto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
        		() -> new NotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );
        
        Optional<Cliente> clienteExistente = clienteRepository.findByTelefone(dto.getTelefone());
        if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(cliente.getId())) {
            throw new TelefoneUniqueViolationException(String.format("Número não pode ser atualizado para '%s' pois esse telefone já existe no sistema de outro cliente", dto.getTelefone()));
        }

        try {
            cliente.setNome(dto.getNome());
            cliente.setTelefone(dto.getTelefone());
            cliente.setCorrentista(dto.getCorrentista());
            cliente.setSaldoCc(dto.getSaldoCc());
            
            cliente = ClienteUtil.verificaCliente(cliente);
            
            return clienteRepository.save(cliente);
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao atualizar o cliente", ex);
        }
    }

}