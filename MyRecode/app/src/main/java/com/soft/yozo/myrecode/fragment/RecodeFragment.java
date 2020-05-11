package com.soft.yozo.myrecode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.bean.Recode;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.dao.RecodeDao;
import com.soft.yozo.myrecode.dao.SubjectDao;
import com.soft.yozo.myrecode.service.SubjectTimeService;
import com.soft.yozo.myrecode.view.CustomWeekView;
import com.soft.yozo.myrecode.view.HorWheelView;
import com.soft.yozo.myrecode.view.listener.OnItemListener;
import com.soft.yozo.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *打卡页面
 */
public class RecodeFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private View mView;
    private RecyclerView mRecyclerView;
    private List<Subject> mSubjects;
    private MyAdapter mMyAdapter;
    private SubjectDao subjectDao;
    private RecodeDao mRecodeDao;
    private TextView mTextView;
    private List<Subject> mSelecteds;
    private HorWheelView customWeekView;
    private long selectTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_recode,null);
        initView();
        return mView;
    }

    private void initView()
    {
        customWeekView = mView.findViewById(R.id.recode_customweekview);
        subjectDao = new SubjectDao();
        mRecodeDao = new RecodeDao();
        mSubjects = subjectDao.getAllSubject();
        mRecyclerView = mView.findViewById(R.id.fragment_recode_rc);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mTextView = mView.findViewById(R.id.fragment_recode_tv);
        mTextView.setOnClickListener(this);

        mMyAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mMyAdapter);
        mSelecteds = new ArrayList<>();

        selectTime = Calendar.getInstance().getTime().getTime();

        customWeekView.setmOnItemListener(new OnItemListener() {
            @Override
            public void onItemSelectedListener(long position) {
                selectTime = position;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubjects = subjectDao.getAllSubject();
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v)
    {
        for(Subject s : mSelecteds)
            {
                Recode recode = new Recode();
                recode.setSid(0);
                recode.setSubId(s._ID);
                recode.setsDate(selectTime);
                mRecodeDao.addRecode(recode);
            }
        Toast.makeText(getContext(),"打卡成功！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        int position = (int) buttonView.getTag();
        if(isChecked)
        {
            boolean hasSubject= false;
            for(Subject s: mSelecteds)
            {
                if(s == mSubjects.get(position))
                {
                    hasSubject = true;
                    break;
                }
            }
            if(!hasSubject)
            {
                mSelecteds.add(mSubjects.get(position));
            }
        }else
        {
            mSelecteds.remove(mSubjects.get(position));
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.item_subject_cb);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {
        private RecodeFragment mFragment;
        public MyAdapter(RecodeFragment fragment)
        {
            this.mFragment = fragment;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mCheckBox.setText(mSubjects.get(position).getSubName());
            holder.mCheckBox.setTag(position);
            holder.mCheckBox.setOnCheckedChangeListener(mFragment);
        }

        @Override
        public int getItemCount() {
            return mSubjects.size();
        }
    }
}
