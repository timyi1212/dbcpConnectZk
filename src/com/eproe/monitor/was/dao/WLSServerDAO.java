package com.eproe.monitor.was.dao;


import com.eproe.monitor.was.model.WLSServer;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

public class WLSServerDAO
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
    public List<WLSServer> getAllWLSServer() {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.selectList("getAllWLSServer");
    }

    public int getWLSServerCountByParamsIPPort(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return ((Integer)sqlSession.selectOne("getWLSServerCountByParamsIPPort", params)).intValue();
    }

    public int getWASServerCountByParamsPORT(Map<String, Object> params)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return ((Integer)sqlSession.selectOne("getWASServerCountByParamsPORT", params)).intValue();
    }

    public boolean addWLSServer(WLSServer wlsServer) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.insert("addWLSServer", wlsServer) > 0;
    }


    public boolean deleteWASServer(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.delete("deleteWASServer", params) > 0;
    }

    public boolean deleteWLSServer(Map<String, Object> params) {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.delete("deleteWLSServer", params) > 0;
    }

    public boolean updateWLSServer(WLSServer wlsServer)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return sqlSession.update("updateWLSServer", wlsServer) > 0;
    }

    public WLSServer getWLSServerById(Map<String, Object> params)
    {
        //SqlSession session = this.sessionFactory.openSession();
        return (WLSServer)sqlSession.selectOne("getWLSServerById", params);
    }
}