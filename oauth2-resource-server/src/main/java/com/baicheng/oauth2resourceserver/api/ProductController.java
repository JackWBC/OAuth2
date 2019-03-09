package com.baicheng.oauth2resourceserver.api;

import com.alibaba.fastjson.JSON;
import com.baicheng.oauth2resourceserver.bos.ProductBO;
import com.baicheng.oauth2resourceserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 22:05
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @RequestMapping("/all")
//    public ResponseEntity<List<ProductBO>> findAll(){
//        List<ProductBO> products = productService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }

    @RequestMapping("/all")
    public String findAll(){
        List<ProductBO> products = productService.getAllProducts();
        return JSON.toJSONString(products);
    }

//    @RequestMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PRODUCT_ADMIN123')")
//    public ResponseEntity<ProductBO> findById(@PathVariable("id") Integer id){
//        if (null == id){
//            return ResponseEntity.of(Optional.empty());
//        }
//        return ResponseEntity.ok(productService.findById(id));
//
//    }
}