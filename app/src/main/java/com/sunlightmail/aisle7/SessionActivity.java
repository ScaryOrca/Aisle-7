package com.sunlightmail.aisle7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListDate>  listDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.datesRecyclerView);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                lm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(lm);

        listDates = new ArrayList<>();

        // Dummy Data
        for(int i = 0; i < 30; i++) {
            ListDate listDate = new ListDate("MON", "12:00AM - 12:00PM", "September " + (i + 1));
            listDates.add(listDate);
        }

        adapter = new DateAdapter(listDates, this);
        recyclerView.setAdapter(adapter);
    }
}
