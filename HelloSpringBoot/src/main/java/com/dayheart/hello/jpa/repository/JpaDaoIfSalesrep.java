/**
 * 
 */
package com.dayheart.hello.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dayheart.hello.jpa.Customer;
import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Salesrep;

/**
 * @author dayheart
 *
 */
public interface JpaDaoIfSalesrep extends JpaRepository<Salesrep, Integer> {
	
	@Query("SELECT s FROM salesreps s where s.emplNum = :emplNum")
	Salesrep retrieveBySalesrep(@Param("emplNum") Integer emplNum);
	

}
