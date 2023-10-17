package br.ufsm.lpbd.banking.exception;

/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
@SuppressWarnings("serial")
public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String arg0) {
		super(arg0);
	}
}
