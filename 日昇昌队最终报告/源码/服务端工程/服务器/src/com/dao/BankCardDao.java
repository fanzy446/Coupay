package com.dao;

import java.util.List;

import com.domain.BankCard;
import com.util.MyDaoSupport;

public class BankCardDao extends MyDaoSupport {
	
	/**
	 * 根据卡号查询BankCard实例
	 * @param cardNumber         卡号
	 * @return                   卡号对应实例
	 */
	public BankCard getBankCard(String cardNumber) {
		return getHibernateTemplate().get(BankCard.class, cardNumber);
	}
	
	/**
	 * 保存BankCard实例
	 * @param bankCard           要保存的实例
	 */
	public void saveBankCard(BankCard bankCard) {
		getHibernateTemplate().save(bankCard);
	}
	
	/**
	 * 修改BankCard实例
	 * @param bankCard          要修改的实例
	 */
	public void updateBankCard(BankCard bankCard) {
		getHibernateTemplate().update(bankCard);
	}
	
	/**
	 * 删除BankCard实例
	 * @param bankCard        要删除的实例
	 */
	public void deleteBankCard(BankCard bankCard) {
		getHibernateTemplate().delete(bankCard);
	}
	
	/**
	 * 查询用户绑定的所有银行卡
	 * @param registerName      用户名
	 * @return                  绑定的所有银行卡
	 */
	public List<BankCard>getBankCards (String registerName) {
		String hql = "from BankCard where owner.name='" + registerName +"'";
		@SuppressWarnings("unchecked")
		List<BankCard> list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
