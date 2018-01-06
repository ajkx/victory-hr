package com.victory.common.domain;

import net.kaczmarzyk.spring.data.jpa.domain.PathSpecification;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;
import net.kaczmarzyk.spring.data.jpa.utils.QueryContext;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 实现NotEqual的注解条件
 * 废弃，原框架已更新1.1.1 有了这个支持
 * Created by ajkx
 * Date: 2017/9/22.
 * Time:10:31
 */
@Deprecated
public class NotEqual<T> extends PathSpecification<T> {

    protected String notExpectedValue;
    private Converter converter;

    public NotEqual(QueryContext queryContext, String path) {
        super(queryContext, path);
    }

//    public NotEqual(String path, String[] httpParamValues, Converter converter) {
//        super(path);
//        if (httpParamValues == null || httpParamValues.length != 1) {
//            throw new IllegalArgumentException();
//        }
//        this.notExpectedValue = httpParamValues[0];
//        this.converter = converter;
//    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Class<?> typeOnPath = path(root).getJavaType();
        return cb.notEqual(path(root), converter.convert(notExpectedValue, typeOnPath));
    }
}
