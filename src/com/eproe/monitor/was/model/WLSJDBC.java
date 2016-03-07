package com.eproe.monitor.was.model;

import java.util.Date;

public class WLSJDBC
{
    private Integer id;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date updatedTime;
    private String isDeleted;
    private Integer wlsServerId;
    private String wlsServerIp;
    private String dsName;
    public String wlsServerName;

    public Integer getId()
    {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getModifier() {
        return this.modifier;
    }
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdatedTime() {
        return this.updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    public String getIsDeleted() {
        return this.isDeleted;
    }
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Integer getWlsServerId() {
        return this.wlsServerId;
    }
    public void setWlsServerId(Integer wlsServerId) {
        this.wlsServerId = wlsServerId;
    }
    public String getWlsServerIp() {
        return this.wlsServerIp;
    }
    public void setWlsServerIp(String wlsServerIp) {
        this.wlsServerIp = wlsServerIp;
    }
    public String getDsName() {
        return this.dsName;
    }
    public void setDsName(String dsName) {
        this.dsName = dsName;
    }
    public String getWlsServerName() {
        return this.wlsServerName;
    }
    public void setWlsServerName(String wlsServerName) {
        this.wlsServerName = wlsServerName;
    }
}