package com.eproe.monitor.was.service;

import com.eproe.monitor.was.model.WLSJDBC;
import java.util.List;
import java.util.Map;

public abstract interface WLSJDBCService
{
    public abstract List<WLSJDBC> getALLWLSJDBC();

    public abstract int getdsCountByWLSIDDSName(Map<String, Object> paramMap);

    public abstract boolean addWLSJDBC(WLSJDBC paramWLSJDBC);

    public abstract boolean deleteWLSJDBC(Map<String, Object> paramMap);

    public abstract List<WLSJDBC> getWLSJDBCById(Map<String, Object> paramMap);
}