package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.OverdraftAccount;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

public aspect TransferFromOverdraft {
    pointcut debitOnAccount(float amount, Account acc) :
        execution(* Account.debit(float)) && args(amount) && target(acc) && !execution(* OverdraftAccount.debit(float));

    void around(float amount, Account acc) throws InsufficientBalanceException : debitOnAccount(amount, acc) {
        try {
            if (acc.getBalance() - amount >= 100) {
                proceed(amount, acc); // A transação é realizada normalmente
            } else {
                Account overdraftAccount = findOverdraftAccount(acc); // Encontre a conta de empréstimo
                if (overdraftAccount != null && overdraftAccount.getBalance() >= amount - 100) {
                    // Faça a transferência da conta de empréstimo para a conta corrente/poupança
                    float transferAmount = amount - 100;
                    overdraftAccount.debit(transferAmount);
                    acc.credit(transferAmount);
                    proceed(100, acc); // O saque de 100 para o limite de segurança é realizado
                } else {
                    throw new InsufficientBalanceException("Saldo insuficiente, incluindo o limite de segurança.");
                }
            }
        } catch (InsufficientBalanceException e) {
        	throw new InsufficientBalanceException("Saldo insuficiente na conta de empréstimo.");
        }
    }

    private Account findOverdraftAccount(Account account) {
    	if (account instanceof OverdraftAccount) {
            return (OverdraftAccount) account;
        }
        return null; // Não há conta de empréstimo associada
    }
}
