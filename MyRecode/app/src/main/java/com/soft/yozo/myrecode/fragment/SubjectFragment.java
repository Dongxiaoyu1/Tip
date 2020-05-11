package com.soft.yozo.myrecode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.activity.AddSubjectActivity;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.bean.SubjectServiceBean;
import com.soft.yozo.myrecode.dao.SubjectDao;
import com.soft.yozo.myrecode.service.SubjectService;
import com.soft.yozo.myrecode.view.Item4TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *打卡页面
 */
public class SubjectFragment extends Fragment
{
    private View mView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private List<SubjectServiceBean> mSubjects;
    private SubjectAdapter mSubjectAdapter;
    private SubjectService subjectService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_subject, null);
        initView();
        initData();
        return mView;
    }

    private void initView()
    {
        mFloatingActionButton = mView.findViewById(R.id.subject_add);
        mRecyclerView = mView.findViewById(R.id.subject_rc);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSubjectAdapter = new SubjectAdapter();
        mRecyclerView.setAdapter(mSubjectAdapter);

        subjectService = new SubjectService();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSubjectActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubjects = subjectService.getAllSubjects();
        mSubjectAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && subjectService != null)
        {
            mSubjects = subjectService.getAllSubjects();
            mSubjectAdapter.notifyDataSetChanged();
        }
    }

    private void initData()
    {
        mSubjects = new ArrayList<>();

    }

    private class ViewHold extends RecyclerView.ViewHolder
    {
        Item4TextView mTextView;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_fragment_common_name);
        }
    }
    private class SubjectAdapter extends RecyclerView.Adapter<ViewHold>
    {

        @NonNull
        @Override
        public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_subject_common,parent,false);
            return new ViewHold(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHold holder, int position)
        {
            SubjectServiceBean subjectServiceBean = mSubjects.get(position);
            holder.mTextView.setTexts(new String[]{subjectServiceBean.getmSubjectName(),
                    subjectServiceBean.getmTotalCounts()+"",subjectServiceBean.getmUseCounts()+"",
            subjectServiceBean.getmRestCounts()+""});
        }

        @Override
        public int getItemCount() {
            return mSubjects.size();
        }
    }
}
