package com.eproe.monitor.was.service.impl;

import com.eproe.monitor.was.dao.WLSJVMDetailDAO;
import com.eproe.monitor.was.model.WLSJVMDetail;
import com.eproe.monitor.was.service.WLSJVMDetailService;
import java.util.List;
import java.util.Map;

public class WLSJVMDetailServiceImpl
        implements WLSJVMDetailService
{
    private WLSJVMDetailDAO wlsJVMDetailDAO;

    public void setWlsJVMDetailDAO(WLSJVMDetailDAO wlsJVMDetailDAO)
    {
        this.wlsJVMDetailDAO = wlsJVMDetailDAO;
    }

    public boolean add(WLSJVMDetail jvmDetail)
    {
        return this.wlsJVMDetailDAO.add(jvmDetail);
    }

    public List<WLSJVMDetail> getAllJVMDetail(Map<String, Object> params)
    {
        return null;
    }

    public List<WLSJVMDetail> getJVMDetailByDay(Map<String, Object> params)
    {
        return this.wlsJVMDetailDAO.getJVMDetailByDay(params);
    }

    public List<WLSJVMDetail> getJVMDetailByMonth(Map<String, Object> params)
    {
        return this.wlsJVMDetailDAO.getJVMDetailByMonth(params);
    }

    public List<WLSJVMDetail> getJVMDetailByWeek(Map<String, Object> params)
    {
        return this.wlsJVMDetailDAO.getJVMDetailByWeek(params);
    }
}