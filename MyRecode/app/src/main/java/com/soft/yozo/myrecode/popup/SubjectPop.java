package com.soft.yozo.myrecode.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.bean.Subject;

import java.util.List;

public class SubjectPop extends BasepopupWindows
{
    private View mView;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;

    private List<Subject> mSubjects;
    private SubjectCallback subjectCallback;

    public SubjectPop(Context context , List<Subject> subjects)
    {
        super(context);
        this.mSubjects = subjects;
    }

    @Override
    void setContentView()
    {
        mView = LayoutInflater.from(mContext).inflate(R.layout.popup_subject,null);
        mContainer.addView(mView);
        mRecyclerView = mView.findViewById(R.id.popup_subject_cy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),linearLayoutManager.getOrientation()));
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);

    }

    public void setSubjectCallback(SubjectCallback callback)
    {
        this.subjectCallback = callback;
    }

    @Override
    boolean isShowTitle()
    {
        return false;
    }

    @Override
    boolean isShowBottom()
    {
        return false;
    }

    @Override
    String getTitle()
    {
        return null;
    }

    class ViewHold extends RecyclerView.ViewHolder
    {
        public TextView item;
        public View view;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_popup_name);
            view = itemView.findViewById(R.id.item_popup_ll);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ViewHold> implements View.OnClickListener
    {
        @NonNull
        @Override
        public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup_subject, parent, false);
            item.findViewById(R.id.item_popup_ll).setOnClickListener(this);
            return new ViewHold(item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHold holder, int position)
        {
            holder.item.setText(mSubjects.get(position).subName);
            holder.view.setTag(position);
            holder.view.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return mSubjects.size();
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();

            subjectCallback.onSubjectCallback(mSubjects.get(position));
        }
    }

    public interface SubjectCallback
    {
        void onSubjectCallback(Subject subject);
    }
}
