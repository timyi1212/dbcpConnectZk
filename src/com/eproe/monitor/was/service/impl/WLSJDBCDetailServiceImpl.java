package com.eproe.monitor.was.service.impl;

import com.eproe.monitor.was.dao.WLSJDBCDetailDAO;
import com.eproe.monitor.was.model.WLSJDBCDetail;
import com.eproe.monitor.was.service.WLSJDBCDetailService;
import java.util.List;
import java.util.Map;

public class WLSJDBCDetailServiceImpl
        implements WLSJDBCDetailService
{
    private WLSJDBCDetailDAO wlsJDBCDetailDAO;

    public void setWlsJDBCDetailDAO(WLSJDBCDetailDAO wlsJDBCDetailDAO)
    {
        this.wlsJDBCDetailDAO = wlsJDBCDetailDAO;
    }

    public List<WLSJDBCDetail> getJDBCDetailByDay(Map<String, Object> params)
    {
        return this.wlsJDBCDetailDAO.getJDBCDetailByDay(params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByMonth(Map<String, Object> params)
    {
        return this.wlsJDBCDetailDAO.getJDBCDetailByMonth(params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByWeek(Map<String, Object> params)
    {
        return this.wlsJDBCDetailDAO.getJDBCDetailByWeek(params);
    }
}