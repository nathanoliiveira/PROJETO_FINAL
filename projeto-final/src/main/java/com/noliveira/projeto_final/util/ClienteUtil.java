package com.noliveira.projeto_final.util;

import com.noliveira.projeto_final.entity.Cliente;
import com.noliveira.projeto_final.exception.NotCorrentistaException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteUtil {
	
	private final static float CREDITO_PADRAO = 100F;
	
	public static Cliente verificaCliente(Cliente cliente) {
		if (!cliente.getCorrentista() && cliente.getSaldoCc() != null) {
	        throw new NotCorrentistaException("Impossível salvar saldo de conta corrente se o cliente não é correntista.");
	    }
		
		if (!cliente.getCorrentista()) {
			cliente.setSaldoCc(null);
			cliente.setScoreCredito(CREDITO_PADRAO);
		}else if(cliente.getCorrentista() && cliente.getSaldoCc() <= 1000) {
			cliente.setScoreCredito(CREDITO_PADRAO);
		} else if(cliente.getSaldoCc() > 1000) {
			cliente.setScoreCredito((float) (cliente.getSaldoCc() * 0.1));
		}
		
		return cliente;
	}
}
