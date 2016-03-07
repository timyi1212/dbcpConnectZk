package com.eproe.monitor.was.action;

import com.eproe.monitor.was.model.WLSServer;
import com.eproe.monitor.was.service.WLSServerService;
import com.eproe.monitor.was.util.ReturnCode;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping({"wlsServer"})
public class WASServerAction
{
    Log logger = LogFactory.getLog(WASServerAction.class);

    @Autowired
    private WLSServerService wlsServerService;

    @RequestMapping(value={"getChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void getChart(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=utf-8");
        resp.setContentType("application/json");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            this.logger.error("WASServerAction addTask error: ", e);
        }

        ReturnCode returnCode = new ReturnCode();
        new Gson().toJson(returnCode, writer);
    }

    @RequestMapping(value={"add.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String add(HttpServletRequest req, HttpServletResponse resp)
    {
        String server = req.getParameter("servername");
        String ip = req.getParameter("ip");
        String port = req.getParameter("port");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        WLSServer wlsServer = new WLSServer();
        wlsServer.setServername(server);
        wlsServer.setIpAddr(ip);
        wlsServer.setPort(port);
        wlsServer.setUsername(username);
        wlsServer.setPassword(password);
        wlsServer.setCreator("sys");
        wlsServer.setModifier("sys");
        wlsServer.setCreateTime(new Date());
        wlsServer.setUpdatedTime(new Date());
        wlsServer.setIsDeleted("n");
        try
        {
            Map paramsIPPort = new HashMap();

            paramsIPPort.put("ipAddr", ip);
            paramsIPPort.put("port", port);

            if (this.wlsServerService.getWLSServerCountByIPPort(paramsIPPort) == 0)
            {
                this.wlsServerService.addWLSServer(wlsServer);
            }

        }
        catch (Exception e)
        {
            this.logger.error("WASServerAction add ERROR: ", e);
        }

        return "redirect:/wasServerConfig.htm";
    }

    @RequestMapping(value={"delete.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        String id = req.getParameter("id");
        this.logger.info("Delete WLS Server with unique id: " + id);
        Map params = new HashMap();
        params.put("id", id);

        this.wlsServerService.deleteWLSServer(params);

        return "redirect:/wasServerConfig.htm";
    }

    @RequestMapping(value={"update.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        String id = req.getParameter("id");

        Integer idInter = Integer.valueOf(Integer.parseInt(id));
        String servername = req.getParameter("servername");
        String ipAddr = req.getParameter("ipAddr");
        String port = req.getParameter("port");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        WLSServer wlsServer = new WLSServer();
        wlsServer.setId(idInter);
        wlsServer.setServername(servername);
        wlsServer.setIpAddr(ipAddr);
        wlsServer.setPort(port);
        wlsServer.setUsername(username);
        wlsServer.setPassword(password);
        wlsServer.setUpdatedTime(new Date());

        WASMonitorAction action = new WASMonitorAction();
        WASMonitorAction.wlsServerMap.put(String.valueOf(idInter), wlsServer);

        this.logger.info("Change WAS Server with UniqueId=" + id +
                ",servername=" + servername +
                ",ip=" + ipAddr +
                ",port=" + port +
                ",username=" + username +
                ",password=" + password);

        this.wlsServerService.updateWLSServer(wlsServer);
        return "redirect:/wasServerConfig.htm";
    }
}