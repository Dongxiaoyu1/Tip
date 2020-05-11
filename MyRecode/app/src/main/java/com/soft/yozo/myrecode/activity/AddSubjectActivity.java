package com.soft.yozo.myrecode.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.soft.yozo.myrecode.R;
import com.soft.yozo.myrecode.bean.Subject;
import com.soft.yozo.myrecode.dao.SubjectDao;

import java.util.List;

public class AddSubjectActivity extends BaseActivity
{
    private EditText mEditText;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        initView();
    }

    private void initView()
    {
        mEditText = findViewById(R.id.subject_et);
        mButton = findViewById(R.id.subject_add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditText.getText().toString().trim();
                if(name.length() <= 0)
                {
                    Toast.makeText(AddSubjectActivity.this,"课程名不能为空",Toast.LENGTH_SHORT).show();
                }else
                {
                    Subject subject = new Subject();
                    subject.setSubName(name);;
                    SubjectDao dao = new SubjectDao();
                    dao.addSubject(subject);
                    Toast.makeText(AddSubjectActivity.this,"添加成功!",Toast.LENGTH_SHORT).show();
                    AddSubjectActivity.this.finish();
                }
            }
        });

    }
}
