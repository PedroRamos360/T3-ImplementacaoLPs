package br.ufsm.lpbd.banking.aspect;

// exercício 5.1

public aspect NotifyWhenThresholdExceeded {
    before(float amount) : execution(void br.ufsm.lpbd.banking.core.Account.debit(float)) && args(amount) && if(amount > 10000) {
       System.out.println("Saque maior que R$ 10.000 na conta " + thisJoinPoint.getTarget() + ": R$" + amount);
   }
}
