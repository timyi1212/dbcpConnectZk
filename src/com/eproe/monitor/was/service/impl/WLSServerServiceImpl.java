package com.eproe.monitor.was.service.impl;

import com.eproe.monitor.was.dao.WLSServerDAO;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSServerService;
import java.util.List;
import java.util.Map;

public class WLSServerServiceImpl
        implements WLSServerService
{
    private WLSServerDAO wlsServerDAO;

    public void setWlsServerDAO(WLSServerDAO wlsServerDAO)
    {
        this.wlsServerDAO = wlsServerDAO;
    }

    public List<WLSServer> getAllWLSServer()
    {
        return this.wlsServerDAO.getAllWLSServer();
    }

    public WLSServer getWLSSServerById(Map<String, Object> params)
    {
        return this.wlsServerDAO.getWLSServerById(params);
    }

    public int getWLSServerCountByIPPort(Map<String, Object> params)
    {
        return this.wlsServerDAO.getWLSServerCountByParamsIPPort(params);
    }

    public boolean addWLSServer(WLSServer wlsServer)
    {
        return this.wlsServerDAO.addWLSServer(wlsServer);
    }

    public boolean deleteWLSServer(Map<String, Object> params)
    {
        return this.wlsServerDAO.deleteWLSServer(params);
    }

    public boolean updateWLSServer(WLSServer wlsServer)
    {
        return this.wlsServerDAO.updateWLSServer(wlsServer);
    }
}