package com.example.tetris.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.tetris.MainActivity;
import com.example.tetris.R;
import com.example.tetris.dao.UserDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.login.LoginActivity;

public class UserFragment extends Fragment {
    private View view;
    private TextView tv_username;
    private TextView tv_fxid;
    private Intent it;
    private RelativeLayout rl;
    private RelativeLayout r2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_user,container,false);
            inint();

            String username = it.getStringExtra("Username");

            tv_username.setText("用户名:"+username);
            tv_fxid.setText("账号："+username);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("关于我的")  // 弹窗标题
                            .setMessage("这是我的课设")  // 弹窗内容
                            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击关闭按钮的逻辑
                                    dialog.dismiss();  // 关闭弹窗
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });
            r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 在按钮点击事件中处理返回逻辑
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
    public void inint(){
        it = getActivity().getIntent();
        tv_username = view.findViewById(R.id.tv_name);
        tv_fxid = view.findViewById(R.id.tv_fxid);
        rl = view.findViewById(R.id.re_aboutwe);
        r2 = view.findViewById(R.id.tuichu);
    }
}