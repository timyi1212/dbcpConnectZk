package com.eproe.monitor.was.service.impl;

import com.eproe.monitor.was.dao.WLSThreadPoolDetailDAO;
import com.eproe.monitor.was.model.WLSThreadPoolDetail;
import com.eproe.monitor.was.service.WLSThreadPoolDetailService;
import java.util.List;
import java.util.Map;

public class WLSThreadPoolDetailServiceImpl
        implements WLSThreadPoolDetailService
{
    private WLSThreadPoolDetailDAO wlsThreadPoolDetailDAO;

    public List<WLSThreadPoolDetail> getThreadPoolDetailByDay(Map<String, Object> params)
    {
        return this.wlsThreadPoolDetailDAO.getThreadPoolDetailByDay(params);
    }

    public void setWlsThreadPoolDetailDAO(WLSThreadPoolDetailDAO wlsThreadPoolDetailDAO)
    {
        this.wlsThreadPoolDetailDAO = wlsThreadPoolDetailDAO;
    }

    public List<WLSThreadPoolDetail> getThreadPoolDetailByMonth(Map<String, Object> params)
    {
        return this.wlsThreadPoolDetailDAO.getThreadPoolDetailByMonth(params);
    }

    public List<WLSThreadPoolDetail> getThreadPoolDetailByWeek(Map<String, Object> params)
    {
        return this.wlsThreadPoolDetailDAO.getThreadPoolDetailByWeek(params);
    }
}
