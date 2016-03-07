package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSServer;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class WLSServerDAO
{
    private SqlSessionFactory sessionFactory;

    public void setSessionFactory(SqlSessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public List<WLSServer> getAllWLSServer() {
        SqlSession session = this.sessionFactory.openSession();
        return session.selectList("getAllWLSServer");
    }

    public int getWLSServerCountByParamsIPPort(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return ((Integer)session.selectOne("getWLSServerCountByParamsIPPort", params)).intValue();
    }

    public int getWASServerCountByParamsPORT(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return ((Integer)session.selectOne("getWASServerCountByParamsPORT", params)).intValue();
    }

    public boolean addWLSServer(WLSServer wlsServer) {
        SqlSession session = this.sessionFactory.openSession();
        return session.insert("addWLSServer", wlsServer) > 0;
    }


    public boolean deleteWASServer(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.delete("deleteWASServer", params) > 0;
    }

    public boolean deleteWLSServer(Map<String, Object> params) {
        SqlSession session = this.sessionFactory.openSession();
        return session.delete("deleteWLSServer", params) > 0;
    }

    public boolean updateWLSServer(WLSServer wlsServer)
    {
        SqlSession session = this.sessionFactory.openSession();
        return session.update("updateWLSServer", wlsServer) > 0;
    }

    public WLSServer getWLSServerById(Map<String, Object> params)
    {
        SqlSession session = this.sessionFactory.openSession();
        return (WLSServer)session.selectOne("getWLSServerById", params);
    }
}