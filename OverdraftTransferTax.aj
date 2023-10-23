package br.ufsm.lpbd.banking.aspect;
import br.ufsm.lpbd.banking.core.Account;

public aspect OverdraftTransferTax {
    pointcut transferFromOverdraft(float amount, Account source, Account target) :
        execution(void TransferFromOverdraft.transfer(float)) && args(amount, source, target);
    	// Usa o aspecto do exercício anterior

    before(float amount, Account source, Account target) : transferFromOverdraft(amount, source, target) {
        float taxAmount = amount * 0.01f; // 1% de taxa sobre o valor transferido
        source.registerTax(taxAmount); // Registre a taxa na conta de origem (empréstimo)
    }
}
