package com.fdw.dao;

import java.util.List;

import com.fdw.entity.Customer;
import com.fdw.util.JdbcTemplate;

public class UserLogin {
	
	public List<Customer>  login (Customer customer) throws Exception{
		String sql = "select * from customer  c where  c.first_name=?";
	  return	JdbcTemplate.queryData(sql,new Object[]{customer.getFirstName()},Customer.class);
	}
}
