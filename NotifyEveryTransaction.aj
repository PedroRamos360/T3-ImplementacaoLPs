package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;

// Exercício 5.2 

public aspect NotifyEveryTransaction {
   pointcut callTransaction(float amount, Account acc) :
	   (execution(* Account.credit(float)) || execution(* Account.debit(float))) && args(amount) && target(acc);
	   
   before (float amount, Account acc) : callTransaction(amount, acc) {
	   System.out.println("Conta " + acc.getAccountNumber() + " executou uma transferência de " +
   amount + " com a operação " + thisJoinPoint.getSignature().getName() + " e agora está com o saldo total de " + acc.getBalance());
   }
}
