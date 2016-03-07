package com.eproe.monitor.was.dao;

import com.eproe.monitor.was.model.WLSJDBCDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class WLSJDBCDetailDAO
{
    private SqlSessionFactory sessionFactory;

    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public List<WLSJDBCDetail> getWLSJDBCDetailByPeriod(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getWLSJDBCDetailByPeriod", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByDay(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJDBCDetailByDay", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByWeek(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJDBCDetailByWeek", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByMonth(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJDBCDetailByMonth", params);
    }
}