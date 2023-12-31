package com.estore.service;

import com.estore.entity.Brand;
import com.estore.entity.User;
import com.estore.dto.ProductDto;
import com.estore.entity.Product;
import com.estore.repository.BrandRepository;
import com.estore.repository.ProductRepository;
import com.estore.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository ;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Transactional
    public void store(ProductDto productDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Product product  = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductCode(productDto.getProductCode());
        product.setBarCode(productDto.getBarCode());

        Optional<Brand> brand = brandRepository.findById(productDto.getBrandId());
        if(brand.isEmpty()){
           throw new EntityExistsException("Brand not found");
        }

        product.setBrand(brand.get());


        List<User> userList = userRepository.findByEmail(auth.getName());
        product.setUserList(userList);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        product.setCreatedAt(strDate);
        List<Product> products = productRepository.findByProductName(product.getProductName());

        if (products.isEmpty()) {
            productRepository.save(product);
        }else{
            throw new EntityExistsException("Product name used!");
        }
    }
}
