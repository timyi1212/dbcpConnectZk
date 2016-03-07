package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSJDBC;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class WLSJDBCDAO
{
    private SqlSessionFactory sessionFactory;

    public List<WLSJDBC> getALLWLSJDBC()
    {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getAllWLSJDBC");
    }

    public int getDSCountByWLSIDDSName(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return ((Integer)session.selectOne("getDSCountByWSNameDSName", params)).intValue();
    }

    public boolean addWLSJDBC(WLSJDBC wlsJDBC) {
        SqlSession session = this.sessionFactory.openSession();
        return session.insert("addWLSJDBC", wlsJDBC) > 0;
    }

    public boolean deleteWLSJDBC(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return session.delete("deleteWLSJDBC", params) > 0;
    }

    public List<WLSJDBC> getWLSJDBCById(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getWLSJDBCById", params);
    }

    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }





    public boolean deleteWASJDBC(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.delete("deleteWASJDBC", params) > 0;
    }

    public boolean updateWASJDBC(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.update("updateWASJDBC", params) > 0;
    }


    public int getDSCountByProviderJndi(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return ((Integer)session.selectOne("getDSCountByProviderJndi", params)).intValue();
    }
}