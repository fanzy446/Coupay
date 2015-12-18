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
 * 封装VipCard 和 VipCardCollection 实例CURD操作的dao类。
 */
public class VipCardDao extends MyDaoSupport {
	
	/**
	 * 保存VipCard实例
	 * @param card       要保存的实例
	 * @return           保存对象的标识属性值
	 */
	public Integer saveVipCard(VipCard card) {
		return (Integer) getHibernateTemplate().save(card);
	}
	
	/**
	 * 根据id查询VipCard实例 
	 * @param id          指定id
	 * @return            id对应实例
	 */
	public VipCard getVipCard(int id) {
		return getHibernateTemplate().get(VipCard.class, id);
	}
	
	/**
	 * 查询某客户收藏的所有VipCard
	 * @param customerName        客户用户名
	 * @return                    该客户所有的VipCard
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
	 * 查询某商家的所有VipCard
	 * @param sellerName        商家用户名
	 * @return                  该商家的所有VipCard
	 */
	public List<VipCard> getVipCardOfSeller(String sellerName) {
		String hql = "from vipCard　where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<VipCard> cards = getHibernateTemplate().find(hql);
		return cards; 
				
	}
	
	/**
	 * 查询商家指定卡号的VipCard 实例
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
	 * 查询客户收藏的某商家指定卡号的VipCard实例
	 * @param customerName       客户用户名
	 * @param sellerName         商家用户名
	 * @param vipCardNumber      卡号
	 * @return                   对应VipCard实例
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
	 * 删除VipCard实例
	 * @param card          要删除的实例
	 */
	public void deleteVipCard(VipCard card) {
		getHibernateTemplate().delete(card);
	}
	
	/**
	 * 保存VipCardCollection实例
	 * @param collection         要保存的实例
	 * @return                   保存对象标识属性值
	 */
	public Integer saveVipCardCollection(VipCardCollection collection) {
		return (Integer) getHibernateTemplate().save(collection);
	}
	
	
	/**
	 * 根据id查询VipCardCollection 实例
	 * @param id       指定id
	 * @return         id 对应VipCardCollection实例
	 */
	public VipCardCollection getVipCardCollection(int id) {
		return getHibernateTemplate().get(VipCardCollection.class, id);
	}
	
	/**
	 * 查询某客户的所有VipCardCollection实例
	 * @param customerName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VipCardCollection> getVipCardCollection(String customerName) {
		String hql = "from VipCardCollectin where owner.name ='" + customerName + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询用户在某商家指定卡号的VipCardCollection 对象
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
	 * 修改VipCardCollection实例
	 * @param collection
	 */
	public void updateVipCardCollection(VipCardCollection collection) {
		getHibernateTemplate().update(collection);
	}
	
	/**
	 * 删除VipCardCollection实例
	 * @param collection
	 */
	public void deleteVipCardCollection(VipCardCollection collection) {
		getHibernateTemplate().delete(collection);
	}
}
