package com.dao;

import java.util.List;
import com.domain.Account;
import com.util.MyDaoSupport;

/**
 * @author luo
 * 
 * ��װ��Accountʵ��CURD������dao�ࡣ
 */
public class AccountDao extends MyDaoSupport {
	
	/**
	 * ����Accountʵ��
	 * @param account       Ҫ�����ʵ��
	 * @return              �������ı�ʶ����ֵ
	 */
	public Integer saveAccount(Account account) {
		return (Integer) getHibernateTemplate().save(account);
	}
	
	/**
	 * ����id��Accountʵ��
	 * @param id        ָ����id
	 * @return          ��Ӧ��ʵ��
	 */
	public Account getAccount(int id) {
		return getHibernateTemplate().get(Account.class, id);
	}
	
	/**
	 * �����û�����Accountʵ��
	 * @param name         �û���
	 * @return             ��ӦAccountʵ��
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
	 * �޸�Accountʵ��
	 * @param account      Ҫ�޸ĵ�ʵ��
	 */
	public void updateAccount(Account account) {
		getHibernateTemplate().update(account);
	}
	
	/**
	 * ɾ��Accountʵ��
	 * @param account       Ҫɾ����ʵ��
	 */
	public void deleteAccount(Account account) {
		getHibernateTemplate().delete(account);
	}
	
	/**
	 * ת��
	 * @param sender                 ת�����û���
	 * @param receiver               �տ����û���
	 * @param amount                 ת�˽��
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
