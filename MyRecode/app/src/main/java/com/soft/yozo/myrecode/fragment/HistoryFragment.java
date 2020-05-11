package com.soft.yozo.myrecode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.activity.AddFeeActivity;
import com.soft.yozo.myrecode.activity.AddSubjectActivity;
import com.soft.yozo.myrecode.bean.Schooling;
import com.soft.yozo.myrecode.bean.SchoolingServiceBean;
import com.soft.yozo.myrecode.bean.SubjectServiceBean;
import com.soft.yozo.myrecode.service.SchoolingService;
import com.soft.yozo.myrecode.view.Item4TextView;
import com.soft.yozo.utils.DateUtils;

import java.util.List;

/**
 *打卡页面
 */
public class HistoryFragment extends Fragment implements View.OnClickListener
{
    private View mView;
    private FloatingActionButton mbutton;
    private RecyclerView mRecyclerView;
    private SchoolingAdapter schoolingAdapter;

    private List<SchoolingServiceBean> schoolingServiceBeans;
    private SchoolingService schoolingService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_history,null);
        mRecyclerView = mView.findViewById(R.id.subject_rc);
        mbutton = mView.findViewById(R.id.history_add);
        mbutton.setOnClickListener(this);
        initdata();
        return mView;
    }

    private void initdata()
    {
        schoolingAdapter = new SchoolingAdapter();
        schoolingService = new SchoolingService();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter(schoolingAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        schoolingServiceBeans = schoolingService.getALLSchooling();
        schoolingAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            if(schoolingService != null)
            {
                schoolingServiceBeans = schoolingService.getALLSchooling();
                schoolingAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddFeeActivity.class);
        getActivity().startActivity(intent);
    }

    private class ViewHold extends RecyclerView.ViewHolder
    {
        Item4TextView mTextView;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_fragment_common_name);
        }
    }
    private class SchoolingAdapter extends RecyclerView.Adapter<HistoryFragment.ViewHold>
    {

        @NonNull
        @Override
        public HistoryFragment.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_subject_common,parent,false);
            return new HistoryFragment.ViewHold(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryFragment.ViewHold holder, int position)
        {
            SchoolingServiceBean schoolingServiceBean = schoolingServiceBeans.get(position);
            Schooling schooling = schoolingServiceBean.getSchooling();
            String date = schooling.getSdate();
            holder.mTextView.setTexts(new String[]{schoolingServiceBean.getSubName(),
                    schooling.scounts+"",schooling.sfee+"",
                    DateUtils.getCurrentDate(date) +""});
        }

        @Override
        public int getItemCount() {
            return schoolingServiceBeans.size();
        }
    }
}
