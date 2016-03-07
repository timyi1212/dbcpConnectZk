package com.eproe.monitor.was.model;

import java.util.Date;

public class WLSServer
{
    private Integer id;
    private String creator;
    private String modifier;
    private Date createTime;
    private Date updatedTime;
    private String isDeleted;
    private String ipAddr;
    private String port;
    private String servername;
    private String username;
    private String password;

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
    public String getIpAddr() {
        return this.ipAddr;
    }
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    public String getPort() {
        return this.port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getServername() {
        return this.servername;
    }
    public void setServername(String servername) {
        this.servername = servername;
    }
}