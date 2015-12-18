package com.dao;

import java.util.List;
import com.domain.Account;
import com.util.MyDaoSupport;

/**
 * @author luo
 * 
 * 封装对Account实例CURD操作的dao类。
 */
public class AccountDao extends MyDaoSupport {
	
	/**
	 * 保存Account实例
	 * @param account       要保存的实例
	 * @return              保存对象的标识属性值
	 */
	public Integer saveAccount(Account account) {
		return (Integer) getHibernateTemplate().save(account);
	}
	
	/**
	 * 根据id查Account实例
	 * @param id        指定的id
	 * @return          对应的实例
	 */
	public Account getAccount(int id) {
		return getHibernateTemplate().get(Account.class, id);
	}
	
	/**
	 * 根据用户名查Account实例
	 * @param name         用户名
	 * @return             对应Account实例
	 */
	public Account getAccount(String name) {
		String hql = "from Account where owner.name = '" + name + "'";
		@SuppressWarnings("unchecked")
		List<Account> list = getHibernateTemplate().find(hql);
		if(list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 修改Account实例
	 * @param account      要修改的实例
	 */
	public void updateAccount(Account account) {
		getHibernateTemplate().update(account);
	}
	
	/**
	 * 删除Account实例
	 * @param account       要删除的实例
	 */
	public void deleteAccount(Account account) {
		getHibernateTemplate().delete(account);
	}
	
	/**
	 * 转账
	 * @param sender                 转账人用户名
	 * @param receiver               收款人用户名
	 * @param amount                 转账金额
	 * @return
	 */
	public boolean doTransfer(String sender,String receiver,double amount) {
		try {
			Account senderAccount = getAccount(sender);
			Account receiverAccount = getAccount(receiver);
			senderAccount.setBalance(senderAccount.getBalance() - amount);
			receiverAccount.setBalance(receiverAccount.getBalance() + amount);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
