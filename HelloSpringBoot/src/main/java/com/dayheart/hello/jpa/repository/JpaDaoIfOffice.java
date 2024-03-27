/**
 * 
 */
package com.dayheart.hello.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dayheart.hello.jpa.Office;

/**
 * @author dayheart
 *
 */
public interface JpaDaoIfOffice extends JpaRepository<Office, Integer> {
	
	@Query("SELECT o FROM offices o where o.office = :office")
	Office retrieveByOffice(@Param("office") Integer office);
	

}
