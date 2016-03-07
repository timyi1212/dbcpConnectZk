package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSJVMDetail;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class WLSJVMDetailDAO
{
    private SqlSessionFactory sessionFactory;

    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public boolean add(WLSJVMDetail jvmDetail) {
        SqlSession session = this.sessionFactory.openSession();
        return session.insert("insertWLSJVMDetail", jvmDetail) > 0;
    }





    public List<WLSJVMDetail> getJVMDetailByDay(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJVMDetailByDay", params);
    }

    public List<WLSJVMDetail> getJVMDetailByWeek(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJVMDetailByWeek", params);
    }

    public List<WLSJVMDetail> getJVMDetailByMonth(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getJVMDetailByMonth", params);
    }

    public List<WLSJVMDetail> getAllJVMDetail(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getAllJVMDetail", params);
    }
}