package com.dao;

import java.util.LinkedList;
import java.util.List;

import com.domain.VipCard;
import com.domain.VipCardCollection;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 *
 * ��װVipCard �� VipCardCollection ʵ��CURD������dao�ࡣ
 */
public class VipCardDao extends MyDaoSupport {
	
	/**
	 * ����VipCardʵ��
	 * @param card       Ҫ�����ʵ��
	 * @return           �������ı�ʶ����ֵ
	 */
	public Integer saveVipCard(VipCard card) {
		return (Integer) getHibernateTemplate().save(card);
	}
	
	/**
	 * ����id��ѯVipCardʵ�� 
	 * @param id          ָ��id
	 * @return            id��Ӧʵ��
	 */
	public VipCard getVipCard(int id) {
		return getHibernateTemplate().get(VipCard.class, id);
	}
	
	/**
	 * ��ѯĳ�ͻ��ղص�����VipCard
	 * @param customerName        �ͻ��û���
	 * @return                    �ÿͻ����е�VipCard
	 */
	public List<VipCard> getVipCardOfCustomer(String customerName) {
		String hql = "from VipCardCollection where owner.name = '" + customerName + "'";
		@SuppressWarnings("unchecked")
		List<VipCardCollection> list = getHibernateTemplate().find(hql);
		List<VipCard> cards = new LinkedList<VipCard>();
		for(VipCardCollection collection : list) {
			cards.add(collection.getVipCard());
		}
		return cards;
	}
	
	/**
	 * ��ѯĳ�̼ҵ�����VipCard
	 * @param sellerName        �̼��û���
	 * @return                  ���̼ҵ�����VipCard
	 */
	public List<VipCard> getVipCardOfSeller(String sellerName) {
		String hql = "from vipCard��where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<VipCard> cards = getHibernateTemplate().find(hql);
		return cards; 
				
	}
	
	/**
	 * ��ѯ�̼�ָ�����ŵ�VipCard ʵ��
	 * @param sellerName
	 * @param vipCardNumber
	 * @return
	 */
	public VipCard getVipCard (String sellerName, String vipCardNumber) {
		String hql = " from VipCard where seller.name=? and vipCardNumber = ?";
		Object[] values = { sellerName, vipCardNumber };
		@SuppressWarnings("unchecked")
		List<VipCard> list = find(hql, values);
		if(list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * ��ѯ�ͻ��ղص�ĳ�̼�ָ�����ŵ�VipCardʵ��
	 * @param customerName       �ͻ��û���
	 * @param sellerName         �̼��û���
	 * @param vipCardNumber      ����
	 * @return                   ��ӦVipCardʵ��
	 */
	public VipCard getVipCard(String customerName, String sellerName, String vipCardNumber) {
		String hql = "from VipCardCollection where owner.name=? and vipCard.id in"
				+ "select id from VipCard where seller.name=? and vipCardNumber=?";
		Object[] values = { customerName,sellerName,vipCardNumber};
		
		@SuppressWarnings("unchecked")
		List<VipCardCollection> list = find(hql, values);
		if(list != null && list.size() == 1) {
			return list.get(0).getVipCard();
		}
		return null;
	}
	
	/**
	 * ɾ��VipCardʵ��
	 * @param card          Ҫɾ����ʵ��
	 */
	public void deleteVipCard(VipCard card) {
		getHibernateTemplate().delete(card);
	}
	
	/**
	 * ����VipCardCollectionʵ��
	 * @param collection         Ҫ�����ʵ��
	 * @return                   ��������ʶ����ֵ
	 */
	public Integer saveVipCardCollection(VipCardCollection collection) {
		return (Integer) getHibernateTemplate().save(collection);
	}
	
	
	/**
	 * ����id��ѯVipCardCollection ʵ��
	 * @param id       ָ��id
	 * @return         id ��ӦVipCardCollectionʵ��
	 */
	public VipCardCollection getVipCardCollection(int id) {
		return getHibernateTemplate().get(VipCardCollection.class, id);
	}
	
	/**
	 * ��ѯĳ�ͻ�������VipCardCollectionʵ��
	 * @param customerName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VipCardCollection> getVipCardCollection(String customerName) {
		String hql = "from VipCardCollectin where owner.name ='" + customerName + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * ��ѯ�û���ĳ�̼�ָ�����ŵ�VipCardCollection ����
	 * @param customerName
	 * @param sellerName
	 * @param vipCardNumber
	 * @return
	 */
	public VipCardCollection getVipCardCollection(String customerName, String sellerName,String vipCardNumber) {
		String hql = "from VipCardCollection where owner.name=? and vipCard.id in"
				+ "select id from VipCard where seller.name=? and vipCardNumber=?";
		Object[] values = { customerName,sellerName,vipCardNumber};
		@SuppressWarnings("unchecked")
		List<VipCardCollection> list = find(hql, values);
		if(list != null && list.size() == 1)
			return list.get(0);
		return null;
	}
	
	/**
	 * �޸�VipCardCollectionʵ��
	 * @param collection
	 */
	public void updateVipCardCollection(VipCardCollection collection) {
		getHibernateTemplate().update(collection);
	}
	
	/**
	 * ɾ��VipCardCollectionʵ��
	 * @param collection
	 */
	public void deleteVipCardCollection(VipCardCollection collection) {
		getHibernateTemplate().delete(collection);
	}
}
