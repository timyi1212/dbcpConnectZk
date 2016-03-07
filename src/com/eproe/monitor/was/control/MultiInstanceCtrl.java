package com.eproe.monitor.was.control;

import com.eproe.monitor.was.concurrent.FecthDataTaskMultipal;
import com.eproe.monitor.was.dto.InstanceDataDTO;
import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSJDBCService;
import com.eproe.monitor.was.service.WLSServerService;
import java.io.PrintStream;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class MultiInstanceCtrl
        implements Controller
{
    public static Log logger = LogFactory.getLog(MultiInstanceCtrl.class);

    @Autowired
    WLSServerService wlsServerService;

    @Autowired
    WLSJDBCService wlsJDBCService;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
    {
        ModelAndView mv = new ModelAndView("multiInstance");

        List dataDTOList = new ArrayList();

        Map params = new HashMap();

        List wlsServerList = this.wlsServerService.getAllWLSServer();

        int sizeOld = 0;
        if ((wlsServerList != null) && (wlsServerList.size() > 0)) {
            int size = wlsServerList.size();

            ExecutorService exec = Executors.newCachedThreadPool();

            List datasourceDTOList = new ArrayList();
            for (int i = 0; i < wlsServerList.size(); i++)
            {
                WLSServer wlsServer = (WLSServer)wlsServerList.get(i);

                params = new HashMap();
                params.put("wlsServerId", wlsServer.getId());
                List wlsJDBCList = this.wlsJDBCService.getWLSJDBCById(params);
                int sizeNew = wlsJDBCList.size();
                if (sizeNew > sizeOld) {
                    sizeOld = sizeNew;
                }
                exec.submit(new FecthDataTaskMultipal(wlsServer, dataDTOList, wlsJDBCList));
            }

            exec.shutdown();
        }

        while (dataDTOList.size() != wlsServerList.size())
        {
            try
            {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
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
        mv.addObject("multiDataList", dataDTOList);

        mv.addObject("colspan", Integer.valueOf(sizeOld));
        return mv;
    }
}