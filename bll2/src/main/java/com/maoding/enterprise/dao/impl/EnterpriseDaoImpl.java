package com.maoding.enterprise.dao.impl;

import com.maoding.enterprise.dao.EnterpriseDao;
import com.maoding.enterprise.dto.EnterpriseDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("enterpriseDao")
public class EnterpriseDaoImpl implements EnterpriseDao {

    @Autowired
    protected SqlSessionTemplate sqlSession;

    @Override
    public String getEnterpriseNameById(String id) {
        return this.sqlSession.selectOne("EnterpriseMapper.getEnterpriseNameById",id);
    }

    @Override
    public EnterpriseDTO getEnterpriseById(String id) {
        return this.sqlSession.selectOne("EnterpriseMapper.getEnterpriseById",id);
    }

    @Override
    public String getEnterpriseNameByProjectId(String projectId) {
        return sqlSession.selectOne("EnterpriseMapper.getEnterpriseNameByProjectId", projectId);
    }
}
