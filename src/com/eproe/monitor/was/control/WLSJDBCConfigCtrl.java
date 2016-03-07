package com.eproe.monitor.was.control;

import com.eproe.monitor.was.service.WLSJDBCService;
import com.eproe.monitor.was.service.WLSServerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class WLSJDBCConfigCtrl
        implements Controller
{
    Log logger = LogFactory.getLog(WLSJDBCConfigCtrl.class);

    @Autowired
    WLSJDBCService wlsJDBCService;

    @Autowired
    WLSServerService wlsServerService;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception { ModelAndView mv = new ModelAndView("wasManage/wlsJDBCConfig");
        List wlsJDBCList = this.wlsJDBCService.getALLWLSJDBC();

        mv.addObject("wlsJDBCList", wlsJDBCList);
        List wlsServerList = this.wlsServerService.getAllWLSServer();

        mv.addObject("wlsServerList", wlsServerList);
        return mv;
    }
}