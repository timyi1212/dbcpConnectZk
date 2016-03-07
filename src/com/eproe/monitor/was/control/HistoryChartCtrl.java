package com.eproe.monitor.was.control;

import com.eproe.monitor.was.service.WLSServerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HistoryChartCtrl
        implements Controller
{

    @Autowired
    private WLSServerService wlsServerService;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        ModelAndView mv = new ModelAndView("historyChart");

        List wlsServerList = this.wlsServerService.getAllWLSServer();
        mv.addObject("wlsServerList", wlsServerList);

        return mv;
    }
}
