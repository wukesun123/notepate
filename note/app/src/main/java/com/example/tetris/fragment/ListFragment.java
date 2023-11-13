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
import com.example.tetris.adapter.MyAdapter1;
import com.example.tetris.dao.ListcardDao;
import com.example.tetris.db.AppDatabase;
import com.example.tetris.entity.Listcard;

import java.util.List;

public class ListFragment extends Fragment {
    private View view;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list,container,false);

        listView = view.findViewById(R.id.listview_list);
        AppDatabase db = Room.databaseBuilder( requireContext(), AppDatabase.class, "mydb" )
                .allowMainThreadQueries()
                .build();
        ListcardDao listcardDao = db.listcardDao();
        Intent it = getActivity().getIntent();
        List<Listcard> datalist = listcardDao.findListcards(it.getIntExtra("uid",1));
        MyAdapter1 adapter = new MyAdapter1(getActivity(),R.layout.list_item,datalist);
        listView.setAdapter(adapter);

        return view;
    }
}