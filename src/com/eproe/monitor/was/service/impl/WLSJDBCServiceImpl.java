package com.eproe.monitor.was.service.impl;

import com.eproe.monitor.was.dao.WLSJDBCDAO;
import com.eproe.monitor.was.model.WLSJDBC;
import com.eproe.monitor.was.service.WLSJDBCService;
import java.util.List;
import java.util.Map;

public class WLSJDBCServiceImpl
        implements WLSJDBCService
{
    private WLSJDBCDAO wlsJDBCDAO;

    public void setWlsJDBCDAO(WLSJDBCDAO wlsJDBCDAO)
    {
        this.wlsJDBCDAO = wlsJDBCDAO;
    }

    public List<WLSJDBC> getALLWLSJDBC()
    {
        return this.wlsJDBCDAO.getALLWLSJDBC();
    }

    public int getdsCountByWLSIDDSName(Map<String, Object> params)
    {
        return this.wlsJDBCDAO.getDSCountByWLSIDDSName(params);
    }

    public boolean addWLSJDBC(WLSJDBC wlsJDBC)
    {
        return this.wlsJDBCDAO.addWLSJDBC(wlsJDBC);
    }

    public boolean deleteWLSJDBC(Map<String, Object> params) {
        return this.wlsJDBCDAO.deleteWLSJDBC(params);
    }

    public List<WLSJDBC> getWLSJDBCById(Map<String, Object> params)
    {
        return this.wlsJDBCDAO.getWLSJDBCById(params);
    }
}