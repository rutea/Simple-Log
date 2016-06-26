package com.my.web.liveapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.my.web.liveapp.model.LiveLog;
import com.my.web.liveapp.model.Page;
import com.my.web.liveapp.service.LiveLogService;

@RestController
public class LiveLogController {

    @Resource(name = "liveLogService")
    private LiveLogService liveLogService;

    private final DateFormat FORMATTOR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/query")
    public String queryLog(HttpServletRequest request) {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String log = request.getParameter("log");
        String startIndexStr = request.getParameter("startIndex");
        String pageSizeStr = request.getParameter("pageSize");

        Date startDate = null;
        Date endDate = null;
        int startIndex = 0;
        int pageSize = 0;
        try {
            if (startDateStr != null && startDateStr != "") {
                startDate = FORMATTOR.parse(startDateStr + " 00:00:00");
            }
            if (endDateStr != null && endDateStr != "") {
                endDate = FORMATTOR.parse(endDateStr + " 23:59:59");
            }
            if (startIndexStr != null && startIndexStr != "") {
                startIndex = Integer.valueOf(startIndexStr);
            }
            if (pageSizeStr != null && pageSizeStr != "") {
                pageSize = Integer.valueOf(pageSizeStr);
            }
        } catch (Exception e) {
        }

        Page<LiveLog> liveLogs = liveLogService.findLog(log, startDate, endDate, startIndex, pageSize);

        return JSON.toJSONString(liveLogs);
    }

    @RequestMapping(value = "/add")
    public void addLog(HttpServletRequest request) {
        String editDateStr = request.getParameter("editDate");
        String log = request.getParameter("editLog");

        Date date = null;
        try {
            if (editDateStr != null && editDateStr != "") {
                date = FORMATTOR.parse(editDateStr + " 00:00:00");
            }
        } catch (Exception e) {
        }

        LiveLog liveLog = new LiveLog();
        liveLog.setTime(date);
        liveLog.setLog(log);
        liveLogService.addLog(liveLog);
    }

    @RequestMapping(value = "/update")
    public void updateLog(HttpServletRequest request) {
        String editId = request.getParameter("editId");
        String editLog = request.getParameter("editLog");

        liveLogService.updateLog(editId, editLog);
    }

    @RequestMapping(value = "/delete")
    public void deleteLog(HttpServletRequest request) {
        String logId = request.getParameter("id");
        liveLogService.deleteLog(logId);
    }

}