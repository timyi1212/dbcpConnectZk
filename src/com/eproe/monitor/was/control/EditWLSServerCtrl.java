package com.eproe.monitor.was.control;

import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSServerService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EditWLSServerCtrl
        implements Controller
{
    Log logger = LogFactory.getLog(EditWLSServerCtrl.class);

    @Autowired
    private WLSServerService wlsServerService;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mv = new ModelAndView("editWLSServer");

        String id = req.getParameter("id");

        Map params = new HashMap();
        params.put("id", id);
        WLSServer wlsServer = this.wlsServerService.getWLSSServerById(params);
        mv.addObject("wlsServer", wlsServer);
        return mv;
    }
}