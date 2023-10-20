package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

// exercício 5.3 testado, parece que funciona

public aspect BlockDebitBelowLimit {
	pointcut callAccountTransaction(float amount, Account acc) : 
		execution(* Account.debit(float)) && args(amount) && target(acc);
	
	before (float amount, Account acc) throws InsufficientBalanceException : callAccountTransaction(amount, acc) {
		if (acc.getBalance() - amount < 100) {
			throw new InsufficientBalanceException("Limite de segurança atigido");
		}
	}
}
