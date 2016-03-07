package com.eproe.monitor.was.service;

import com.eproe.monitor.was.model.WLSJDBCDetail;
import java.util.List;
import java.util.Map;

public abstract interface WLSJDBCDetailService
{
    public abstract List<WLSJDBCDetail> getJDBCDetailByDay(Map<String, Object> paramMap);

    public abstract List<WLSJDBCDetail> getJDBCDetailByWeek(Map<String, Object> paramMap);

    public abstract List<WLSJDBCDetail> getJDBCDetailByMonth(Map<String, Object> paramMap);
}