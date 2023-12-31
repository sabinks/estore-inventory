package com.estore.service;

import com.estore.dto.BrandDto;
import com.estore.entity.Brand;
import com.estore.entity.User;
import com.estore.entity.Product;
import com.estore.repository.BrandRepository;
import com.estore.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserRepository userRepository;

    public List<Brand> index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> user = userRepository.findByEmail(auth.getPrincipal().toString());
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CLIENT"))) {
            List<Brand> brandList = brandRepository.findByUserId(user.get(0).getId());

            return brandList;
        }

        List<Brand> brandList = brandRepository.findAll();

        return brandList;
    }

    @Transactional
    public void store(BrandDto brandDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Brand brand = new Brand();
        brand.setTitle(brandDto.getTitle());
        brand.setSummary(brandDto.getSummary());
        brand.setContent(brandDto.getContent());

        List<User> userList = userRepository.findByEmail(auth.getName());
        brand.setUser(userList.get(0));

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        brand.setCreatedAt(strDate);
        brand.setUpdatedAt(strDate);

        List<Brand> brandList = brandRepository.findByTitle(brand.getTitle());

        if (brandList.isEmpty()) {
            brandRepository.save(brand);
        } else {
            throw new EntityExistsException("Brand title used!");
        }
    }

    public Brand show(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {

            throw new EntityNotFoundException("Brand not found!");
        }

        return brand.get();

    }

    @Transactional
    public void update(BrandDto brandDto, Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {

            throw new EntityNotFoundException("Brand not found!");
        }
        List<Brand> brandList = brandRepository.findByTitle(brandDto.getTitle());
        if (brandList.size() >= 1) {

            throw new EntityExistsException("Brand title used!");
        }
        brand.get().setTitle(brandDto.getTitle());
        brand.get().setSummary(brandDto.getSummary());
        brand.get().setContent(brandDto.getContent());
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        brand.get().setUpdatedAt(strDate);

        brandRepository.save(brand.get());

    }

    @Transactional
    public void delete(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {

            throw new EntityNotFoundException("Brand not found!");
        }
        List<Product> products = brand.get().getProductList();
        if (products.size() >= 1) {

            throw new EntityExistsException("Brand used in product, cannot delete");
        }

        brandRepository.delete(brand.get());
    }


}
