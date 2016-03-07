package com.eproe.monitor.was.backup;

import com.eproe.monitor.was.concurrent.FecthDataTaskDB;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSServerService;
import com.eproe.monitor.was.service.impl.WLSServerServiceImpl;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BackupJobThread extends Thread
{
    public static Log logger = LogFactory.getLog(BackupJobThread.class);
    public static int flag = 0;
    WLSServerService wlsServerService;
    String resource = "spring-datasource.xml";

    InputStream is = BackupJobThread.class.getClassLoader().getResourceAsStream(this.resource);
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(this.is);
    SqlSession session = this.sessionFactory.openSession();

    public void backupJob(int flag)
    {
        this.wlsServerService = new WLSServerServiceImpl();

        List wlsServerList = this.session.selectList("getAllWLSServer");
        System.out.println("长度为" + wlsServerList.size());
        if ((wlsServerList != null) && (wlsServerList.size() > 0))
        {
            ExecutorService exec = Executors.newCachedThreadPool();

            for (int i = 0; i < wlsServerList.size(); i++)
            {
                WLSServer wlsServer = (WLSServer)wlsServerList.get(i);
                exec.submit(new FecthDataTaskDB(flag, wlsServer, this.session));
            }

            exec.shutdown();
        }
    }

    public void run()
    {
        int flag = 0;
        while (true)
        {
            try
            {
                backupJob(flag);
            }
            catch (Exception e) {
                logger.error("执行后台数据库录入线程发生异常", e);
            }
            try {
                flag++;
                Thread.sleep(60000L);
            }
            catch (InterruptedException e) {
                logger.error("录入线程睡眠发生异常", e);
            }
        }
    }
}