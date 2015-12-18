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
 *	对HibernateDaoSupport进行扩充的一个工具类。
 *  实现了根据hql语句和其他一些参数来进行分页查询的方法;
 *  另外实现了根据hql语句和其他参数进行查询
 */
public class MyDaoSupport extends HibernateDaoSupport {
	
	/**
	 * 使用hql 语句进行分页查询
	 * @param hql         hql 语句
	 * @param page        当前页号
	 * @param pageSize    每页记录数
	 * @return            当前页所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final int page, final int pageSize) {
		
		//通过一个HibernateCallback 对象来执行查询
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				//执行分页查询
				List result = session.createQuery(hql).setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
		});
		//返回结果
		return list;
	}
	
	/**
	 * 使用hql进行分页查询
	 * @param hql           hql 语句
	 * @param value         传入hql语句的一个参数
	 * @param page          当前页号
	 * @param pageSize      每页记录号
	 * @return              当前页所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object value, final int page, final int pageSize) {
		
		//通过一个 	HibernateCallback 对象来执行分页查询
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				//执行分页查询
				Query query = session.createQuery(hql).setParameter(0, value);
				List result = query.setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
		});
		
		//返回结果
		return list;
	}
	
	/**
	 * 使用hql 语句进行分页查询
	 * @param hql             hql 语句
	 * @param values          传入hql语句的一组参数
	 * @param page            当前页号
	 * @param pageSize        每页记录数
	 * @return                当前页所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPage(final String hql, final Object[] values, final int page, final int pageSize) {
		
		//使用一个HibernateCallback对象来进行分页查询
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//创建query
				Query query = session.createQuery(hql);
				//传入参数
				for(int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				//执行分页查询
				List result = query.setFirstResult((page-1)*pageSize).setMaxResults(pageSize).list();
				return result;
			}
			
		});
		
		//返回结果
		return list;
	}
	
	/**
	 * 根据hql语句和其他参数查询
	 * @param hql             hql 语句
	 * @param values          传入hql语句的参数
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
		
		//返回结果
		return list;
	}
	
}
