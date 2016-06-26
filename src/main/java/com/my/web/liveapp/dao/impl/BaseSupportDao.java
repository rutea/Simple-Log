package com.my.web.liveapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.my.web.liveapp.dao.BaseDao;
import com.my.web.liveapp.model.Page;

@Repository
public class BaseSupportDao extends HibernateDaoSupport implements BaseDao {

    @Resource(name = "sessionFactory")
    public void injectSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T findById(Class<T> entityClass, Serializable id) {
        Assert.notNull(id);
        return (T) getSessionFactory().getCurrentSession().get(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> entityClass) {
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public <T> T save(T entity) {
        Assert.notNull(entity);
        getSessionFactory().getCurrentSession().save(entity);
        return entity;
    }

    @Override
    public <T> T update(T entity) {
        Assert.notNull(entity);
        getSessionFactory().getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public <T> void delete(T entity) {
        Assert.notNull(entity);
        getSessionFactory().getCurrentSession().delete(entity);
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int excute(final String hql, final Object... values) {
        Assert.hasText(hql);
        return (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = createQuery(session, hql, values);
                return query.executeUpdate();
            }
        });

    }

    public Query createQuery(Session session, String hql, Object... values) {
        Assert.hasText(hql);
        Query query = session.createQuery(hql);
        if (getSessionFactory() != null) {
            SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
                    .getResource(getSessionFactory());
            if (sessionHolder != null && sessionHolder.hasTimeout()) {
                query.setTimeout(sessionHolder.getTimeToLiveInSeconds());
            }
        }
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> findPageByCriteria(DetachedCriteria dc, int startIndex, int pageSize) {
        int totalCount = this.countByDeCriteria(dc).intValue();
        Page<T> page = new Page<T>(null, totalCount, startIndex, pageSize);
        List<T> list = (List<T>) getHibernateTemplate().findByCriteria(dc, startIndex, pageSize);
        page.setItems(list);
        return page;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Long countByDeCriteria(final DetachedCriteria dc) {
        return (Long) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = dc.getExecutableCriteria(session);
                CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
                Projection projection = criteriaImpl.getProjection();
                Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
                if (totalCount == null) {
                    totalCount = 0L;
                }
                criteria.setProjection(projection);
                return totalCount;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria criteria) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
    }

}