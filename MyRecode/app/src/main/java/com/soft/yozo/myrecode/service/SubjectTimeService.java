package com.soft.yozo.myrecode.service;

import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectTime;
import com.soft.yozo.myrecode.bean.SubjectTimeServiceBean;
import com.soft.yozo.myrecode.dao.SubjectDao;
import com.soft.yozo.myrecode.dao.SubjectTimeDao;
import com.soft.yozo.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class SubjectTimeService
{
    SubjectTimeDao subjectTimeDao;
    SubjectDao subjectDao;
    public SubjectTimeService()
    {
        subjectTimeDao  = new SubjectTimeDao();
        subjectDao = new SubjectDao();
    }

    /**
     * 得到本周提醒
     * @return
     */
    public List<String> getWeekTip()
    {
        List<String> tips = new ArrayList<>();
        List<SubjectTime> subjectTimes = subjectTimeDao.getCurWeekSubjectTime();
        for(SubjectTime st: subjectTimes)
        {
            Subject subject = subjectDao.getSubjectById(st.getSubId());
            String tip = st.getWeek()+"有"+subject.getSubName()+"课";
            tips.add(tip);
        }
        return tips;
    }

    /**
     * 得到本周提醒
     * @return
     */
    public List<String> getTodayTip()
    {
        List<String> tips = new ArrayList<>();
        List<SubjectTime> subjectTimes = subjectTimeDao.getCurWeekSubjectTime(DateUtils.getCurrentDate());
        for(SubjectTime st: subjectTimes)
        {
            Subject subject = subjectDao.getSubjectById(st.getSubId());
            String tip = "今天有"+subject.getSubName()+"课";
            tips.add(tip);
        }
        return tips;
    }

    public List<SubjectTimeServiceBean> getDateTip(String date)
    {
        List<SubjectTimeServiceBean> tips = new ArrayList<>();
        List<SubjectTime> subjectTimes = subjectTimeDao.getSubjectTimeByDate(date);
        for(SubjectTime st: subjectTimes)
        {
            Subject subject = subjectDao.getSubjectById(st.getSubId());
            SubjectTimeServiceBean bean = new SubjectTimeServiceBean();
            bean.setmSubjectName(subject.subName);
            bean.setmStart(st.startDate);
            bean.setmEnd(st.endDate);
            tips.add(bean);
        }
        return tips;
    }


}
