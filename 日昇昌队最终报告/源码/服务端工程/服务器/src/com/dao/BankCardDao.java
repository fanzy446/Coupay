package com.dao;

import java.util.List;

import com.domain.BankCard;
import com.util.MyDaoSupport;

public class BankCardDao extends MyDaoSupport {
	
	/**
	 * ���ݿ��Ų�ѯBankCardʵ��
	 * @param cardNumber         ����
	 * @return                   ���Ŷ�Ӧʵ��
	 */
	public BankCard getBankCard(String cardNumber) {
		return getHibernateTemplate().get(BankCard.class, cardNumber);
	}
	
	/**
	 * ����BankCardʵ��
	 * @param bankCard           Ҫ�����ʵ��
	 */
	public void saveBankCard(BankCard bankCard) {
		getHibernateTemplate().save(bankCard);
	}
	
	/**
	 * �޸�BankCardʵ��
	 * @param bankCard          Ҫ�޸ĵ�ʵ��
	 */
	public void updateBankCard(BankCard bankCard) {
		getHibernateTemplate().update(bankCard);
	}
	
	/**
	 * ɾ��BankCardʵ��
	 * @param bankCard        Ҫɾ����ʵ��
	 */
	public void deleteBankCard(BankCard bankCard) {
		getHibernateTemplate().delete(bankCard);
	}
	
	/**
	 * ��ѯ�û��󶨵��������п�
	 * @param registerName      �û���
	 * @return                  �󶨵��������п�
	 */
	public List<BankCard>getBankCards (String registerName) {
		String hql = "from BankCard where owner.name='" + registerName +"'";
		@SuppressWarnings("unchecked")
		List<BankCard> list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
