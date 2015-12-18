package com.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


@SuppressWarnings("deprecation")
public class HqlUtil {
	private static SessionFactory sessionFactory=null;
	//ʹ���ֲ߳̾�ģʽ
	private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();
	public HqlUtil() {
		
	}
	
	/*static {
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}*/
	
	public void setSessionFactory(SessionFactory factory){
		sessionFactory = factory;
	}
	
	//��ȡȫ�µ�ȫ�µ�sesession
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	
	//��ȡ���̹߳�����session
	public static Session getCurrentSession(){
		
		Session session=threadLocal.get();
		//�ж��Ƿ�õ�
		if(session==null){
			session=sessionFactory.openSession();
			//��session�������õ� threadLocal,�൱�ڸ�session�Ѿ����̰߳�
			threadLocal.set(session);
		}
		else if(!session.isOpen()) {
			threadLocal.remove();
			session = sessionFactory.openSession();
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
	
	
	//ͳһ��һ���޸ĺ�ɾ��(���� hql) hql"delete upate ...??"
	public static void executeUpdate(String hql,String [] parameters){
		
		Session s=null;
		Transaction tx=null;
		
		try {
			s=openSession();
			tx=s.beginTransaction();
			Query query=s.createQuery(hql);
			//���ж��Ƿ��в���Ҫ��
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
	
	//ͳһ����ӵķ���
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
	
	//�ṩһ��ͳһ�Ĳ�ѯ����(����ҳ) hql ��ʽ from ��  where ����=? ..
	@SuppressWarnings("unchecked")
	public static List<Object> executeQueryByPage(String hql,String [] parameters,int pageSize,int pageNow){
		Session s=null;
		List<Object> list=null;
		
		try {
			s=openSession();
			Query query=s.createQuery(hql);
			//���ж��Ƿ��в���Ҫ��
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
	
	//�ṩһ��ͳһ�Ĳ�ѯ���� hql ��ʽ from ��  where ����=? ..
	@SuppressWarnings("unchecked")
	public static List<Object> executeQuery(String hql,String [] parameters){
		
		Session s=null;
		List<Object> list=null;
		
		try {
			s=openSession();
			Query query=s.createQuery(hql);
			//���ж��Ƿ��в���Ҫ��
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
