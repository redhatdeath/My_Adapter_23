package ru.shanin.myadapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAdapter();
        initRecyclerView();
        initFABView();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initFABView() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            //TODO Make method to action on click on fab
            for (int i = 0; i < 5; i++) AppStart.addNewPeople();
            for (People p : AppStart.localData) showLog(p.toString());
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                MyAdapter.VIEW_TYPE_PEOPLE_ID_0, MyAdapter.MAX_POOL_SIZE);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                MyAdapter.VIEW_TYPE_PEOPLE_ID_1, MyAdapter.MAX_POOL_SIZE);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                MyAdapter.VIEW_TYPE_PEOPLE_ID_2, MyAdapter.MAX_POOL_SIZE);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(
                MyAdapter.VIEW_TYPE_PEOPLE_ID_3, MyAdapter.MAX_POOL_SIZE);
    }

    private void initAdapter() {
        adapter = new MyAdapter(new MyDiffCallback());
        adapter.submitList(AppStart.localData);
    }

    private void showLog(String string) {
        Log.d(this.getClass().getSimpleName(), string);
    }

}