package com.eproe.monitor.was.concurrent;

/**
 * Created by TimYi on 2016/2/29.
 */
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.util.WLSUtil;
import java.util.concurrent.Callable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConcurrentFetchJVMUsedMem
        implements Callable<Long>
{
    public static Log logger = LogFactory.getLog(ConcurrentFetchJVMUsedMem.class);
    WLSServer wlsServer;

    public ConcurrentFetchJVMUsedMem(WLSServer wlsServer)
    {
        this.wlsServer = wlsServer;
    }

    public Long call()
    {
        WLSUtil wasUtil = new WLSUtil();
        long jvmUsedMem = wasUtil.getMemUsage(this.wlsServer).longValue();
        return Long.valueOf(jvmUsedMem);
    }
}