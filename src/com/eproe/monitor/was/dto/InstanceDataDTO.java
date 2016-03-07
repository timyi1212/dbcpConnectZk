package com.eproe.monitor.was.dto;

import java.util.ArrayList;
import java.util.List;

public class InstanceDataDTO
{
    private String serverName;
    private String port;
    private Integer id;
    private String ipAddr;
    private Long memUsage;
    private Long threadPoolSize;
    private Long dsPoolSize;
    private String dsnameconds;
    private List<DataSourceDataDTO> datasourceDTOList = new ArrayList();

    public String getServerName()
    {
        return this.serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getPort() {
        return this.port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIpAddr() {
        return this.ipAddr;
    }
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    public Long getMemUsage() {
        return this.memUsage;
    }
    public void setMemUsage(Long memUsage) {
        this.memUsage = memUsage;
    }
    public Long getThreadPoolSize() {
        return this.threadPoolSize;
    }
    public void setThreadPoolSize(Long threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
    public Long getDsPoolSize() {
        return this.dsPoolSize;
    }
    public void setDsPoolSize(Long dsPoolSize) {
        this.dsPoolSize = dsPoolSize;
    }
    public String getDsnameconds() {
        return this.dsnameconds;
    }
    public void setDsnameconds(String dsnameconds) {
        this.dsnameconds = dsnameconds;
    }
    public List<DataSourceDataDTO> getDatasourceDTOList() {
        return this.datasourceDTOList;
    }
    public void setDatasourceDTOList(List<DataSourceDataDTO> datasourceDTOList) {
        this.datasourceDTOList = datasourceDTOList;
    }
}