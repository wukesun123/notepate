package com.example.tetris.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.tetris.R;
import com.example.tetris.adapter.MyAdapter;
import com.example.tetris.dao.DiaryDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.entity.Diary;

import java.util.List;

public class DiaryFragment extends Fragment {
    private View view;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diary,container,false);
        listView = view.findViewById(R.id.listview_diary);
        AppDatabase db = Room.databaseBuilder( requireContext(), AppDatabase.class, "mydb" )
                .allowMainThreadQueries()
                .build();
        DiaryDao diaryDao = db.diaryDao();
        Intent it = getActivity().getIntent();
        List<Diary> datalist = diaryDao.findDiarys(it.getIntExtra("uid",1));
        MyAdapter adapter = new MyAdapter(getActivity(),R.layout.list_item,datalist);
        listView.setAdapter(adapter);

        return view;
    }
}