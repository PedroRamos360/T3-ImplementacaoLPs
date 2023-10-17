package br.ufsm.lpbd.banking.core;

import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public abstract class AccountSimpleImpl implements Account {
	private float taxes;
	private int accountNumber;
	private float balance;
	private Customer customer;
	
	public AccountSimpleImpl (int accountNumber, Customer customer) {
		this.accountNumber = accountNumber;
		this.customer = customer;
	}
	
	@Override
	public int getAccountNumber() {
		return accountNumber;
	}

	@Override
	public void credit(float amount) {
		balance = balance + amount;
	}

	@Override
	public void debit(float amount) throws InsufficientBalanceException {
		if (balance < amount) {
			throw new InsufficientBalanceException("Total balance is not sufficient");
		} else {
			balance = balance - amount;
		}
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public float getTaxes() {
		return taxes;
	}

	@Override
	public void registerTax(float value) {
		taxes += value;
	}
}
