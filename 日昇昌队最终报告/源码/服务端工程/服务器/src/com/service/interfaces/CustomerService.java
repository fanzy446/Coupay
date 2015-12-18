package com.service.interfaces;

import java.util.List;

import com.domain.Customer;

public interface CustomerService {

	public void addCustomer(Customer customer);
	
	public Customer getCustomer(String name);
	
	public void updateCustomer(Customer customer);
	
	public List<Customer> searchCustomer(String partition);
	
}
