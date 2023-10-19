package br.ufsm.lpbd.banking.core;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// Exercício 5.2

@Aspect
public class TransactionLoggingAspect {

    @Before("execution(* Account.debit(double)) && args(amount)")
    public void beforeDebitTransaction(JoinPoint joinPoint, double amount) {
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        System.out.println("Antes da transação - Método: " + methodName + ", Conta: " + target + ", Quantia: " + amount);
    }

    @After("execution(* Account.debit(double))")
    public void afterDebitTransaction(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        double balance = ((Account) target).getBalance(); // Supondo que há um método getBalance na classe Account
        System.out.println("Após a transação - Saldo da Conta: " + balance);
    }

    @Before("execution(* Account.credit(double)) && args(amount)")
    public void beforeCreditTransaction(JoinPoint joinPoint, double amount) {
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        System.out.println("Antes da transação - Método: " + methodName + ", Conta: " + target + ", Quantia: " + amount);
    }

    @After("execution(* Account.credit(double))")
    public void afterCreditTransaction(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        double balance = ((Account) target).getBalance(); // Supondo que há um método getBalance na classe Account
        System.out.println("Após a transação - Saldo da Conta: " + balance);
    }
}

