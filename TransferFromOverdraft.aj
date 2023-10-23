package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.OverdraftAccount;
import br.ufsm.lpbd.banking.core.Customer;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

import java.util.List;

public aspect TransferFromOverdraft {
    pointcut debitOnAccount(float amount, Account acc) :
        execution(* Account.debit(float)) && args(amount) && target(acc) && !execution(* OverdraftAccount.debit(float));

    void around(float amount, Account acc) throws InsufficientBalanceException : debitOnAccount(amount, acc) {
    	System.out.println("executou");
        try {
            if (acc.getBalance() - amount >= 100) {
            	System.out.println("Fez a transferência normal");
                proceed(amount, acc); // A transação é realizada normalmente
            } else {
                Account overdraftAccount = findOverdraftAccount(acc); // Encontre a conta de empréstimo
                if (overdraftAccount != null) {
            	    System.out.println("Fez a transferência na conta de empréstimo");
                    // Faça a transferência da conta de empréstimo para a conta corrente/poupança
                    float transferAmount = (acc.getBalance() - amount) * (-1) + 100;
                    overdraftAccount.debit(transferAmount);
                    acc.credit(transferAmount);
                    proceed(amount, acc); // Procede pra fazer a transação depois de pegar o dinheiro
                }
            }
        } catch (InsufficientBalanceException e) {
        	System.out.println(e);
        	throw new InsufficientBalanceException("Saldo insuficiente na conta de empréstimo.");
        }
    }

    private Account findOverdraftAccount(Account account) {
    	Customer customer = account.getCustomer();
    	List<Account> overdrafts = customer.getOverdraftAccounts();
    	return overdrafts.get(0);
    }
}
