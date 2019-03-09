package com.baicheng.oauth2resourceserver.bos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 22:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBO implements Serializable {
    private static final long serialVersionUID = 2434031845931403365L;

    private Integer id;
    private Integer version;
    private Boolean isDeleted;
    private String name;
}
