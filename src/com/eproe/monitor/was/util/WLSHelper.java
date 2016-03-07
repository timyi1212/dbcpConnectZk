package com.eproe.monitor.was.util;

import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WLSHelper
{
    public static Log logger = LogFactory.getLog(WLSHelper.class);

    public static JMXConnector getWLSConnection(String ip, String port, String serverName, String username, String password)
    {
        Integer portInteger = Integer.valueOf(port);
        int portInt = portInteger.intValue();
        JMXServiceURL serviceURL = null;
        JMXConnector connector = null;
        MBeanServerConnection connection = null;
        try {
            serviceURL = new JMXServiceURL("t3", ip, portInt, "/jndi/weblogic.management.mbeanservers.runtime");
        }
        catch (Exception e) {
            logger.error("JMXServiceURL get error: ", e);
        }
        Hashtable h = new Hashtable();
        h.put("java.naming.security.principal", username);
        h.put("java.naming.security.credentials", password);
        h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
        try {
            connector = JMXConnectorFactory.connect(serviceURL, h);
        } catch (Exception e) {
            logger.error("JMXConnector get error: ", e);
        }

        return connector;
    }

    public static ObjectName getServerRuntime(MBeanServerConnection wlsconnection, String servername) {
        ObjectName serviceRuntime = null;
        ObjectName serverRuntime = null;
        try {
            serviceRuntime = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
        } catch (Exception e) {
            logger.info("serviceRuntime ObjectName get error", e);
        }
        try {
            serverRuntime = (ObjectName)wlsconnection.getAttribute(serviceRuntime, "ServerRuntime");
        } catch (Exception e) {
            logger.info("serverRuntime ObjectName get error", e);
        }
        return serverRuntime;
    }

    public static ObjectName getDomainRuntime(MBeanServerConnection wlsconnection, String servername) {
        ObjectName serviceRuntime = null;
        ObjectName domainRuntime = null;
        try {
            serviceRuntime = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean" + servername);
        } catch (Exception e) {
            logger.info("serviceRuntime ObjectName get error", e);
        }
        try {
            domainRuntime = (ObjectName)wlsconnection.getAttribute(serviceRuntime, "DomainConfiguration");
        } catch (Exception e) {
            logger.info("domainRuntime ObjectName get error", e);
        }
        return domainRuntime;
    }
}