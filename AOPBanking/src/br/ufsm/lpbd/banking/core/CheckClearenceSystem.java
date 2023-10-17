package br.ufsm.lpbd.banking.core;

import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class CheckClearenceSystem {
	public static void debit(Account account, float amount) throws InsufficientBalanceException {
		account.debit(amount);
	}
	
	public static void credit(Account account, float amount) {
		account.credit(amount);
	}
}
