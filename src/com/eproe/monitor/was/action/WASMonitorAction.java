package com.eproe.monitor.was.action;

import com.eproe.monitor.was.concurrent.FecthDataTaskMultipal;
import com.eproe.monitor.was.dto.InstanceDataDTO;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSJDBCService;
import com.eproe.monitor.was.service.WLSServerService;
import com.eproe.monitor.was.util.ReturnCode;
import com.eproe.monitor.was.util.WLSUtil;
import com.google.gson.Gson;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"wlsMonitor"})
public class WASMonitorAction
{
    public static final Log logger = LogFactory.getLog(WASMonitorAction.class);

    public static Map<String, WLSServer> wlsServerMap = new HashMap();

    @Autowired
    WLSServerService wlsServerService;

    @Autowired
    WLSJDBCService wlsJDBCService;

    @RequestMapping(value={"memUsageChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void memUsageChart(HttpServletRequest req, HttpServletResponse resp)
    {
        PrintWriter out = null;
        String json = null;

        String wlsServerID = req.getParameter("wasServerId");
        try
        {
            WLSServer wlsServer = (WLSServer)wlsServerMap.get(wlsServerID);
            if (wlsServer == null) {
                Map params = new HashMap();
                params.put("id", wlsServerID);
                wlsServer = this.wlsServerService.getWLSSServerById(params);
                wlsServerMap.put(wlsServerID, wlsServer);
            }
            WLSUtil wlsUtil = new WLSUtil();

            long memUsage = wlsUtil.getMemUsage(wlsServer).longValue();
            logger.info("MemUsageInfo Jvm used mem: " + memUsage + " Mb");
            json = new Gson().toJson(Long.valueOf(memUsage));
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSMonitorAction memUsageChart ERROR", e);
        }
        out.print(json);
    }

    @RequestMapping(value={"threadActiveChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void threadActiveeChart(HttpServletRequest req, HttpServletResponse resp)
    {
        PrintWriter out = null;
        String json = null;

        String wlsServerID = req.getParameter("wasServerId");
        try
        {
            WLSServer wlsServer = (WLSServer)wlsServerMap.get(wlsServerID);
            if (wlsServer == null) {
                Map params = new HashMap();
                params.put("id", wlsServerID);
                wlsServer = this.wlsServerService.getWLSSServerById(params);
                wlsServerMap.put(wlsServerID, wlsServer);
            }
            WLSUtil wlsUtil = new WLSUtil();

            long threadActive = wlsUtil.getActiveThread(wlsServer).longValue();
            logger.info("ThreadCountInfo thread active count: " + threadActive + " Count");
            json = new Gson().toJson(Long.valueOf(threadActive));
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSMonitorAction threadActiveChart ERROR", e);
        }
        out.print(json);
    }

    @RequestMapping(value={"dsPoolSizeChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void dsPoolSizeChart(HttpServletRequest req, HttpServletResponse resp)
    {
        PrintWriter out = null;
        String json = null;

        String wlsServerID = req.getParameter("wlsServerId");
        String dsName = req.getParameter("dsInfo");
        try
        {
            WLSServer wlsServer = (WLSServer)wlsServerMap.get(wlsServerID);
            if (wlsServer == null) {
                Map params = new HashMap();
                params.put("id", wlsServerID);
                wlsServer = this.wlsServerService.getWLSSServerById(params);
                wlsServerMap.put(wlsServerID, wlsServer);
            }
            WLSUtil wlsUtil = new WLSUtil();
            long dsPoolSize = wlsUtil.getDsPoolsSize(wlsServer, dsName).longValue();

            logger.info("DataSourceCountInfo jdbc pool size count: " + dsPoolSize + " Count");
            json = new Gson().toJson(Long.valueOf(dsPoolSize));
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSMonitorAction jdbcPoolSizeChart ERROR", e);
        }
        out.print(json);
    }

    @RequestMapping(value={"getMultiInstanceData.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void getMultiInstanceData(HttpServletRequest req, HttpServletResponse resp)
    {
        String json = null;
        PrintWriter out = null;
        ReturnCode returnCode = new ReturnCode();
        List dataDTOList = new ArrayList();

        List wlsServerList = this.wlsServerService.getAllWLSServer();
        Map params = new HashMap();
        if ((wlsServerList != null) && (wlsServerList.size() > 0))
        {
            int size = wlsServerList.size();

            ExecutorService exec = Executors.newCachedThreadPool();

            List datasourceDTOList = new ArrayList();
            for (int i = 0; i < size; i++)
            {
                WLSServer wlsServer = (WLSServer)wlsServerList.get(i);
                params = new HashMap();
                params.put("wlsServerId", wlsServer.getId());
                List wlsJDBCList = this.wlsJDBCService.getWLSJDBCById(params);
                exec.submit(new FecthDataTaskMultipal(wlsServer, dataDTOList, wlsJDBCList));
            }

            exec.shutdown();
        }

        while (dataDTOList.size() != wlsServerList.size())
        {
            try
            {
                Thread.sleep(3000L);
            }
            catch (InterruptedException e) {
                logger.error("线程睡眠3s出错", e);
            }
            System.out.println("睡眠3s，等待全部实例加载完毕");
        }

        Collections.sort(dataDTOList, new Comparator()
        {
            public int compare(Object o1, Object o2) {
                InstanceDataDTO data1 = (InstanceDataDTO)o1;
                InstanceDataDTO data2 = (InstanceDataDTO)o2;
                return data1.getId().compareTo(data2.getId());
            }
        });
        returnCode.setCode(200);
        returnCode.setData(dataDTOList);
        try {
            json = new Gson().toJson(returnCode);
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSMonitorAction getMultiInstanceData ERROR", e);
            returnCode.setCode(10000);
        }
        out.print(json);
    }
}