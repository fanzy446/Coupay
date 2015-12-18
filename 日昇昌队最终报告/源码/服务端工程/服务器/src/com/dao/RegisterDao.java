package com.dao;

import java.util.List;

import com.domain.Register;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * ��װ��Register�����CURD������ dao ��
 */
public class RegisterDao extends MyDaoSupport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * ���� Register ʵ��
	 * @param id  ��ʶ����ֵ
	 * @return    ָ��id��Ӧ��Reigsterʵ��
	 */
	public Register get(Integer id) {
		return getHibernateTemplate().get(Register.class, id);
	}
	
	/**
	 * ����Register ʵ��
	 * @param register     Ҫ�����ʵ��
	 * @return             ���ر������ı�ʶ����ֵ
	 */
	public Integer save(Register register) {
		return (Integer) getHibernateTemplate().save(register);
	}
	
	/**
	 * �޸�Registerʵ��
	 * @param register        Ҫ�޸ĵ�Registerʵ��
	 */
	public void update(Register register) {
		getHibernateTemplate().update(register);
	}
	
	/**
	 * ɾ��Registerʵ��
	 * @param register         Ҫɾ����Registerʵ��
	 */
	public void delete(Register register) {
		getHibernateTemplate().delete(register);
	}
	
	/**
	 * �����û�����ѯRegisterʵ��
	 * @param name        �û���
	 * @return            �û�����ӦRegisterʵ��
	 */
	public Register getRegister(String name) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from Register where name='"+name+"'");
		if(list != null && list.size() == 1) {
			return (Register) list.get(0);
		}
		return null;
	}
	
	
}
