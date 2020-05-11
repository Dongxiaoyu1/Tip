package com.soft.yozo.myrecode.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CustomWeekView extends LinearLayout implements View.OnClickListener
{
    private final static  int ITEM_LIMIT = 5;
    private int itemWidth;
    private LinearLayout[] linearLayouts;
    private int[] res;
    private TextView[] textViews;
    private int[] tvRes;
    private View mView;
    private Context mContext;
    private boolean isFirstState;
    private boolean isAnimating;
    private float scaleSize = 1.2f;
    enum WEEKDAY{
        wk1,wk2,wk3,wk4,wk5,wk6,wk7
    }

    private String[] WEEKSTR= new String[]{"一","二","三","四","五","六","日"};
    private WEEKDAY centerNow;
    private Map<Integer, String> values;
    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9;
    private OnClickItemListener onClickItemListener;
    public CustomWeekView(Context context)
    {
        super(context);
        this.mContext = context;
        isFirstState = false;
        isAnimating = false;
        initView();
    }

    public CustomWeekView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        isFirstState = false;
        isAnimating = false;
        initView();
    }

    public CustomWeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        centerNow = WEEKDAY.wk4;
        isFirstState = false;
        isAnimating = false;
        initView();
    }

    private void initView()
    {
        values = DateUtils.getDayAndWeek(DateUtils.getWeeks());
        linearLayouts = new LinearLayout[9];
        res = new int[]{R.id.view_week_ll1,R.id.view_week_ll2,R.id.view_week_ll3,R.id.view_week_ll4
        ,R.id.view_week_ll5,R.id.view_week_ll6,R.id.view_week_ll7,R.id.view_week_ll8,R.id.view_week_ll9};
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.view_custom_week,this,true);
        for(int i=0;i<linearLayouts.length;i++)
        {
            linearLayouts[i] = mView.findViewById(res[i]);
            linearLayouts[i].setOnClickListener(this);
        }
        textViews = new TextView[9];
        tvRes = new int[]{R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine};
        for(int i=0;i<textViews.length;i++)
        {
            textViews[i] = mView.findViewById(tvRes[i]);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("TAG","width=="+getMeasuredWidth()+"height==="+getMeasuredHeight());
        if(itemWidth <=0)
        {
            itemWidth = getMeasuredWidth() / ITEM_LIMIT;
            for(int i =0; i<linearLayouts.length;i++)
            {
               LayoutParams layoutParams = (LayoutParams) linearLayouts[i].getLayoutParams();
               layoutParams.width = itemWidth;
               linearLayouts[i].setLayoutParams(layoutParams);
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        Log.d(TAG, "onLayout "+changed+"  l="+l+" t="+t+" r="+r+" b="+b);

        if(!isFirstState) {
            //设置今天的日期在中间
//            animalFinish = false;
            Calendar cal = Calendar.getInstance();
            int todayNum = cal.get(Calendar.DAY_OF_WEEK)-1;
            centerNow = getEnumByNum(todayNum);
            setCenter(centerNow);
//            animalFinish = true;
            isFirstState = true;
        }
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener)
    {
        this.onClickItemListener = onClickItemListener;
    }

    @Override
    public void onClick(View v) {

        if(isAnimating)
        {
            return;
        }
        int id = v.getId();
        for(int i=0;i<res.length;i++)
        {
            if(id == res[i])
            {
                setClickWitch(linearLayouts[i]);
                if(onClickItemListener != null)
                {
                    int day = (int) v.getTag();
                    onClickItemListener.onItemClick(DateUtils.getWeeks().get(day-1).getTime());
                }
                break;
            }

        }
    }

    /**1、找到正在展示的五个item，并将预备item复位*/
    private void setCenter(WEEKDAY weekDay){
        //记录当前显示在中间的星期X
        centerNow = weekDay;
        //1、找到当前显示的5个条目的位置
        List<LinearLayout> list = new ArrayList<>();
        list.add(linearLayouts[0]);
        list.add(linearLayouts[1]);
        list.add(linearLayouts[2]);
        list.add(linearLayouts[3]);
        list.add(linearLayouts[4]);
        list.add(linearLayouts[5]);
        list.add(linearLayouts[6]);
        list.add(linearLayouts[7]);
        list.add(linearLayouts[8]);
        for(int i = 0; i<5; i++){
            for(int j = 0; j<list.size(); j++){
                LinearLayout ll = list.get(j);
                if(ll.getX()==itemWidth*i){
                    list.remove(ll);      //找到之后就remove可以减少后面遍历的次数
//                    Log.d(TAG, "找到"+i+"了"+ll);
                    switch (i) {
                        case 0:
                            ll1 = ll;
                            break;
                        case 1:
                            ll2 = ll;
                            break;
                        case 2:
                            ll3 = ll;
                            //当前中间位置item放大
                            ll3.getChildAt(0).setScaleX(scaleSize);
                            ll3.getChildAt(0).setScaleY(scaleSize);
                            break;
                        case 3:
                            ll4 = ll;
                            break;
                        case 4:
                            ll5 = ll;
                            break;
                    }
                }
            }
        }
//        Log.i("TAG", "找完后还剩"+list.size()+"  总："+llList.size());
        //2、剩余的四个作为预备，归位，左边隐藏两个，右边隐藏两个
        for(int i = 0; i<list.size(); i++){
            LinearLayout ll = list.get(i);
            switch (i){
                case 0:   //左1
                    ll.setX(-itemWidth*2);
                    ll8=ll;
                    break;
                case 1:   //左2
                    ll.setX(-itemWidth*1);
                    ll9=ll;
                    break;
                case 2:   //右1
                    ll.setX(itemWidth*5);
                    ll6=ll;
                    break;
                case 3:   //右2
                    ll.setX(itemWidth*6);
                    ll7=ll;
                    break;
            }
        }
        //绑定数据
        reBoundDataByCenter(weekDay);
    }

    /**2、重新绑定数据*/
    private void reBoundDataByCenter(WEEKDAY weekDay){
        if(weekDay == WEEKDAY.wk1){
            /*星期1在中间，依次为4、5、6、7、1、2、3、4、5*/
            setLLText(ll8, 4, false);
            setLLText(ll9, 5, false);
            setLLText(ll1, 6, false);
            setLLText(ll2, 7, false);
            setLLText(ll3, 1, true);
            setLLText(ll4, 2, false);
            setLLText(ll5, 3, false);
            setLLText(ll6, 4, false);
            setLLText(ll7, 5, false);
        }else if(weekDay == WEEKDAY.wk2){
            /*星期2在中间，依次为5、6、7、1、2、3、4、5、6*/
            setLLText(ll8, 5, false);
            setLLText(ll9, 6, false);
            setLLText(ll1, 7, false);
            setLLText(ll2, 1, false);
            setLLText(ll3, 2, true);
            setLLText(ll4, 3, false);
            setLLText(ll5, 4, false);
            setLLText(ll6, 5, false);
            setLLText(ll7, 6, false);
        }else if(weekDay == WEEKDAY.wk3){
            /*星期3在中间，依次为6、7、1、2、3、4、5、6、7*/
            setLLText(ll8, 6, false);
            setLLText(ll9, 7, false);
            setLLText(ll1, 1, false);
            setLLText(ll2, 2, false);
            setLLText(ll3, 3, true);
            setLLText(ll4, 4, false);
            setLLText(ll5, 5, false);
            setLLText(ll6, 6, false);
            setLLText(ll7, 7, false);
        }else if(weekDay == WEEKDAY.wk4){
            /*星期4在中间，依次为7、1、2、3、4、5、6、7、1*/
            setLLText(ll8, 7, false);
            setLLText(ll9, 1, false);
            setLLText(ll1, 2, false);
            setLLText(ll2, 3, false);
            setLLText(ll3, 4, true);
            setLLText(ll4, 5, false);
            setLLText(ll5, 6, false);
            setLLText(ll6, 7, false);
            setLLText(ll7, 1, false);
        }else if(weekDay == WEEKDAY.wk5){
            /*星期5在中间，依次为1、2、3、4、5、6、7、1、2*/
            setLLText(ll8, 1, false);
            setLLText(ll9, 2, false);
            setLLText(ll1, 3, false);
            setLLText(ll2, 4, false);
            setLLText(ll3, 5, true);
            setLLText(ll4, 6, false);
            setLLText(ll5, 7, false);
            setLLText(ll6, 1, false);
            setLLText(ll7, 2, false);
        }else if(weekDay == WEEKDAY.wk6){
            /*星期6在中间，依次为2、3、4、5、6、7、1、2、3*/
            setLLText(ll8, 2, false);
            setLLText(ll9, 3, false);
            setLLText(ll1, 4, false);
            setLLText(ll2, 5, false);
            setLLText(ll3, 6, true);
            setLLText(ll4, 7, false);
            setLLText(ll5, 1, false);
            setLLText(ll6, 2, false);
            setLLText(ll7, 3, false);
        }else if(weekDay == WEEKDAY.wk7){
            /*星期7在中间，依次为3、4、5、6、7、1、2、3、4*/
            setLLText(ll8, 3, false);
            setLLText(ll9, 4, false);
            setLLText(ll1, 5, false);
            setLLText(ll2, 6, false);
            setLLText(ll3, 7, true);
            setLLText(ll4, 1, false);
            setLLText(ll5, 2, false);
            setLLText(ll6, 3, false);
            setLLText(ll7, 4, false);
        }
    }

    private void setLLText(LinearLayout ll, int witchDay, boolean showDate){
        ll.setTag(witchDay);   //便于区分点击事件
        LinearLayout innerLL = (LinearLayout)ll.getChildAt(0);
        if(witchDay == 1)
        {
            innerLL.setBackgroundResource(R.drawable.view_day1_backgroud);
        }else if(witchDay == 2)
        {
            innerLL.setBackgroundResource(R.drawable.view_day2_backgroud);
        }else if(witchDay == 3)
        {
            innerLL.setBackgroundResource(R.drawable.view_day3_backgroud);
        }else if(witchDay == 4)
        {
            innerLL.setBackgroundResource(R.drawable.view_day4_backgroud);
        }else if(witchDay == 5)
        {
            innerLL.setBackgroundResource(R.drawable.view_day5_backgroud);
        }else if(witchDay == 6)
        {
            innerLL.setBackgroundResource(R.drawable.view_day6_backgroud);
        }else if(witchDay == 7)
        {
            innerLL.setBackgroundResource(R.drawable.view_day7_backgroud);
        }
        TextView tv = (TextView)innerLL.getChildAt(0);
        String text = "星期" + WEEKSTR[witchDay-1];
        tv.setText(text);

        TextView tvDate = (TextView)innerLL.getChildAt(1);
        text = values.get(witchDay);
        tvDate.setText(text);
        if(showDate){
            tvDate.setVisibility(View.VISIBLE);
        }else{
            tvDate.setVisibility(View.GONE);
        }
    }

    private void setClickWitch(LinearLayout ll)
    {

        startAnimation(ll);


    }

    private void startAnimation(LinearLayout ll)
    {
        final int day = (int) ll.getTag();
        int offset = getXOffset(day);

        ObjectAnimator[] objectAnimators = new ObjectAnimator[13];
        for(int i=0;i<linearLayouts.length;i++)
        {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayouts[i],"X",linearLayouts[i].getX(),linearLayouts[i].getX()+offset);
            objectAnimators[i] = objectAnimator;
        }


        ObjectAnimator anim100 = ObjectAnimator.ofFloat(ll3.getChildAt(0), "scaleX", ll3.getChildAt(0).getScaleX(), 1.0f);
        ObjectAnimator anim101 = ObjectAnimator.ofFloat(ll3.getChildAt(0), "scaleY", ll3.getChildAt(0).getScaleY(), 1.0f);
        //被点击的需要放大
        ObjectAnimator anim102 = ObjectAnimator.ofFloat(ll.getChildAt(0), "scaleX", 1, scaleSize);
        ObjectAnimator anim103 = ObjectAnimator.ofFloat(ll.getChildAt(0), "scaleY", 1, scaleSize);

        objectAnimators[9] = anim100;
        objectAnimators[10] = anim101;
        objectAnimators[11] = anim102;
        objectAnimators[12] = anim103;
                //透明度动画
        ObjectAnimator anim104 = ObjectAnimator.ofFloat(ll.getChildAt(0), "scaleY", 1, scaleSize);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(objectAnimators);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setCenter(getEnumByNum(day));
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();

    }

    private WEEKDAY getEnumByNum(int num){
        switch (num){
            case 1:
                return WEEKDAY.wk1;
            case 2:
                return WEEKDAY.wk2;
            case 3:
                return WEEKDAY.wk3;
            case 4:
                return WEEKDAY.wk4;
            case 5:
                return WEEKDAY.wk5;
            case 6:
                return WEEKDAY.wk6;
            case 7:
                return WEEKDAY.wk7;
        }
        return WEEKDAY.wk1;

    }

    private int getXOffset(int day)
    {
        if(centerNow == WEEKDAY.wk1)
        {
            if(day == 6)
            {
                return itemWidth*2;
            }else if(day == 7)
            {
                return itemWidth*1;
            }else if(day == 2)
            {
                return itemWidth* -1;
            }else if(day == 3)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk2)
        {
            if(day == 7)
            {
                return itemWidth*2;
            }else if(day == 1)
            {
                return itemWidth*1;
            }else if(day == 3)
            {
                return itemWidth* -1;
            }else if(day == 4)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk3)
        {
            if(day == 1)
            {
                return itemWidth*2;
            }else if(day == 2)
            {
                return itemWidth*1;
            }else if(day == 4)
            {
                return itemWidth* -1;
            }else if(day == 5)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk4)
        {
            if(day == 2)
            {
                return itemWidth*2;
            }else if(day == 3)
            {
                return itemWidth*1;
            }else if(day == 5)
            {
                return itemWidth* -1;
            }else if(day == 6)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk5)
        {
            if(day == 3)
            {
                return itemWidth*2;
            }else if(day == 4)
            {
                return itemWidth*1;
            }else if(day == 6)
            {
                return itemWidth* -1;
            }else if(day == 7)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk6)
        {
            if(day == 4)
            {
                return itemWidth*2;
            }else if(day == 5)
            {
                return itemWidth*1;
            }else if(day == 7)
            {
                return itemWidth* -1;
            }else if(day == 1)
            {
                return itemWidth* -2;
            }
        }
        if(centerNow == WEEKDAY.wk7)
        {
            if(day == 5)
            {
                return itemWidth*2;
            }else if(day == 6)
            {
                return itemWidth*1;
            }else if(day == 1)
            {
                return itemWidth* -1;
            }else if(day == 2)
            {
                return itemWidth* -2;
            }
        }
        return 0;
    }

    public interface  OnClickItemListener{
        void onItemClick(long time);
    }

}
