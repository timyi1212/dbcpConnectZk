package com.eproe.monitor.was.util;

public class ReturnCode
{
    private int code;
    private String msg;
    private Object data;
    private Object downloadurl;

    public Object getDownloadurl()
    {
        return this.downloadurl;
    }
    public void setDownloadurl(Object downloadurl) {
        this.downloadurl = downloadurl;
    }

    public int getCode()
    {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return this.data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}