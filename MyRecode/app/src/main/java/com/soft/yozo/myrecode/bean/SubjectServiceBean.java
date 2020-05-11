package com.soft.yozo.myrecode.bean;

public class SubjectServiceBean
{
    private String mSubjectName;
    private int mTotalCounts;
    private int mUseCounts;
    private int mRestCounts;
    private int mTotalFee;

    public String getmSubjectName() {
        return mSubjectName;
    }

    public void setmSubjectName(String mSubjectName) {
        this.mSubjectName = mSubjectName;
    }

    public int getmTotalCounts() {
        return mTotalCounts;
    }

    public void setmTotalCounts(int mTotalCounts) {
        this.mTotalCounts = mTotalCounts;
    }

    public int getmUseCounts() {
        return mUseCounts;
    }

    public void setmUseCounts(int mUseCounts) {
        this.mUseCounts = mUseCounts;
    }

    public int getmRestCounts() {
        return mTotalCounts - mUseCounts;
    }

    public void setmRestCounts(int mRestCounts) {
        this.mRestCounts = mRestCounts;
    }

    public int getmTotalFee() {
        return mTotalFee;
    }

    public void setmTotalFee(int mTotalFee) {
        this.mTotalFee = mTotalFee;
    }
}
