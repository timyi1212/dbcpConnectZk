package com.eproe.monitor.was.service;

import com.eproe.monitor.was.model.WLSJVMDetail;
import java.util.List;
import java.util.Map;

public abstract interface WLSJVMDetailService
{
    public abstract boolean add(WLSJVMDetail paramWLSJVMDetail);

    public abstract List<WLSJVMDetail> getJVMDetailByDay(Map<String, Object> paramMap);

    public abstract List<WLSJVMDetail> getJVMDetailByWeek(Map<String, Object> paramMap);

    public abstract List<WLSJVMDetail> getJVMDetailByMonth(Map<String, Object> paramMap);

    public abstract List<WLSJVMDetail> getAllJVMDetail(Map<String, Object> paramMap);
}