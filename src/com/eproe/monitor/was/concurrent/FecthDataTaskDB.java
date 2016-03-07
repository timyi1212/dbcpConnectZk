package com.eproe.monitor.was.concurrent;

import com.eproe.monitor.was.model.WLSJDBC;
import com.eproe.monitor.was.model.WLSJDBCDetail;
import com.eproe.monitor.was.model.WLSJVMDetail;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.model.WLSThreadPoolDetail;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

public class FecthDataTaskDB
        implements Runnable
{
    public static Log logger = LogFactory.getLog(FecthDataTaskDB.class);
    WLSServer wlsServer;
    int flag;
    SqlSession session;

    public FecthDataTaskDB(int flag, WLSServer wlsServer, SqlSession session)
    {
        this.wlsServer = wlsServer;

        this.session = session;

        this.flag = flag;
    }

    public void insertWLSJVMDetail(Integer wlsServerId, Long memUsage)
    {
        WLSJVMDetail jvmDetail = new WLSJVMDetail();

        jvmDetail.setCreateTime(new Date());
        jvmDetail.setUpdatedTime(new Date());
        jvmDetail.setCreator("sys");
        jvmDetail.setModifier("sys");
        jvmDetail.setIsDeleted("n");
        jvmDetail.setWlsServerId(wlsServerId);
        jvmDetail.setUsedMemory(memUsage);

        this.session.insert("insertWLSJVMDetail", jvmDetail);
    }

    public void insertWLSThreadPoolDetail(Integer wlsServerId, Long threadPoolSize)
    {
        WLSThreadPoolDetail threadPoolDetail = new WLSThreadPoolDetail();

        threadPoolDetail.setCreateTime(new Date());
        threadPoolDetail.setUpdatedTime(new Date());
        threadPoolDetail.setCreator("sys");
        threadPoolDetail.setModifier("sys");
        threadPoolDetail.setIsDeleted("n");
        threadPoolDetail.setWlsServerId(wlsServerId);
        threadPoolDetail.setActiveThreadCount(threadPoolSize);
        this.session.insert("insertWLSThreadPoolDetail", threadPoolDetail);
    }

    public void insertWLSJDBCDetail(Integer wlsJDBCId, Long dsPoolSize) {
        WLSJDBCDetail jdbcDetail = new WLSJDBCDetail();
        jdbcDetail.setIsDeleted("n");
        jdbcDetail.setCreateTime(new Date());
        jdbcDetail.setUpdatedTime(new Date());
        jdbcDetail.setCreator("sys");
        jdbcDetail.setModifier("sys");
        jdbcDetail.setWlsJDBCId(wlsJDBCId);
        jdbcDetail.setPoolSize(dsPoolSize);
        this.session.insert("insertWLSJDBCDetail", jdbcDetail);
    }

    public void run()
    {
        if (this.flag == 0) {
            ExecutorService executor = Executors.newFixedThreadPool(1);

            Map params = new HashMap();
            Future Future = executor.submit(new ConcurrentFetchJVMUsedMem(this.wlsServer));
            Long jvmUsedMem = Long.valueOf(-1L);
            Long averageThreadPoolSize = Long.valueOf(-1L);
            Long dsPoolSize = Long.valueOf(-1L);
            try
            {
                jvmUsedMem = (Long)Future.get(3000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                logger.error(
                        "jvmUsedMem get error,maybe adminclient fetch error: ",
                        e);
            }

            Future = executor
                    .submit(new ConcurrentFetchThreadAveragePoolSize(
                            this.wlsServer));
            try {
                averageThreadPoolSize = (Long)Future.get(3000L,
                        TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                logger.error(
                        "threadpoolsize get error,maybe adminclient fetch error: ",
                        e);
            }

            params = new HashMap();
            params.put("wlsServerId", this.wlsServer.getId());
            List wlsJDBCList = this.session
                    .selectList("getWLSJDBCById", params);
            if ((wlsJDBCList != null) && (wlsJDBCList.size() > 0)) {
                for (int j = 0; j < wlsJDBCList.size(); j++) {
                    WLSJDBC wlsJDBC = (WLSJDBC)wlsJDBCList.get(j);
                    String dsName = wlsJDBC.getDsName();

                    Future = executor
                            .submit(new ConcurrentFetchDSPoolSize(this.wlsServer,
                                    wlsJDBC));
                    try {
                        dsPoolSize = (Long)Future.get(3000L,
                                TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        logger.error("dspoolsize:" + dsName +
                                "  get error: ", e);
                    }

                }

            }

            executor.shutdown();
        }
        else if (this.flag > 0)
        {
            ExecutorService executor = Executors.newFixedThreadPool(1);

            Map params = new HashMap();
            Future Future = executor.submit(new ConcurrentFetchJVMUsedMem(this.wlsServer));
            Long jvmUsedMem = Long.valueOf(-1L);
            Long averageThreadPoolSize = Long.valueOf(-1L);
            Long dsPoolSize = Long.valueOf(-1L);
            try
            {
                jvmUsedMem = (Long)Future.get(5000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                logger.error(
                        "jvmUsedMem get error,maybe adminclient fetch error: ",
                        e);
            }

            insertWLSJVMDetail(this.wlsServer.getId(), jvmUsedMem);

            Future = executor
                    .submit(new ConcurrentFetchThreadAveragePoolSize(
                            this.wlsServer));
            try {
                averageThreadPoolSize = (Long)Future.get(5000L,
                        TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                logger.error(
                        "threadpoolsize get error,maybe adminclient fetch error: ",
                        e);
            }

            insertWLSThreadPoolDetail(this.wlsServer.getId(),
                    averageThreadPoolSize);

            params = new HashMap();
            params.put("wlsServerId", this.wlsServer.getId());
            List wlsJDBCList = this.session
                    .selectList("getWLSJDBCById", params);
            if ((wlsJDBCList != null) && (wlsJDBCList.size() > 0)) {
                for (int j = 0; j < wlsJDBCList.size(); j++) {
                    WLSJDBC wlsJDBC = (WLSJDBC)wlsJDBCList.get(j);
                    String dsName = wlsJDBC.getDsName();

                    Future = executor
                            .submit(new ConcurrentFetchDSPoolSize(this.wlsServer,
                                    wlsJDBC));
                    try {
                        dsPoolSize = (Long)Future.get(5000L,
                                TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        logger.error("dspoolsize:" + dsName +
                                "  get error: ", e);
                    }

                    insertWLSJDBCDetail(wlsJDBC.getId(), dsPoolSize);
                }

            }

            this.session.commit();

            executor.shutdown();
        }
    }
}