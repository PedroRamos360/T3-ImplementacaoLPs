package br.ufsm.lpbd.banking.core;

import java.util.ArrayList;
import java.util.List;
/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class Customer {
	private String name;
	private List<Account> accounts = new ArrayList<Account>();
	private List<Account> overdraftAccounts = new ArrayList<Account>();
	
	public Customer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void addOverdraftAccount(Account overdraftAccount) {
		this.overdraftAccounts.add(overdraftAccount);
	}
	
	public List<Account> getOverdraftAccounts() {
		return overdraftAccounts;
	}
}
