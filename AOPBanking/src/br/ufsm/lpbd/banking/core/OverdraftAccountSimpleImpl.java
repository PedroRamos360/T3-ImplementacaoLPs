package br.ufsm.lpbd.banking.core;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class OverdraftAccountSimpleImpl extends AccountSimpleImpl implements OverdraftAccount {

	public OverdraftAccountSimpleImpl(int accountNumber, Customer customer) {
		super(accountNumber, customer);
	}

	public String toString() {
		return String.format("OverdraftAccount (%d)", getAccountNumber());
	}
}
