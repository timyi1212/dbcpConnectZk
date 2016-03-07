package com.eproe.monitor.was.action;

import com.eproe.monitor.was.model.WLSJDBCDetail;
import com.eproe.monitor.was.model.WLSJVMDetail;
import com.eproe.monitor.was.model.WLSThreadPoolDetail;
import com.eproe.monitor.was.service.WLSJDBCDetailService;
import com.eproe.monitor.was.service.WLSJVMDetailService;
import com.eproe.monitor.was.service.WLSThreadPoolDetailService;
import com.eproe.monitor.was.util.ReturnCode;
import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"wlsChart"})
public class WASChartAction
{
    public static final Log logger = LogFactory.getLog(WASChartAction.class);

    @Autowired
    WLSJVMDetailService wlsJVMDetailService;

    @Autowired
    WLSJDBCDetailService wlsJDBCDetailService;

    @Autowired
    WLSThreadPoolDetailService wlsThreadPoolDetailService;

    @RequestMapping(value={"memUsageChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void memUsageChart(HttpServletRequest req, HttpServletResponse resp) { PrintWriter out = null;
        String json = null;

        String wlsServerId = req.getParameter("wasServerId");

        String timePeriod = req.getParameter("timePeriod");

        FileOutputStream fosJPG = null;
        try
        {
            Map params = new HashMap();
            String filePath = "";
            params.put("wlsServerId", wlsServerId);
            List jvmDetailList = null;
            String dateFormat = "MM/dd/HH:mm";

            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("day".equals(timePeriod))) {
                System.out.println("day here!!!!!!!");
                params.put("interval", Integer.valueOf(24));
                jvmDetailList = this.wlsJVMDetailService.getJVMDetailByDay(params);
            }
            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("week".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(7));
                jvmDetailList = this.wlsJVMDetailService.getJVMDetailByWeek(params);
            }

            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("month".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(31));
                jvmDetailList = this.wlsJVMDetailService.getJVMDetailByMonth(params);
            }

            if (jvmDetailList != null)
            {
                TimeSeries ts = new TimeSeries("JVMUsedMemory",
                        Second.class);
                Date date = null;
                long usedMem = 0L;


                for (int i=0; i<jvmDetailList.size(); i++) {
                    WLSJVMDetail jvmDetail = (WLSJVMDetail) jvmDetailList.get(i);
                    date = jvmDetail.getCreateTime();
                    usedMem = jvmDetail.getUsedMemory().longValue();
                    ts.addOrUpdate(new Second(date), usedMem);

                }

 /*               for (WLSJVMDetail jvmDetail : jvmDetailList) {
                    date = jvmDetail.getCreateTime();
                    usedMem = jvmDetail.getUsedMemory().longValue();
                    ts.addOrUpdate(new Second(date), usedMem);
                }*/

                TimeSeriesCollection dataset = new TimeSeriesCollection();
                dataset.addSeries(ts);
                String chartTitle = "Used JVM Memory(MB)";
                JFreeChart jvmChart = createJFreeChart(dataset, chartTitle, dateFormat);

                String rootPath = req.getSession().getServletContext().getRealPath("/");

                filePath = "/report/memUsage_day_" + new Date().getTime() + ".jpg";

                File file = new File(rootPath + filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fosJPG = new FileOutputStream(file);
                ChartUtilities.writeChartAsJPEG(fosJPG, jvmChart, 1000, 700);
                fosJPG.flush();
                fosJPG.close();
            }

            ReturnCode code = new ReturnCode();
            code.setCode(200);
            String requestContextPath = req.getContextPath();
            filePath = requestContextPath + filePath;
            System.out.println(filePath);

            code.setData(filePath);

            json = new Gson().toJson(code);
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSChartAction memUsageChart ERROR", e);
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException g) {
                logger.error("WLSChartAction close fosJPG", g);
            }
        }
        finally
        {
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException e) {
                logger.error("WLSChartAction close fosJPG", e);
            }
        }
        out.print(json);
    }

    @RequestMapping(value={"threadPoolSizeChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void threadPoolSizeChart(HttpServletRequest req, HttpServletResponse resp)
    {
        PrintWriter out = null;
        String json = null;

        String wlsServerId = req.getParameter("wasServerId");
        String timePeriod = req.getParameter("timePeriod");

        FileOutputStream fosJPG = null;
        try
        {
            Map params = new HashMap();
            String filePath = "";
            params.put("wlsServerId", wlsServerId);
            List threadPoolDetailList = null;
            String dateFormat = "MM/dd/HH:mm";

            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("day".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(24));
                threadPoolDetailList = this.wlsThreadPoolDetailService.getThreadPoolDetailByDay(params);
            }
            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("week".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(7));
                threadPoolDetailList = this.wlsThreadPoolDetailService.getThreadPoolDetailByWeek(params);
            }
            if ((!StringUtils.isEmpty(timePeriod)) &&
                    ("month".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(31));
                threadPoolDetailList = this.wlsThreadPoolDetailService.getThreadPoolDetailByMonth(params);
            }

            if (threadPoolDetailList != null) {
                TimeSeries ts = new TimeSeries("Active Thread Count",
                        Second.class);
                Date date = null;
                long threadCount = 0L;

                for(int i = 0; i<threadPoolDetailList.size(); i++) {
                    WLSThreadPoolDetail  threadPoolDetail= (WLSThreadPoolDetail)threadPoolDetailList.get(i);
                    date = threadPoolDetail.getCreateTime();
                    threadCount = threadPoolDetail.getActiveThreadCount().longValue();
                    ts.addOrUpdate(new Second(date), threadCount);
                }
 /*              for (WLSThreadPoolDetail threadPoolDetail : (WLSThreadPoolDetail)threadPoolDetailList) {
                    date = threadPoolDetail.getCreateTime();
                    threadCount = threadPoolDetail.getActiveThreadCount().longValue();
                    ts.addOrUpdate(new Second(date), threadCount);
                }*/

                TimeSeriesCollection dataset = new TimeSeriesCollection();
                dataset.addSeries(ts);
                String chartTitle = "Active Thread Count";
                JFreeChart threadPoolChart = createJFreeChart(dataset, chartTitle, dateFormat);

                String rootPath = req.getSession().getServletContext().getRealPath("/");
                filePath = "/report/activethread_day_" + new Date().getTime() + ".jpg";
                File file = new File(rootPath + filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fosJPG = new FileOutputStream(file);
                ChartUtilities.writeChartAsJPEG(fosJPG, threadPoolChart, 800, 600);
                fosJPG.flush();
                fosJPG.close();
            }

            ReturnCode code = new ReturnCode();
            code.setCode(200);
            String requestContextPath = req.getContextPath();
            filePath = requestContextPath + filePath;
            code.setData(filePath);

            json = new Gson().toJson(code);
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSChartAction activethreadChart ERROR", e);
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException g) {
                logger.error("WLSChartAction close fosJPG", g);
            }
        }
        finally
        {
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException e) {
                logger.error("WLSChartAction close fosJPG", e);
            }
        }
        out.print(json);
    }

    @RequestMapping(value={"dsPoolSizeChart.htm"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public void dsPoolSizeChart(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter out = null;
        String json = null;

        String timePeriod = req.getParameter("timePeriod");
        String wlsJDBCId = req.getParameter("dsInfo");

        System.out.println(wlsJDBCId + "---------------------------------");
        FileOutputStream fosJPG = null;
        ReturnCode code = new ReturnCode();
        try
        {
            Map params = new HashMap();
            String filePath = "";
            params.put("wlsJDBCId", wlsJDBCId);
            List jdbcDetailList = null;
            String dateFormat = "MM/dd/HH:mm";

            if ((!StringUtils.isEmpty(timePeriod)) && (!StringUtils.isEmpty(wlsJDBCId)) &&
                    ("day".equals(timePeriod))) {
                System.out.println("here");
                params.put("interval", Integer.valueOf(24));
                jdbcDetailList = this.wlsJDBCDetailService.getJDBCDetailByDay(params);
            }
            if ((!StringUtils.isEmpty(timePeriod)) && (!StringUtils.isEmpty(wlsJDBCId)) &&
                    ("week".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(7));
                jdbcDetailList = this.wlsJDBCDetailService.getJDBCDetailByWeek(params);
            }
            if ((!StringUtils.isEmpty(timePeriod)) && (!StringUtils.isEmpty(wlsJDBCId)) &&
                    ("month".equals(timePeriod))) {
                params.put("interval", Integer.valueOf(31));
                jdbcDetailList = this.wlsJDBCDetailService.getJDBCDetailByMonth(params);
            }

            if (jdbcDetailList != null) {
                TimeSeries ts = new TimeSeries("Datasource Pool Size",
                        Second.class);
                Date date = null;
                long poolSize = 0L;



                for(int i=0;i<jdbcDetailList.size(); i++) {
                    WLSJDBCDetail jdbcDetail = (WLSJDBCDetail) jdbcDetailList.get(i);
                    date = jdbcDetail.getCreateTime();

                    poolSize = jdbcDetail.getPoolSize().longValue();

                    ts.addOrUpdate(new Second(date), poolSize);
                }
  /*              for (WLSJDBCDetail jdbcDetail : jdbcDetailList) {
                    date = jdbcDetail.getCreateTime();

                    poolSize = jdbcDetail.getPoolSize().longValue();

                    ts.addOrUpdate(new Second(date), poolSize);
                }*/

                TimeSeriesCollection dataset = new TimeSeriesCollection();
                dataset.addSeries(ts);
                String chartTitle = "Datasource Pool Size";
                JFreeChart threadPoolChart = createJFreeChart(dataset, chartTitle, dateFormat);

                String rootPath = req.getSession().getServletContext().getRealPath("/");
                filePath = "/report/dsPoolSize_day_" + new Date().getTime() + ".jpg";
                File file = new File(rootPath + filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fosJPG = new FileOutputStream(file);
                ChartUtilities.writeChartAsJPEG(fosJPG, threadPoolChart, 800, 600);
                fosJPG.flush();
                fosJPG.close();
            }

            code.setCode(200);
            String requestContextPath = req.getContextPath();
            filePath = requestContextPath + filePath;
            code.setData(filePath);
            out = resp.getWriter();
        } catch (Exception e) {
            logger.error("WLSChartAction dsPoolSizeChart ERROR", e);
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException g) {
                logger.error("WLSChartAction close fosJPG", g);
            }
        }
        finally
        {
            try
            {
                if (fosJPG != null)
                    fosJPG.close();
            }
            catch (IOException e) {
                logger.error("WLSChartAction close fosJPG", e);
            }
        }
        json = new Gson().toJson(code);
        out.print(json);
    }

    private JFreeChart createJFreeChart(TimeSeriesCollection dataset, String chartTitle, String dateFormat) {
        JFreeChart jvmChart = ChartFactory.createTimeSeriesChart(
                chartTitle, "date", "", dataset, true, true, false);
        jvmChart.setBackgroundPaint(Color.LIGHT_GRAY);
        XYPlot plot = jvmChart.getXYPlot();
        NumberAxis na = (NumberAxis)plot.getRangeAxis();
        na.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat(dateFormat));
        plot.setDomainAxis(dateAxis);
        renderXYPolt(plot);
        plot.getRangeAxis().setLabelAngle(1.570796326794897D);
        TextTitle textTitle = jvmChart.getTitle();
        textTitle.setFont(new Font("ËÎÌו", 1, 20));
        plot.getRangeAxis().setTickLabelFont(new Font("ËÎÌו", 1, 12));
        dateAxis.setTickLabelFont(new Font("ËÎÌו", 1, 12));
        return jvmChart;
    }

    private void renderXYPolt(XYPlot plot) {
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);
        XYItemRenderer r = plot.getRenderer();
        if ((r instanceof XYLineAndShapeRenderer)) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(false);
            renderer.setSeriesPaint(0, Color.black);
        }
    }
}