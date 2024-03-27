/**
 * 
 */
package com.dayheart.hello.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dayheart.hello.jpa.Customer;

/**
 * @author dayheart
 *
 */
public interface JpaDaoIfCustomer extends JpaRepository<Customer, Integer> {
	
	@Query("SELECT c FROM customers c where c.custNum = :custNum")
	Customer retrieveByCustomer(@Param("custNum") Integer custNum);
	

}
