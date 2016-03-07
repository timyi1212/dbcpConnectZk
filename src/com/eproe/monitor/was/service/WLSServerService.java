package com.eproe.monitor.was.service;

import com.eproe.monitor.was.model.WLSServer;
import java.util.List;
import java.util.Map;

public abstract interface WLSServerService
{
    public abstract List<WLSServer> getAllWLSServer();

    public abstract WLSServer getWLSSServerById(Map<String, Object> paramMap);

    public abstract int getWLSServerCountByIPPort(Map<String, Object> paramMap);

    public abstract boolean updateWLSServer(WLSServer paramWLSServer);

    public abstract boolean deleteWLSServer(Map<String, Object> paramMap);

    public abstract boolean addWLSServer(WLSServer paramWLSServer);
}