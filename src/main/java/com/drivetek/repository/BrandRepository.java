package com.drivetek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.drivetek.model.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long> {
	  List<Brand> findByTitleContaining(String title);

}
