package com.eproe.monitor.was.action;

import com.eproe.monitor.was.model.WLSJDBC;
import com.eproe.monitor.was.service.WLSJDBCService;
import com.eproe.monitor.was.util.ReturnCode;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"wlsJDBC"})
public class WASJDBCAction
{
    Log logger = LogFactory.getLog(WASJDBCAction.class);

    @Autowired
    WLSJDBCService wlsJDBCService;

    @RequestMapping(value={"add.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String add(HttpServletRequest req, HttpServletResponse resp)
    {
        String wlsdata = req.getParameter("wlsServer");
        String dsname = req.getParameter("dsname");
        String[] array = wlsdata.split(",");
        String wlsid = array[0];
        Integer wlsIdInt = Integer.valueOf(Integer.parseInt(wlsid));
        String wlsIp = array[1];
        String wlsServername = array[2];
        Map params = new HashMap();
        params.put("wlsid", wlsid);
        params.put("dsname", dsname);

        if (this.wlsJDBCService.getdsCountByWLSIDDSName(params) == 0) {
            WLSJDBC wlsJDBC = new WLSJDBC();
            wlsJDBC.setCreator("sys");
            wlsJDBC.setModifier("sys");
            wlsJDBC.setCreateTime(new Date());
            wlsJDBC.setUpdatedTime(new Date());
            wlsJDBC.setIsDeleted("n");
            Integer wlsidInter = Integer.valueOf(Integer.parseInt(wlsid));
            wlsJDBC.setId(wlsidInter);
            wlsJDBC.setWlsServerId(wlsIdInt);
            wlsJDBC.setWlsServerIp(wlsIp);
            wlsJDBC.setWlsServerName(wlsServername);
            wlsJDBC.setDsName(dsname);
            this.wlsJDBCService.addWLSJDBC(wlsJDBC);
        }

        return "redirect:/wasJDBCConfig.htm";
    }

    @RequestMapping(value={"delete.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        String jdbcid = req.getParameter("id");
        Map params = new HashMap();
        params.put("id", jdbcid);
        this.wlsJDBCService.deleteWLSJDBC(params);

        return "redirect:/wasJDBCConfig.htm";
    }
    @RequestMapping(value={"get.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String wlsServerId = req.getParameter("wasServerId");

        Map params = new HashMap();
        params.put("wlsServerId", wlsServerId);
        List wlsJDBCList = this.wlsJDBCService.getWLSJDBCById(params);

        resp.setContentType("text/html;charset=utf-8");
        resp.setContentType("application/json");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            this.logger.error("WLSJDBCInfoAction get ERROR: ", e);
        }

        ReturnCode returnCode = new ReturnCode();
        returnCode.setCode(200);
        returnCode.setData(wlsJDBCList);

        writer.print(new Gson().toJson(returnCode));
    }
}