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
 *	��װ��Ȧ���йص�ʵ��ʵ��CURD������dao��
 */
public class CircleEntityDao extends MyDaoSupport{
	/**
	 * ����Requestʵ��
	 * @param request     Ҫ�����ʵ��
	 * @return            ��������ʶ����ֵ
	 */
	public int saveRequest(Request request) {
		return (Integer) getHibernateTemplate().save(request);
	}
	
	/**
	 * ��ѯĳ�ͻ��յ�������Request����
	 * @param receiver          �յ�Request�Ŀͻ��û���
	 * @return                  �յ�Requestʵ���б�
	 */
	@SuppressWarnings("unchecked")
	public List<Request> getReceivedRequest(String receiver) {
		String hql = "from Request where responder.name='" + receiver + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * ��ѯ�ͻ�����������Request����
	 * @param requester          ����Request�Ŀͻ��û���
	 * @return                   ������Requestʵ���б�
	 */
	@SuppressWarnings("unchecked")
	public List<Request> getSentRequest(String requester) {
		String hql = "from Request where requester.name='" + requester + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 *���������ߺͻ�Ӧ�Ų�Requestʵ��
	 * @param requester         �������û���
	 * @param responder         ��Ӧ���û���
	 * @return                  ��Ӧ��ʵ��
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
	 * �޸�Requestʵ��
	 * @param request       Ҫ�޸ĵ�ʵ��
	 */
	public void updateRequest(Request request) {
		getHibernateTemplate().update(request);
	}
	
	/**
	 * ɾ��Requestʵ��
	 * @param request��������Ҫɾ����ʵ��
	 */
	public void deleteRequest(Request request) {
		getHibernateTemplate().delete(request);
	}
	
	/**
	 * ����Friendship ʵ��
	 * @param frendship      Ҫ�����ʵ��
	 * @return
	 */
	public Integer saveFriendShip(Friendship frendship) {
		return (Integer) getHibernateTemplate().save(frendship);
	}
	
	/**
	 * �����û�����ѯ����Friendshipʵ��
	 * @param name     �û���
	 * @return         ��Ӧ����Friendshipʵ��
	 */
	@SuppressWarnings("unchecked")
	public List<Friendship> getFriendship(String name) {
		String hql = "from Friendship where person1.name='" + name + "'";
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * ����˫���û�����Friendshipʵ��
	 * @param name1                   ��һ���û���
	 * @param name2                   �ڶ����û���
	 * @return           ��Ӧ��Friendshipʵ��  
	 */
	@SuppressWarnings("unchecked")
	public List<Friendship> getFriendship(String name1, String name2) {
		String hql = "from Friendship where person1.name= ? and person2.name=? or"
				+ " person1.name=? and person2.name=?";
		Object[] values = { name1, name2, name2, name1 };
		return find(hql, values);
	}
	
	/**
	 * �޸�Friendshipʵ��
	 * @param friendship
	 */
	public void updateFriendship(Friendship friendship) {
		getHibernateTemplate().update(friendship);
	}
	
	public void deleteFriendship(Friendship friendship) {
		getHibernateTemplate().delete(friendship);
	}

//	/**
//	 * ����commentʵ��
//	 * @param comment 	Ҫ�����ʵ��
//	 * @return			�������ı�ʶ����ֵ    				
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
//		//��comments���а����ڵĽ�������
//		//TODO
//	}
	/**
	 * ����shareʵ��
	 * @param share          Ҫ�����ʵ��
	 * @return               �������ı�ʶ����ֵ
	 */
	public Integer saveShare(Share share) {
		return (Integer) getHibernateTemplate().save(share);
	}
	
	/**
	 * ����id��Shareʵ��
	 * @param id            ָ��id
	 * @return              id��ӦShareʵ��
	 */
	public Share getShare(int id) {
		return getHibernateTemplate().get(Share.class, id);
	}
	
	/**
	 * ��ҳ��ѯĳ�ͻ���Shareʵ��
	 * @param name          �û���
	 * @param page          ��ǰҳ
	 * @return              ��ǰҳ���м�¼
	 */
	@SuppressWarnings("unchecked")
	public List<Share> getShare(String name,final int page, final int pageSize) {
		String hql = "from Share where share.name='" + name + "'";
		return findByPage(hql, page, pageSize);
	}
	
	/**
	 * ��ҳ��ȡ����Ȧ�ķ�����
	 * @param name        �û���
	 * @param page        ��ǰҳ
	 * @param pageSize    ÿҳ������
	 * @return            ��ǰҳ����Shareʵ��
	 */
	@SuppressWarnings("unchecked")
	public List<Share> getTopic(String name,final int page,final int pageSize) {
		String hql  = "from Share where sharer.name=? or sharer.name in (select person1.name from Friendship " +
				"where person2.name = ?) or sharer.name in (select person2.name from Friendship where person1.name =?) order by date desc";
		Object[] values = { name, name, name };
		return findByPage(hql, values, page, pageSize);
	}
	
	/**
	 * ɾ��Shareʵ��
	 * @param share       Ҫɾ����ʵ��
	 */
	public void deleteShare(Share share) {
		getHibernateTemplate().delete(share);
	}
	
	/**
	 * ����Focusʵ��
	 * @param focus           Ҫ�����ʵ��
	 * @return
	 */
	public Integer saveFoucus(Focus focus) {
		return (Integer) getHibernateTemplate().save(focus);
	}
	
	/**
	 * ��ҳ��ѯ��עĳ�̼ҵĿͻ�
	 * @param sellerName       �̼��û���
	 * @param page             ��ǰҳ
	 * @param pageSize         ÿҳ��¼��
	 * @return                 ��ǰҳ���м�¼
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
	 * ��ҳ��ѯĳ�ͻ���ע��Sellerʵ��
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
	 * ���ݿͻ��û������̼��û�����ѯFocusʵ��
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
	 * ����ͼƬ·������ͼƬ�Ƿ����
	 * @param path
	 * @return false �����ڣ� true ����
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
