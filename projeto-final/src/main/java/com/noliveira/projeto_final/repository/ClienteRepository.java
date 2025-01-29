package com.noliveira.projeto_final.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noliveira.projeto_final.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByTelefone(Long telefone);

}
