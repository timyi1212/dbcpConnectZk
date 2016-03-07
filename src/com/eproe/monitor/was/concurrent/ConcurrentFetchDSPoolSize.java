package com.eproe.monitor.was.concurrent;

/**
 * Created by TimYi on 2016/2/29.
 */
import com.eproe.monitor.was.model.WLSJDBC;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.util.WLSUtil;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConcurrentFetchDSPoolSize
        implements Callable<Long>
{
    public static Log logger = LogFactory.getLog(ConcurrentFetchDSPoolSize.class);
    WLSServer wlsServer;
    WLSJDBC wlsJDBC;

    public ConcurrentFetchDSPoolSize(WLSServer wlsServer, WLSJDBC wlsJDBC)
    {
        this.wlsServer = wlsServer;
        this.wlsJDBC = wlsJDBC;
    }

    public Long call()
    {
        WLSUtil wlsUtil = new WLSUtil();
        long dsPoolSize = wlsUtil.getDsPoolsSize(this.wlsServer, this.wlsJDBC.getDsName()).longValue();
        return Long.valueOf(dsPoolSize);
    }
}