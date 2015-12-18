package com.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import com.domain.Customer;
import com.domain.Focus;
import com.domain.Friendship;
import com.domain.Request;
import com.domain.Seller;
import com.domain.Share;
import com.util.MyDaoSupport;

/**
 * @author luo
 *
 *	封装与圈子有关的实体实例CURD操作的dao类
 */
public class CircleEntityDao extends MyDaoSupport{
	/**
	 * 保存Request实例
	 * @param request     要保存的实例
	 * @return            保存对象标识属性值
	 */
	public int saveRequest(Request request) {
		return (Integer) getHibernateTemplate().save(request);
	}
	
	/**
	 * 查询某客户收到的所有Request对象
	 * @param receiver          收到Request的客户用户名
	 * @return                  收到Request实例列表
	 */
	@SuppressWarnings("unchecked")
	public List<Request> getReceivedRequest(String receiver) {
		String hql = "from Request where responder.name='" + receiver + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 查询客户发出的所有Request对象
	 * @param requester          发出Request的客户用户名
	 * @return                   发出的Request实例列表
	 */
	@SuppressWarnings("unchecked")
	public List<Request> getSentRequest(String requester) {
		String hql = "from Request where requester.name='" + requester + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 *根据请求者和回应着查Request实例
	 * @param requester         请求者用户名
	 * @param responder         回应着用户名
	 * @return                  对应的实例
	 */
	public Request getRequest(String requester, String responder) {
		String hql = " form Request where reqeuster.name=? and responder.name=?";
		Object[] values = { requester, responder };
		@SuppressWarnings("unchecked")
		List<Request> list = find(hql, values);
		if(list != null && list.size() == 1)
			return list.get(0);
		return null;
	}
	
	/**
	 * 修改Request实例
	 * @param request       要修改的实例
	 */
	public void updateRequest(Request request) {
		getHibernateTemplate().update(request);
	}
	
	/**
	 * 删除Request实例
	 * @param request　　　　要删除的实例
	 */
	public void deleteRequest(Request request) {
		getHibernateTemplate().delete(request);
	}
	
	/**
	 * 保存Friendship 实例
	 * @param frendship      要保存的实例
	 * @return
	 */
	public Integer saveFriendShip(Friendship frendship) {
		return (Integer) getHibernateTemplate().save(frendship);
	}
	
	/**
	 * 根据用户名查询所有Friendship实例
	 * @param name     用户名
	 * @return         对应所用Friendship实例
	 */
	@SuppressWarnings("unchecked")
	public List<Friendship> getFriendship(String name) {
		String hql = "from Friendship where person1.name='" + name + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 根据双方用户名查Friendship实例
	 * @param name1                   第一个用户名
	 * @param name2                   第二个用户名
	 * @return           对应的Friendship实例  
	 */
	@SuppressWarnings("unchecked")
	public List<Friendship> getFriendship(String name1, String name2) {
		String hql = "from Friendship where person1.name= ? and person2.name=? or"
				+ " person1.name=? and person2.name=?";
		Object[] values = { name1, name2, name2, name1 };
		return find(hql, values);
	}
	
	/**
	 * 修改Friendship实例
	 * @param friendship
	 */
	public void updateFriendship(Friendship friendship) {
		getHibernateTemplate().update(friendship);
	}
	
	public void deleteFriendship(Friendship friendship) {
		getHibernateTemplate().delete(friendship);
	}

//	/**
//	 * 保存comment实例
//	 * @param comment 	要保存的实例
//	 * @return			保存对象的标识属性值    				
//	 */
//		return (Integer) getHibernateTemplate().save(comment);
//	}
	
//	public List<Comment> getComment(List<String> friends) {
//		List<Comment> comments = new LinkedList<Comment>();
//		for(String name: friends){
//			String hql = "from Comment where sender.name='" + name + "'";
//			List<Comment> comment = (List<Comment>) find(hql, null);
//			for(Comment c: comment){
//				comments.add(c);
//			}
//		}
//		//对comments进行按日期的降序排序
//		//TODO
//	}
	/**
	 * 保存share实例
	 * @param share          要保存的实例
	 * @return               保存对象的标识属性值
	 */
	public Integer saveShare(Share share) {
		return (Integer) getHibernateTemplate().save(share);
	}
	
	/**
	 * 根据id查Share实例
	 * @param id            指定id
	 * @return              id对应Share实例
	 */
	public Share getShare(int id) {
		return getHibernateTemplate().get(Share.class, id);
	}
	
	/**
	 * 分页查询某客户的Share实例
	 * @param name          用户名
	 * @param page          当前页
	 * @return              当前页所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<Share> getShare(String name,final int page, final int pageSize) {
		String hql = "from Share where share.name='" + name + "'";
		return findByPage(hql, page, pageSize);
	}
	
	/**
	 * 分页获取朋友圈的分享话题
	 * @param name        用户名
	 * @param page        当前页
	 * @param pageSize    每页分享数
	 * @return            当前页所有Share实例
	 */
	@SuppressWarnings("unchecked")
	public List<Share> getTopic(String name,final int page,final int pageSize) {
		String hql  = "from Share where sharer.name=? or sharer.name in (select person1.name from Friendship " +
				"where person2.name = ?) or sharer.name in (select person2.name from Friendship where person1.name =?) order by date desc";
		Object[] values = { name, name, name };
		return findByPage(hql, values, page, pageSize);
	}
	
	/**
	 * 删除Share实例
	 * @param share       要删除的实例
	 */
	public void deleteShare(Share share) {
		getHibernateTemplate().delete(share);
	}
	
	/**
	 * 保存Focus实例
	 * @param focus           要保存的实例
	 * @return
	 */
	public Integer saveFoucus(Focus focus) {
		return (Integer) getHibernateTemplate().save(focus);
	}
	
	/**
	 * 分页查询关注某商家的客户
	 * @param sellerName       商家用户名
	 * @param page             当前页
	 * @param pageSize         每页记录数
	 * @return                 当前页所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> getFocusingCustomer(String sellerName, int page, int pageSize) {
		String hql = "from Foucus where seller.name='" + sellerName + "'";
		List<Focus> list = findByPage(hql, page, pageSize);
		List<Customer> customers = new LinkedList<Customer>();
		for(Focus f : list) {
			customers.add(f.getCustomer());
		}
		return  customers;
	}
	
	/**
	 * 分页查询某客户关注的Seller实例
	 * @param customerName 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Seller> getFocusedSeller(String customerName, int page, int pageSize) {
		String hql = "from Foucus where customer.name='" + customerName + "'";
		@SuppressWarnings("unchecked")
		List<Focus> list = findByPage(hql, page, pageSize);
		List<Seller> customers = new LinkedList<Seller>();
		for(Focus f : list) {
			customers.add(f.getSeller());
		}
		return  customers;
	}
	
	/**
	 * 根据客户用户名和商家用户名查询Focus实例
	 * @param customerName 
	 * @param sellerName
	 * @return
	 */
	public Focus getFocus(String customerName,String sellerName) {
		String hql = "from Focus where customer.name=? and seller.name = ?";
		Object[] values = { customerName, sellerName };
		@SuppressWarnings("unchecked")
		List<Focus> list = find(hql, values);
		if(list != null && list.size() == 1)
			return list.get(0);
		return null;
	}
	
	public void deleteFocus(Focus focus) {
		getHibernateTemplate().delete(focus);
	}
	
	/**
	 * 根据图片路径检查该图片是否存在
	 * @param path
	 * @return false 不存在， true 存在
	 */
	public boolean checkPicturePath(String path) {
		String hql = "from Share where share.picturePath = " + path;
		@SuppressWarnings("unchecked")
		List<String> list = find(hql, null);
		if(list.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
}
