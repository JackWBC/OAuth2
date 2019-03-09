package com.baicheng.oauth2resourceserver.test.mapper;

import com.baicheng.oauth2resourceserver.Oauth2ResourceServerApplicationTests;
import com.baicheng.oauth2resourceserver.mapper.ProductMapper;
import com.baicheng.oauth2resourceserver.model.ProductPO;
import com.baicheng.oauth2resourceserver.model.ProductPOExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 21:13
 */
@Slf4j
public class ProductMapperTest extends Oauth2ResourceServerApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testProductMapper(){
        List<ProductPO> productPOS = productMapper.selectByExample(new ProductPOExample());
        log.info("productPOS: {}", productPOS);
    }
}
