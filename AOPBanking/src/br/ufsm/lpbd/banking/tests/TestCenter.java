package br.ufsm.lpbd.banking.tests;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class TestCenter {
	public static void main (String[] args) {
		 JUnitCore junit = new JUnitCore();
		 junit.addListener(new TextListener(System.out));
		 junit.run(BankingTest.class);
	}
}
