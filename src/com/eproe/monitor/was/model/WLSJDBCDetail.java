package com.eproe.monitor.was.model;

import java.util.Date;

public class WLSJDBCDetail
{
    private Integer id;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date updatedTime;
    private String isDeleted;
    private Integer wlsJDBCId;
    private Long poolSize;

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
    public Integer getWlsJDBCId() {
        return this.wlsJDBCId;
    }
    public void setWlsJDBCId(Integer wlsJDBCId) {
        this.wlsJDBCId = wlsJDBCId;
    }
    public Long getPoolSize() {
        return this.poolSize;
    }
    public void setPoolSize(Long poolSize) {
        this.poolSize = poolSize;
    }
}