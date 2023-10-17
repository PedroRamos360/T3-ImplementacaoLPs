package br.ufsm.lpbd.banking.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufsm.lpbd.banking.core.Account;
import br.ufsm.lpbd.banking.core.Customer;

/*
 * @author Cristiano de Favari
 * Universidade Federal de Santa Maria
 * 
 */ 
public class BalanceReport {
	public static void print (Customer customer) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(String.format("========== Balance Report - %s ==========", dateFormat.format(date)));
		System.out.println(String.format("Customer : %s ", customer.getName()));
		for (Account account : customer.getAccounts())
			System.out.println(String.format("Account  : %s, Balance : %f, Taxes : %f", account.toString(), account.getBalance(), account.getTaxes()));
		System.out.println(String.format("==== Overdraft Accounts ==="));
		for (Account account : customer.getOverdraftAccounts())
			System.out.println(String.format("Account  : %s Limit : %f ", account.toString(), account.getBalance()));
		System.out.println(String.format("========== End of report =========="));
	}
}
