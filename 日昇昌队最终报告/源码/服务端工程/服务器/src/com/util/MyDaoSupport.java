package com.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * @author luo
 *	��HibernateDaoSupport���������һ�������ࡣ
 *  ʵ���˸���hql��������һЩ���������з�ҳ��ѯ�ķ���;
 *  ����ʵ���˸���hql���������������в�ѯ
 */
public class MyDaoSupport extends HibernateDaoSupport {
	
	/**
	 * ʹ��hql �����з�ҳ��ѯ
	 * @param hql         hql ���
	 * @param page        ��ǰҳ��
	 * @param pageSize    ÿҳ��¼��
	 * @return            ��ǰҳ���м�¼
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final int page, final int pageSize) {
		
		//ͨ��һ��HibernateCallback ������ִ�в�ѯ
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				//ִ�з�ҳ��ѯ
				List result = session.createQuery(hql).setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
		});
		//���ؽ��
		return list;
	}
	
	/**
	 * ʹ��hql���з�ҳ��ѯ
	 * @param hql           hql ���
	 * @param value         ����hql����һ������
	 * @param page          ��ǰҳ��
	 * @param pageSize      ÿҳ��¼��
	 * @return              ��ǰҳ���м�¼
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object value, final int page, final int pageSize) {
		
		//ͨ��һ�� 	HibernateCallback ������ִ�з�ҳ��ѯ
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				//ִ�з�ҳ��ѯ
				Query query = session.createQuery(hql).setParameter(0, value);
				List result = query.setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
		});
		
		//���ؽ��
		return list;
	}
	
	/**
	 * ʹ��hql �����з�ҳ��ѯ
	 * @param hql             hql ���
	 * @param values          ����hql����һ�����
	 * @param page            ��ǰҳ��
	 * @param pageSize        ÿҳ��¼��
	 * @return                ��ǰҳ���м�¼
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object[] values, final int page, final int pageSize) {
		
		//ʹ��һ��HibernateCallback���������з�ҳ��ѯ
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//����query
				Query query = session.createQuery(hql);
				//�������
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				//ִ�з�ҳ��ѯ
				List result = query.setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
			
		});
		
		//���ؽ��
		return list;
	}
	
	/**
	 * ����hql��������������ѯ
	 * @param hql             hql ���
	 * @param values          ����hql���Ĳ���
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List find(final String hql, final Object[] values) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				List result = query.list();
				return result;
			}
		});
		
		//���ؽ��
		return list;
	}
	
}
