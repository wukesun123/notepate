package com.example.tetris.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import com.example.tetris.R;
import com.example.tetris.dao.DiaryDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.entity.Diary;
import com.example.tetris.eidt.EditActivity_diary;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Diary> {
    private Context mContext;
    private int mResource;
    private List<Diary> mDiaries;

    public MyAdapter(Context context, int resource, List<Diary> diaries) {
        super(context, resource, diaries);
        mContext = context;
        mResource = resource;
        mDiaries = diaries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Diary currentDiary = mDiaries.get(position);

        TextView textViewTitle = listItemView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(currentDiary.title);

        TextView textViewTime = listItemView.findViewById(R.id.textViewTime);
        textViewTime.setText(currentDiary.time);

        TextView textViewContent = listItemView.findViewById(R.id.textViewContent);
        textViewContent.setText(currentDiary.content);

        Button buttonEdit = listItemView.findViewById(R.id.buttonEdit);
        // 设置编辑按钮的点击事件
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个跳转到编辑页面的Intent对象
                Intent intent = new Intent(mContext, EditActivity_diary.class);

                // 将需要编辑的数据传递给编辑页面
                Diary currentDiary = mDiaries.get(position);
                intent.putExtra("time", currentDiary.time);

                // 使用startActivity方法启动编辑页面
                mContext.startActivity(intent);
            }
        });


        Button buttonDelete = listItemView.findViewById(R.id.buttonDelete);
        // 设置删除按钮的点击事件
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("确认删除")
                        .setMessage("确定要删除这条数据吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "mydb" )
                                        .allowMainThreadQueries()
                                        .build();
                                DiaryDao diaryDao = db.diaryDao();
                                diaryDao.delete(diaryDao.findDiary(currentDiary.time));
                                mDiaries.remove(position); // 从数据集中移除对应位置的数据
                                notifyDataSetChanged(); // 通知适配器数据变化
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        return listItemView;
    }
}

