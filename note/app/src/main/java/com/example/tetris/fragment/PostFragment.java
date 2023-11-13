package com.example.tetris.fragment;
import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.tetris.R;
import com.example.tetris.dao.DiaryDao;
import com.example.tetris.dao.ListcardDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.entity.Diary;
import com.example.tetris.entity.Listcard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostFragment extends Fragment {
    private View view;
    private TextView time;
    private EditText content,title;
    private Handler handler;
    private Runnable runnable;
    private Button save,image;
    private AppDatabase db;
    private DiaryDao diaryDao;
    private ListcardDao listcardDao;
    private Diary diary;
    private Listcard list;
    private Intent it;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_post,container,false);
        inint();

        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000); // 每隔1秒更新一次时间
            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSelectionDialog();

            }
        });
        // 通过按钮点击事件启动相册应用
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        // 处理相册应用返回的结果
        content.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    SpannableStringBuilder builder = (SpannableStringBuilder) content.getText();
                    ImageSpan[] imageSpans = builder.getSpans(0, builder.length(), ImageSpan.class);
                    int cursorPosition = content.getSelectionStart();

                    for (ImageSpan span : imageSpans) {
                        int start = builder.getSpanStart(span);
                        int end = builder.getSpanEnd(span);

                        if (cursorPosition >= start && cursorPosition <= end) {
                            // 删除对应的ImageSpan
                            builder.replace(start, end, "");
                            builder.removeSpan(span);
                            content.setText(builder);
                            content.setSelection(start); // 恢复光标位置
                            return true;
                        }
                    }
                }
                return false;
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            // 使用选中的图片URI进行处理，显示在ImageView
            try {
                // 将URI转换为Bitmap对象
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);

                // 创建一个可编辑的SpannableStringBuilder对象，并将图片插入其中
                SpannableStringBuilder builder = new SpannableStringBuilder();

// 压缩图片
                Bitmap compressedBitmap = compressImage(bitmap);

// 调整图片大小
                int desiredWidth = 200; // 设置你想要的图片宽度
                int desiredHeight = 200; // 设置你想要的图片高度
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(compressedBitmap, desiredWidth, desiredHeight, false);

// 将调整大小后的Bitmap对象转换为Drawable对象
                Drawable drawable = new BitmapDrawable(getResources(), scaledBitmap);

// 创建ImageSpan对象，并将Drawable设置为可删除
                ImageSpan imageSpan = new ImageSpan(drawable);

// 在可编辑的SpannableStringBuilder对象中插入ImageSpan
                builder.append(" ");
                builder.setSpan(imageSpan, builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                // 获取EditText的当前光标位置
                int selectionStart = content.getSelectionStart();

                // 在当前光标位置插入图片
                Editable editable = content.getText();
                editable.insert(selectionStart,builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);  // 压缩质量为80%
        byte[] compressedData = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);
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

    private void updateTime() {
        // 获取当前时间并格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = dateFormat.format(new Date());

        // 在TextView中显示当前时间
        time.setText(currentTime);
    }
    private void showSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select, null);
        builder.setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理用户选择的逻辑
                        RadioButton selectedOption = dialogView.findViewById(R.id.radio_option1);
                        Log.d("check","1");
                        if (selectedOption.isChecked()&&content.getText()!=null) {
                            Log.d("check","2");
////                             用户选择了选项1
                            int uid = it.getIntExtra("uid",1);
                            Log.d("check","4");
                            diary.uid = uid;
                            diary.title = title.getText().toString();
                            diary.content = content.getText().toString();
                            diary.time = time.getText().toString();
                            Log.d("check","5");
                            diaryDao.insert(diary);
                            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                            title.setText("");
                            content.setText("");
//                            Log.d("ddd","2");
                        } else if (content.getText()==null){
                            dialog.dismiss();
                            Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                        } else {
                            // 用户选择了选项2（或其他选项）
                            int uid = it.getIntExtra("uid",1);
                            list.uid = uid;
                            list.title = title.getText().toString();
                            list.content = content.getText().toString();
                            list.time = time.getText().toString();
                            listcardDao.insert(list);
                            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                            title.setText("");
                            content.setText("");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }










    public void inint(){
        time = view.findViewById(R.id.time);
        content = view.findViewById(R.id.context);
        title = view.findViewById(R.id.diary_title);
        handler = new Handler();
        save = view.findViewById(R.id.save);
        db = Room.databaseBuilder( requireContext(), AppDatabase.class, "mydb" )
                .allowMainThreadQueries()
                .build();
//        Log.d("flag", "inint: ");
        diaryDao = db.diaryDao();
        listcardDao = db.listcardDao();
        it = getActivity().getIntent();
        diary = new Diary();
        list = new Listcard();
        image = view.findViewById(R.id.image);
//        Log.d("")
    }
}