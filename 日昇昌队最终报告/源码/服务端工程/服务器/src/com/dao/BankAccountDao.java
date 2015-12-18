package com.dao;

import com.domain.BankAccount;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 *
 *	��װBankCardAccountʵ��CURD������dao��
 */
public class BankAccountDao extends MyDaoSupport {
	
	/**
	 * ����BankAccountʵ��
	 * @param account
	 * @return
	 */
	public String saveBankCardAccount(BankAccount account) {
		return (String) getHibernateTemplate().save(account);
	}
	
	/**
	 * ���ݿ��Ų�ѯBankAccountʵ��
	 * @param cardNumber        ����
	 * @return
	 */
	public BankAccount getBankAccount(String cardNumber) {
		return getHibernateTemplate().get(BankAccount.class, cardNumber);
	}
	
	/**
	 * �޸�BankAccountʵ��
	 * @param account    Ҫ�޸ĵ�ʵ��
	 */
	public void updateBankAccount(BankAccount account) {
		getHibernateTemplate().update(account);
	}
	
	/**
	 * ɾ��BankAccountʵ��
	 * @param account
	 */
	public void deleteBankAccount(BankAccount account) {
		getHibernateTemplate().delete(account);
	}
}
