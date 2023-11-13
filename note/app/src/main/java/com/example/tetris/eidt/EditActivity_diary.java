package com.example.tetris.eidt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.tetris.MainActivity;
import com.example.tetris.R;
import com.example.tetris.dao.DiaryDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.entity.Diary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity_diary extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private TextView textViewTime;
    private Button button;
    private EditText title,context;
    private AppDatabase db;
    private DiaryDao diaryDao;
    private Intent intent;
    private Diary diary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_layout);
        inint();

        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000); // 每隔1秒更新一次时间
            }
        };
        // 其他逻辑操作
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getText()!=null){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentTime = dateFormat.format(new Date());
                    diary.content=context.getText().toString();
                    diary.title = title.getText().toString();
                    diaryDao.update(diary);
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(), "内容为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void updateTime() {
        // 获取当前时间并格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(new Date());

        // 在TextView中显示当前时间
        textViewTime.setText(currentTime);
    }
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
    public void inint(){
        textViewTime = findViewById(R.id.textViewTime);
        handler = new Handler();
        button = findViewById(R.id.buttonSave);
        title = findViewById(R.id.editTextTitle);
        context = findViewById(R.id.edit_context);
        db = Room.databaseBuilder( this, AppDatabase.class, "mydb" )
                .allowMainThreadQueries()
                .build();
        diaryDao = db.diaryDao();
        intent = getIntent();
        diary = diaryDao.findDiary(intent.getStringExtra("time"));
//        Toast.makeText(getApplicationContext(), diary.content, Toast.LENGTH_SHORT).show();
        title.setText(diary.title);
        context.setText(diary.content);
    }
}
