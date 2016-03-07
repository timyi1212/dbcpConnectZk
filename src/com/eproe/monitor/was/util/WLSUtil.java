package com.eproe.monitor.was.util;

import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSServerService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WLSUtil
{
    public static Log logger = LogFactory.getLog(WLSUtil.class);
    public static Map<String, JMXConnector> wlsconnMap = new HashMap();
    WLSServerService wlsServerService;

    public MBeanServerConnection fetchWlsConnection(WLSServer wlsServer)
    {
        JMXConnector connector = null;
        MBeanServerConnection connection = null;

        String ipAddr = wlsServer.getIpAddr();
        String port = wlsServer.getPort();
        String username = wlsServer.getUsername();
        String password = wlsServer.getPassword();
        String servername = wlsServer.getServername();
        try
        {
            connector = WLSHelper.getWLSConnection(ipAddr, port, servername, username, password);

            wlsconnMap.put(ipAddr + port, connector);
            connection = connector.getMBeanServerConnection();
        } catch (Exception e) {
            logger.error("WASUtil fetchWlsConnection ERROR: ", e);
        }

        return connection;
    }

    public Long getMemUsage(WLSServer wlsServer)
    {
        MBeanServerConnection wlsconn = fetchWlsConnection(wlsServer);

        Long memUsedMB = Long.valueOf(-1L);
        try {
            ObjectName serverRuntime = WLSHelper.getServerRuntime(wlsconn, wlsServer.getServername());
            ObjectName jvmObjectName = (ObjectName)wlsconn.getAttribute(serverRuntime, "JVMRuntime");
            Long memCurrentSize = Long.valueOf(Long.parseLong(String.valueOf(wlsconn.getAttribute(jvmObjectName, "HeapSizeCurrent"))));

            Long memFreeSize = Long.valueOf(Long.parseLong(String.valueOf(wlsconn.getAttribute(jvmObjectName, "HeapFreeCurrent"))));

            Long memUsed = Long.valueOf(memCurrentSize.longValue() - memFreeSize.longValue());

            memUsedMB = Long.valueOf(memUsed.longValue() / 1024000L);
            memUsedMB = Long.valueOf(new BigDecimal(memUsedMB.longValue()).setScale(0, 4).longValue());
        }
        catch (Exception e)
        {
            logger.error("MemUsage get  ERROR: ", e);
        }
        finally {
            if (wlsconn != null)
            {
                wlsconn = null;
            }
        }
        return memUsedMB;
    }

    public Long getActiveThread(WLSServer wlsServer)
    {
        MBeanServerConnection wlsconn = fetchWlsConnection(wlsServer);

        Long threadActive = Long.valueOf(-1L);

        ObjectName serverRuntime = WLSHelper.getServerRuntime(wlsconn, wlsServer.getServername());
        try {
            ObjectName threadObjectName = (ObjectName)wlsconn.getAttribute(serverRuntime, "ThreadPoolRuntime");
            Long threadPoolSize = Long.valueOf(Long.parseLong(String.valueOf(wlsconn.getAttribute(threadObjectName, "ExecuteThreadTotalCount"))));
            Long threadStandby = Long.valueOf(Long.parseLong(String.valueOf(wlsconn.getAttribute(threadObjectName, "StandbyThreadCount"))));
            threadActive = Long.valueOf(threadPoolSize.longValue() - threadStandby.longValue());
        }
        catch (Exception e) {
            logger.error("active thread pool get ERROR: ", e);
        }
        finally {
            if (wlsconn != null) {
                wlsconn = null;
            }
        }
        return threadActive;
    }

    public Long getDsPoolsSize(WLSServer wlsServer, String dsname)
    {
        MBeanServerConnection wlsconn = fetchWlsConnection(wlsServer);
        String jdbcObjectNameStr = JTMConstant.getJDBCObjectName(dsname, wlsServer.getServername());
        Long dsPoolSize = Long.valueOf(-1L);
        try
        {
            ObjectName jdbcObjectName = new ObjectName(jdbcObjectNameStr);
            dsPoolSize = Long.valueOf(Long.parseLong(String.valueOf(wlsconn.getAttribute(jdbcObjectName, "CurrCapacity"))));
        }
        catch (Exception e) {
            logger.error("dspoolsize get ERROR: ", e);
        }
        finally
        {
            if (wlsconn != null) {
                wlsconn = null;
            }
        }
        return dsPoolSize;
    }
}