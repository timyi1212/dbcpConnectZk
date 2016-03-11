package com.eproe.monitor.was.dao;

import com.eproe.monitor.was.model.WLSJDBCDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public class WLSJDBCDetailDAO
{
    private SqlSessionFactory sessionFactory;
    private SqlSessionTemplate sqlSession;
    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    public List<WLSJDBCDetail> getWLSJDBCDetailByPeriod(Map<String, Object> params)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getWLSJDBCDetailByPeriod", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByDay(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJDBCDetailByDay", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByWeek(Map<String, Object> params) {
       // SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJDBCDetailByWeek", params);
    }

    public List<WLSJDBCDetail> getJDBCDetailByMonth(Map<String, Object> params) {
       // SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJDBCDetailByMonth", params);
    }
}