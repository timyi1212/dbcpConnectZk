package com.eproe.monitor.was.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HistoryCSVChartCtrl
        implements Controller
{
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception
    {
        ModelAndView mv = new ModelAndView("historyCSVChart");

        return mv;
    }
}