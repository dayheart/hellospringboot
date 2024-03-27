/**
 * 
 */
package com.dayheart.hello.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dayheart.hello.jpa.Customer;
import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Order;

/**
 * @author dayheart
 *
 */
public interface JpaDaoIfOreder extends JpaRepository<Order, Long> {
	
	@Query("SELECT o FROM orders o where o.orderNum = :orderNum")
	Order retrieveByOrder(@Param("orderNum") Long orderNum);
	

}
