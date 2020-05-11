package com.soft.yozo.myrecode.fragment;

import android.content.Context;
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

import com.soft.yozo.mycalendar.view.CalendarView;
import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.activity.AddSubjectActivity;
import com.soft.yozo.myrecode.activity.AddTipActivity;
import com.soft.yozo.myrecode.bean.SubjectTimeServiceBean;
import com.soft.yozo.myrecode.service.SubjectTimeService;
import com.soft.yozo.myrecode.view.CurrentItem;
import com.soft.yozo.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *日程页面
 */
public class CurrentFragment extends Fragment implements CalendarView.CalendarItemClick, View.OnClickListener
{
    private Context mContext;
    private View mView;
    private CalendarView calendarView;
    private TextView mSelDate;
    private RecyclerView mRecyclerView;
    private TextView add;
    private SubjectTimeService subjectTimeService;
    private List<SubjectTimeServiceBean> subjects;
    private TipAdapter tipAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_current, null);
        mContext = getContext();
        initView();
        initDate();
        return mView;
    }

    private void initView()
    {
        calendarView = mView.findViewById(R.id.recode_calendar);
        calendarView.addItemClick(this);
        mSelDate = mView.findViewById(R.id.current_sel_date);
        mSelDate.setText(DateUtils.getCurrentDate(Calendar.getInstance()));
        mRecyclerView = mView.findViewById(R.id.current_tip_rc);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tipAdapter = new TipAdapter();
        mRecyclerView.setAdapter(tipAdapter);
        add = mView.findViewById(R.id.current_add);
        add.setOnClickListener(this);

    }

    private void initDate()
    {
        subjects = new ArrayList<>();
        subjectTimeService = new SubjectTimeService();
        subjects = subjectTimeService.getDateTip(DateUtils.getDate(Calendar.getInstance()));
        tipAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(Calendar calendar)
    {
        mSelDate.setText(DateUtils.getCurrentDate(calendar));
        subjects = subjectTimeService.getDateTip(DateUtils.getDate(calendar));
        tipAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(getActivity(), AddTipActivity.class);
        getActivity().startActivity(intent);
    }

    class  TipViewHold extends RecyclerView.ViewHolder
    {
        CurrentItem item;
        public TipViewHold(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_current_tip_item);
        }
    }

    class TipAdapter extends RecyclerView.Adapter<TipViewHold>
    {

        @NonNull
        @Override
        public TipViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_current_tip,null);
            return new TipViewHold(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TipViewHold holder, int position)
        {
            holder.item.setTime(subjects.get(position).getmStart(),subjects.get(position).getmEnd());
            holder.item.setContent(subjects.get(position).getmSubjectName());
        }

        @Override
        public int getItemCount()
        {
            return subjects.size();
        }
    }
}
