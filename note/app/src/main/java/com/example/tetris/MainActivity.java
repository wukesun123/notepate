package com.example.tetris;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tetris.fragment.DiaryFragment;
import com.example.tetris.fragment.ListFragment;
import com.example.tetris.fragment.PostFragment;
import com.example.tetris.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioButton rb_diary,rb_post,rb_list,rb_user;
    private RadioGroup rg_group;
    private List<Fragment> fragments;
    private int position=0;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        actionBar.hide(); //隐藏ActionBar

        rb_diary=findViewById(R.id.rb_diary);
        rb_list=findViewById(R.id.rb_list);
        rb_post=findViewById(R.id.rb_post);
        rb_user=findViewById(R.id.rb_user);
        rg_group=findViewById(R.id.rg_group);

        //默认选中第一个
        rb_diary.setSelected(true);


        rg_group.setOnCheckedChangeListener(this);

        //初始化fragment
        initFragment();

        //默认布局，选第一个
        defaultFragment();
    }

    private void defaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout,fragments.get(0));
        transaction.commit();
    }

    private void setSelected() {
        rb_diary.setSelected(false);
        rb_list.setSelected(false);
        rb_post.setSelected(false);
        rb_user.setSelected(false);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(0,new DiaryFragment());
        fragments.add(1,new ListFragment());
        fragments.add(2,new PostFragment());
        fragments.add(3,new UserFragment());


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        //获取fragment管理类对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        //拿到fragmentManager的触发器
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (i){
            case R.id.rb_diary:
                position=0;
                //调用replace方法，将fragment,替换到fragment_layout这个id所在UI，或者这个控件上面来
                //这是创建replace这个事件，如果想要这个事件执行，需要把这个事件提交给触发器
                //用commit()方法
                transaction.replace(R.id.fragment_layout,fragments.get(0));
                //将所有导航栏设成默认色
                setSelected();
                rb_diary.setSelected(true);
                break;
            case R.id.rb_list:
                position=1;
                transaction.replace(R.id.fragment_layout,fragments.get(1));
                //将所有导航栏设成默认色
                setSelected();
                rb_list.setSelected(true);
                break;
            case R.id.rb_post:
                position=2;
                transaction.replace(R.id.fragment_layout,fragments.get(2));
                //将所有导航栏设成默认色
                setSelected();
                rb_post.setSelected(true);
                break;
            case R.id.rb_user:
                position=3;
                transaction.replace(R.id.fragment_layout,fragments.get(3));
                //将所有导航栏设成默认色
                setSelected();
                rb_user.setSelected(true);
                break;
        }
        //事件的提交
        transaction.commit();
    }
}
