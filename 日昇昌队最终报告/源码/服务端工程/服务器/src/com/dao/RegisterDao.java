package com.dao;

import java.util.List;

import com.domain.Register;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * 封装对Register对象的CURD操作的 dao 类
 */
public class RegisterDao extends MyDaoSupport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 加载 Register 实例
	 * @param id  标识属性值
	 * @return    指定id对应的Reigster实例
	 */
	public Register get(Integer id) {
		return getHibernateTemplate().get(Register.class, id);
	}
	
	/**
	 * 保存Register 实例
	 * @param register     要保存的实例
	 * @return             返回保存对象的标识属性值
	 */
	public Integer save(Register register) {
		return (Integer) getHibernateTemplate().save(register);
	}
	
	/**
	 * 修改Register实例
	 * @param register        要修改的Register实例
	 */
	public void update(Register register) {
		getHibernateTemplate().update(register);
	}
	
	/**
	 * 删除Register实例
	 * @param register         要删除的Register实例
	 */
	public void delete(Register register) {
		getHibernateTemplate().delete(register);
	}
	
	/**
	 * 根据用户名查询Register实例
	 * @param name        用户名
	 * @return            用户名对应Register实例
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
