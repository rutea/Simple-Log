package com.my.web.liveapp.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

    public <T> T findById(Class<T> entityClass, Serializable id);

    public <T> T save(T entity);

    public <T> T update(T entity);

    public <T> void delete(T entity);

    public <T> void saveOrUpdate(T entity);

    public <T> List<T> findAll(Class<T> entityClass);

}