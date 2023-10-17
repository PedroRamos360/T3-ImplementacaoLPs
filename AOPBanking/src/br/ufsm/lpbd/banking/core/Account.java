package br.ufsm.lpbd.banking.core;

import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public interface Account {
	public float getTaxes();
	public void registerTax(float amount);
	public int getAccountNumber();
	public void credit(float amount);
	public void debit(float amount) throws InsufficientBalanceException;
	public float getBalance();
	public Customer getCustomer();
}
