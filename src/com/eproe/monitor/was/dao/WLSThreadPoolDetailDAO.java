package com.eproe.monitor.was.dao;

import com.eproe.monitor.was.model.WLSThreadPoolDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public class WLSThreadPoolDetailDAO
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
    public boolean add(WLSThreadPoolDetail detail) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.insert("insertWLSThreadPoolDetail", detail) > 0;
    }

    public List<WLSThreadPoolDetail> getThreadPoolDetailByDay(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getThreadPoolDetailByDay", params);
    }

    public List<WLSThreadPoolDetail> getThreadPoolDetailByWeek(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getThreadPoolDetailByWeek", params);
    }

    public List<WLSThreadPoolDetail> getThreadPoolDetailByMonth(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getThreadPoolDetailByMonth", params);
    }
}