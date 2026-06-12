package com.example.skillsaga.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.model.HistoryModel;
import com.example.skillsaga.R;
import com.example.skillsaga.adapter.HistoryAdapter;
import com.example.skillsaga.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private List<HistoryModel> historyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistory = view.findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        historyList = new ArrayList<>();

        loadHistoryData();
    }

    private void loadHistoryData() {
        Cursor cursor = dbHelper.getAllHistory();
        historyList.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));
                String level = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVEL));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE));
                int xp = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_XP));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

                historyList.add(new HistoryModel(category, level, score, xp, date));
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);
    }
}