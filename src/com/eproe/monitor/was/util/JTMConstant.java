package com.eproe.monitor.was.util;

public class JTMConstant
{
    public static final int REQ_SUCCESS = 200;
    public static final int REQ_ERROR = 10000;
    public static final String TIME_PERIOD_DAY = "day";
    public static final String TIME_PERIOD_WEEK = "week";
    public static final String TIME_PERIOD_MONTH = "month";
    public static final String mbeanServer = "weblogic.management.mbeanservers.runtime";
    public static final String wlsProtocl = "t3";
    public static final String jndiroot = "/jndi/";
    public static final String serviceRuntime = "com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
    public static final String serverRuntime = "ServerRuntime";
    public static final String domainRuntime = "DomainConfiguration";
    public static final String jvmObjectName = "JVMRuntime";
    public static final String heapSizeCurrent = "HeapSizeCurrent";
    public static final String HeapFreeCurrent = "HeapFreeCurrent";
    public static final String ThreadPoolRuntime = "ThreadPoolRuntime";
    public static final String ExecuteThreadTotalCount = "ExecuteThreadTotalCount";
    public static final String StandbyThreadCount = "StandbyThreadCount";
    public static final String jdbcPoolSize = "CurrCapacity";

    public static String getJDBCObjectName(String dsname, String servername)
    {
        String jdbcObjectName = "com.bea:ServerRuntime=" + servername + ",Name=" + dsname + ",Type=JDBCDataSourceRuntime";
        return jdbcObjectName;
    }
}