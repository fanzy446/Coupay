package com.service.implement;

import java.util.List;

import com.dao.CustomerDao;
import com.domain.Customer;
import com.service.interfaces.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao dao;
	
	
	public void setDao(CustomerDao dao) {
		this.dao = dao;
	}

	@Override
	public void addCustomer(Customer customer) {
		dao.save(customer);
	}

	@Override
	public Customer getCustomer(String name) {
		return dao.getCustomer(name);
	}

	@Override
	public void updateCustomer(Customer customer) {
		dao.update(customer);
	}

	@Override
	public List<Customer> searchCustomer(String partition) {
		return dao.searchCustomer(partition);
	}

}
