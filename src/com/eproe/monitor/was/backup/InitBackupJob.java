package com.eproe.monitor.was.backup;

/**
 * Created by TimYi on 2016/2/29.
 */
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class InitBackupJob
        implements HttpSessionListener, ServletContextListener, ServletContextAttributeListener
{
    public void sessionCreated(HttpSessionEvent arg0)
    {
    }

    public void sessionDestroyed(HttpSessionEvent arg0)
    {
    }

    public void contextDestroyed(ServletContextEvent arg0)
    {
    }

    public void contextInitialized(ServletContextEvent arg0)
    {
        BackupJobThread backupjob = new BackupJobThread();

        backupjob.start();
    }

    public void attributeAdded(ServletContextAttributeEvent arg0)
    {
    }

    public void attributeRemoved(ServletContextAttributeEvent arg0)
    {
    }

    public void attributeReplaced(ServletContextAttributeEvent arg0)
    {
    }
}