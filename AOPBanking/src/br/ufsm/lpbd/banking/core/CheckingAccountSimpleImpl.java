package br.ufsm.lpbd.banking.core;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class CheckingAccountSimpleImpl extends AccountSimpleImpl implements CheckingAccount {

	public CheckingAccountSimpleImpl(int accountNumber, Customer customer) {
		super(accountNumber, customer);
	}

	public String toString() {
		return String.format("CheckingAccount (%d)", getAccountNumber());
	}
}
