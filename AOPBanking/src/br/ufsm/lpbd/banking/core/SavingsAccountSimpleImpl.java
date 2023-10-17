package br.ufsm.lpbd.banking.core;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class SavingsAccountSimpleImpl extends AccountSimpleImpl implements SavingsAccount {

	public SavingsAccountSimpleImpl(int accountNumber, Customer customer) {
		super(accountNumber, customer);
	}

	public String toString() {
		return String.format("SavingsAccount (%d)", getAccountNumber());
	}
}
