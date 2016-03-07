package com.eproe.monitor.was.dto;

public class InstanceDataDTONew
{
    private Integer id;
    private String ipAddr;
    private String memUsage;
    private String threadPoolSize;
    private String dsPoolSize;
    private String jndiconds;

    public Integer getId()
    {
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
    public String getMemUsage() {
        return this.memUsage;
    }
    public void setMemUsage(String memUsage) {
        this.memUsage = memUsage;
    }
    public String getThreadPoolSize() {
        return this.threadPoolSize;
    }
    public void setThreadPoolSize(String threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
    public String getDsPoolSize() {
        return this.dsPoolSize;
    }
    public void setDsPoolSize(String dsPoolSize) {
        this.dsPoolSize = dsPoolSize;
    }
    public String getJndiconds() {
        return this.jndiconds;
    }
    public void setJndiconds(String jndiconds) {
        this.jndiconds = jndiconds;
    }
}