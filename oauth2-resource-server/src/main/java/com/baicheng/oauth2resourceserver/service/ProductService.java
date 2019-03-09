package com.baicheng.oauth2resourceserver.service;

import com.baicheng.oauth2resourceserver.bos.ProductBO;
import com.baicheng.oauth2resourceserver.mapper.ProductMapper;
import com.baicheng.oauth2resourceserver.model.ProductPO;
import com.baicheng.oauth2resourceserver.model.ProductPOExample;
import com.baicheng.oauth2resourceserver.utils.BOPOParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 22:07
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<ProductBO> getAllProducts(){
        try {
            List<ProductPO> productPOS = productMapper.selectByExample(new ProductPOExample());
            return BOPOParser.parseFromPOs(productPOS, ProductBO.class);
        } catch (Exception e){
            log.error("[ProductService getAllProducts] error", e);
            return new ArrayList<>();
        }
    }

    public ProductBO findById(Integer id){
        try {
            ProductPO productPO = productMapper.selectByPrimaryKey(id);
            return BOPOParser.parseFromPO(productPO, ProductBO.class);
        } catch (Exception e){
            log.error("[ProductService findById] error, id: {}", id, e);
            return null;
        }
    }
}
