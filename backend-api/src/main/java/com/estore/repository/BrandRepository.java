package com.estore.repository;

import com.estore.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByTitle(String title);

    List<Brand> findByUserId(Long id);
}
