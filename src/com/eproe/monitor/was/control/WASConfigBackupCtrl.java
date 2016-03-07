package com.eproe.monitor.was.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class WASConfigBackupCtrl
        implements Controller
{
    Log logger = LogFactory.getLog(WASConfigBackupCtrl.class);

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        ModelAndView mv = new ModelAndView("wasManage/wasConfigBackup");

        return mv;
    }
}