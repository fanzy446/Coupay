package com.subsystem.bankSystem;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.domain.BankAccount;
import com.externalSystemInteface.IBankSystem;
import com.util.HqlUtil;


public class MyBankSystem implements IBankSystem{
	
	public MyBankSystem(){
		
	}
	
	/**
	 * ��֤�˻��Ƿ����
	 */
	@Override
	public boolean checkAccount(String cardNumber) {
		boolean exist = false;
		BankAccount account = (BankAccount) HqlUtil.get(BankAccount.class, cardNumber);
		if(account != null)  
			exist = true;
		return exist;
	}
	
	/**
	 * ��֤����
	 */
	@Override
	public boolean checkPassword(String cardNumber, String password) {
		boolean valid = false;
		BankAccount bankAccount = (BankAccount) HqlUtil.get(BankAccount.class, cardNumber);
		if(bankAccount != null){
			valid = bankAccount.getPassword().equals(password);
		}
		return valid;
	}
	
	/**
	 * ���ݿ��ź���������˻����
	 */
	@Override
	public double getBalance(String cardNumber, String password) {
		double balance = 0;
		
		return balance;
	}
	
	/**
	 * �ṩ���ţ����룬���
	 */
	@Override
	public boolean deposit(String cardNumber,double amount) {
		boolean sucess = false;
		
		return sucess;
	}

	/**
	 * ȡ�Ҫ���ṩ���ţ�����ͽ��
	 */
	@Override
	public boolean withDraw(String cardNumber, String password, double amount) {
		boolean sucess = false;
		BankAccount account = (BankAccount) HqlUtil.get(BankAccount.class, cardNumber);
		if(account.getPassword().equals(password) && account.getBalance() >= amount) {
			account.setBalance(account.getBalance()-amount);
			String hql = "update BankAccount set balance = " + account.getBalance() +" where cardNumber = " + account.getCardNumber();
			HqlUtil.executeUpdate(hql, null);
		}
		
		return sucess;
	}

	
	@Override
	public String getUserName(String cardNumber) {
		String userName = null;
		
		return userName;
	}

	/**
	 * ����ת��
	 */
	@Override
	public void doTransfer(String sender, String receiver, double amount) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = HqlUtil.getCurrentSession();
			transaction = session.beginTransaction();
			BankAccount senderAccount = (BankAccount) session.load(BankAccount.class, sender);
			BankAccount receiverAccount = (BankAccount) session.load(BankAccount.class, receiver);
			if(senderAccount.getBalance() >= amount){
				senderAccount.setBalance(senderAccount.getBalance() - amount);
				receiverAccount.setBalance(receiverAccount.getBalance() + amount);
				session.update(senderAccount);
				session.update(receiverAccount);
				transaction.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
			if(session != null && session.isOpen())
				session.close();
		}
	}
	
}
