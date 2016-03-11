package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSJDBC;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public class WLSJDBCDAO
{
    private SqlSessionFactory sessionFactory;
    private SqlSessionTemplate sqlSession;

    public List<WLSJDBC> getALLWLSJDBC()
    {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getAllWLSJDBC");
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int getDSCountByWLSIDDSName(Map<String, Object> params)
    {
       // SqlSession session = this.sessionFactory.openSession();
        return ((Integer)sqlSession.selectOne("getDSCountByWSNameDSName", params)).intValue();
    }

    public boolean addWLSJDBC(WLSJDBC wlsJDBC) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.insert("addWLSJDBC", wlsJDBC) > 0;
    }

    public boolean deleteWLSJDBC(Map<String, Object> params)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.delete("deleteWLSJDBC", params) > 0;
    }

    public List<WLSJDBC> getWLSJDBCById(Map<String, Object> params)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getWLSJDBCById", params);
    }

    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }





    public boolean deleteWASJDBC(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.delete("deleteWASJDBC", params) > 0;
    }

    public boolean updateWASJDBC(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.update("updateWASJDBC", params) > 0;
    }


    public int getDSCountByProviderJndi(Map<String, Object> params) {
       // SqlSession session = this.sessionFactory.openSession();
        return ((Integer)sqlSession.selectOne("getDSCountByProviderJndi", params)).intValue();
    }
}