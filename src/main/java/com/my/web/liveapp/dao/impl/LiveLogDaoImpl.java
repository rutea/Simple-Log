package com.my.web.liveapp.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.my.web.liveapp.dao.LiveLogDao;
import com.my.web.liveapp.model.LiveLog;
import com.my.web.liveapp.model.Page;

@Repository("liveLogDao")
public class LiveLogDaoImpl extends BaseSupportDao implements LiveLogDao {

    @Override
    public LiveLog add(LiveLog liveLog) {
        return super.save(liveLog);
    }

    @Override
    public void update(String id, String log) {
        String hql = "update " + LiveLog.class.getName() + " m set m.log=? where m.id=?";
        super.excute(hql, log, Integer.valueOf(id));
    }

    @Override
    public void deleteLog(String id) {
        String hql = "delete from " + LiveLog.class.getName() + " m where m.id=?";
        super.excute(hql, Integer.valueOf(id));
    }

    @Override
    public Page<LiveLog> findLog(String log, Date start, Date end, int startIndex, int pageSize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(LiveLog.class);
        if (StringUtils.isNotBlank(log)) {
            criteria.add(Restrictions.like("log", log, MatchMode.ANYWHERE));
        }
        if (start != null) {
            criteria.add(Restrictions.ge("time", start));
        }
        if (end != null) {
            criteria.add(Restrictions.le("time", end));
        }
        criteria.addOrder(Order.desc("time"));
        return super.findPageByCriteria(criteria, startIndex, pageSize);
    }

}