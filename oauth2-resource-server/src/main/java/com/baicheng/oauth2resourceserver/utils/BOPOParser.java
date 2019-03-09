package com.baicheng.oauth2resourceserver.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author baicheng
 * @description
 * @create 2019-02-27 22:19
 */
public final class BOPOParser {


    private BOPOParser(){}

    /**
     * BO 2 PO 转换
     * @param BO
     * @param clazz
     * @param <BO>
     * @param <PO>
     * @return
     */
    public static <BO, PO> PO parseFromBO(BO BO,Class<PO> clazz){
        if (BO == null){
            return null;
        }
        try {
            PO p = clazz.newInstance();
            BeanUtils.copyProperties(BO, p);
            return p;
        } catch (Exception e) {
            throw new IllegalStateException("instace fail: " + BO,e);
        }
    }

    /**
     * BOS 2 POS 转换
     * @param bos
     * @param clazz
     * @param <BO>
     * @param <PO>
     * @return
     */
    public static <BO,PO> List<PO> parseFromBOs(List<BO> bos, Class<PO> clazz){
        if (CollectionUtils.isEmpty(bos)){
            return Collections.emptyList();
        }
        return bos.stream().filter(Objects::nonNull)
                .map(item -> parseFromBO(item, clazz))
                .collect(Collectors.toList());
    }

    /**
     * PO 2 BO 转换
     * @param p
     * @param clazz
     * @param <BO>
     * @param <PO>
     * @return
     */
    public static <BO, PO> BO parseFromPO(PO p,Class<BO> clazz){
        if (p == null){
            return null;
        }
        try {
            BO b = clazz.newInstance();
            BeanUtils.copyProperties(p,b);
            return b;
        } catch (Exception e) {
            throw new IllegalStateException("instace fail: " + p,e);
        }

    }

    /**
     * POS 2 BOS 转换
     * @param pos
     * @param clazz
     * @param <BO>
     * @param <PO>
     * @return
     */
    public static <BO,PO> List<BO> parseFromPOs(List<PO>pos,Class<BO> clazz){
        if (CollectionUtils.isEmpty(pos)){
            return Collections.emptyList();
        }
        return pos.stream().filter(Objects::nonNull)
                .map(item -> parseFromPO(item, clazz))
                .collect(Collectors.toList());
    }
}
