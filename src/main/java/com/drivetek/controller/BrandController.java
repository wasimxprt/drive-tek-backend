package com.drivetek.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drivetek.model.Brand;
import com.drivetek.repository.BrandRepository;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api")
public class BrandController {

	@Autowired
	BrandRepository brandRepository;

	@GetMapping("/brands")
	public ResponseEntity<List<Brand>> getAllBrands(@RequestParam(required = false) String title) {
		try {
			List<Brand> brands = new ArrayList<Brand>();

			if (title == null)
				brandRepository.findAll().forEach(brands::add);
			else
				brandRepository.findByTitleContaining(title).forEach(brands::add);

			if (brands.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(brands, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/brands/{id}")
	  public ResponseEntity<Brand> getBrandById(@PathVariable("id") long id) {
	    Optional<Brand> brandData = brandRepository.findById(id);

	    if (brandData.isPresent()) {
	      return new ResponseEntity<>(brandData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @PostMapping("/brands")
	  public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
	    try {
	    	Brand _brand = brandRepository.save(new Brand(brand.getTitle(), brand.getDescription()));
	      return new ResponseEntity<>(_brand, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	@PutMapping("/brands/{id}")
	public ResponseEntity<Brand> updateTutorial(@PathVariable("id") long id, @RequestBody Brand brand) {
		Optional<Brand> tutorialData = brandRepository.findById(id);

		if (tutorialData.isPresent()) {
			Brand _tutorial = tutorialData.get();
			_tutorial.setTitle(brand.getTitle());
			_tutorial.setDescription(brand.getDescription());			
			return new ResponseEntity<>(brandRepository.save(_tutorial), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/brands/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			brandRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/brands")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			brandRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
