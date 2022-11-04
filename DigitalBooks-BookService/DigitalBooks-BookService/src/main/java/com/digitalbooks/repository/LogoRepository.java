package com.digitalbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Logo;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long>{
	
	public Logo findByLogoId(Long logoId);

}
