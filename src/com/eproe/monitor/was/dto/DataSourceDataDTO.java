package com.eproe.monitor.was.dto;

public class DataSourceDataDTO
{
    private String sdName;
    private long dsPoolSize;
    private String dsNameConcatDSPoolSize;
    private Integer id;
    private Integer wlsServerId;

    public String getSdName()
    {
        return this.sdName;
    }
    public void setSdName(String sdName) {
        this.sdName = sdName;
    }
    public long getDsPoolSize() {
        return this.dsPoolSize;
    }
    public void setDsPoolSize(long dsPoolSize) {
        this.dsPoolSize = dsPoolSize;
    }
    public String getDsNameConcatDSPoolSize() {
        return this.dsNameConcatDSPoolSize;
    }
    public void setDsNameConcatDSPoolSize(String dsNameConcatDSPoolSize) {
        this.dsNameConcatDSPoolSize = dsNameConcatDSPoolSize;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getWlsServerId() {
        return this.wlsServerId;
    }
    public void setWlsServerId(Integer wlsServerId) {
        this.wlsServerId = wlsServerId;
    }
}