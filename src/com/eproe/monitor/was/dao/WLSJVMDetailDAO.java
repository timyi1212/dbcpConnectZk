package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSJVMDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
public class WLSJVMDetailDAO
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
    public boolean add(WLSJVMDetail jvmDetail) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.insert("insertWLSJVMDetail", jvmDetail) > 0;
    }





    public List<WLSJVMDetail> getJVMDetailByDay(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJVMDetailByDay", params);
    }

    public List<WLSJVMDetail> getJVMDetailByWeek(Map<String, Object> params) {
       // SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJVMDetailByWeek", params);
    }

    public List<WLSJVMDetail> getJVMDetailByMonth(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getJVMDetailByMonth", params);
    }

    public List<WLSJVMDetail> getAllJVMDetail(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getAllJVMDetail", params);
    }
}