package com.soft.yozo.myrecode.service;

import com.soft.yozo.myrecode.bean.Recode;
import com.soft.yozo.myrecode.bean.Schooling;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectServiceBean;
import com.soft.yozo.myrecode.dao.RecodeDao;
import com.soft.yozo.myrecode.dao.SchoolingDao;
import com.soft.yozo.myrecode.dao.SubjectDao;

import java.util.ArrayList;
import java.util.List;

public class SubjectService
{
    private SubjectDao subjectDao;
    private RecodeDao recodeDao;
    private SchoolingDao schoolingDao;

    public SubjectService()
    {
        subjectDao = new SubjectDao();
        recodeDao = new RecodeDao();
        schoolingDao = new SchoolingDao();
    }


    public List<SubjectServiceBean> getAllSubjects()
    {
        List<SubjectServiceBean> subjectServices = new ArrayList<>();
        List<Subject> subjects = subjectDao.getAllSubject();
        for(Subject s: subjects)
        {
            int total = 0;
            int allCounts = 0;
            SubjectServiceBean subjectServiceBean = new SubjectServiceBean();
            subjectServiceBean.setmSubjectName(s.subName);
            List<Schooling> schoolings = schoolingDao.getSchoolingBySubId(s._ID);
            for(Schooling sch: schoolings)
            {
                total += sch.sfee;
                allCounts += sch.scounts;
            }
            subjectServiceBean.setmTotalFee(total);
            subjectServiceBean.setmTotalCounts(allCounts);

            List<Recode> recodes = recodeDao.getAllRecodeBySubId(s._ID);
            subjectServiceBean.setmUseCounts(recodes.size());
            subjectServices.add(subjectServiceBean);
        }
        return subjectServices;
    }

}
