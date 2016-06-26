package com.my.web.liveapp.service;

import java.util.Date;

import com.my.web.liveapp.model.LiveLog;
import com.my.web.liveapp.model.Page;

public interface LiveLogService {

    public void addLog(LiveLog liveLog);

    public void updateLog(String id, String log);

    public void deleteLog(String id);

    public Page<LiveLog> findLog(String log, Date start, Date end, int startIndex, int pageSize);

}