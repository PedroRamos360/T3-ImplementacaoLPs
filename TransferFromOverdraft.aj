package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.OverdraftAccount;
import br.ufsm.lpbd.banking.core.Customer;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

import java.util.List;

// Exercícios 5.4 e 5.5

public aspect TransferFromOverdraft {
    pointcut debitOnAccount(float amount, Account acc) :
        execution(* Account.debit(float)) && args(amount) && target(acc) && !execution(* OverdraftAccount.debit(float));

    void around(float amount, Account acc) throws InsufficientBalanceException : debitOnAccount(amount, acc) {
            if (acc.getBalance() - amount >= 100) {
            	System.out.println("Fez a transferência normal");
                proceed(amount, acc); // A transação é realizada normalmente
            } else {
            	System.out.println("Fez a transferência na conta de empréstimo");
                Account overdraftAccount = findOverdraftAccount(acc); // Encontre a conta de empréstimo
                float transferAmount = (acc.getBalance() - amount) * (-1) + 100;
                if (overdraftAccount != null) {
                	if (overdraftAccount.getBalance() < transferAmount) {
                		throw new InsufficientBalanceException("Saldo insuficiente na conta de empréstimo");
                	}
                    
                    overdraftAccount.debit(transferAmount);
                    acc.registerTax(transferAmount * 0.01f); // exercício 5.5
                    acc.credit(transferAmount);
                    proceed(amount, acc); 
                } 
            }
    }

    private Account findOverdraftAccount(Account account) {
    	Customer customer = account.getCustomer();
    	List<Account> overdrafts = customer.getOverdraftAccounts();
    	return overdrafts.get(0);
    }
}
