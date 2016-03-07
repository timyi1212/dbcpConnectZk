package com.eproe.monitor.was.control;

import com.eproe.monitor.was.service.WLSServerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class AddWLSServerCtrl
        implements Controller
{
    Log logger = LogFactory.getLog(AddWLSServerCtrl.class);

    @Autowired
    private WLSServerService wlsServerService;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        ModelAndView mv = new ModelAndView("addWLSServer");
        List wlsServerList = this.wlsServerService.getAllWLSServer();
        mv.addObject("wlsServerList", wlsServerList);

        return mv;
    }
}