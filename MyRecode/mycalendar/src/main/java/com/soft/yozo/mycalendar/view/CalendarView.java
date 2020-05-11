package com.soft.yozo.mycalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.yozo.mycalendar.R;
import com.soft.yozo.mycalendar.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarView extends LinearLayout implements View.OnClickListener
{
    private RelativeLayout rootView;
    private Context mContext;
    private RecyclerView recyclerView;
    private ArrayList<Integer> days;
    private DateAdapter dateAdapter;
    private Calendar curCalendar;
    private ImageView mLast;
    private ImageView mNext;
    private TextView mMonth;

    private int mShowMonth;
    private int mShowYear;
    private Calendar selectDate;

    private CalendarItemClick mCalendarItemClick;

    public CalendarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context,attrs);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs)
    {
        mContext = context;
        rootView = (RelativeLayout) View.inflate(mContext, R.layout.calendar_layout,null);
        //rootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.calendar_layout,null);
        addView(rootView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        recyclerView = rootView.findViewById(R.id.calendar_date);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        curCalendar = Calendar.getInstance();
        selectDate = Calendar.getInstance();
        days = DateUtils.getDays(curCalendar.get(Calendar.YEAR),curCalendar.get(Calendar.MONTH));
        mShowMonth = (curCalendar.get(Calendar.MONTH) );
        mShowYear = curCalendar.get(Calendar.YEAR);
        dateAdapter = new DateAdapter();
        recyclerView.setAdapter(dateAdapter);

        mLast = rootView.findViewById(R.id.calendar_last);
        mNext = rootView.findViewById(R.id.calendar_next);
        mMonth = rootView.findViewById(R.id.calendar_month);
        mMonth.setText((mShowMonth+ 1 )+ "月");

        mLast.setOnClickListener(this);
        mNext.setOnClickListener(this);

    }

    public void addItemClick(CalendarItemClick calendarItemClick)
    {
        this.mCalendarItemClick = calendarItemClick;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.calendar_next)
        {
            //下一个月
            if(mShowMonth == 11)
            {
                mShowYear +=1;
                mShowMonth = 0;
            }else
            {
                mShowMonth +=1;
            }
            mMonth.setText((mShowMonth+ 1 )+ "月");
            days = DateUtils.getDays(mShowYear,mShowMonth);
            dateAdapter.notifyDataSetChanged();

        }else if(id == R.id.calendar_last)
        {
            //上一个月
            //下一个月
            if(mShowMonth == 0)
            {
                mShowYear -=1;
                mShowMonth = 11;
            }else
            {
                mShowMonth -=1;
            }
            mMonth.setText((mShowMonth+ 1 )+ "月");
            days = DateUtils.getDays(mShowYear,mShowMonth);
            dateAdapter.notifyDataSetChanged();
        }
    }

    class DateHoldView extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public LinearLayout itemRoot;
        public DateHoldView(@NonNull View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.item_day);
            itemRoot = itemView.findViewById(R.id.item_parent);
        }
    }

    class DateAdapter extends RecyclerView.Adapter<DateHoldView>
    {

        @NonNull
        @Override
        public DateHoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_day_layout, null);
            return new DateHoldView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final DateHoldView holder, final int position)
        {
            int week = days.get(0);
            if(position+1 >= week)
            {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,mShowYear);
                calendar.set(Calendar.MONTH, mShowMonth);
                calendar.set(Calendar.DAY_OF_MONTH,days.get(position-week+2));
                if(calendar.get(Calendar.YEAR) == curCalendar.get(Calendar.YEAR)
                   && calendar.get(Calendar.MONTH) == curCalendar.get(Calendar.MONTH)
                   && calendar.get(Calendar.DATE) == curCalendar.get(Calendar.DATE))
                {
                    holder.itemRoot.setBackgroundResource(R.drawable.item_cur_date_background);
                }else if(calendar.get(Calendar.YEAR) == selectDate.get(Calendar.YEAR)
                        && calendar.get(Calendar.MONTH) == selectDate.get(Calendar.MONTH)
                        && calendar.get(Calendar.DATE) == selectDate.get(Calendar.DATE))
                {
                    holder.itemRoot.setBackgroundResource(R.drawable.item_select_date_background);
                }
                else
                {
                    holder.itemRoot.setBackgroundResource(R.drawable.item_date_background);
                }
                holder.textView.setText(String.valueOf(days.get(position-week+2)));

                holder.itemRoot.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDate.set(Calendar.YEAR,mShowYear);
                        selectDate.set(Calendar.MONTH, mShowMonth);
                        selectDate.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DATE));
                        if(mCalendarItemClick != null)
                        {
                            mCalendarItemClick.onItemClick(calendar);
                        }
                        DateAdapter.this.notifyDataSetChanged();
                    }
                });
            }else
            {
                holder.textView.setText("");
                holder.itemRoot.setOnClickListener(null);
                holder.itemRoot.setBackgroundResource(R.drawable.item_date_background);
            }

        }

        @Override
        public int getItemCount() {
            return days.size() + days.get(0) - 2;
        }
    }

    public interface CalendarItemClick
    {
        void onItemClick(Calendar calendar);
    }
}
