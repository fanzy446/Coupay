package com.dao;

import com.domain.BankAccount;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 *
 *	封装BankCardAccount实例CURD操作的dao类
 */
public class BankAccountDao extends MyDaoSupport {
	
	/**
	 * 保存BankAccount实例
	 * @param account
	 * @return
	 */
	public String saveBankCardAccount(BankAccount account) {
		return (String) getHibernateTemplate().save(account);
	}
	
	/**
	 * 根据卡号查询BankAccount实例
	 * @param cardNumber        卡号
	 * @return
	 */
	public BankAccount getBankAccount(String cardNumber) {
		return getHibernateTemplate().get(BankAccount.class, cardNumber);
	}
	
	/**
	 * 修改BankAccount实例
	 * @param account    要修改的实例
	 */
	public void updateBankAccount(BankAccount account) {
		getHibernateTemplate().update(account);
	}
	
	/**
	 * 删除BankAccount实例
	 * @param account
	 */
	public void deleteBankAccount(BankAccount account) {
		getHibernateTemplate().delete(account);
	}
}
