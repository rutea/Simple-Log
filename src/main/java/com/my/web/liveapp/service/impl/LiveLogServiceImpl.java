package com.my.web.liveapp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.web.liveapp.dao.LiveLogDao;
import com.my.web.liveapp.model.LiveLog;
import com.my.web.liveapp.model.Page;
import com.my.web.liveapp.service.LiveLogService;

@Service("liveLogService")
public class LiveLogServiceImpl implements LiveLogService {

    @Resource(name = "liveLogDao")
    private LiveLogDao liveLogDao;

    @Override
    @Transactional
    public void addLog(LiveLog liveLog) {
        liveLogDao.add(liveLog);
    }

    @Override
    public void updateLog(String id, String log) {
        liveLogDao.update(id, log);
    }

    @Override
    @Transactional
    public void deleteLog(String id) {
        liveLogDao.deleteLog(id);
    }

    @Override
    public Page<LiveLog> findLog(String log, Date start, Date end, int startIndex, int pageSize) {
        return liveLogDao.findLog(log, start, end, startIndex, pageSize);
    }

}