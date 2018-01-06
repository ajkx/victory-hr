package com.victory.common.service;

import com.victory.common.entity.AbstractEntity;
import com.victory.common.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:13:56
 */
public class BaseService<T extends AbstractEntity,ID extends Serializable> {

    @Autowired
    protected BaseRepository<T, ID> baseRepository;

    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return  返回保存后的实体
     */
    public T save(T t) {
        return baseRepository.save(t);
    }

    public T saveAndFlush(T t) {
        return baseRepository.saveAndFlush(t);
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    public T findOne(ID id) {
        return baseRepository.findOne(id);
    }

    /**
     * 更新实体
     * @param t
     * @return
     */
    public T update(T t) {
        return baseRepository.save(t);
    }

    public void delete(ID id) {
        baseRepository.delete(id);
    }

    public void delete(T t) {
        baseRepository.delete(t);
    }

    public long count() {
        return baseRepository.count();
    }

    public Object packEntity(T t) {
        return t;
    }
}
