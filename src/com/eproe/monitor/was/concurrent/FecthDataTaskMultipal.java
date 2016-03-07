package com.eproe.monitor.was.concurrent;

import com.eproe.monitor.was.dto.DataSourceDataDTO;
import com.eproe.monitor.was.dto.InstanceDataDTO;
import com.eproe.monitor.was.model.WLSJDBC;
import com.eproe.monitor.was.model.WLSServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FecthDataTaskMultipal
        implements Runnable
{
    public static Log logger = LogFactory.getLog(FecthDataTaskMultipal.class);
    WLSServer wlsServer;
    List<InstanceDataDTO> dataDTOList;
    List<WLSJDBC> wlsJDBCList;

    public FecthDataTaskMultipal(WLSServer wlsServer, List<InstanceDataDTO> dataDTOList, List<WLSJDBC> wlsJDBCList)
    {
        this.wlsServer = wlsServer;
        this.dataDTOList = dataDTOList;
        this.wlsJDBCList = wlsJDBCList;
    }

    public void run()
    {
        InstanceDataDTO dataDTO = new InstanceDataDTO();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        List datasourceDTOList = new ArrayList();
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
                    "jvmUsedMem get error,maybe connection hung: ",
                    e);
        }

        dataDTO.setId(this.wlsServer.getId());
        dataDTO.setPort(this.wlsServer.getPort());
        dataDTO.setIpAddr(this.wlsServer.getIpAddr());
        dataDTO.setServerName(this.wlsServer.getServername());
        dataDTO.setMemUsage(jvmUsedMem);

        Future = executor
                .submit(new ConcurrentFetchThreadAveragePoolSize(
                        this.wlsServer));
        try {
            averageThreadPoolSize = (Long)Future.get(5000L,
                    TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error(
                    "threadpoolsize get error,maybe connection hung: ",
                    e);
        }

        dataDTO.setThreadPoolSize(averageThreadPoolSize);

        String jndiConcatDSPoolSizeM = "";
        if ((this.wlsJDBCList != null) && (this.wlsJDBCList.size() > 0)) {
            for (int j = 0; j < this.wlsJDBCList.size(); j++) {
                DataSourceDataDTO dataSourceDTO = new DataSourceDataDTO();
                WLSJDBC wlsJDBC = (WLSJDBC)this.wlsJDBCList.get(j);
                String dsname = wlsJDBC.getDsName();
                Future = executor
                        .submit(new ConcurrentFetchDSPoolSize(this.wlsServer,
                                wlsJDBC));
                try {
                    dsPoolSize = (Long)Future.get(5000L,
                            TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    logger.error("dspoolsize:" + wlsJDBC.getDsName() +
                            "  get error,maybe connection hung: ", e);
                }

                String dsnameConDSpoolsize = dsname + "=" + String.valueOf(dsPoolSize);
                dataSourceDTO.setDsNameConcatDSPoolSize(dsnameConDSpoolsize);
                datasourceDTOList.add(dataSourceDTO);

                if (j == 0) {
                    jndiConcatDSPoolSizeM = dsname + "=" + String.valueOf(dsPoolSize) + "?";
                }
                else if ((j > 0) && (j != this.wlsJDBCList.size() - 1)) {
                    jndiConcatDSPoolSizeM = jndiConcatDSPoolSizeM + dsname + "=" + String.valueOf(dsPoolSize) + "?";
                }
                else {
                    jndiConcatDSPoolSizeM = jndiConcatDSPoolSizeM + dsname + "=" + String.valueOf(dsPoolSize);
                }
            }

            dataDTO.setDatasourceDTOList(datasourceDTOList);
            dataDTO.setDsnameconds(jndiConcatDSPoolSizeM);
        }

        executor.shutdown();
        this.dataDTOList.add(dataDTO);
    }
}