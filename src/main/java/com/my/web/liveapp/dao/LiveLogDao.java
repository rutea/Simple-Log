package com.my.web.liveapp.dao;

import java.util.Date;

import com.my.web.liveapp.model.LiveLog;
import com.my.web.liveapp.model.Page;

public interface LiveLogDao extends BaseDao {

    public LiveLog add(LiveLog liveLog);

    public void update(String id, String log);

    public void deleteLog(String id);

    public Page<LiveLog> findLog(String log, Date start, Date end, int startIndex, int pageSize);

}