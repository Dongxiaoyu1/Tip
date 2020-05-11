package com.soft.yozo.myrecode.service;

import com.soft.yozo.myrecode.bean.Schooling;
import com.soft.yozo.myrecode.bean.SchoolingServiceBean;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.dao.SchoolingDao;
import com.soft.yozo.myrecode.dao.SubjectDao;

import java.util.ArrayList;
import java.util.List;

public class SchoolingService
{
    private SchoolingDao schoolingDao;
    private SubjectDao subjectDao;

    public SchoolingService()
    {
        schoolingDao = new SchoolingDao();
        subjectDao = new SubjectDao();
    }

    public List<SchoolingServiceBean> getALLSchooling()
    {
        List<SchoolingServiceBean> beans = new ArrayList<>();
        List<Schooling> schoolings = schoolingDao.getSchooling();
        for(Schooling sch: schoolings)
        {
            Subject s = subjectDao.getSubjectById(sch.subid);
            if(s != null)
            {
                SchoolingServiceBean bean = new SchoolingServiceBean();
                bean.setSubName(s.subName);
                bean.setSchooling(sch);
                beans.add(bean);
            }
        }
        return beans;

    }
}
