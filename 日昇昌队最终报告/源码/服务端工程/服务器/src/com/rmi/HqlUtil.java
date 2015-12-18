package com.rmi;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class HqlUtil {
	private static SessionFactory sessionFactory=null;
	//使用线程局部模式
	private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();
	public HqlUtil() {
		
	}
	
	public void setSessionFactory(SessionFactory factory){
		sessionFactory = factory;
	}
	
	//获取全新的全新的sesession
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	
	//获取和线程关联的session
	public static Session getCurrentSession(){
		
		Session session=threadLocal.get();
		//判断是否得到
		if(session==null){
			session=sessionFactory.openSession();
			//把session对象设置到 threadLocal,相当于该session已经和线程绑定
			threadLocal.set(session);
		}
		return session;		
	}
	
	public static Object get(Class<?> c,Serializable key){
		Session s = null;
		Transaction tx=null;
		try{
			s = openSession();
			tx = s.beginTransaction();
			Object o = s.get(c,key);
			tx.commit();
			s.close();
			return o;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(s != null && s.isOpen()){
				s.close();
			}
		}
		return null;
	}
	
	
	//统一的一个修改和删除(批量 hql) hql"delete upate ...??"
	public static void executeUpdate(String hql,String [] parameters){
		
		Session s=null;
		Transaction tx=null;
		
		try {
			s=openSession();
			tx=s.beginTransaction();
			Query query=s.createQuery(hql);
			//先判断是否有参数要绑定
			if(parameters!=null&& parameters.length>0){
				for(int i=0;i<parameters.length;i++){
					query.setString(i, parameters[i]);
				}
			}
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			// TODO: handle exception
		}finally{
			
			if(s!=null&&s.isOpen()){
				s.close();
			}
			
		}
		
	}
	
	//统一的添加的方法
	public  static void save(Object obj){
		Session s=null;
		Transaction tx=null;
		
		try {
			s=openSession();
			tx=s.beginTransaction();
			s.save(obj);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new RuntimeException(e.getMessage());
			// TODO: handle exception
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		
	}
	
	public static void delete(Class<?> c ,Serializable key){
		Session s = null;
		Transaction tx = null;
		
		try {
			s=openSession();
			tx=s.beginTransaction();
			Object obj = s.load(c, key);
			s.delete(obj);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new RuntimeException(e.getMessage());
			// TODO: handle exception
		}finally{
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
		
	}
	//提供一个统一的查询方法(带分页) hql 形式 from 类  where 条件=? ..
	@SuppressWarnings("unchecked")
	public static List<Object> executeQueryByPage(String hql,String [] parameters,int pageSize,int pageNow){
		Session s=null;
		List<Object> list=null;
		
		try {
			s=openSession();
			Query query=s.createQuery(hql);
			//先判断是否有参数要绑定
			if(parameters!=null&& parameters.length>0){
				for(int i=0;i<parameters.length;i++){
					query.setString(i, parameters[i]);
				}
			}
			query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize);
			
			list=query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			// TODO: handle exception
		}finally{
			
			if(s!=null&&s.isOpen()){
				s.close();
			}
			
		}
		return list;
	}
	
	//提供一个统一的查询方法 hql 形式 from 类  where 条件=? ..
	@SuppressWarnings("unchecked")
	public static List<Object> executeQuery(String hql,String [] parameters){
		
		Session s=null;
		List<Object> list=null;
		
		try {
			s=openSession();
			Query query=s.createQuery(hql);
			//先判断是否有参数要绑定
			if(parameters!=null&& parameters.length>0){
				for(int i=0;i<parameters.length;i++){
					query.setString(i, parameters[i]);
				}
			}
			list=query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			// TODO: handle exception
		}finally{
			
			if(s!=null&&s.isOpen()){
				s.close();
			}
			
		}
		return list;
	}
	
}
