package com.estore.controller;

import com.estore.dto.BrandDto;
import com.estore.entity.Brand;
import com.estore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandController {
    @Autowired
    BrandService brandService;

    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','CLIENT')")
    @GetMapping("/brands")
    public List<Brand> index(){
        List<Brand> brandList =  brandService.index();

        return brandList;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/brands")
    public ResponseEntity<String> store(@RequestBody BrandDto brandDto){
        brandService.store(brandDto);

        return new ResponseEntity<>("Brand added!", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','CLIENT')")
    @GetMapping("/brands/{id}")
    public Brand show(@PathVariable Long id){
        Brand brand =  brandService.show(id);

        return brand;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/brands/{id}")
    public ResponseEntity<String> update(@RequestBody BrandDto brandDto, @PathVariable Long id){
        brandService.update(brandDto, id);
        return new ResponseEntity<>("Brand updated!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<String> delete( @PathVariable Long id){
        brandService.delete(id);
        return new ResponseEntity<>("Brand deleted!", HttpStatus.OK);
    }
}
