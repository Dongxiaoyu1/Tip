package com.soft.yozo.myrecode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.fragment.SubjectFragment;
import com.soft.yozo.myrecode.view.MyRelativeLayout;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    private ArrayList<String> subjects;
    private Context mContext;

    public MyAdapter(Context context, ArrayList<String> data)
    {
        mContext = context;
        subjects = data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_subject,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return subjects.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {
        public MyRelativeLayout root;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
