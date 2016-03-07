package com.eproe.monitor.was.service;

import com.eproe.monitor.was.model.WLSThreadPoolDetail;
import java.util.List;
import java.util.Map;

public abstract interface WLSThreadPoolDetailService
{
    public abstract List<WLSThreadPoolDetail> getThreadPoolDetailByDay(Map<String, Object> paramMap);

    public abstract List<WLSThreadPoolDetail> getThreadPoolDetailByWeek(Map<String, Object> paramMap);

    public abstract List<WLSThreadPoolDetail> getThreadPoolDetailByMonth(Map<String, Object> paramMap);
}