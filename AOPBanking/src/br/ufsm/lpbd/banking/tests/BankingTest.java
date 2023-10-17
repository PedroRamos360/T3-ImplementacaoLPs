package br.ufsm.lpbd.banking.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.CheckClearenceSystem;
import br.ufsm.lpbd.banking.core.CheckingAccountSimpleImpl;
import br.ufsm.lpbd.banking.core.Customer;
import br.ufsm.lpbd.banking.core.OverdraftAccountSimpleImpl;
import br.ufsm.lpbd.banking.core.SavingsAccountSimpleImpl;
import br.ufsm.lpbd.banking.exception.InsufficientBalanceException;

/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 * Atenção : Os testes somente passam após os aspectos da lista serem implementados
 *           Execute cada um dos testes a medida que os aspectos forem implementados
 *           retirando os comentários @Test dos métodos. 
 *  
 */
public class BankingTest {
	private Customer c1;
	private Customer c2;
	private Customer c3;
	private Customer c4;
	
	@Before public void setUp() {
		c1 = new Customer("Eratóstenes de Cirenia");
		c2 = new Customer("Plutarco de Queroneia");
		c3 = new Customer("Tales de Mileto");
		c4 = new Customer("Anaxágoras de Clazômenas");
	}
	
	// Fake test - somente para não subir exceção dizendo que não existem testes para serem executados 
	@Test public void smile() {
		assertTrue(true);
	}
	
	/**
	 * Teste Caminho Azul : Sem violação de limites de saldo.
	 */
	/*@Test*/ public void bluePath() {
		// Cria contas
		Account savingsAccount = new SavingsAccountSimpleImpl(100, c1);
		Account checkingAccount = new CheckingAccountSimpleImpl(101, c1);
		Account overdraftAccount = new OverdraftAccountSimpleImpl(666, c1);
		
		// Associa ao cliente
		c1.addAccount(savingsAccount);
		c1.addAccount(checkingAccount);
		c1.addOverdraftAccount(overdraftAccount);
		
		// Faz alguns depósitos
		savingsAccount.credit(10000);
		assertTrue(savingsAccount.getBalance() == 10000);
		checkingAccount.credit(15000);
		assertTrue(checkingAccount.getBalance() == 15000);
		// Configura o limite de crédito
		overdraftAccount.credit(1000);
		assertTrue(overdraftAccount.getBalance() == 1000);		
		try {
			// Saque ATM na conta corrente
			checkingAccount.debit(5000);
			assertTrue(checkingAccount.getBalance() == 10000);
			checkingAccount.debit(5000);
			assertTrue(checkingAccount.getBalance() == 5000);
			checkingAccount.debit(4900); // Deixar 100 na conta como limite de segurança.
			assertTrue(checkingAccount.getBalance() == 100);
			assertTrue(overdraftAccount.getBalance() == 1000);
			assertTrue(checkingAccount.getTaxes() == 0);

			// Saque ATM na conta poupança
			savingsAccount.debit(1000);
			assertTrue(savingsAccount.getBalance() == 9000);
			savingsAccount.debit(500);
			assertTrue(savingsAccount.getBalance() == 8500);
			assertTrue(overdraftAccount.getBalance() == 1000);
			assertTrue(checkingAccount.getTaxes() == 0);
			
			// Compensa 2 cheques para conta corrente
			//System.out.println("checkingAccountBalanceBefore : " + checkingAccount.getAccountNumber() + " " + checkingAccount.getBalance());
			CheckClearenceSystem.debit(checkingAccount, 400);
			//System.out.println("checkingAccountBalanceAfter : " + checkingAccount.getAccountNumber() + " " + checkingAccount.getBalance());
			assertTrue(checkingAccount.getBalance() == 100);
			assertTrue(overdraftAccount.getBalance() == 600);
			assertTrue(checkingAccount.getTaxes() == 4);
			
			CheckClearenceSystem.debit(checkingAccount, 600);
			assertTrue(checkingAccount.getBalance() == 100);
			assertTrue(overdraftAccount.getBalance() == 0);
			assertTrue(checkingAccount.getTaxes() == 10);
			
			CheckClearenceSystem.debit(savingsAccount, 8000);
			CheckClearenceSystem.debit(savingsAccount, 400);
			assertTrue(savingsAccount.getBalance() == 100);
			assertTrue(overdraftAccount.getBalance() == 0);
			assertTrue(savingsAccount.getTaxes() == 0);
			
		} catch (InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}		
	}

	/**
	 * Teste para testar empréstimos acima do permitido
	 */
	/*@Test (expected= InsufficientBalanceException.class)*/ public void ExceedLoan() throws InsufficientBalanceException {
		Account savingsAccount = new SavingsAccountSimpleImpl(100, c2);
		Account checkingAccount = new CheckingAccountSimpleImpl(101, c2);
		Account overdraftAccount = new OverdraftAccountSimpleImpl(666, c2);
		c2.addAccount(savingsAccount);
		c2.addAccount(checkingAccount);
		c2.addOverdraftAccount(overdraftAccount);

		checkingAccount.credit(15000);
		savingsAccount.credit(10000);
		overdraftAccount.credit(1000);
		
		checkingAccount.debit(14000);
		assertTrue(checkingAccount.getBalance() == 1000);
		checkingAccount.debit(2000); // sinaliza InsufficientBalanceException
	}

	/*@Test (expected= InsufficientBalanceException.class)*/ public void safetyBalanceRule() throws InsufficientBalanceException {
		Account savingsAccount = new SavingsAccountSimpleImpl(100, c3);
		Account checkingAccount = new CheckingAccountSimpleImpl(101, c3);
		Account overdraftAccount = new OverdraftAccountSimpleImpl(666, c3);
		c3.addAccount(savingsAccount);
		c3.addAccount(checkingAccount);
		c3.addOverdraftAccount(overdraftAccount);
		checkingAccount.credit(15000);
		savingsAccount.credit(10000);
		overdraftAccount.credit(0);
		
		checkingAccount.debit(14000);
		assertTrue(checkingAccount.getBalance() == 1000);
		checkingAccount.debit(950); 
		// sinaliza InsufficientBalanceException ja que não há saldo de empréstimo e o limite de segurança é 100,00 		
		
	}

	/*@Test*/ public void taxRule() throws InsufficientBalanceException {
		Account savingsAccount = new SavingsAccountSimpleImpl(104, c4);
		Account checkingAccount = new CheckingAccountSimpleImpl(204, c4);
		Account overdraftAccount = new OverdraftAccountSimpleImpl(304, c4);
		c4.addAccount(savingsAccount);
		c4.addAccount(checkingAccount);
		c4.addOverdraftAccount(overdraftAccount);
		checkingAccount.credit(15000);
		savingsAccount.credit(10000);
		overdraftAccount.credit(1000);
		
		// Descontou cheque maior que saldo da conta
		// Emprestou 500(excedente) + 100 de limite de segurança
		CheckClearenceSystem.debit(savingsAccount, 10500);
		// Saldo fica em 100
		assertTrue(savingsAccount.getBalance() == 100); 
		// Limite fica em 400
		assertTrue(overdraftAccount.getBalance() == 400);
		// Taxas de 1% de 600 = 6
		assertTrue(savingsAccount.getTaxes() == 6); 
	}
}
